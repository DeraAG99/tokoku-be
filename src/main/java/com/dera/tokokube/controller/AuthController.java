package com.dera.tokokube.controller;

import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dera.tokokube.entity.Pengguna;
import com.dera.tokokube.model.ApiResponse;
import com.dera.tokokube.model.JwtResponse;
import com.dera.tokokube.model.LoginRequest;
import com.dera.tokokube.model.RefreshTokenRequest;
import com.dera.tokokube.model.SignupRequest;
import com.dera.tokokube.security.jwt.JwtUtils;
import com.dera.tokokube.security.service.UserDetailsImpl;
import com.dera.tokokube.security.service.UserDetailsServiceImpl;
import com.dera.tokokube.services.PenggunaService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PenggunaService penggunaService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    JwtUtils jwtUtils;

    private final static Logger logger = LoggerFactory.getLogger(AuthController.class);

    // Login menggunakan username dan password dan kembalikan data JwtResponse
    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);
        String refreshToken = jwtUtils.generateRefreshJwtToken(authentication);
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        logger.info("Signin Succeed : {}", request.getUsername());
        return ResponseEntity.ok().body(new JwtResponse(token, refreshToken, principal.getUsername(), principal.getEmail(), principal.getRoles()));
    }

    // Register pengguna
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody SignupRequest request) throws BadRequestException {
        Pengguna pengguna = new Pengguna();
        pengguna.setId(request.getUsername());
        pengguna.setEmail(request.getEmail());
        pengguna.setPassword(passwordEncoder.encode(request.getPassword()));
        pengguna.setNama(request.getNama());
        pengguna.setRoles("user");
        penggunaService.create(pengguna);
        logger.info("Signup Succeed : {}", request.getUsername());
        return ResponseEntity.ok().body((new ApiResponse(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase().toUpperCase(), pengguna)));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        String token = request.getRefreshToken();
        boolean valid = jwtUtils.validateJwtToken(token);
        if (!valid) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String username = jwtUtils.getUsernameFromJwtToken(token);
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) userDetailsServiceImpl.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetailsImpl, null,
                userDetailsImpl.getAuthorities());
        String newToken = jwtUtils.generateJwtToken(authentication);
        String refreshToken = jwtUtils.generateRefreshJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(newToken, refreshToken, username, userDetailsImpl.getEmail(), userDetailsImpl.getRoles()));
    }
}
