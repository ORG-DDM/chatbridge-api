package org.ddm.chatbridge.user.service;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.ddm.chatbridge.common.TestFixture;
import org.ddm.chatbridge.common.exception.BadRequestException;
import org.ddm.chatbridge.user.dataaccess.UserRepository;
import org.ddm.chatbridge.user.domain.UserEntityFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.ddm.chatbridge.common.support.Status.USER_ALREADY_EXISTS;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateUserServiceTest {
    @InjectMocks
    private CreateUserService createUserService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;


    @Test
    void success() {
        //given
        var request = new RequestFixture().build();
        given(userRepository.findByUniqueId(eq(request.uniqueId())))
            .willReturn(Optional.empty());
        given(passwordEncoder.encode(eq(request.password())))
            .willReturn("testEncodedPassword");

        //when
        var throwable = catchThrowable(() -> createUserService.create(request));

        //then
        assertThat(throwable).isNull();
        verify(passwordEncoder, times(1)).encode(eq(request.password()));
    }

    @Test
    void failAlreadyExists() {
        //given
        var request = new RequestFixture().build();
        given(userRepository.findByUniqueId(eq(request.uniqueId())))
            .willReturn(Optional.of(new UserEntityFixture().build()));

        //when
        var throwable = catchThrowable(() -> createUserService.create(request));

        //then
        assertThat(throwable).isInstanceOf(BadRequestException.class);
        assertThat(throwable.getMessage()).isEqualTo(USER_ALREADY_EXISTS.message());
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    private static class RequestFixture implements TestFixture<CreateUserService.Request> {
        private String uniqueId = "testUniqueId";
        private String name = "testName";
        private String password = "testPassword";

        @Override
        public CreateUserService.Request build() {
            return new CreateUserService.Request(
                uniqueId,
                name,
                password
            );
        }
    }

}