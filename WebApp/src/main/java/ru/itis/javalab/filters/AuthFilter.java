package ru.itis.javalab.filters;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.itis.javalab.config.AppConfiguration;
import ru.itis.javalab.models.User;
import ru.itis.javalab.services.UsersService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebFilter("/*")
public class AuthFilter implements Filter {
    private UsersService usersService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        usersService = (UsersService) filterConfig.getServletContext().getAttribute("usersService");
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("context.xml");
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        usersService = applicationContext.getBean(UsersService.class);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        boolean flag = false;
        HttpSession session = request.getSession();
        String uuid = (String) session.getAttribute("Auth");
        if(uuid!=null) {
            Optional<User> user = usersService.findByUuid(uuid);
            if (user.isPresent()) {
                flag = true;
            }
        }

        if (!flag && !request.getRequestURI().equals("/Login")) {
            response.sendRedirect("/Login");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}