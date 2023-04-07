package com.micronaut.serde

import io.micronaut.context.annotation.ConfigurationProperties

@ConfigurationProperties("tc.mockserver")
interface MockServerProperties {

    val host: String
    val port: Int
}