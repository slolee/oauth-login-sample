package com.example.oauth2login.api.oauth2login.controller

import com.example.oauth2login.api.oauth2login.service.OAuth2LoginService
import com.example.oauth2login.client.oauth2.naver.NaverOAuth2Client
import com.example.oauth2login.common.type.OAuth2Provider
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class NaverOAuth2LoginController(
    private val naverOAuth2Client: NaverOAuth2Client,
    private val oAuth2LoginService: OAuth2LoginService
) {

    @GetMapping("/oauth2/login/naver")
    fun redirectLoginPage(response: HttpServletResponse) {
        response.sendRedirect(naverOAuth2Client.generateLoginPageUrl())
    }

    @GetMapping("/oauth2/callback/naver")
    fun callback(@RequestParam(name = "code") authorizationCode: String): String {
        return oAuth2LoginService.login(OAuth2Provider.NAVER, authorizationCode)
    }
}