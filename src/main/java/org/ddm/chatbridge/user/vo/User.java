package org.ddm.chatbridge.user.vo;

import org.ddm.chatbridge.user.domain.UserEntity;

public record User(
    Long id,
    String uniqueId,
    String name
){
    public static User from(UserEntity userEntity) {
        return new User(
            userEntity.id(),
            userEntity.uniqueId(),
            userEntity.name()
        );
    }
}
