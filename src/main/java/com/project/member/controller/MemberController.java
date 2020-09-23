package com.project.member.controller;

import com.project.member.Member;
import com.project.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/member")
public class MemberController {
    private final MemberService service;

    @Autowired
    public MemberController(MemberService memberService) {
        this.service = memberService;
    }

    @RequestMapping(value = "/join")
    public String joinForm() {

        return "member/joinForm";
    }
    @RequestMapping(value = "/joinProcess", method = RequestMethod.POST)
    public String joinResult(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("email") String email,
                             Model model) {
        int result = service.memberRegister(username, password, email);
        if (result == 0) {
            return "redirect:/member/join";
        }
        model.addAttribute("username", username);
        model.addAttribute("email", email);
        return "member/joinResult";
    }

    @RequestMapping(value = "/login")
    public String loginForm() {

        return "member/loginForm";
    }

//    @RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
//    public String loginResult(Member member, HttpSession session) {
//
//        Member member1 = service.memberSearch(member);
//        if(member1 == null) { return "redirect:/member/loginForm"; }
//        session.setAttribute("member", member1);
//
//        return "redirect:/";
//    }
//
//    @RequestMapping(value = "/logout")
//    public String logout(Member member, HttpSession session) {
//
//        Member member1 = (Member) session.getAttribute("member");
//        System.out.println("member logout: Id: " + member1.getUsername());
//        session.invalidate();
//
//        return "redirect:/";
//    }

    @RequestMapping(value = "/modify")
    public ModelAndView modifyForm(HttpSession session, Authentication authentication) {
        Member member = service.loadUserByUsername(authentication.getName());
        // 사용자에게 Member정보를 전달할 때 비밀번호 지우고 전달.
        member.eraseCredentials();
        System.out.println(member.toString());
        ModelAndView mav = new ModelAndView();
        mav.addObject("member", member);
        mav.setViewName("member/modifyForm");

        return mav;
    }
    @RequestMapping(value = "/modifyProcess", method = RequestMethod.POST)
    public String modifyResult(@RequestParam("password") String password,
                                     @RequestParam("email") String email,
                                     Authentication authentication) {

        Member member = service.loadUserByUsername(authentication.getName());
        Member newM = service.memberModify(member, password, email);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                newM,null, newM.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
//            session.setAttribute("member", member);

        return "redirect:/";
    }

    @RequestMapping(value = "remove")
    public ModelAndView removeForm(Authentication authentication) {
        ModelAndView mav = new ModelAndView();
//        Member member1 = (Member) session.getAttribute("member");
        Member member = service.loadUserByUsername(authentication.getName());
        member.eraseCredentials();

        mav.addObject("member", member);
        mav.setViewName("member/removeForm");

        return mav;
    }
    @RequestMapping(value = "removeProcess")
    public String removeResult(@RequestParam("password") String password,
                               HttpSession session,
                               Authentication authentication) {
        Member member = service.loadUserByUsername(authentication.getName());

        int result = service.memberRemove(member, session, password);
        if(result == 0) {
            return "redirect:/member/remove";
        }
//        session.invalidate();
        SecurityContextHolder.clearContext();
        return "redirect:/";
    }
}