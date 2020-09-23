package com.project.Security;

import com.project.member.Member;
import com.project.member.service.MemberService;
import com.project.service.ImageService;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final MemberService memberService;
    private final ImageService imageService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(MemberService memberService,
                                        ImageService imageService, PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.imageService = imageService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Member userDetails = memberService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new UsernameNotFoundException(username);
        }
//        else if (!password.equals(userDetails.getPassword())) {
//            throw new BadCredentialsException(password);
//        }
        else if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("password error");
        }
        else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException(username);
        }
        else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException(username);
        }
        else if (!userDetails.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(username);
        }
        else if (!userDetails.isEnabled()) {
            throw new DisabledException(username);
        }

        if (username.equals("test")) {
            imageService.testDataInsert();
        }
//        System.out.println("Authorities: " + userDetails.getAuthorities());
//        System.out.println("Password: " + password + ", " + userDetails.getPassword());

        // 일반적으로 인증이 완료된 사용자 토큰은 비밀번호를 제거하여 만든다.
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,null, userDetails.getAuthorities());
        authToken.setDetails(authentication.getDetails());

        return authToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
