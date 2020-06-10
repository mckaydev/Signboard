package com.project.service;

import com.project.member.Member;
import com.project.srchhisto.Srchhisto;
import com.project.srchhisto.dao.SrchhistoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@Service
public class ImageService {
    private final SrchhistoDAO dao;

    @Autowired
    public ImageService(SrchhistoDAO srchhistoDAO) {
        this.dao = srchhistoDAO;
    }

    public void saveImageToTable(HttpSession session, Srchhisto srchhisto) {
        // 여기서부터 테이블에 데이터 저장.
        // srchhisto 스키마에 member.memberId
        // memberId는 세션에서 getAttribute("member")
        // 상호명, 메뉴, 전화번호 테이블에 저장
        Member member = (Member) session.getAttribute("member");
        // 회원일 경우
        if(member != null) {
            // 테이블에 데이터 저장.
            // 새로운 매퍼 생성?
            srchhisto.setMemberId(member.getMemberId());
            srchhisto.setIsBookmarked("0");
            srchhisto.setGpsAddress("234324423324");
            dao.storeInsert(srchhisto);
        }
    }

    public void saveImage(HttpSession session, MultipartFile imageFile) throws IOException {
        String path = session.getServletContext().getRealPath("/") + "/resources/img/" + imageFile.getOriginalFilename();
        System.out.println("Path:" + path);
        File file = new File(path);
        if(!file.exists()) {
            boolean created = file.mkdirs();
            if(created) {
                System.out.println("(out)make directory successful");
            } else {
                System.out.println("(out)make directory failed");
            }
        }
        imageFile.transferTo(file);
    }
}
