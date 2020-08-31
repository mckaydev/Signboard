package com.project.member.service;

import com.project.member.LMember;
import com.project.member.Member;

import javax.servlet.http.HttpSession;

public interface IMemberService {
    int memberRegister(String username, String password, String email);
    Member memberSearch(Member member);
    Member memberModify(Member member);
    int memberRemove(Member member, HttpSession session);
}
