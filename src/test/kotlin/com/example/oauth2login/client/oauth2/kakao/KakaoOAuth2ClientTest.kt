package com.example.oauth2login.client.oauth2.kakao

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.web.client.RestClient

class KakaoOAuth2ClientTest {

    private lateinit var server: MockWebServer
    private val restClient = RestClient.create()

    @BeforeEach
    fun setUp() {
        server = MockWebServer()
    }

    @Test
    fun `getAccessToken - 정상조회시 동작 확인`() {
        // GIVEN
        val client = KakaoOAuth2Client(
            clientId = "CLIENT_ID",
            redirectUrl = "http://localhost:8080/oauth2/callback/kakao",
            authServerBaseUrl = server.url("/").toString(),
            resourceServerBaseUrl = server.url("/").toString(),
            restClient = restClient
        )
        MockResponse()
            .addHeader("Content-Type", MediaType.APPLICATION_JSON)
            .setResponseCode(200)
            .setBody(
                """
                    {
                        "access_token":"bbJ7t-hf8eJZSgflHk0SbgFkJIT6mx7IqKgKKiWPAAABjP0hJCUp9hBbJybEWQ",
                        "token_type":"bearer",
                        "refresh_token":"wiZbj8VafhZYxk0tdqt3MEPngHqdvT9nHCwKKiWPAAABjP0hJCIp9hBbJybEWQ",
                        "expires_in":21599,
                        "scope":"profile_nickname",
                        "refresh_token_expires_in":5183999
                    }
                """.trimIndent()
            )
            .let { server.enqueue(it) }

        // WHEN
        val accessToken = client.getAccessToken("TEST CODE")

        // THEN
        accessToken shouldBe "bbJ7t-hf8eJZSgflHk0SbgFkJIT6mx7IqKgKKiWPAAABjP0hJCUp9hBbJybEWQ"
    }

    @Test
    fun `getAccessToken - 에러발생시 동작 확인`() {
        // GIVEN
        val client = KakaoOAuth2Client(
            clientId = "CLIENT_ID",
            redirectUrl = "http://localhost:8080/oauth2/callback/kakao",
            authServerBaseUrl = server.url("/").toString(),
            resourceServerBaseUrl = server.url("/").toString(),
            restClient = restClient
        )
        MockResponse()
            .addHeader("Content-Type", MediaType.APPLICATION_JSON)
            .setResponseCode(500)
            .let { server.enqueue(it) }

        // WHEN & THEN
        shouldThrow<RuntimeException> {
            client.getAccessToken("TEST_CODE")
        }.let {
            it.message shouldBe "카카오 AccessToken 조회 실패"
        }
    }
}