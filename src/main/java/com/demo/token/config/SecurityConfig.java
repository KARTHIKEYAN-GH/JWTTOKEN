package com.demo.token.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.demo.token.filter.JwtAuthenticationFilter;
import com.demo.token.serviceImpl.UserDetailsServiceImp;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final UserDetailsServiceImp userDetailsSeviceImpl;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
    public SecurityConfig(UserDetailsServiceImp userDetailsSeviceImpl, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsSeviceImpl = userDetailsSeviceImpl;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(req ->
                req	
                	.requestMatchers("/api/user/login").permitAll()
                	
                    .requestMatchers("/api/user/**").hasAuthority("ADMIN")
                    
                    .requestMatchers("/api/categories/getAllCategories","/api/categories/getById/{Uuid}","/api/topics/getAllTopics","/api/topics/getById/{Uuid}","/api/topics/viewDescription/{Uuid}").hasAnyAuthority("ADMIN","TRAINEE","ATTENDEE")
                    
                    .requestMatchers("/api/categories/**").hasAnyAuthority("ADMIN","TRAINEE")

                    .requestMatchers("/api/topics/**").hasAnyAuthority("ADMIN","TRAINEE")
                    
                    .anyRequest().authenticated()
            ).userDetailsService(userDetailsSeviceImpl)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
//        	.formLogin()
//        	.loginPage("/login") // Custom login page URL
//        	.permitAll() // Allow all to access the login page
//        	.and()
//        	.logout()
//            .permitAll();

        return http.build();
    }


	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
		
	}
	@Bean
	
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception
	{
		return configuration.getAuthenticationManager();
	}

}