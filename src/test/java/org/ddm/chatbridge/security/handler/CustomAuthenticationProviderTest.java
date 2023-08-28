package org.ddm.chatbridge.security.handler;

import org.assertj.core.api.Assertions;
import org.ddm.chatbridge.security.vo.UserDetails;
import org.ddm.chatbridge.user.dataaccess.UserRepository;
import org.ddm.chatbridge.user.domain.UserEntityFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CustomAuthenticationProviderTest {
    @InjectMocks
    private CustomAuthenticationProvider provider;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @DisplayName("인증 성공")
    @Test
    void success() {
        //given
        var user = new UserEntityFixture().build();
        var authentication = new TestAuthentication(user.loginId(), user.password());
        given(userRepository.findByLoginId(eq(user.loginId())))
            .willReturn(Optional.of(user));
        given(passwordEncoder.matches(eq(authentication.password()), eq(user.password())))
            .willReturn(true);

        //when
        var throwable = Assertions.catchThrowable(() -> provider.authenticate(authentication));

        //then
        assertThat(throwable).isNull();
    }

    @DisplayName("인증 실패")
    @Test
    void fail() {
        //given
        var user = new UserEntityFixture().build();
        var authentication = new TestAuthentication(user.loginId(), "test1234");
        given(userRepository.findByLoginId(eq(user.loginId())))
            .willReturn(Optional.of(user));
        given(passwordEncoder.matches(eq(authentication.password()), eq(user.password())))
            .willReturn(false);

        //when
        var throwable = Assertions.catchThrowable(() -> provider.authenticate(authentication));

        //then
        assertThat(throwable).isExactlyInstanceOf(BadCredentialsException.class);
    }

    record TestAuthentication(
        String loginId,
        String password
    ) implements Authentication {

        @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return password;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return loginId;
            }
        }
}