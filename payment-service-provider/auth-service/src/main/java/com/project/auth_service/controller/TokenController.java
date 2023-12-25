package com.project.auth_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.auth_service.dto.AgencyInformationDto;
import com.project.auth_service.dto.TokenDataDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequiredArgsConstructor
public class TokenController {

    @PostMapping("/get-token")
    public TokenDataDto generateUrlToken(@RequestBody AgencyInformationDto agencyInformationDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(agencyInformationDto);
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        String base64 = Base64.getUrlEncoder().encodeToString(bytes);
        String token = Jwts.builder().claim("agencyInformation",base64).signWith(SignatureAlgorithm.HS256, "somesecret").compact();
        TokenDataDto tokenDataDto = new TokenDataDto();
        tokenDataDto.setToken(token);
        return tokenDataDto;
    }

    @PostMapping("/get-token-data")
    public AgencyInformationDto decodeUrlToken(@RequestBody TokenDataDto token) throws JsonProcessingException {
        String userJsonBase64 = Jwts.parser().setSigningKey("somesecret").parseClaimsJws(token.getToken()).getBody().get("agencyInformation", String.class);
        byte [] bytes = Base64.getDecoder().decode(userJsonBase64);
        String json = new String(bytes, StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, AgencyInformationDto.class);
    }
}
