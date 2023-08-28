package org.ddm.chatbridge.security.handler;

import lombok.RequiredArgsConstructor;
import org.ddm.chatbridge.security.vo.UserDetails;
import org.ddm.chatbridge.user.dataaccess.UserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var user = userRepository.findByLoginId(authentication.getName())
            .orElseThrow(() -> new UsernameNotFoundException("Not Found User"));
        var reqPassword = authentication.getCredentials().toString();

        if(!passwordEncoder.matches(reqPassword, user.password())) {
            throw new BadCredentialsException("password is not matched");
        }

        var userDetails = UserDetails.from(user);

        return new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            Set.of(() -> "ROLE_USER") // note: admin, user 구분 생기면 수정 바람
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
