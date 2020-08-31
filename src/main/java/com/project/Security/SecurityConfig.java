package com.project.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
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
                .antMatchers("/resources/js/**")
                .antMatchers("/resources/css/**")
                .antMatchers("/resources/img/**")
                .antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers("/cropImage").hasRole("USER")
//                .antMatchers("/bookmarkedSearch").hasRole("USER")
//                .antMatchers("/priorSearch").hasRole("USER")
                .antMatchers("/").permitAll()
                .antMatchers("/member/**").permitAll()
//                .antMatchers("/**").permitAll()
                .anyRequest().hasRole("USER")
                .and()
                .formLogin()
                    .loginPage("/member/login")
                    .loginProcessingUrl("/member/loginProcess")
                    .successForwardUrl("/")
                    // 간판 업로드시 예외처리 위해 true값
                    .defaultSuccessUrl("/", true)
                    .permitAll()
                .and()
                .logout()
                .logoutUrl("/member/logout")
                .logoutSuccessUrl("/")
                .and().csrf().disable();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
}
