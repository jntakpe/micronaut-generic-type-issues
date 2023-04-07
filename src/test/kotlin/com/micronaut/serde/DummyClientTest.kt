package com.micronaut.serde

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockserver.client.MockServerClient
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.mockserver.model.MediaType

@MicronautTest
class DummyClientTest(@Inject private val mockClient: MockServerClient,
                      @Inject private val dummyClient: DummyClient) {

    @Test
    fun `test client wrapped list`() {
        mockClient
            .`when`(
                HttpRequest.request()
                    .withPath("/api/dummy/wrapped/list")
            )
            .respond(
                HttpResponse.response()
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withBody("""
                        {
                          "content": [
                            {
                              "name": "test-1"
                            },
                            {
                              "name": "test-2"
                            }
                          ]
                        }
                    """.trimIndent())
            )
        assertThat(dummyClient.wrappedList().content).isNotEmpty
        assertThat(dummyClient.wrappedList().content.map { it.name }).containsExactly("test-1", "test-2")
    }

    @Test
    fun `test client wrapped nested`() {
        mockClient
            .`when`(
                HttpRequest.request()
                    .withPath("/api/dummy/wrapped/nested")
            )
            .respond(
                HttpResponse.response()
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withBody("""
                        {
                          "content": {
                            "content": {
                              "name": "test-1"
                            }
                          }
                        }
                    """.trimIndent())
            )
        val wrappedNested = dummyClient.wrappedNested()
        assertThat(wrappedNested.content.content.name).isNotNull.isEqualTo("test-1")
    }

    @Test
    fun `test client wrapped simple`() {
        mockClient
            .`when`(
                HttpRequest.request()
                    .withPath("/api/dummy/wrapped/simple")
            )
            .respond(
                HttpResponse.response()
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withBody("""
                        {
                          "content":  {
                              "name": "test-1"
                            }
                        }
                    """.trimIndent())
            )
        assertThat(dummyClient.wrappedSimple().content.name).isEqualTo("test-1")
    }

    @Test
    fun `test client raw`() {
        mockClient
            .`when`(
                HttpRequest.request()
                    .withPath("/api/dummy/raw")
            )
            .respond(
                HttpResponse.response()
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withBody("""
                      [
                            {
                              "name": "test-1"
                            },
                            {
                              "name": "test-2"
                            }
                          ]
                    """.trimIndent())
            )
        assertThat(dummyClient.simpleList().map { it.name }).containsExactly("test-1", "test-2")
    }
}