package com.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.member.Member;
import com.project.service.ImageService;
import com.project.srchhisto.Srchhisto;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.net.HttpCookie;
import java.util.List;

@Controller
public class HomeController {
    private final ImageService imageService;

    // 단일 생성자의 경우에는 Autowired annotation 생략 가능하다.
    // @controller 위에 @RequiredArgsConstructor 를 이용하여 아래의 코드도 생략 가능하다.(22~25)
    @Autowired
    public HomeController(ImageService imageService) {
        this.imageService = imageService;
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

    @RequestMapping(value = "/inputImageSuccess", method = RequestMethod.POST)
    public String inputImageSuccess(Model model, HttpSession session,
                                    @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        String ocrResult = imageService.saveImage(session, imageFile);
        model.addAttribute("getOriginalFilename", imageFile.getOriginalFilename());
        model.addAttribute("ocrResult", ocrResult);

        return "inputImageSuccess";
    }

    @RequestMapping(value = "/storeData", method = RequestMethod.POST)
    public String storeData(HttpSession session, Srchhisto srchhisto) {
        imageService.saveImageToTable(session, srchhisto);

        return "redirect:/";
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ModelAndView find(Model model) {
        ModelAndView mav = new ModelAndView();
        // addObject로 select한 테이블 데이터들 List로 전달.
//        mav.addObject();
        mav.setViewName("findOk");

        return mav;
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

        int result = imageService.storeDelete(srchhisto);
        return "redirect:/" + where;
    }
}
