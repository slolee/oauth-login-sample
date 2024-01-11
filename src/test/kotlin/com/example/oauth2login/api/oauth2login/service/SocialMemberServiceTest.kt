package com.example.oauth2login.api.oauth2login.service

import com.example.oauth2login.client.oauth2.OAuth2LoginUserInfo
import com.example.oauth2login.common.BaseRepositoryTest
import com.example.oauth2login.common.type.OAuth2Provider
import com.example.oauth2login.domain.socialmember.entity.SocialMember
import com.example.oauth2login.domain.socialmember.repository.SocialMemberRepository
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class SocialMemberServiceTest @Autowired constructor(
    private val socialMemberRepository: SocialMemberRepository
) : BaseRepositoryTest() {

    private val socialMemberService = SocialMemberService(socialMemberRepository)

    @Test
    fun `registerIfAbsent - 사용자 정보 없을 경우 회원가입처리`() {
        // GIVEN
        socialMemberRepository.deleteAll()
        val userInfo = OAuth2LoginUserInfo(provider = OAuth2Provider.KAKAO, id = "9999", nickname = "ch4njun")

        // WHEN
        val result = socialMemberService.registerIfAbsent(userInfo)

        // THEN
        result.id shouldNotBe null
        result.id shouldBe 1L
        result.provider shouldBe OAuth2Provider.KAKAO
        result.providerId shouldBe "9999"
        result.nickname shouldBe "ch4njun"
        socialMemberRepository.findAll().toList().let {
            it.size shouldBe 1
            it[0].id shouldBe 1L
            it[0].provider shouldBe OAuth2Provider.KAKAO
            it[0].providerId shouldBe "9999"
            it[0].nickname shouldBe "ch4njun"
        }
    }

    @Test
    fun `registerIfAbsent - 사용자 정보가 이미 존재하는 경우`() {
        // GIVEN
        val 기존사용자정보 = socialMemberRepository.save(
            SocialMember(
                provider = OAuth2Provider.KAKAO,
                providerId = "9999",
                nickname = "ch4njun"
            )
        )
        val userInfo = OAuth2LoginUserInfo(provider = OAuth2Provider.KAKAO, id = "9999", nickname = "ch4njun")

        // WHEN
        val result = socialMemberService.registerIfAbsent(userInfo)

        // THEN
        result shouldBe 기존사용자정보
        socialMemberRepository.findAll().toList().size shouldBe 1L
    }
}