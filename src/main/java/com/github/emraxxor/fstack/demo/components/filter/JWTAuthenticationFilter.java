package com.github.emraxxor.fstack.demo.components.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import com.github.emraxxor.fstack.demo.config.ApplicationUser;
import com.github.emraxxor.fstack.demo.data.type.SimpleUser;
import com.github.emraxxor.fstack.demo.data.type.SimpleUserNameAndPassword;
import com.github.emraxxor.fstack.demo.entities.UserLog;
import com.github.emraxxor.fstack.demo.service.UserService;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Base64Utils;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * 
 * @author attila
 *
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter  {
	
		private final AuthenticationManager authenticationManager;
	    
	    private static final String COOKIE_NAME = "token";

	    private static final int EXPIRATION =  300 * 60 * 1000;
	    
	    private final String secret;

		private final UserService userService;


		public JWTAuthenticationFilter(AuthenticationManager authenticationManager, String secret, UserService userService) {
	        this.authenticationManager = authenticationManager;
	        this.secret = secret;
	        this.userService = userService;
	        setFilterProcessesUrl("/authenticate"); 
	    }

	    @Override
	    @SneakyThrows
	    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
	       var creds = new ObjectMapper().readValue(req.getInputStream(), SimpleUserNameAndPassword.class);

	       return authenticationManager.authenticate(
	                    new UsernamePasswordAuthenticationToken(
	                            creds.getUsername(),
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
	        var mapper = new ModelMapper();
	        var user = (ApplicationUser)auth.getPrincipal();
	        var strategy = new ExclusionStrategy() {
				
				@Override
				public boolean shouldSkipField(FieldAttributes field) {
					return field.getDeclaringClass() == SimpleUser.class && field.getName().equals("userPassword");
				}
				
				@Override
				public boolean shouldSkipClass(Class<?> clazz) {
					return false;
				}
			};

			var optionalUser = userService.findById(user.getUserId());
			if ( optionalUser.isPresent() ) {
				var userLog = UserLog.builder().user(optionalUser.get()).ip(req.getRemoteAddr()).build();
				userService.save(userLog);
			}


			var gson = new GsonBuilder()
					  .addSerializationExclusionStrategy(strategy)
					  .create();

			
	        
	        String token = JWT.create()
	        		.withSubject(auth.getName())
	        		.withIssuedAt(new Date(now))
	        		.withExpiresAt(new Date(now + EXPIRATION))
	        		.withClaim("roles", ((ApplicationUser)auth.getPrincipal()).
	        				getGrantedAuthorities()
	        				.stream()
	        				.map(GrantedAuthority::getAuthority).collect(Collectors.toList())
	        		 )
	        		.withClaim("user", Base64Utils.encodeToString( 
	        								gson.toJson( mapper.map( user.getUser() , SimpleUser.class )).getBytes()  
	        						   ) 
	        		 )
	        		.withClaim("uid",  ((ApplicationUser) auth.getPrincipal()).getUser().getUserId() )
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
