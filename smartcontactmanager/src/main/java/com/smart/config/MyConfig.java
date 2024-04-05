package com.smart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.AntPathMatcher;

@Configuration
@EnableWebSecurity
public class MyConfig{
	
	@Bean
	public UserDetailsService getUserDetailsService() {
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		
		daoAuthenticationProvider.setUserDetailsService(getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return daoAuthenticationProvider;
	}
	
	//configure method
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.authenticationProvider(authenticationProvider());
	}
	
//	protected void configure(HttpSecurity http) throws Exception{
//		http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
//		.antMatchers("/user/**").hasRole("USER")
//		.antMatchers("/**").permitAll().and().formLogin().and().csrf().disable();
//	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        http.authorizeHttpRequests(requests -> requests.requestMatchers("/admin/**").hasRole("ADMIN")
//                .requestMatchers("/user/**").hasRole("USER")
//                .requestMatchers("/**").permitAll()).formLogin(withDefaults()).csrf(csrf -> csrf.disable());
//		
		
		http
	    .authorizeHttpRequests((authorize) -> authorize
	    		.requestMatchers("/user/**").hasAuthority("ROLE_USER")
	    		.requestMatchers("/admin/**").hasAuthority("ADMIN")
	    		.requestMatchers("/**").permitAll()
//	    		.
//	        .anyRequest()
//	        .authenticated()
//	        .permitAll()
//	        .denyAll()
	        
	    )
	    .formLogin
//	    .permitAll()
	    ((formLogin) -> formLogin
//	    		.defaultSuccessUrl("/signup")
//	    		.failureUrl("/login?error=true")
                .loginPage("/signin")
                .defaultSuccessUrl("/user/index")
                .loginProcessingUrl("/doLogin")
                .permitAll()
            )
	    .logout(
	    		(formLogout) -> formLogout
//	    		.logoutSuccessUrl("/signin?logout=true")
//	    		.logoutSuccessUrl("/signup")
	    		.logoutUrl("/logout")
//	    		.deleteCookies("JSESSIONID")
//	    		.invalidateHttpSession(true)
	    		.permitAll()
	    		)
//	    .rememberMe(Customizer.withDefaults())
	    ;
//	    http
//	    .authorizeHttpRequests((authorize) -> authorize
//	    		.anyRequest().authenticated());
//			.requestMatchers("/user/**").hasAuthority("USER")
//			.requestMatchers("/static/**", "/signup", "/about").permitAll()
	    
//	    http
//        .csrf().disable() // Consider enabling for production
//        .authorizeRequests(authorize -> authorize
//                .antMatchers("/", "/public/**").permitAll()
//                .antMatchers("/user/**").hasRole("USER")
//                .anyRequest().authenticated())
//        .formLogin() // Enable form login
//        .and()
//        .logout() // Enable logout
//        .permitAll();
	    
		http.authenticationProvider(authenticationProvider());
		DefaultSecurityFilterChain build = http.build();
		return build;
	}
	private Customizer<FormLoginConfigurer<HttpSecurity>> withDefaults() {
		// TODO Auto-generated method stub
		return null;
	}
//	@Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers("/admin/**");
//    }

	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
}
