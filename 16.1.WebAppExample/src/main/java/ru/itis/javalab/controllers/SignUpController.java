package ru.itis.javalab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.javalab.models.User;
import ru.itis.javalab.services.SignUpService;


@Controller
public class SignUpController {

    @Autowired
    private SignUpService service;


    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public ModelAndView getSignUp() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("signUp");
        return modelAndView;
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public void postSignUp(@RequestParam(value = "email") String email,
                           @RequestParam(value = "password") String password) {

        User user = User.builder()
                .email(email)
                .password(password)
                .build();

        service.signUp(user);
    }
}
