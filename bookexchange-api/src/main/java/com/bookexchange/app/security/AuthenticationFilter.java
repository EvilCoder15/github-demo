package com.bookexchange.app.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter

{

    private AuthenticationManager authenticationManager;


    public AuthenticationFilter(AuthenticationManager authenticationManager)

    {

        this.authenticationManager = authenticationManager;

        setFilterProcessesUrl("/login");

    }


    @Override

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException

    {

        try

        {

            com.bookexchange.app.model.User creds = new ObjectMapper().readValue(request.getInputStream(), com.bookexchange.app.model.User.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(),new ArrayList<>()));

        }

        catch(IOException e)

        {

            throw new RuntimeException("Could not read request" + e);

        }

    }


    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication)

    {

        String token = Jwts.builder()

                .setSubject(((User) authentication.getPrincipal()).getUsername())

                .setExpiration(new Date(System.currentTimeMillis() + 864_000_000))

                .signWith(SignatureAlgorithm.HS512, "SecretKeyToGenJWTs".getBytes())

                .compact();

        response.addHeader("Authorization","Bearer " + token);

    }

}