package org.ddm.chatbridge.common;

import org.ddm.chatbridge.common.web.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @GetMapping("/test")
    ApiResponse<Void> test() {
        return ApiResponse.success();
    }
}
