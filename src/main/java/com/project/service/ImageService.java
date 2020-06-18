package com.project.service;

import com.project.CropLoc;
import com.project.member.Member;
import com.project.srchhisto.Srchhisto;
import com.project.srchhisto.dao.SrchhistoDAO;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    public ImageService(SrchhistoDAO srchhistoDAO) {
        this.dao = srchhistoDAO;
    }


    public int storeBookmark(Srchhisto srchhisto) {
        if (srchhisto.getIsBookmarked() == 0) {
            return dao.storeBookmark(srchhisto);
        }
        return dao.storeUnBookmark(srchhisto);
    }

    public int storeDelete(Srchhisto srchhisto) {

        return dao.storeDelete(srchhisto);
    }

    public List<Srchhisto> viewBookmarked(HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            return dao.bookmarkedStoreSelect(member);
        }
        return null;
    }

    public List<Srchhisto> viewPrior(HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            List<Srchhisto> list = dao.storeSelect(member);
            for (Srchhisto srchhisto : list) {
                System.out.println("------");
                System.out.print(srchhisto.getImageFileName() + " | ");
                System.out.print(srchhisto.getStoreName() + " | ");
                System.out.print(srchhisto.getStoreMenu() + " | ");
                System.out.print(srchhisto.getStorePhone() + " | ");
                System.out.println(srchhisto.getIsBookmarked());
            }
            return list;
        }
        return null;
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
            srchhisto.setMemberId(member.getMemberId());
            srchhisto.setIsBookmarked(0);
            srchhisto.setGpsAddress("234324423324");
            dao.storeInsert(srchhisto);
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
                            double offsetWidth, double offsetHeight) throws IOException {
        String imgPath = session.getServletContext().getRealPath("/") + "/resources/img/" + originalFileName;
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

        return OCR(cropImage, ocrPath);
    }

    public String OCR(BufferedImage imgFile, String ocrPath) {
        String result = null;
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath(ocrPath);

        try {
            result = tesseract.doOCR(imgFile);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }

        StringBuffer result1 = new StringBuffer();

        System.out.println("result: ");
        System.out.println("----------------------------------------------");
        for(int i = 0; i < Objects.requireNonNull(result).length(); i ++) {
            if(result.charAt(i) >= 'A' && result.charAt(i) <= 'Z') {
                result1.append(result.charAt(i));
            }
            if (result.charAt(i) >= 'a' && result.charAt(i) <= 'z') {
                result1.append(result.charAt(i));
            }
            if (result.charAt(i) == ' ') {
                result1.append(result.charAt(i));
            }
        }
        System.out.println(result1);
        System.out.println("----------------------------------------------");

        return result1.toString();
    }
}
