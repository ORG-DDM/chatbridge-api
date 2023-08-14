package org.ddm.chatbridge.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.ddm.chatbridge.common.entity.BaseEntity;

@Entity
@Table(name = "user")
@Getter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String uniqueId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    public UserEntity(String uniqueId,
                      String name,
                      String password) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.password = password;
    }
}
