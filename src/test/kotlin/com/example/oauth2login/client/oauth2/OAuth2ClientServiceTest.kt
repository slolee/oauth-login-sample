package com.example.oauth2login.client.oauth2

import com.example.oauth2login.client.oauth2.kakao.KakaoOAuth2Client
import com.example.oauth2login.client.oauth2.naver.NaverOAuth2Client
import com.example.oauth2login.common.type.OAuth2Provider
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import jakarta.transaction.NotSupportedException
import org.junit.jupiter.api.Test

class OAuth2ClientServiceTest {

    @Test
    fun `generateLoginPageUrl - 올바른 Client 선택후 정상동작 확인`() {
        // GIVEN
        val kakaoOAuth2Client = mockk<KakaoOAuth2Client>()
        val naverOAuth2Client = mockk<NaverOAuth2Client>()
        val oAuth2ClientService = OAuth2ClientService(clients = listOf(kakaoOAuth2Client, naverOAuth2Client))

        every { kakaoOAuth2Client.supports(any()) } returns false
        every { kakaoOAuth2Client.supports(OAuth2Provider.KAKAO) } returns true
        every { naverOAuth2Client.supports(any()) } returns false
        every { naverOAuth2Client.supports(OAuth2Provider.NAVER) } returns true

        every { kakaoOAuth2Client.generateLoginPageUrl() } returns "KAKAO REDIRECT URL"
        every { naverOAuth2Client.generateLoginPageUrl() } returns "NAVER REDIRECT URL"

        // WHEN
        val url = oAuth2ClientService.generateLoginPageUrl(OAuth2Provider.KAKAO)

        // THEN
        url shouldBe "KAKAO REDIRECT URL"
    }

    @Test
    fun `generateLoginPageUrl - 지원하지 않는 OAuth Provider 일 경우 에러발생 확인`() {
        // GIVEN
        val naverOAuth2Client = mockk<NaverOAuth2Client>()
        val oAuth2ClientService = OAuth2ClientService(clients = listOf(naverOAuth2Client))

        every { naverOAuth2Client.supports(any()) } returns false
        every { naverOAuth2Client.supports(OAuth2Provider.NAVER) } returns true

        // WHEN & THEN
        shouldThrow<NotSupportedException> {
            oAuth2ClientService.generateLoginPageUrl(OAuth2Provider.KAKAO)
        }.let {
            it.message shouldBe "지원하지 않는 OAuth Provider 입니다."
        }
    }
}