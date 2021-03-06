package com.project.member.service;

import com.project.member.Member;

import javax.servlet.http.HttpSession;

public interface IMemberService {
    int memberRegister(String username, String password, String email);
    Member memberSearch(Member member);
    Member memberModify(Member member, String password, String email);
    int memberRemove(Member member, HttpSession session, String password);
}
