package com.project.member.service;

import com.project.member.Member;
import com.project.member.dao.MemberDAO;
import com.project.srchhisto.Srchhisto;
import com.project.srchhisto.dao.SrchhistoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MemberService implements IMemberService, UserDetailsService {
    private final MemberDAO dao;
    private final SrchhistoDAO srchhistoDAO;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberDAO memberDAO, SrchhistoDAO srchhistoDAO, PasswordEncoder passwordEncoder) {
        this.dao = memberDAO;
        this.srchhistoDAO = srchhistoDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public int memberRegister(String username, String password, String email) {
        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (username.equals("admin")) {
            roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        String encodePassword = passwordEncoder.encode(password);

        Member member = new Member(username, encodePassword, email, roles.toString(),
                true, true, true, true);

        int result = dao.memberCreate(member);
        System.out.println(result);
        if(result == 1) {
            System.out.println("new member register: " +
                "\nId: " + member.getUsername() +
                "\nEmail: " + member.getEmail());
        } else {
            System.out.println("member register fail");
        }
        return result;
    }

    @Override
    public Member loadUserByUsername(String username) throws UsernameNotFoundException {
//        List<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
//        auth.add(new SimpleGrantedAuthority("ROLE_USER"));
//        if(("admin").equals(username)) {
//            auth.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        }
//        return new Member(member.getUsername(), member.getPassword(),
//                member.getEmail(), member.getAuthorities().toString(),
//                member.isAccountNonExpired(), member.isAccountNonLocked(),
//                member.isCredentialsNonExpired(), member.isEnabled());
        return dao.findById(username);
    }

    @Override
    public Member memberSearch(Member member) {
        Member member1 = dao.memberRead(member);
        if(member1 == null) {
            System.out.println("member login fail");
        } else {
            System.out.println("member login " +
                    "\nId: " + member.getUsername());
        }
        return member1;
    }

    @Override
    public Member memberModify(Member member, String password, String email) {
        String encodePassword = passwordEncoder.encode(password);
        member.modify(encodePassword, email);

        int result = dao.memberUpdate(member);
        System.out.println(result);
        if(result == 0) {
            System.out.println("member modify fail");
        } else {
            System.out.println("modified member " +
                    "\nId: " + member.getUsername() +
                    "\nEmail: " + member.getEmail());
        }
        return member;
    }

    @Override
    public int memberRemove(Member member, HttpSession session, String password) {
        if (!passwordEncoder.matches(password, member.getPassword())) {
            return 0;
        }
        List<Srchhisto> list = srchhistoDAO.storeSelect(member);
        String imgPath;
        for (Srchhisto srchhisto : list) {
            imgPath = session.getServletContext().getRealPath("/") +
                    "/resources/img/" +srchhisto.getImageFileName();
            File file = new File(imgPath);
            String fileName = srchhisto.getImageFileName().substring(0, 8);
            if(file.exists() && !fileName.equals("example/")) {
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
                    "\nId: " + member.getUsername());
        }
        return result;
    }
}
