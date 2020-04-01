package ru.itis.servlets.security.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ru.itis.servlets.security.authentification.JwtAuthentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component( "jwtAuthenticationFilter")
public class JwtAuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;


        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("cookieName")) {
                    token = cookie.getValue();
                }
            }
        }
        if(token != null) {
            // создаем объект аутентификации
            Authentication authentication = new JwtAuthentication(token);
            // кладем его в контекст для текущего потока
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // отправили запрос дальше
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
