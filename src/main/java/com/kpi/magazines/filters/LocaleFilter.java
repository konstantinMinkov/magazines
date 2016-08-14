package com.kpi.magazines.filters;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Konstantin Minkov on 06.07.2016.
 */
public class LocaleFilter implements Filter {

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String preferredLocale = request.getParameter("locale");
        String localeCookie = null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("locale")) {
                localeCookie = cookie.getValue();
            }
        }
        if (preferredLocale == null) {
            if (localeCookie == null) {
                request.setAttribute("locale", "en");
            } else {
                request.setAttribute("locale", localeCookie);
            }
        } else {
            request.setAttribute("locale", preferredLocale);
            response.addCookie(new Cookie("locale", preferredLocale));
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        filterConfig = null;
    }
}
