package org.ddm.chatbridge.user.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.ddm.chatbridge.common.web.ApiResponse;
import org.ddm.chatbridge.user.service.CreateUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class CreateUserController {
    private final CreateUserService createUserService;

    @PostMapping("/users")
    public ApiResponse<Void> create(@RequestBody @Valid Request request) {
        var serviceRequest = new CreateUserService.Request(
            request.loginId(),
            request.name(),
            request.password()
        );

        createUserService.create(serviceRequest);

        return ApiResponse.success();
    }

    record Request(
        @NotBlank(message = "id는 필수입니다.")
        String loginId,
        @NotBlank(message = "이름은 필수입니다.")
        String name,
        @NotBlank(message = "비밀번호는 필수입니다.")
        String password
    ) {
    }
}