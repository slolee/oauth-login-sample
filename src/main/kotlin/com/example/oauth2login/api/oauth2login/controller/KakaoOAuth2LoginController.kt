package com.example.oauth2login.api.oauth2login.controller

import com.example.oauth2login.api.oauth2login.service.OAuth2LoginService
import com.example.oauth2login.client.oauth2.kakao.KakaoOAuth2Client
import com.example.oauth2login.common.type.OAuth2Provider
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class KakaoOAuth2LoginController(
    private val oAuth2LoginService: OAuth2LoginService,
    private val kakaoOAuth2Client: KakaoOAuth2Client
) {

    @GetMapping("/oauth2/login/kakao")
    fun redirectLoginPage(response: HttpServletResponse) {
        response.sendRedirect(kakaoOAuth2Client.generateLoginPageUrl())
    }

    @GetMapping("/oauth2/callback/kakao")
    fun callback(@RequestParam(name = "code") authorizationCode: String): String {
        return oAuth2LoginService.login(OAuth2Provider.KAKAO, authorizationCode)
    }
}