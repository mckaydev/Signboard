package com.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.CropLoc;
import com.project.member.Member;
import com.project.member.service.MemberService;
import com.project.service.ImageService;
import com.project.service.NaverGeocoding;
import com.project.service.NaverRvrsGeocoding;
import com.project.service.NaverSearch;
import com.project.srchhisto.Srchhisto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    private final ImageService imageService;
    private final MemberService memberService;
    private final NaverSearch naverSearch;
    private final NaverGeocoding naverGeocoding;
    private final NaverRvrsGeocoding naverRGeocoding;

    // 단일 생성자의 경우에는 Autowired annotation 생략 가능하다.
    // @controller 위에 @RequiredArgsConstructor 를 이용하여 아래의 코드도 생략 가능하다.(22~25)
    @Autowired
    public HomeController(ImageService imageService,
                          MemberService memberService,
                          NaverSearch naverSearch,
                          NaverGeocoding naverGeocoding,
                          NaverRvrsGeocoding naverRGeocoding) {
        this.imageService = imageService;
        this.memberService = memberService;
        this.naverSearch = naverSearch;
        this.naverGeocoding = naverGeocoding;
        this.naverRGeocoding = naverRGeocoding;
    }

    @RequestMapping(value = "/map", method = RequestMethod.GET)
    public String mapAPITest() {

        return "mapAPITest";
    }

    @RequestMapping(value = "/exif", method = RequestMethod.GET)
    public String exifTest() {

        return "exifTest";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("message", "인덱스 페이지 입니다.");

        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String inputImage() {

        return "inputImageForm";
    }

    @RequestMapping(value = "/example")
    public String examplePage() {

        return "exampleInput";
    }

    @RequestMapping(value = "cropExample", method = RequestMethod.POST)
    public String cropExample(Model model, @RequestParam("exampleImg") String exampleImg) {
        if (exampleImg.equals("1")) {
            model.addAttribute("getOriginalFilename", "example/" + "은진식당 gps.jpg");
        } else if (exampleImg.equals("2")) {
            model.addAttribute("getOriginalFilename", "example/" + "스타벅스 북촌로점 gps.jpg");
        } else if (exampleImg.equals("3")) {
            model.addAttribute("getOriginalFilename", "example/" + "은진식당.jpg");
        } else if (exampleImg.equals("4")) {
            model.addAttribute("getOriginalFilename", "example/" + "스타벅스 북촌로점.jpg");
        }
        return "cropImage";
    }

    @RequestMapping(value = "/cropImage", method = RequestMethod.POST)
    public String cropImage(Model model, HttpSession session,
                            @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        imageService.saveImage(session, imageFile);
        model.addAttribute("getOriginalFilename", imageFile.getOriginalFilename());

        return "cropImage";
    }

    @RequestMapping(value = "/cropResult", method = RequestMethod.POST)
    public String cropResult(Model model, HttpSession session, CropLoc cropLoc,
                             @RequestParam("originalFileName") String originalFileName,
                             @RequestParam("offsetWidth") double offsetWidth,
                             @RequestParam("offsetHeight") double offsetHeight,
                             @RequestParam("ddX") double ddX,
                             @RequestParam("ddY") double ddY,
                             @RequestParam("dong") String inputDong,
                             @RequestParam("whatLang") String whatLang) throws IOException {

        // 간판 사진을 OCR 하고 OCR한 정보 model에 탑재
        String ocrResult = imageService.imageCrop(originalFileName, cropLoc, session, offsetWidth, offsetHeight, whatLang);
        //OCR 결과가 없으면 간판의 영역을 다시 지정 하도록 한다.
        if (ocrResult.equals("")) {
            model.addAttribute("getOriginalFilename", originalFileName);
            model.addAttribute("ocrResult", "fail");
            return "cropImage";
        }
        model.addAttribute("cropImageLoc", "cropImageLoc");
        model.addAttribute("getOriginalFilename", originalFileName);
        model.addAttribute("ocrResult", ocrResult);

        System.out.println("ddX: " + ddX);
        System.out.println("ddY: " + ddY);
        Double ddx = ddX;

        String dong = "";
        if (ddx.isNaN()) {
            System.out.println("gps empty");
            dong = inputDong;
        } else {
            String address = naverRGeocoding.rvrsGeocode(ddX, ddY);
            dong = naverRGeocoding.exportAddress(address);
            System.out.println(dong);
        }
        // /cropImage 에서 받아온 DD_X, DD_Y 좌표를 통해 네이버 reverse geocoding을 진행한다.
        // 결과값에서 지번주소를 추출한다.

        // 가게의 정보를 네이버 검색 API로 검색하고 가게의 정보(json)을 model에 탑재
        String searchResult = naverSearch.search(dong, ocrResult);
        System.out.println(dong + " " + ocrResult);
        model.addAttribute("searchResult", searchResult);

        // 검색 API에서 뽑은 정보에서 가게의 도로명 주소만을 추출한다.
        String roadAddress = naverSearch.exportRoadAddress(searchResult);
        if (roadAddress.equals("NotFoundErr")) {
            model.addAttribute("getOriginalFilename", originalFileName);
            model.addAttribute("apiResult", "fail");
            return "cropImage";
        }
//        System.out.println(roadAddress);

        // 가게의 도로명 주소를 네이버 맵 API의 geocoding에 전송하여 위도와 경도를 뽑아낸다.
        String geoLoc = naverGeocoding.geocode(roadAddress);
        System.out.println(geoLoc);
        model.addAttribute("geoLoc", geoLoc);

        return "inputImageSuccess";
    }

    @RequestMapping(value = "/storeData", method = RequestMethod.POST)
    public String storeData(HttpSession session, Authentication authentication, Srchhisto srchhisto) {
        imageService.saveImageToTable(session, authentication, srchhisto);

        return "redirect:/";
    }

    public ModelAndView makeJson(Authentication authentication,
                                 ModelAndView mav, List<Srchhisto> list) throws JsonProcessingException {
        Member member = memberService.loadUserByUsername(authentication.getName());
        if (member != null) {
            String shListJson = new ObjectMapper().writeValueAsString(list);
            mav.addObject("shListJson", shListJson);
            System.out.println(shListJson);
        } else {
            // 로그인 해주세요 alert
            mav.setViewName("redirect:/");
            return mav;
        }
        return mav;
    }

    @RequestMapping(value = "/bookmarkedSearch", method = RequestMethod.GET)
    public ModelAndView bookmarkedSearch(Authentication authentication,
                                         HttpServletResponse response,
                                         @RequestParam(value = "curPage",
                                                 required = false,
                                                 defaultValue = "0") int curPage) throws JsonProcessingException {
        ModelAndView mav = new ModelAndView();

        // 1-----------------------------------------------
        // js에 데이터를 전부 넘겨주고 paging을 js에서 작업할 때
        List<Srchhisto> list = imageService.viewBookmarked(authentication);
        // ------------------------------------------------

        // 2-----------------------------------------------
        // sublist를 이용하여 paging을 구현할 때 (추가 구문)
        int startIndexOfData = curPage * 2;
//        int endIndexOfData = list.size() > startIndexOfData + 2 ?
//                startIndexOfData + 2 :
//                list.size();
        int endIndexOfData = Math.min(list.size(), startIndexOfData + 2);
        List<Srchhisto> pagingList = new ArrayList<>(list.subList(startIndexOfData, endIndexOfData));
        System.out.println("sublist: " + pagingList);
        // ------------------------------------------------

        // 3-----------------------------------------------
        // COUNT, LIMIT 절을 이용한 paging
        int listLength = imageService.getBookmarkedSize(authentication);
        System.out.println("search history length: " + listLength);
        List<Srchhisto> listUseLimit = imageService.viewBookmarked(authentication, startIndexOfData, 2);
//        List<Srchhisto> listUseLimit = imageService.viewBookmarked(authentication, startIndexOfData,
//                endIndexOfData - startIndexOfData);
        // ------------------------------------------------

        Cookie cookie = new Cookie("where", "bookmarkedSearch");
        cookie.setPath("/");
        response.addCookie(cookie);

        mav.setViewName("priorSearch");

        // 1-----------------------------------------------
        // js에 데이터를 전부 넘겨주고 paging을 js에서 작업할 때
//        mav = makeJson(authentication, mav, list);
        // ------------------------------------------------

        // 2-----------------------------------------------
        // sublist를 이용하여 paging을 구현할 때
//        mav = makeJson(authentication, mav, pagingList);
        // ------------------------------------------------


        // 3-----------------------------------------------
        // sql LIMIT 절을 이용하여 paging을 구현할 때
        mav = makeJson(authentication, mav, listUseLimit);
        // ------------------------------------------------

        mav.addObject("listSize", list.size());
        mav.addObject("curPage", curPage);

        return mav;
    }

    @RequestMapping(value = "/priorSearch", method = RequestMethod.GET)
    public ModelAndView priorSearch(Authentication authentication,
                                    HttpServletResponse response,
                                    @RequestParam(value = "curPage",
                                            required = false,
                                            defaultValue = "0") int curPage) throws JsonProcessingException {
        ModelAndView mav = new ModelAndView();

        // 1-----------------------------------------------
        // js에 데이터를 전부 넘겨주고 paging을 js에서 작업할 때
        List<Srchhisto> list = imageService.viewPrior(authentication);
        // ------------------------------------------------

        // 2-----------------------------------------------
        // sublist를 이용하여 paging을 구현할 때 (추가 구문)
        int startIndexOfData = curPage * 2;
//        int endIndexOfData = list.size() > startIndexOfData + 2 ?
//                startIndexOfData + 2 :
//                list.size();
        int endIndexOfData = Math.min(list.size(), startIndexOfData + 2);
        List<Srchhisto> pagingList = new ArrayList<>(list.subList(startIndexOfData, endIndexOfData));
        System.out.println("sublist: " + pagingList);
        // ------------------------------------------------

        // 3-----------------------------------------------
        // COUNT, LIMIT 절을 이용한 paging
        int listLength = imageService.getListSize(authentication);
        System.out.println("search history length: " + listLength);
        List<Srchhisto> listUseLimit = imageService.viewPrior(authentication, startIndexOfData, 2);
//        List<Srchhisto> listUseLimit = imageService.viewPrior(authentication, startIndexOfData,
//                endIndexOfData - startIndexOfData);
        // ------------------------------------------------

        Cookie cookie = new Cookie("where", "priorSearch");
        cookie.setPath("/");
        response.addCookie(cookie);

        mav.setViewName("priorSearch");

        // 1-----------------------------------------------
        // js에 데이터를 전부 넘겨주고 paging을 js에서 작업할 때
//        mav = makeJson(authentication, mav, list);
        // ------------------------------------------------

        // 2-----------------------------------------------
        // sublist를 이용하여 paging을 구현할 때
//        mav = makeJson(authentication, mav, pagingList);
        // ------------------------------------------------

        // 3-----------------------------------------------
        // sql LIMIT 절을 이용하여 paging을 구현할 때
        mav = makeJson(authentication, mav, listUseLimit);
        // ------------------------------------------------

        mav.addObject("listSize", list.size());
        mav.addObject("curPage", curPage);

        return mav;
    }

    @RequestMapping(value = "/bookmarkHistory", method = RequestMethod.POST)
    public String bookmarkHistory(Authentication authentication,
                                  HttpServletRequest request,
                                  Srchhisto srchhisto,
                                  Model model,
                                  @RequestParam("curPage") int curPage) {

        Member member = memberService.loadUserByUsername(authentication.getName());
        srchhisto.setUsername(member.getUsername());

        Cookie[] cookies = request.getCookies();
        String where = "priorSearch";
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals("where")) {
                where = cookie.getValue();
            }
        }

        int temp = srchhisto.getIsBookmarked();
        System.out.println(temp);
        int result = imageService.storeBookmark(srchhisto);
        System.out.println(curPage);
        model.addAttribute("curPage", curPage);
        return "redirect:/" + where;
    }

    @RequestMapping(value = "/deleteHistory", method = RequestMethod.POST)
    public String deleteHistory(Authentication authentication,
                                HttpSession session,
                                HttpServletRequest request,
                                Srchhisto srchhisto,
                                Model model,
                                @RequestParam("curPage") int curPage) {

        Member member = memberService.loadUserByUsername(authentication.getName());
        srchhisto.setUsername(member.getUsername());

        Cookie[] cookies = request.getCookies();
        String where = "priorSearch";
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals("where")) {
                where = cookie.getValue();
            }
        }

        int result = imageService.storeDelete(srchhisto, session);
        model.addAttribute("curPage", curPage);
        return "redirect:/" + where;
    }
}
