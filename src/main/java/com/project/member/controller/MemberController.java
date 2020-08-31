package com.project.member.controller;

import com.project.member.Member;
import com.project.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public String joinForm(Member member) {

        return "member/joinForm";
    }
    @RequestMapping(value = "/joinProcess", method = RequestMethod.POST)
    public String joinResult(Member member) {
        int result = service.memberRegister(member);
        if (result != 1) {
            return "redirect:/member/joinForm";
        }
        return "member/joinResult";
    }

    @RequestMapping(value = "/SecLoginResult", method = RequestMethod.POST)
    public String secLoginResult() {

        return "redirect:/";
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
    @RequestMapping(value = "/logout")
    public String logout(Member member, HttpSession session) {

        Member member1 = (Member) session.getAttribute("member");
        System.out.println("member logout: Id: " + member1.getUsername());
        session.invalidate();

        return "redirect:/";
    }

    @RequestMapping(value = "/modify")
    public ModelAndView modifyForm(HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        ModelAndView mav = new ModelAndView();
        mav.addObject("member", service.memberSearch(member));
        mav.setViewName("member/modifyForm");

        return mav;
    }
    @RequestMapping(value = "/modifyProcess", method = RequestMethod.POST)
    public ModelAndView modifyResult(Member member, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        Member member1 = service.memberModify(member);
        if(member1 == null) {
            mav.setViewName("redirect:/member/modifyForm");
        } else {
            session.setAttribute("member", member1);
            mav.setViewName("redirect:/");
        }

        return mav;
    }

    @RequestMapping(value = "remove")
    public ModelAndView removeForm(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        Member member1 = (Member) session.getAttribute("member");

        mav.addObject("member", member1);
        mav.setViewName("member/removeForm");

        return mav;
    }
    @RequestMapping(value = "removeProcess")
    public String removeResult(Member member, HttpSession session) {
        int result = service.memberRemove(member, session);
        if(result == 0) {
            return "redirect:/member/removeForm";
        }
        session.invalidate();
        return "redirect:/";
    }
}