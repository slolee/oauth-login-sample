package com.example.oauth2login.client.oauth2

import com.example.oauth2login.common.type.OAuth2Provider
import jakarta.transaction.NotSupportedException
import org.springframework.stereotype.Service

@Service
class OAuth2ClientService(
    private val clients: List<OAuth2Client>
) {

    fun login(provider: OAuth2Provider, authorizationCode: String): OAuth2LoginUserInfo {
        val client = this.selectClient(provider)
        return client.getAccessToken(authorizationCode)
            .let { client.retrieveUserInfo(it) }
    }

    private fun selectClient(provider: OAuth2Provider): OAuth2Client {
        return clients.find { it.isSupport(provider) }
            ?: throw NotSupportedException()
    }
}