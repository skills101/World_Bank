package com.henry.World.Banking.Application.service;

import com.henry.World.Banking.Application.payload.request.LoginRequest;
import com.henry.World.Banking.Application.payload.request.UserRequest;
import com.henry.World.Banking.Application.payload.response.ApiResponse;
import com.henry.World.Banking.Application.payload.response.BankResponse;
import com.henry.World.Banking.Application.payload.response.JwtAuthResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    BankResponse registerUser(UserRequest userRequest);

    ResponseEntity<ApiResponse<JwtAuthResponse>> loginUser(LoginRequest loginRequest);

}
