package ru.itis.javalab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.javalab.models.User;
import ru.itis.javalab.services.UsersService;


import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
public class UsersController {

    @Autowired
    private UsersService service;


    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView getUsers(@RequestParam(value = "userId") String userIdAsString, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        System.out.println(userIdAsString);
        Long userId = Long.parseLong(userIdAsString);

        Optional<User> userOptional = service.getUserById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            modelAndView.addObject("user", user);
            modelAndView.setViewName("usersPage");
        } else {
            response.setStatus(404);
            modelAndView.setViewName("errorPage");
        }
        return modelAndView;
    }

    // POST /users?action=delete&userId=2
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String postUsers(@RequestParam(value = "action", required = false) String action,
                            @RequestParam(value = "userId", required = false) String userId) {
        if (action != null && action.equals("delete")) {
            service.deleteUserById(Long.parseLong(userId));
        }
        return "redirect:/profile";
    }
}
