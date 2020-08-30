package com.project.member.service;

import com.project.member.Member;
import com.project.member.dao.MemberDAO;
import com.project.srchhisto.Srchhisto;
import com.project.srchhisto.dao.SrchhistoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService implements IMemberService, UserDetailsService {
    private final MemberDAO dao;
    private final SrchhistoDAO srchhistoDAO;

    @Autowired
    public MemberService(MemberDAO memberDAO, SrchhistoDAO srchhistoDAO) {
        this.dao = memberDAO;
        this.srchhistoDAO = srchhistoDAO;
    }

    @Override
    public int memberRegister(Member member) {
        int result = dao.memberCreate(member);
        System.out.println(result);
        if(result == 1) {
            System.out.println("new member register: " +
                "\nId: " + member.getMemberId() +
                "\nEmail: " + member.getMemberEmail());
        } else {
            System.out.println("member register fail");
        }
        return result;
    }

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        Member member = dao.findById(memberId);

        List<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        System.out.println(member.getMemberId() + " Login");
        if(("admin").equals(memberId)) {
            auth.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            auth.add(new SimpleGrantedAuthority("ROLE_USER"));
            System.out.println("Granted Role: USER");
        }
        return new User(member.getMemberId(), member.getMemberPw(), auth);
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
            System.out.println("modified member " +
                    "\nId: " + member.getMemberId() +
                    "\nEmail: " + member.getMemberEmail());
        }
        return member;
    }

    @Override
    public int memberRemove(Member member, HttpSession session) {
        List<Srchhisto> list = srchhistoDAO.storeSelect(member);
        String imgPath;
        for (Srchhisto srchhisto : list) {
            imgPath = session.getServletContext().getRealPath("/") +
                    "/resources/img/" +srchhisto.getImageFileName();
            File file = new File(imgPath);
            if(file.exists()) {
                if(file.delete()) {
                    System.out.println("삭제 이미지:" + srchhisto.getImageFileName());
                } else {
                    System.out.println("삭제 실패");
                }
            }
        }

        int result = dao.memberDelete(member);
        System.out.println(result);
        if(result == 0) {
            System.out.println("member remove fail");
        } else {
            System.out.println("removed member " +
                    "\nId: " + member.getMemberId());
        }
        return result;
    }
}
