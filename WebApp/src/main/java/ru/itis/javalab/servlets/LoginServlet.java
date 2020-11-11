package ru.itis.javalab.servlets;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itis.javalab.models.User;
import ru.itis.javalab.services.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@WebServlet("/Login")
public class LoginServlet extends HttpServlet {

    private UsersService usersService;
    private PasswordEncoder passwordEncoder;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.usersService = (UsersService) config.getServletContext().getAttribute("usersService");
        this.passwordEncoder = (PasswordEncoder) config.getServletContext().getAttribute("passwordEncoder");
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/Login.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = usersService.findByUsername(username);


        if (user.getUsername().equals(username)
                && passwordEncoder.matches(password, user.getPassword())) {
            String uuid = UUID.randomUUID().toString();
            user.setUuid(uuid);
            usersService.updateUser(user);
            HttpSession session = req.getSession();
            session.setAttribute("Auth", user.getUuid());
            resp.addCookie(new Cookie("Auth", user.getUuid()));
            req.getRequestDispatcher("/WEB-INF/Profile.jsp").forward(req, resp);
        }
    }

}