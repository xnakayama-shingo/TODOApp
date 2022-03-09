package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Value("${env}")
//    private String env;

    @Autowired
    private DataSource dataSource;

    //���[�UID�ƃp�X���[�h���擾����SQL��
    // �g�p�ۂ͑S��TRUE�Őݒ�
    private static final String USER_SQL = "SELECT "
            + "id AS username, "
            + "password AS password, "
//            + "name, "
            + "true "
            + "FROM user1 "
            + "WHERE id = ?";

    private static final String ROLE_SQL = "SELECT "
            + "id AS username, "
            + "role "
            + "FROM user1 "
            + "WHERE id = ?";

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(USER_SQL)
                .authoritiesByUsernameQuery(ROLE_SQL)
                .passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.logout().logoutUrl("/api/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .logoutSuccessHandler((req, res, auth) -> res.setStatus(HttpServletResponse.SC_OK))
        ;

        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/vue/**").permitAll()
                .antMatchers("/api/**").authenticated()
        ;

        // Spring Security�f�t�H���g�ł́A�A�N�Z�X�����iROLE�j�ݒ肵���y�[�W�ɖ��F�؏�ԂŃA�N�Z�X�����403��Ԃ��̂ŁA
        // 401��Ԃ��悤�ɕύX
        http.exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));

        http.addFilter(new SpaAuthenticationFilter(authenticationManager(), bCryptPasswordEncoder()));
        http.csrf().ignoringAntMatchers("/api/login").csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }
}
