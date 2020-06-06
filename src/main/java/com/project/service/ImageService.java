package com.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@Service
public class ImageService {
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
