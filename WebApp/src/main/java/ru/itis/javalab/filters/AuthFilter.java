package ru.itis.javalab.filters;

import ru.itis.javalab.models.User;
import ru.itis.javalab.services.UsersService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebFilter(urlPatterns = {"/Profile"})
public class AuthFilter implements Filter {
    private UsersService usersService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        usersService = (UsersService) filterConfig.getServletContext().getAttribute("usersService");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Cookie[] cookies = request.getCookies();
        boolean flag = false;
        if (cookies != null)
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Auth")) {
                    Optional<User> user = usersService.findByUuid(cookie.getValue());
                    if (user.isPresent()) {
                        flag = true;
                    }
                }
            }

        if (!flag) {
            response.sendRedirect("/Login");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}