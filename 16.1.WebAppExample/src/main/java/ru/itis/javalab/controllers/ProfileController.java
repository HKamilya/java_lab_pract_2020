package ru.itis.javalab.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class ProfileController {

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public void getProfileController(HttpServletResponse response, HttpServletRequest request) throws IOException {
        PrintWriter writer = response.getWriter();
        Cookie[] cookies = request.getCookies();
        String fileName = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("fileUploaded")) {
                fileName = cookie.getValue();
            }
        }

        writer.println("<!doctype html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <title>Document</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>Hello, User</h1>" +
                "<h1>" + fileName + "</h1>\n" +
                "<img src=\"/image\">\n" +
                "</body>\n" +
                "</html>");
    }
}
