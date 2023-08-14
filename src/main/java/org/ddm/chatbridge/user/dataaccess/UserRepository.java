package org.ddm.chatbridge.user.dataaccess;

import org.ddm.chatbridge.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUniqueId(String uniqueId);

}
