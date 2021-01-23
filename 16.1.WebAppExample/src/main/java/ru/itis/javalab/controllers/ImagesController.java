package ru.itis.javalab.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
public class ImagesController {


    @RequestMapping(value = "/image", method = RequestMethod.GET)
    public void getImagesPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("fileUploaded")) {
                File file = new File("C://files/" + cookie.getValue());
                response.setContentType("image/jpeg");
                response.setContentLength((int) file.length());
                response.setHeader("Content-Disposition", "filename=\"" + cookie.getValue() + "\"");
                Files.copy(file.toPath(), response.getOutputStream());
                response.flushBuffer();
            }
        }
    }
}
