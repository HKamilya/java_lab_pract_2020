package ru.itis.javalab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.javalab.filters.ResponseUtil;
import ru.itis.javalab.services.SignInService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class SignInController {

    @Autowired
    public SignInService service;


    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
    public ModelAndView getSignIn() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("signIn");
        return modelAndView;
    }


    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public String postSignInPage(@RequestParam(value = "email") String email,
                                 @RequestParam(value = "password") String password,
                                 @RequestParam(value = "redirect", required = false) String redirect,
                                 HttpServletRequest request, HttpServletResponse response) throws IOException {

        // /signIn?redirect=/users?userId=2
        if (service.authenticate(email, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("authenticated", true);

            if (redirect == null) {
                return "redirect:/profile";
            } else {
                return "redirect:" + redirect;
            }
        } else {
            ResponseUtil.sendForbidden(request, response);
        }
        return "redirect:/profile";
    }
}
