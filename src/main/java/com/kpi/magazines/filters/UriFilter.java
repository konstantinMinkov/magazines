package com.kpi.magazines.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Konstantin Minkov on 09.07.2016.
 */
public class UriFilter implements Filter {

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final String URI = ((HttpServletRequest) servletRequest).getRequestURI();
        final String[] uriPartials = URI.split("/");
        try {
            servletRequest.setAttribute("userURI", "/" + uriPartials[1]);
            servletRequest.setAttribute("command", uriPartials[2]);
        } catch (ArrayIndexOutOfBoundsException ignored) { }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        filterConfig = null;
    }
}
