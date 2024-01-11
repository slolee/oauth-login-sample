package com.example.oauth2login.api.oauth2login.converter

import com.example.oauth2login.common.type.OAuth2Provider
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class OAuth2ProviderConverterTest {

    private val converter = OAuth2ProviderConverter()

    @Test
    fun `소문자 들어왔을 때 컨버팅 되는지 확인`() {
        // GIVEN
        val source = "kakao"

        // WHEN
        val provider = converter.convert(source)

        // THEN
        provider shouldBe OAuth2Provider.KAKAO
    }

    @Test
    fun `대문자 들어왔을 때 컨버팅 되는지 확인`() {
        // GIVEN
        val source = "KAKAO"

        // WHEN
        val provider = converter.convert(source)

        // THEN
        provider shouldBe OAuth2Provider.KAKAO
    }

    @Test
    fun `잘못된 문자 들어왔을 때 에러나는지 확인`() {
        // GIVEN
        val source = "k4k4o"

        // WHEN & THEN
        shouldThrow<IllegalArgumentException> {
            converter.convert(source)
        }
    }
}