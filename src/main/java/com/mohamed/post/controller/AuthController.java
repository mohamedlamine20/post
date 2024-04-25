package com.mohamed.post.controller;
import com.mohamed.post.model.*;
import com.mohamed.post.repository.UserRepository;
import com.mohamed.post.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Date;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request)  {
        authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(request.email(),request.password()));
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        String jwt = jwtService.generateToken(user.getEmail());
        Date date= jwtService.extractExpiration(jwt);
       LoginResponse response = new LoginResponse(request.email(), jwt,UUID.randomUUID().toString(),date) ;
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest signupRequest){
        User user1 = User.builder()
                .email(signupRequest.email())
                .password(passwordEncoder.encode(signupRequest.password()))
                .build();

        userRepository.save(user1);
        SignupResponse signupResponse = new SignupResponse("",UUID.randomUUID().toString(),
                signupRequest.email(),"",jwtService.generateToken(signupRequest.email()),true,
                jwtService.generateToken(signupRequest.email()),new Date());
        return ResponseEntity.ok(signupResponse);
    }







}
