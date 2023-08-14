package org.ddm.chatbridge.config;

import org.ddm.chatbridge.common.web.CommonObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfiguration {
    @Bean
    ObjectMapper objectMapper() {
        return new CommonObjectMapper();
    }
}
