package com.skrbomb.eCommerce.security;


import com.skrbomb.eCommerce.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
@Slf4j
public class JwtUtils {

    private static final long EXPIRATION_TIME_IN_MILLISEC=1000L*60L*60L*24L*30L*6L;//expires 6 months

    private SecretKey secretKey;

    @Value("${secretJwtString}")
    private String secretJwtString;
    //make sure the value in the application.properties is equal or more than 32 characters long


    @PostConstruct
    private void init(){
        byte[] keyBytes=secretJwtString.getBytes(StandardCharsets.UTF_8);
        this.secretKey=new SecretKeySpec(keyBytes,"HmacSHA256");
    }

    public String generateToken(User user){
        String username= user.getEmail();
        return generateToken(username);
    }

    public String generateToken(String username){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME_IN_MILLISEC))
                .signWith(secretKey)
                .compact();
    }

    public String getUsernameFromToken(String token){
        return extractClaims(token, Claims::getSubject);
    }

    /*構建出來一個配置好的JwtParser實例
    * 解析帶簽名的 JWT，並將結果返回為 SignedClaims 對象，包含 JWT 的完整內容:
    * Header：JWT 的元數據，如加密算法 (alg)。
    * Payload：JWT 的聲明部分（即 Claims）。
    * Signature：JWT 的簽名，用於驗證數據完整性。*/
    private <T> T extractClaims(String token, Function<Claims,T> claimsTFunction){
        return claimsTFunction.apply(
                Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload());
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username=getUsernameFromToken(token);
        return  (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        return extractClaims(token,Claims::getExpiration).before(new Date());
    }
}
