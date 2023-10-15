package com.vitor.todolist.filter;

import java.io.IOException;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.vitor.todolist.user.UserRepository;
import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component // tell to spring manage this class
/*
 * Filter if user is loged
 */
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
            
        var servletPath = request.getServletPath(); // Get route requested 
        if (servletPath.startsWith("/task/")) { // if Requested Route if equals to /task/
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
                    request.setAttribute("idUser", user.getId()); // set a attribute to get in taskController
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
