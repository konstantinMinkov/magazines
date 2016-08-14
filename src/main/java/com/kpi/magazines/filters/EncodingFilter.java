package com.kpi.magazines.filters;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Konstantin Minkov on 06.07.2016.
 */
public class EncodingFilter implements Filter {

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("utf-8");
        servletResponse.setCharacterEncoding("utf-8");
        filterChain.doFilter(servletRequest, servletResponse);
        servletResponse.setCharacterEncoding("utf-8");
    }

    @Override
    public void destroy() {
        filterConfig = null;
    }
}
