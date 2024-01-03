package com.example.oauth2login.common

import org.springframework.stereotype.Component

@Component
class JwtHelper {

    fun generateAccessToken(id: Long): String {
        // TODO
        return "SampleAccessToken"
    }
}