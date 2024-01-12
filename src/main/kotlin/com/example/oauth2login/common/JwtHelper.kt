package com.example.oauth2login.common

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Component
class JwtHelper(
    @Value("\${jwt.secret_key}") private val secretKey: String
) {

    private val key: Key by lazy {
        val encodedKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
        Keys.hmacShaKeyFor(encodedKey.toByteArray())
    }

    fun generateAccessToken(id: Long): String {
        val now = LocalDateTime.now().toInstant(ZoneOffset.UTC)
        return Jwts.builder()
            .setSubject(id.toString())
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(now.plusSeconds(60 * 60)))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }
}