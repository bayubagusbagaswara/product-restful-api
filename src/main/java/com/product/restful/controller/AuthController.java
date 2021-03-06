package com.product.restful.controller;

import com.product.restful.dto.ApiResponse;
import com.product.restful.dto.WebResponse;
import com.product.restful.dto.auth.LogoutRequest;
import com.product.restful.dto.refreshToken.RefreshTokenRequest;
import com.product.restful.dto.auth.AuthenticationResponse;
import com.product.restful.dto.auth.LoginRequest;
import com.product.restful.dto.auth.SignUpRequest;
import com.product.restful.dto.user.UserDto;
import com.product.restful.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        UserDto userDto = authService.signUp(signUpRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/{userId}")
                .buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity
                .created(location)
                .body(new ApiResponse(Boolean.TRUE, "User registered successfully"));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<WebResponse<AuthenticationResponse>> authenticateUser(
            @Valid @RequestBody LoginRequest loginRequest) {
        AuthenticationResponse authenticationResponse = authService.signIn(loginRequest);
        return new ResponseEntity<>(new WebResponse<>(Boolean.TRUE, "User authenticated successfully", authenticationResponse), HttpStatus.OK);
    }

    @PostMapping(value = "/refresh/token")
    public ResponseEntity<WebResponse<AuthenticationResponse>> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        AuthenticationResponse authenticationResponse = authService.refreshToken(refreshTokenRequest);
        return new ResponseEntity<>(new WebResponse<>(Boolean.TRUE, "Refresh token successfully updated", authenticationResponse), HttpStatus.OK);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<ApiResponse> logout(@Valid @RequestBody LogoutRequest logoutRequest) {
        authService.logout(logoutRequest);
        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "Refresh token deleted successfully"), HttpStatus.OK);
    }

}
