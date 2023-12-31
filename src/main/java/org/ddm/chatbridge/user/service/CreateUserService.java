package org.ddm.chatbridge.user.service;

import org.ddm.chatbridge.common.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.ddm.chatbridge.user.domain.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ddm.chatbridge.user.dataaccess.UserRepository;

import static org.ddm.chatbridge.common.support.Status.USER_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
public class CreateUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void create(Request request) {
        checkAlreadyExists(request.loginId());

        var user = createUserEntity(request);

        userRepository.save(user);
    }

    private UserEntity createUserEntity(Request request) {
        return new UserEntity(
            request.loginId(),
            request.name(),
            passwordEncoder.encode(request.password())
        );
    }

    private void checkAlreadyExists(String loginId) {
        userRepository.findByLoginId(loginId)
            .ifPresent(user -> {
                throw new BadRequestException(USER_ALREADY_EXISTS);
            });
    }

    public record Request(
        String loginId,
        String name,
        String password
    ) {
    }
}
