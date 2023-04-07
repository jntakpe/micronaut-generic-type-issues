package com.micronaut.serde

import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import org.mockserver.client.MockServerClient

@Factory
class MicronautTestConfiguration {

    @Bean
    fun mockServerClient(mockServerProperties: MockServerProperties): MockServerClient {
        return MockServerClient(mockServerProperties.host, mockServerProperties.port)
    }
}