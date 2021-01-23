package ru.itis.javalab.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
@MultipartConfig
public class FilesUploadController {


    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public ModelAndView getFilesUpload() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("fileUpload");
        return modelAndView;
    }

    @RequestMapping(value = "/files", method = RequestMethod.POST)
    public String postFilesUpload(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Part part = request.getPart("file");
        System.out.print(part.getSubmittedFileName() + " ");
        System.out.print(part.getContentType() + " ");
        System.out.println(part.getSize());
        Files.copy(part.getInputStream(), Paths.get("C://files/" + part.getSubmittedFileName()));
        Cookie cookie = new Cookie("fileUploaded", part.getSubmittedFileName());
        response.addCookie(cookie);
        response.sendRedirect("/files");
        return "redirect:/files";
    }
}
