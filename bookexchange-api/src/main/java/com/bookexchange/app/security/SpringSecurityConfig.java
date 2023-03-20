package com.bookexchange.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter

{

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;

    private static final String[] AUTH_WHITELIST = {

            "/v2/api-docs",

            "/swagger-resources",

            "/swagger-resources/**",

            "/configuration/ui",

            "/configuration/security",

            "/swagger-ui.html",

            "/webjars/**"

    };


    protected void configure(HttpSecurity httpSecurity) throws Exception

    {
    	
        httpSecurity.cors().and().csrf().disable().authorizeRequests()

        
                .antMatchers(HttpMethod.POST, "/cachedemo/v1/users/signup").permitAll()

                .anyRequest().authenticated()

                .and().addFilter(new AuthenticationFilter(authenticationManager))

                .addFilter(new AuthorizationFilter(authenticationManager))

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        	
        	
//        http.csrf().disable();
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.authorizeRequests().requestMatchers("/", "/login/**").permitAll();
//        http.authorizeRequests().requestMatchers(HttpMethod.GET, "/admin/manage-app/**").hasAnyAuthority("ROLE_USER");
//        http.authorizeRequests().anyRequest().authenticated();
//        http.addFilter(new AuthenticationFilter(authenticationManager());
//        http.addFilter(new AuthorizationFilter(authenticationManager());
      

    }


	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception

   {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        authenticationManager=authenticationManagerBuilder.build();
       
    }

    @Bean

    CorsConfigurationSource corsConfigurationSource()

    {

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**",new CorsConfiguration().applyPermitDefaultValues());

        return source;

    }

}
