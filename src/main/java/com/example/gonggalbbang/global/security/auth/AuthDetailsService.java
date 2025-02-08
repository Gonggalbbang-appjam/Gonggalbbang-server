package com.example.gonggalbbang.global.security.auth;

import com.example.gonggalbbang.domain.auth.domain.AuthRepository;
import com.example.gonggalbbang.domain.auth.domain.entity.User;
import com.example.gonggalbbang.domain.auth.exception.AuthErrorCode;
import com.example.gonggalbbang.domain.auth.presentation.dto.UserVO;
import com.example.gonggalbbang.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthDetailsService implements UserDetailsService {

    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        // User 엔티티 조회
        User user = authRepository.findById(username)
                .orElseThrow(() -> new CustomException(AuthErrorCode.USER_NOT_FOUND));

        // UserVO로 변환
        UserVO userVO = UserVO.fromUser(user);

        // UserDetails로 래핑
        return new AuthDetails(userVO);
    }
}
