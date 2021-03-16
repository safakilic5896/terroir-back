package fr.epita.pfa.terroirback.config;

import fr.epita.pfa.terroirback.service.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthentificationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String header = httpServletRequest.getHeader("Authorization");
        if (header == null) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(httpServletRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String email = null;
        if (header != null) {
            header = header.replace("Bearer ", "");
            email = (String) jwtTokenUtil.getClaimFromToken(header, "email");
            if (email != null) {
                String role = (String) jwtTokenUtil.getClaimFromToken(header, "role");
                List<GrantedAuthority> roles = new ArrayList<>();
                roles.add(new SimpleGrantedAuthority(role));
                return new UsernamePasswordAuthenticationToken(email, null, roles);
            }
            return null;
        }
        return null;
    }
}
