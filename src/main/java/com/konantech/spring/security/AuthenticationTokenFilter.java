package com.konantech.spring.security;

import com.konantech.spring.domain.user.SecurityUser;
import com.konantech.spring.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

    private static Logger log = LoggerFactory.getLogger(AuthenticationTokenFilter.class);

    private final String ORIGIN_LOCAL = "local";

    @Value("${jwt.token.header}")
    private String tokenHeader;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthService authService;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String authToken = httpRequest.getHeader(tokenHeader);
        String username = tokenUtils.getUserNameFromToken(authToken);
        String userid = tokenUtils.getUserIdFromToken(authToken);
        String agentid = tokenUtils.getAgentIdFromToken(authToken);

        if (username != null) {
            String orgin = tokenUtils.getOriginFromToken(authToken);
            SecurityUser currentUser = null;
            if (ORIGIN_LOCAL.equals(orgin)) currentUser = (SecurityUser) authService.loadUserByUsername(username);
            if (tokenUtils.validateToken(authToken, currentUser)) {
                String commaSprAuthorities = tokenUtils.getAuthoritiesFromToken(authToken);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username, null, AuthorityUtils.commaSeparatedStringToAuthorityList(commaSprAuthorities));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(httpRequest, httpResponse);
    }
}
