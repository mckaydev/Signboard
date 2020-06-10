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
        System.out.println(result);
        if(result == 1) {
            System.out.println("member register fail");
        } else {
            System.out.print("new member register: " +
                    "\nId: " + member.getMemberId() +
                    "\nEmail: " + member.getMemberEmail());
        }
    }

    @Override
    public Member memberSearch(Member member) {
        Member member1 = dao.memberRead(member);
        if(member1 == null) {
            System.out.println("member login fail");
        } else {
            System.out.println("member login " +
                    "\nId: " + member.getMemberId());
        }
        return member1;
    }

    @Override
    public Member memberModify(Member member) {
        int result = dao.memberUpdate(member);
        System.out.println(result);
        if(result == 0) {
            System.out.println("member modify fail");
        } else {
            System.out.print("modified member " +
                    "\nId: " + member.getMemberId() +
                    "\nEmail: " + member.getMemberEmail());
        }
        return member;
    }

    @Override
    public int memberRemove(Member member) {
        int result = dao.memberDelete(member);
        System.out.println(result);
        if(result == 0) {
            System.out.println("member remove fail");
        } else {
            System.out.print("removed member " +
                    "\nId: " + member.getMemberId());
        }
        return result;
    }
}
