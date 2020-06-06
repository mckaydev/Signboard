package com.project.member.service;

import com.project.member.Member;
import com.project.member.dao.MemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService implements IMemberService {
    private final MemberDAO dao;

    @Autowired
    public MemberService(MemberDAO memberDAO) {
        this.dao = memberDAO;
    }

    @Override
    public void memberRegister(Member member) {
        int result = dao.memberCreate(member);
        if(result == 0) {
            System.out.println("회원가입 실패");
        } else {
            System.out.println("신규 회원 가입: " +
                    "\nId: " + member.getMemberId() +
                    "\nEmail: " + member.getMemberEmail());
        }
    }

    @Override
    public Member memberSearch(Member member) {
        Member member1 = dao.memberRead(member);
        if(member1 == null) {
            System.out.println("로그인 실패");
        } else {
            System.out.println("회원 로그인: " +
                    "\nId: " + member.getMemberId());
        }
        return member1;
    }

    @Override
    public Member memberModify(Member member) {
        int result = dao.memberUpdate(member);
        if(result == 0) {
            System.out.println("정보 변경 실패");
        } else {
            System.out.println("실패 회원: " +
                    "\nId: " + member.getMemberId() +
                    "\nEmail: " + member.getMemberEmail());
        }
        return member;
    }

    @Override
    public int memberRemove(Member member) {
        int result = dao.memberDelete(member);
        if(result == 0) {
            System.out.println("회원 탈퇴 실패");
        } else {
            System.out.println("실패 회원: " +
                    "\nId: " + member.getMemberId());
        }
        return result;
    }
}
