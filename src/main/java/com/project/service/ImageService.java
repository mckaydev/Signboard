package com.project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class ImageService {
    private final SrchhistoDAO dao;
    private final MemberService memberService;

    @Autowired
    public ImageService(SrchhistoDAO srchhistoDAO, MemberService memberService) {
        this.dao = srchhistoDAO;
        this.memberService = memberService;
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
            List<Srchhisto> list = dao.selectBookmarkedHistoryLimit(member.getUsername(), startIndex, contentPerPage);
            return list;
//            return dao.selectBookmark(member.getUsername(), startIndex, contentPerPage);
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
            List<Srchhisto> list = dao.selectHistoryLimit(member.getUsername(), startIndex, contentPerPage);
            return list;
//            return dao.selectHistory(member.getUsername(), startIndex, contentPerPage);
        }
        return null;
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

    public String imageCrop(String originalFileName, CropLoc cropLoc, HttpSession session,
                            double offsetWidth, double offsetHeight, String whatLang) throws IOException {
        String imgPath = session.getServletContext().getRealPath("/") + "/resources/img/" + originalFileName;
        System.out.println("path: " + imgPath);
        String ocrPath = session.getServletContext().getRealPath("/") + "/WEB-INF/classes/tessdata";
        File file = new File(imgPath);
        BufferedImage bufferedImage = ImageIO.read(file);

        System.out.println(bufferedImage.getHeight());
        System.out.println(bufferedImage.getWidth());

        double x1 = cropLoc.getX1() * (bufferedImage.getHeight() / offsetHeight);
        double y1 = cropLoc.getY1() * (bufferedImage.getWidth() / offsetWidth);
        double w = cropLoc.getW() * (bufferedImage.getWidth() / offsetWidth);
        double h = cropLoc.getH() * (bufferedImage.getHeight() / offsetHeight);

        BufferedImage cropImage = bufferedImage.getSubimage((int)x1, (int)y1, (int)w, (int)h);

        return OCR(cropImage, ocrPath, whatLang);
    }

    public String OCR(BufferedImage imgFile, String ocrPath, String whatLang) {
        String result = null;
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath(ocrPath);
        tesseract.setLanguage(whatLang);

        System.out.println("----------------------------------------------");
        try {
            result = tesseract.doOCR(imgFile);
            System.out.println("result: " + result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }

        StringBuffer result1 = new StringBuffer();

        for(int i = 0; i < Objects.requireNonNull(result).length(); i ++) {
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
        System.out.println("remove special character: " + result1);
        if (result1.toString().equals("")) {
            System.out.println("ocr result empty");
        }
        System.out.println("----------------------------------------------");

        return result1.toString();
    }
}
