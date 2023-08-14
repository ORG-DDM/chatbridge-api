package org.ddm.chatbridge.user.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.ddm.chatbridge.common.FixtureReflectionUtils;
import org.ddm.chatbridge.common.TestFixture;

@Getter
@Setter
@Accessors(chain = true)
public class UserEntityFixture implements TestFixture<UserEntity> {
    private String uniqueId = "testUniqueId";
    private String name = "testName";
    private String password = "testPassword";

    @Override
    public UserEntity build() {
        var user = new UserEntity();
        FixtureReflectionUtils.reflect(user, this);
        return user;
    }
}
