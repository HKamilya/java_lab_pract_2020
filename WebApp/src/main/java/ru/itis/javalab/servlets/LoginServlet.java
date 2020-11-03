package ru.itis.javalab.servlets;

import ru.itis.javalab.models.User;
import ru.itis.javalab.services.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@WebServlet("/Login")
public class LoginServlet extends HttpServlet {

    private UsersService usersService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        usersService = (UsersService) config.getServletContext().getAttribute("usersService");
        super.init(config);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/Login.jsp").forward(req, resp);

//        UsersService usersService = (UsersService) getServletContext().getAttribute("usersService");
//        String id= UUID.randomUUID().toString();
//        Cookie cookie = new Cookie("auth",id);
//        cookie.setMaxAge(60*60*24*365);
//        usersService.addCookie(,id);
//        req.getRequestDispatcher("Login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        boolean rememberMe = "true".equals(req.getParameter("remember"));
        if (rememberMe) {
            String id = UUID.randomUUID().toString();
            System.out.println(id);
            usersService.insertUUID(username, id);
        }

        User user = usersService.findByUsername(username);


        if (user.getUsername().equals(username)
                && user.getPassword().equals(password)) {
            resp.addCookie(new Cookie("Auth", user.getUuid()));
            req.getRequestDispatcher("/WEB-INF/Profile.jsp").forward(req,resp);
        }
    }

}