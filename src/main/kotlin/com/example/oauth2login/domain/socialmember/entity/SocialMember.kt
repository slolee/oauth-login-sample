package com.example.oauth2login.domain.socialmember.entity

import com.example.oauth2login.common.type.OAuth2Provider
import jakarta.persistence.*

@Entity
class SocialMember(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "social_member_id")
    var id: Long? = null,

    @Enumerated(EnumType.STRING)
    val provider: OAuth2Provider,
    val providerId: String,
    val nickname: String
)