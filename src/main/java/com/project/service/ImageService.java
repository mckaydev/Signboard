package com.project.service;

import com.project.CropLoc;
import com.project.member.Member;
import com.project.member.service.MemberService;
import com.project.srchhisto.Srchhisto;
import com.project.srchhisto.dao.SrchhistoDAO;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ImageService {
    private final SrchhistoDAO dao;
    private final MemberService memberService;
    private final Tesseract tesseract;

    @Autowired
    public ImageService(SrchhistoDAO srchhistoDAO, MemberService memberService, Tesseract tesseract) {
        this.dao = srchhistoDAO;
        this.memberService = memberService;
        this.tesseract = tesseract;
    }

    public int storeBookmark(Srchhisto srchhisto) {
        if (srchhisto.getIsBookmarked() == 0) {
            return dao.storeBookmark(srchhisto);
        }
        return dao.storeUnBookmark(srchhisto);
    }

    public void imageFileDel(Srchhisto srchhisto, HttpSession session) {
        String imgPath = session.getServletContext().getRealPath("/") +
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

    public int storeDelete(Srchhisto srchhisto, HttpSession session) {
        imageFileDel(srchhisto, session);

        return dao.storeDelete(srchhisto);
    }

    public int getListSize(Authentication authentication) {
        Member member = memberService.loadUserByUsername(authentication.getName());

        return dao.getListSize(member);
    }

    public int getBookmarkedSize(Authentication authentication) {
        Member member = memberService.loadUserByUsername(authentication.getName());

        return dao.getBookmarkedSize(member);
    }

    public List<Srchhisto> viewBookmarked(Authentication authentication) {
        Member member = memberService.loadUserByUsername(authentication.getName());
        if (member != null) {
            return dao.selectBookmarkedHistory(member);
        }
        return null;
    }

    public List<Srchhisto> viewBookmarked(Authentication authentication, int startIndex, int contentPerPage) {
        Member member = memberService.loadUserByUsername(authentication.getName());
        if (member != null) {
//            List<Srchhisto> list = dao.selectBookmarkedHistoryLimit(member.getUsername(), startIndex, contentPerPage);
//            return list;
            return dao.selectBookmarkedHistoryLimit(member.getUsername(), startIndex, contentPerPage);
        }
        return null;
    }

    public List<Srchhisto> viewPrior(Authentication authentication) {
        Member member = memberService.loadUserByUsername(authentication.getName());
        if (member != null) {
            return dao.selectHistory(member);
        }
        return null;
    }

    public List<Srchhisto> viewPrior(Authentication authentication, int startIndex, int contentPerPage) {
        Member member = memberService.loadUserByUsername(authentication.getName());
        if (member != null) {
//            List<Srchhisto> list = dao.selectHistoryLimit(member.getUsername(), startIndex, contentPerPage);
//            return list;
            return dao.selectHistoryLimit(member.getUsername(), startIndex, contentPerPage);
        }
        return null;
    }

    public void testDataInsert() {
        dao.testDataInsert();
    }

    public void saveImageToTable(HttpSession session,
                                 Authentication authentication, Srchhisto srchhisto) {
        // 여기서부터 테이블에 데이터 저장.
        // srchhisto 스키마에 member.memberId
        // memberId는 세션에서 getAttribute("member")
        // 상호명, 메뉴, 전화번호 테이블에 저장
        try {
            Member member = memberService.loadUserByUsername(authentication.getName());

            srchhisto.setUsername(member.getUsername());
            srchhisto.setIsBookmarked(0);
            dao.storeInsert(srchhisto);
        } catch (NullPointerException e) {
            imageFileDel(srchhisto, session);
        }
    }

    public void saveImage(HttpSession session, MultipartFile imageFile) throws IOException {
        String imgPath = session.getServletContext().getRealPath("/") + "/resources/img/" + imageFile.getOriginalFilename();
        System.out.println("Path:" + imgPath);
        File file = new File(imgPath);
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

    public BufferedImage imageCrop(String originalFileName, CropLoc cropLoc, HttpSession session,
                            double offsetWidth, double offsetHeight) throws IOException {
        String imgPath = session.getServletContext().getRealPath("/") + "resources/img/" + originalFileName;
        System.out.println("path: " + imgPath);
        File file = new File(imgPath);
        BufferedImage bufferedImage = ImageIO.read(file);

        System.out.println(bufferedImage.getHeight());
        System.out.println(bufferedImage.getWidth());

        double x1 = cropLoc.getX1() * (bufferedImage.getHeight() / offsetHeight);
        double y1 = cropLoc.getY1() * (bufferedImage.getWidth() / offsetWidth);
        double w = cropLoc.getW() * (bufferedImage.getWidth() / offsetWidth);
        double h = cropLoc.getH() * (bufferedImage.getHeight() / offsetHeight);

//        Rectangle rectangle = new Rectangle((int)x1, (int)y1, (int)w, (int)h);
//        return rectangle;

        return bufferedImage.getSubimage((int)x1, (int)y1, (int)w, (int)h);
    }

    public String OCR(HttpSession session, BufferedImage imgFile) {
        String ocrPath = session.getServletContext().getRealPath("/") + "WEB-INF/classes/tessdata";

        tesseract.setDatapath(ocrPath);
        tesseract.setLanguage("Hangul");
        tesseract.setTessVariable("user_defined_dpi", "300");

        String result = null;
        try {
            result = tesseract.doOCR(imgFile);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("result: " + result);

        StringBuilder result1 = new StringBuilder();

        for(int i = 0; i < result.length(); i ++) {
            if(result.charAt(i) >= 'A' && result.charAt(i) <= 'Z') {
                result1.append(result.charAt(i));
            }
            else if (result.charAt(i) >= 'a' && result.charAt(i) <= 'z') {
                result1.append(result.charAt(i));
            }
            else if (result.charAt(i) == ' ') {
                result1.append(result.charAt(i));
            }
            else if(Character.getType(result.charAt(i)) == 5) {
                result1.append(result.charAt(i));
            }

        }

        System.out.println("----------------------------------------------");
        System.out.println("remove special character: " + result1);
        if (result1.toString().equals("")) {
            System.out.println("ocr result empty");
        }
        System.out.println("----------------------------------------------");

        return result1.toString();
    }
}
