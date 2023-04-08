package com.baeldung.customauth.filter;

import com.baeldung.customauth.configuration.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private AppConfig appConfig;

    @Override
    public void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        if (isAuthenticatedRequest(httpServletRequest)) {
                PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken = new PreAuthenticatedAuthenticationToken(appConfig.getApiAuthHeaderName(),
                        httpServletRequest.getHeader(appConfig.getApiAuthHeaderName()), new ArrayList<>());

                SecurityContextHolder.getContext().setAuthentication(preAuthenticatedAuthenticationToken);
                filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private boolean isAuthenticatedRequest(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader(appConfig.getApiAuthHeaderName()) != null &&
                httpServletRequest.getHeader(appConfig.getApiAuthHeaderName()).equals(appConfig.getApiAuthHeaderSecret());
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return "/app/health".equals(path);
    }
}