package com.example.oauth2login.client.oauth2

import com.example.oauth2login.common.type.OAuth2Provider

interface OAuth2Client {
    fun generateLoginPageUrl(): String
    fun getAccessToken(authorizationCode: String): String
    fun retrieveUserInfo(accessToken: String): OAuth2LoginUserInfo
    fun isSupport(provider: OAuth2Provider): Boolean
}