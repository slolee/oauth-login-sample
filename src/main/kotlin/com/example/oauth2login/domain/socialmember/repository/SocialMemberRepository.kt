package com.example.oauth2login.domain.socialmember.repository

import com.example.oauth2login.common.type.OAuth2Provider
import com.example.oauth2login.domain.socialmember.entity.SocialMember
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SocialMemberRepository : CrudRepository<SocialMember, Long> {

    fun findByProviderAndProviderId(kakao: OAuth2Provider, id: String): SocialMember?
}