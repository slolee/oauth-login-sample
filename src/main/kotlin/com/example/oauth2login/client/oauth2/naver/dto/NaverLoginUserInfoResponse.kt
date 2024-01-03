package com.example.oauth2login.client.oauth2.naver.dto

import com.example.oauth2login.client.oauth2.OAuth2LoginUserInfo
import com.example.oauth2login.common.type.OAuth2Provider
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
class NaverLoginUserInfoResponse(
    id: String,
    nickname: String
) : OAuth2LoginUserInfo(
    provider = OAuth2Provider.NAVER,
    id = id,
    nickname = nickname
)