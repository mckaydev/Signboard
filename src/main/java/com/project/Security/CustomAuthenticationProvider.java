package com.project.Security;

import com.project.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final MemberService memberService;

    @Autowired
    public CustomAuthenticationProvider(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = memberService.loadUserByUsername(username);

//        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
//            throw new BadCredentialsException("password error");
//        }
        System.out.println("Authorities: " + userDetails.getAuthorities());
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(authentication.getDetails());

        // 일반적으로 인증이 완료된 사용자 토큰은 비밀번호를 제거하여 만든다.
        return authToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
