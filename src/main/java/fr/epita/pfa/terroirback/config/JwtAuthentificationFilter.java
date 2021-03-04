package fr.epita.pfa.terroirback.config;

import fr.epita.pfa.terroirback.service.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthentificationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String header = httpServletRequest.getHeader("Authorization");
        String username = null;
        String authToken = null;
        if (header != null && header.startsWith("Bearer ")) {
            authToken = header.replace("Bearer", "");
            try {
                username = (String) jwtTokenUtil.getClaimFromToken(authToken, "name");
            } catch (IllegalArgumentException e) {
                logger.error("an error occured during getting username from token", e);
            }
            if (!jwtTokenUtil.validateToken(authToken)) {
                logger.error("the token is expired and not valid anymore");
            }
        } else {
            logger.error("Not token");
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
