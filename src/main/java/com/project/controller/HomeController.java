package com.project.controller;

import com.project.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;

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
//        System.out.println(request.getRequestURL());
//        System.out.println(session.getServletContext().getRealPath("/"));
//        String path = "D:Portfolio/out/artifacts/Portfolio_war_exploded/resources/img/" + imageFile.getOriginalFilename();
        imageService.saveImage(session, imageFile);
        model.addAttribute("getOriginalFilename", imageFile.getOriginalFilename());

        return "inputImageSuccess";
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public String find(Model model) {

        return "findOk";
    }

    @RequestMapping(value = "/priorSearch", method = RequestMethod.GET)
    public String priorSearch(Model model) {

        return "priorSearch";
    }
}
