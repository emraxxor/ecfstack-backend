package hu.emraxxor.fstack.demo.components.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

import hu.emraxxor.fstack.demo.data.type.SimpleUserNameAndPassword;
import lombok.SneakyThrows;
import lombok.var;

/**
 * 
 * @author attila
 *
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter  {
	
		private AuthenticationManager authenticationManager;
	    
	    private static final String COOKIE_NAME = "token";

	    private static final int EXPIRATION =  30 * 60 * 1000;
	    
	    private String secret;

	    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, String secret) {
	        this.authenticationManager = authenticationManager;
	        this.secret = secret;
	        setFilterProcessesUrl("/authenticate"); 
	    }

	    @Override
	    @SneakyThrows
	    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
	       var creds = new ObjectMapper().readValue(req.getInputStream(), SimpleUserNameAndPassword.class);

	       return authenticationManager.authenticate(
	                    new UsernamePasswordAuthenticationToken(
	                            creds.getUserName(),
	                            creds.getPassword(),
	                            new ArrayList<>())
	            );
	    }

	    @Override
	    protected void successfulAuthentication(HttpServletRequest req,
	                                            HttpServletResponse res,
	                                            FilterChain chain,
	                                            Authentication auth) throws IOException {
	        var now = System.currentTimeMillis();
	        String token = JWT.create()
	                .withSubject(auth.getName())
	                .withIssuedAt(new Date(now))
	                .withExpiresAt(new Date(now + EXPIRATION))
	                .withClaim("roles", 
	                				auth
	                					.getAuthorities()
	                					.stream()
	                					.map(GrantedAuthority::getAuthority)
	                				.collect(Collectors.toList())
	                )
	                .sign(Algorithm.HMAC512(secret.getBytes()));

	        var cookie = new Cookie(COOKIE_NAME, token);
	        cookie.setMaxAge(EXPIRATION);
	        cookie.setPath("/");
	        cookie.setHttpOnly(true);
	        res.addCookie(cookie);

	        var resp = Maps.newHashMap();
	        resp.put("token", token);
	        res.getWriter().write(new ObjectMapper().writeValueAsString(resp));
	        res.getWriter().flush();
	    }
}
