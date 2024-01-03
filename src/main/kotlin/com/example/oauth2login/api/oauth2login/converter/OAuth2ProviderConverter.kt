package com.example.oauth2login.api.oauth2login.converter

import com.example.oauth2login.common.type.OAuth2Provider
import org.springframework.core.convert.converter.Converter

class OAuth2ProviderConverter : Converter<String, OAuth2Provider> {

    override fun convert(source: String): OAuth2Provider {
        return runCatching {
            OAuth2Provider.valueOf(source.uppercase())
        }.getOrElse {
            throw IllegalArgumentException()
        }
    }
}