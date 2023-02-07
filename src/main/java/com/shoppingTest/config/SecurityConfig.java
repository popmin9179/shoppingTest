package com.shoppingTest.config;

import com.shoppingTest.common.security.CustomAccessDeniedHandler;
import com.shoppingTest.common.security.CustomLoginSuccessHandler;
import com.shoppingTest.common.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@EnableWebSecurity
// 시큐리티 애너테이션 활성화를 위한 설정
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/auth/login").permitAll()
                .antMatchers("/user/setup", "/user/register", "/user/registerSuccess").permitAll()
                .antMatchers("/user/**").hasRole("ADMIN")
                .antMatchers("/codegroup/**").hasRole("ADMIN")
                .antMatchers("/codedetail/**").hasRole("ADMIN")
                // 회원 게시판 웹 경로 보안지정
                .antMatchers("/board/list", "/board/read").permitAll()
                .antMatchers("/board/remove").hasAnyRole("MEMBER", "ADMIN")
                .antMatchers("/board/**").hasRole("MEMBER")
                // 공지사항 웹 경로 보안 지정
                .antMatchers("/notice/list", "/notice/read").permitAll()
                .antMatchers("/notice/**").hasRole("ADMIN")
                // 상품 관리 웹 경로 보안 지정
                .antMatchers("/item/list", "/item/read", "/item/display").permitAll()
                .antMatchers("/item/picture").hasAnyRole("MEMBER", "ADMIN")
                // 상품 구매 웹 경로 보안 지정
                .antMatchers("/item/buy", "/item/success").hasRole("MEMBER")
                .antMatchers("/item/**").hasRole("ADMIN")
                // 코인 충전 웹 경로 보안 지정
                .antMatchers("/coin/**").hasRole("MEMBER")
                // 구매 상품 웹 경로 보안 지정
                .antMatchers("/useritem/**").hasRole("MEMBER")
                // 공개 자료실 웹 경로 보안 지정
                .antMatchers("/pds/list", "/pds/read", "/pds/getAttach/**", "/pds/downloadFile").permitAll()
                .antMatchers("/pds/**").hasRole("ADMIN")
                // 에러 페이지 웹 경로 보안 지정
                .antMatchers("/error/**").permitAll()
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/login")
                .successHandler(authenticationSuccessHandler());

        http.logout()
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/");

        http.exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler());

        http.rememberMe()
                .key("KsJjE4VzhhEW3juG")
                .tokenRepository(createJDBCRepository())
                .tokenValiditySeconds(60*60*24);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(customUserDetailService()).passwordEncoder(passwordEncoder());
    }

    @Bean
    public UserDetailsService customUserDetailService(){
        return new CustomUserDetailsService();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new CustomLoginSuccessHandler();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private PersistentTokenRepository createJDBCRepository(){
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);

        return repo;
    }
}
