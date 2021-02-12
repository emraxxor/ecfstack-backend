package hu.emraxxor.fstack.demo.components.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.var;

/**
 * 
 * @author attila
 *
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private static final String COOKIE_NAME = "token";
    
    private String secret;
    
    public JWTAuthorizationFilter(AuthenticationManager authManager, String secret) {
        super(authManager);
        this.secret = secret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        var oToken = getTokenFromCookie(req);
        if (!oToken.isPresent()) {
            oToken = getTokenFromHeader(req);
            if (!oToken.isPresent()) {
                chain.doFilter(req, res);
                return;
            }
        }
        
        var token = oToken.get();
        UsernamePasswordAuthenticationToken authentication = getAuthentication(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        DecodedJWT jwt = JWT
        		 			.require(Algorithm.HMAC512(secret.getBytes()))
        		 			.build()
        		 			.verify(token);
                
        var user = jwt.getSubject();

        if (user != null) {
            var roles = jwt.getClaim("roles").asList(String.class);
            return new UsernamePasswordAuthenticationToken(user, null, roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        }
        
        return null;
    }
    
    private Optional<String> getTokenFromCookie(HttpServletRequest req) {
        if (req.getCookies() == null) 
            return Optional.empty();
        
        var cookie = Arrays
        				.stream(req.getCookies())
        				.filter(c -> c.getName().equals(COOKIE_NAME))
        				.findAny();
        if (!cookie.isPresent()) 
            return Optional.empty();
        
        return !cookie.isPresent() ? Optional.empty() : Optional.of(cookie.get().getValue());

    }
    
    private Optional<String> getTokenFromHeader(HttpServletRequest req) {
        String header = req.getHeader("Authorization");
        
        if (header == null || !header.startsWith("Bearer ")) 
            return Optional.empty();
        
        return Optional.of(header.replace("Bearer ", ""));
    }
}
