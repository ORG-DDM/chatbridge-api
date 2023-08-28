package org.ddm.chatbridge.security.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.ddm.chatbridge.user.domain.UserEntity;

import java.io.Serializable;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public class UserDetails implements Serializable {
    private final Long id;
    private final String uniqueId;
    private final String name;

    public static UserDetails from(UserEntity userEntity) {
        return new UserDetails(
            userEntity.id(),
            userEntity.loginId(),
            userEntity.name()
        );
    }
}
