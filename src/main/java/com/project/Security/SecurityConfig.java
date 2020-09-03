package com.project.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// web.xml에 등록하지 않고 자바 기반의 어노테이션을 사용하여 스프링 시큐리티를 설정
@Configuration
@EnableWebSecurity
@ComponentScan("com.project")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationProvider authenticationProvider;

    @Autowired
    public SecurityConfig(CustomAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**")
                .antMatchers("/resources/js/**")
                .antMatchers("/resources/css/**")
                .antMatchers("/resources/img/**")
                .antMatchers("/img/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers("/cropImage").hasRole("USER")
//                .antMatchers("/bookmarkedSearch").hasRole("USER")
//                .antMatchers("/priorSearch").hasRole("USER")
                .antMatchers("/", "/example", "/cropExample",
                        "/cropImage", "/cropResult", "/storeData").permitAll()
                .antMatchers("/member/**").permitAll()
//                .antMatchers("/**").permitAll()
                .anyRequest().hasRole("USER")
                .and()
                .formLogin()
                    .loginPage("/member/login")
                    .loginProcessingUrl("/member/loginProcess")
                    .failureUrl("/member/login")
                    // 간판 업로드시 예외처리 위해 true값
                    .defaultSuccessUrl("/", true)
                .and()
                .logout()
                .logoutUrl("/member/logout")
                .logoutSuccessUrl("/")
                .and().csrf().disable();
//        http.sessionManagement()
//                // 세션이 끊겼을 때 이동할 페이지
//                .invalidSessionUrl("/member/login")
//                // 최대 허용 가능 중복 세션 개수
//                .maximumSessions(1)
//                // 중복 로그인이 일어 났을 경우 이동할 페이지
//                .expiredUrl("/member/login");
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
