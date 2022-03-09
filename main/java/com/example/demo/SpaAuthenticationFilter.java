package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
 
public class SpaAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
 
    private static final Logger LOGGER = LoggerFactory.getLogger(SpaAuthenticationFilter.class);
 
    private AuthenticationManager authenticationManager;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
 
    @Autowired
    private DataSource dataSource;
 
    public SpaAuthenticationFilter(AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
 
        // ログイン用のpathを変更する
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login", "POST"));
 
        // ログイン用のID/PWのパラメータ名を変更する
        setUsernameParameter("id");
        setPasswordParameter("password");
 
        // ログイン後にリダイレクトのリダイレクトを抑制
        this.setAuthenticationSuccessHandler((req, res, auth) -> res.setStatus(HttpServletResponse.SC_OK));
        // ログイン失敗時のリダイレクト抑制
        this.setAuthenticationFailureHandler((req, res, ex) -> res.setStatus(HttpServletResponse.SC_UNAUTHORIZED));
    }
 
    // 認証の処理
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            // requestパラメータからユーザ情報を読み取る
            UserForm userForm = new ObjectMapper().readValue(req.getInputStream(), UserForm.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userForm.getId(),
                            userForm.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

