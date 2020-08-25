package com.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.CropLoc;
import com.project.member.Member;
import com.project.service.ImageService;
import com.project.service.NaverGeocoding;
import com.project.service.NaverRvrsGeocoding;
import com.project.service.NaverSearch;
import com.project.srchhisto.Srchhisto;
import com.sun.jna.WString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    private final ImageService imageService;
    private final NaverSearch naverSearch;
    private final NaverGeocoding naverGeocoding;
    private final NaverRvrsGeocoding naverRGeocoding;

    // 단일 생성자의 경우에는 Autowired annotation 생략 가능하다.
    // @controller 위에 @RequiredArgsConstructor 를 이용하여 아래의 코드도 생략 가능하다.(22~25)
    @Autowired
    public HomeController(ImageService imageService,
                          NaverSearch naverSearch,
                          NaverGeocoding naverGeocoding,
                          NaverRvrsGeocoding naverRGeocoding) {
        this.imageService = imageService;
        this.naverSearch = naverSearch;
        this.naverGeocoding = naverGeocoding;
        this.naverRGeocoding = naverRGeocoding;
    }

    @RequestMapping(value = "/map", method = RequestMethod.GET)
    public String mapAPITest(Model model) {

        return "mapAPITest";
    }

    @RequestMapping(value = "/exif", method = RequestMethod.GET)
    public String exifTest(Model model) {

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

    @RequestMapping(value = "/cropImage", method = RequestMethod.POST)
    public String cropImage(Model model, HttpSession session,
                            @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        imageService.saveImage(session, imageFile);
        model.addAttribute("getOriginalFilename", imageFile.getOriginalFilename());

        return "cropImage";
    }

    @RequestMapping(value = "/cropResult", method = RequestMethod.POST)
    public String cropResult(Model model, HttpSession session, CropLoc cropLoc, RedirectAttributes rttr,
                             @RequestParam("originalFileName") String originalFileName,
                             @RequestParam("offsetWidth") double offsetWidth,
                             @RequestParam("offsetHeight") double offsetHeight,
                             @RequestParam("ddX") double ddX,
                             @RequestParam("ddY") double ddY,
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
        // /cropImage 에서 받아온 DD_X, DD_Y 좌표를 통해 네이버 reverse geocoding을 진행한다.
        // 결과값에서 지번주소를 추출한다.
        String address = naverRGeocoding.rvrsGeocode(ddX, ddY);
        String dong = naverRGeocoding.exportAddress(address);
        System.out.println(dong);

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
    public String storeData(HttpSession session, Srchhisto srchhisto) {
        imageService.saveImageToTable(session, srchhisto);

        return "redirect:/";
    }

    public ModelAndView makeJson(HttpSession session, ModelAndView mav, List<Srchhisto> list) throws JsonProcessingException {
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            String shListJson = new ObjectMapper().writeValueAsString(list);
            mav.addObject("shListJson", shListJson);
            System.out.println(shListJson);
        } else {
            // 로그인 해주세요 alert
            mav.setViewName("redirect:/");
            return mav;
        }
        mav.addObject("listSize", list.size());
        return mav;
    }

    @RequestMapping(value = "/bookmarkedSearch", method = RequestMethod.GET)
    public ModelAndView bookmarkedSearch(HttpSession session, HttpServletResponse response) throws JsonProcessingException {
        ModelAndView mav = new ModelAndView();
        List<Srchhisto> list = imageService.viewBookmarked(session);

        Cookie cookie = new Cookie("where", "bookmarkedSearch");
        cookie.setPath("/");
        response.addCookie(cookie);

        mav.setViewName("priorSearch");
        mav = makeJson(session, mav, list);

        return mav;
    }

    @RequestMapping(value = "/priorSearch", method = RequestMethod.GET)
    public ModelAndView priorSearch(HttpSession session,  HttpServletResponse response) throws JsonProcessingException {
        ModelAndView mav = new ModelAndView();
        List<Srchhisto> list = imageService.viewPrior(session);

        Cookie cookie = new Cookie("where", "priorSearch");
        cookie.setPath("/");
        response.addCookie(cookie);

        mav.setViewName("priorSearch");
        mav = makeJson(session, mav, list);

        return mav;
    }

    @RequestMapping(value = "/bookmarkHistory", method = RequestMethod.POST)
    public String bookmarkHistory(HttpSession session, HttpServletRequest request, Srchhisto srchhisto) {

        Member member = (Member) session.getAttribute("member");
        srchhisto.setMemberId(member.getMemberId());

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
        return "redirect:/" + where;
    }

    @RequestMapping(value = "/deleteHistory", method = RequestMethod.POST)
    public String deleteHistory(HttpSession session, HttpServletRequest request, Srchhisto srchhisto) {

        Member member = (Member) session.getAttribute("member");
        srchhisto.setMemberId(member.getMemberId());

        Cookie[] cookies = request.getCookies();
        String where = "priorSearch";
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals("where")) {
                where = cookie.getValue();
            }
        }

        int result = imageService.storeDelete(srchhisto, session);
        return "redirect:/" + where;
    }
}
