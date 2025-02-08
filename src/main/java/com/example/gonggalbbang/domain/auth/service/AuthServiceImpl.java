package com.example.gonggalbbang.domain.auth.service;

import com.example.gonggalbbang.domain.auth.domain.AuthRepository;
import com.example.gonggalbbang.domain.auth.domain.entity.User;
import com.example.gonggalbbang.domain.auth.exception.AuthErrorCode;
import com.example.gonggalbbang.domain.auth.presentation.dto.UserVO;
import com.example.gonggalbbang.domain.auth.presentation.dto.request.JoinReq;
import com.example.gonggalbbang.domain.auth.presentation.dto.request.LoginReq;
import com.example.gonggalbbang.domain.auth.presentation.dto.request.RefreshReq;
import com.example.gonggalbbang.domain.auth.presentation.dto.response.LoginRes;
import com.example.gonggalbbang.domain.auth.presentation.dto.response.RefreshRes;
import com.example.gonggalbbang.global.common.Response;
import com.example.gonggalbbang.global.common.ResponseData;
import com.example.gonggalbbang.global.exception.CustomException;
import com.example.gonggalbbang.global.security.jwt.JwtUtil;
import com.example.gonggalbbang.global.security.jwt.enums.TokenType;
import com.example.gonggalbbang.global.security.jwt.exception.JwtErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder;

    @Override
    public Response join(JoinReq joinReq){

        if(authRepository.existsById(joinReq.username())){
            throw new CustomException(AuthErrorCode.USER_ALREADY);
        }

        authRepository.save(JoinReq.fromJoinReq(joinReq, encoder.encode(joinReq.password())));

        return Response.created("회원가입 성공");
    }

    @Override
    public ResponseData<LoginRes> login(LoginReq loginReq){

        User user = authRepository.findById(loginReq.username())
                .orElseThrow(() -> new  CustomException(AuthErrorCode.USER_NOT_FOUND));

        if(!encoder.matches(loginReq.password(), user.getPassword()))
            throw new CustomException(AuthErrorCode.PASSWORD_WRONG);

        UserVO userVO = UserVO.fromUser(user);

        return ResponseData.ok("로그인 성공",jwtUtil.createToken(userVO));

    }

    @Override
    public ResponseData<RefreshRes> refresh(RefreshReq refreshReq){
        String token = jwtUtil.getToken(refreshReq.refreshToken());
        Jws<Claims> claims = jwtUtil.getClaims(token);

        if(jwtUtil.isWrongType(claims, TokenType.REFRESH))
            throw new CustomException(JwtErrorCode.TOKEN_TYPE_ERROR);

        UserVO userVO = authRepository.findById(jwtUtil.getUsername(token))
                .map(UserVO::fromUser)
                .orElseThrow(() -> new CustomException(AuthErrorCode.USER_NOT_FOUND));

        return ResponseData.ok("리프레시 성공", jwtUtil.refreshToken(userVO));
    }
}
