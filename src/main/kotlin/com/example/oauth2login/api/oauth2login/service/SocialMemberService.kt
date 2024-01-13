package com.example.oauth2login.api.oauth2login.service

import com.example.oauth2login.client.oauth2.OAuth2LoginUserInfo
import com.example.oauth2login.domain.socialmember.entity.SocialMember
import com.example.oauth2login.domain.socialmember.repository.SocialMemberRepository
import org.springframework.stereotype.Service

@Service
class SocialMemberService(
    private val socialMemberRepository: SocialMemberRepository
) {

    fun registerIfAbsent(userInfo: OAuth2LoginUserInfo): SocialMember {
        return socialMemberRepository.findByProviderAndProviderId(userInfo.provider, userInfo.id) ?: run {
            val socialMember = SocialMember(
                provider = userInfo.provider,
                providerId = userInfo.id,
                nickname = userInfo.nickname
            )
            socialMemberRepository.save(socialMember)
        }
    }
}