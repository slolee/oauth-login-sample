package com.example.oauth2login.api.oauth2login.service

import com.example.oauth2login.client.oauth2.OAuth2ClientService
import com.example.oauth2login.common.JwtHelper
import com.example.oauth2login.common.type.OAuth2Provider
import org.springframework.stereotype.Service

// Mission! 얘가 카카오 모르게하자
//  + SocialMemberService 도 마찬가지
@Service
class OAuth2LoginService(
    private val oAuth2ClientService: OAuth2ClientService,
    private val socialMemberService: SocialMemberService,
    private val jwtHelper: JwtHelper
) {

    fun login(provider: OAuth2Provider, authorizationCode: String): String {
        return oAuth2ClientService.login(provider, authorizationCode)
            .let { socialMemberService.registerIfAbsent(it) }
            .let { jwtHelper.generateAccessToken(it.id!!) }
    }
}