package com.example.gonggalbbang.global.security.jwt;

import com.example.gonggalbbang.domain.auth.domain.AuthRepository;
import com.example.gonggalbbang.domain.auth.presentation.dto.UserVO;
import com.example.gonggalbbang.domain.auth.presentation.dto.response.LoginRes;
import com.example.gonggalbbang.domain.auth.presentation.dto.response.RefreshRes;
import com.example.gonggalbbang.global.security.auth.AuthDetails;
import com.example.gonggalbbang.global.security.jwt.enums.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtProperties jwtProperties;
    private final AuthRepository authRepository;

    public String getUsername(String token){
        return (String) Jwts.parserBuilder()
                .setSigningKey(jwtProperties.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("username");
    }

    public String getToken(final String token) {
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) { // 토큰이 존재하고 "Bearer "로 시작할때
            return token.substring(7); // "Bearer " 제거
        }
        return token; // 아니면 그냥 token return
    }

    public boolean isWrongType(final Jws<Claims> claims, final TokenType tokenType) {
        return !(claims.getHeader().get(Header.JWT_TYPE).equals(tokenType.toString()));
    }

    public Jws<Claims> getClaims(final String token) {
        try {
            return Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("만료된 토큰");
        } catch (UnsupportedJwtException e) {
            throw new IllegalArgumentException("지원되지 않는 토큰");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 토큰");
        }
    }

//    public Authentication getAuthentication(final String token){
//        final Jws<Claims> claims = getClaims(token);
//
//        if (isWrongType(claims, TokenType.ACCESS))
//            throw new  CustomException(JwtErrorCode.TOKEN_TYPE_ERROR);
//
//        UserVO userVO = authRepository.findById(claims
//                        .getBody()
//                        .getSubject())
//                .map(UserVO::fromUser)
//                .orElseThrow(()-> new IllegalArgumentException("유저가 존재하지 않습니다") );
//
//        final AuthDetails details = new AuthDetails(userVO);
//
//        // 사용자 인증 정보를 생성 후 관리 (객체 생성을 통해)
//        // UsernamePasswordAuthenticationToken(Principal, Credentials, Authorities) // 사용자의 주요정보, 인증과정의 비밀번호 저장 안하려고 null , 사용자의 권한 목록
//        return new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
//    }

    public Authentication getAuthentication(final String token) {
        final Jws<Claims> claims = getClaims(token);

        // subject가 null인지 확인
        String username = claims.getBody().getSubject();
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("토큰에서 username(subject)이 누락되었습니다.");
        }

        // 데이터베이스에서 사용자 조회
        UserVO userVO = authRepository.findById(username)
                .map(UserVO::fromUser)
                .orElseThrow(() -> new IllegalArgumentException("해당 username의 사용자가 존재하지 않습니다."));

        // 인증 객체 생성
        final AuthDetails details = new AuthDetails(userVO);
        return new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
    }

    public LoginRes createToken(UserVO userVO){
        return LoginRes.builder()
                .accessToken(createAccessToken(userVO))
                .refreshToken(createRefreshToken(userVO))
                .build();
    }

    private String createAccessToken(UserVO userVO){
        return Jwts.builder()
                .setSubject(userVO.username()) // username을 subject로 설정
                .setHeaderParam(Header.JWT_TYPE, TokenType.ACCESS)
                .claim("username",userVO.username())
                .claim("role",userVO.role())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessExp()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    private String createRefreshToken(UserVO userVO){
        return Jwts.builder()
                .setSubject(userVO.username()) // username을 subject로 설정
                .setHeaderParam(Header.JWT_TYPE, TokenType.REFRESH)
                .claim("username",userVO.username())
                .claim("role",userVO.role())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshExp()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    public RefreshRes refreshToken(UserVO userVO){ // 토큰 리프레시
        return RefreshRes.builder()
                .accessToken(createAccessToken(userVO)).build();
    }
}