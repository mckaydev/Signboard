package com.project.member.controller;

import com.project.member.dao.MemberDAO;
import com.project.member.Member;
import com.project.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/member")
public class MemberController {
    private final MemberService service;

    @Autowired
    public MemberController(MemberService memberService) {

        this.service = memberService;
    }

    @RequestMapping(value = "/joinForm")
    public String joinForm(Member member) {

        return "joinForm";
    }
    @RequestMapping(value = "/joinResult", method = RequestMethod.POST)
    public String joinResult(Member member) {

        service.memberRegister(member);

        return "joinResult";
    }

    @RequestMapping(value = "/loginForm")
    public String loginForm(Member member) {

        return "loginForm";
    }
    @RequestMapping(value = "/loginResult", method = RequestMethod.POST)
    public String loginResult(Member member, HttpSession session) {

        Member member1 = service.memberSearch(member);
        if(member1 == null) { return "redirect:/member/loginForm"; }
        session.setAttribute("member", member1);

        return "redirect:/";
    }
    @RequestMapping(value = "/logout")
    public String logout(Member member, HttpSession session) {

        Member member1 = (Member) session.getAttribute("member");
        System.out.println("회원 로그아웃: Id: " + member1.getMemberId());
        session.invalidate();

        return "redirect:/";
    }

    @RequestMapping(value = "/modifyForm")
    public ModelAndView modifyForm(HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        ModelAndView mav = new ModelAndView();
        mav.addObject("member", service.memberSearch(member));
        mav.setViewName("modifyForm");

        return mav;
    }
    @RequestMapping(value = "/modifyResult", method = RequestMethod.POST)
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

    @RequestMapping(value = "removeForm")
    public ModelAndView removeForm(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        Member member1 = (Member) session.getAttribute("member");

        mav.addObject("member", member1);
        mav.setViewName("removeForm");

        return mav;
    }
    @RequestMapping(value = "removeResult")
    public String removeResult(Member member, HttpSession session) {
        int result = service.memberRemove(member);
        if(result == 0) {
            return "redirect:/member/removeForm";
        }
        session.invalidate();
        return "redirect:/";
    }
}