package com.example.gonggalbbang.domain.auth.service;

import com.example.gonggalbbang.domain.auth.presentation.dto.request.JoinReq;
import com.example.gonggalbbang.domain.auth.presentation.dto.request.LoginReq;
import com.example.gonggalbbang.domain.auth.presentation.dto.request.RefreshReq;
import com.example.gonggalbbang.domain.auth.presentation.dto.response.LoginRes;
import com.example.gonggalbbang.domain.auth.presentation.dto.response.RefreshRes;
import com.example.gonggalbbang.global.common.Response;
import com.example.gonggalbbang.global.common.ResponseData;

public interface AuthService {

    Response join(JoinReq joinReq);
    ResponseData<LoginRes> login(LoginReq loginReq);
    ResponseData<RefreshRes> refresh(RefreshReq refreshReq);
}
