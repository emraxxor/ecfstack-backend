package hu.emraxxor.fstack.demo.config;

import hu.emraxxor.fstack.demo.components.filter.JWTAuthenticationFilter;
import hu.emraxxor.fstack.demo.components.filter.JWTAuthorizationFilter;
import hu.emraxxor.fstack.demo.service.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 
 * @author Attila Barna
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter  {

	private final PasswordEncoder pw;
	
	private final ApplicationUserService userService;
	
	@Value("${jwt.secret}")
	private String jwtSecret;
	
	@Autowired
	public ApplicationSecurityConfiguration(ApplicationUserService us, PasswordEncoder pe) {
		userService = us;
		pw = pe;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
			 csrf().disable()
   		    .cors()
   		    .and()
   		    .headers()
   		    .frameOptions().disable()
   		    .and()
		 	.authorizeRequests()
		 	.antMatchers("/h2/**","/h2").permitAll()
			.antMatchers("/api/user/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
			.antMatchers("/api/album/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
		 	.antMatchers("/api/admin/**").hasRole(ApplicationPermission.ROLE_ADMIN.get())
			.antMatchers("/authenticate").permitAll()
		 	.antMatchers("/users/**").permitAll()
		 	.anyRequest().authenticated()
		 	.and()
            .addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtSecret))
            .addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtSecret))
		 	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(daoProvider());
	}
	
	@Bean
	public DaoAuthenticationProvider daoProvider() {
		DaoAuthenticationProvider dp = new DaoAuthenticationProvider();
		dp.setPasswordEncoder(pw);
		dp.setUserDetailsService(userService);
		return dp;
	}

}
