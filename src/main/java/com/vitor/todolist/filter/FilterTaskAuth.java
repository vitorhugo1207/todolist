package com.vitor.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.vitor.todolist.user.UserRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component // tell to spring manage this class
/*
 * Filter if user is login
 */
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
            
        var servletPath = request.getServletPath();
        if (servletPath.equals("/task/")) {
            var authorization = request.getHeader("Authorization"); // Get value auth 
    
            var authEncoded = authorization.substring(5).trim(); // trim() remove spaces
    
            byte[] authDecoded = Base64.getDecoder().decode(authEncoded); // Decoder pássword
    
            var authString = new String(authDecoded); // Transfor byte to String
    
            String[] credentials = authString.split(":");
    
            var user = this.userRepository.findByUsername(credentials[0]);
            if (user == null) {
                response.sendError(401);
            } else {
                var resultPassword = BCrypt.verifyer().verify(credentials[1].toCharArray(), user.getPassword()).verified;
                if (resultPassword) {
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401);
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
        
    }

}
