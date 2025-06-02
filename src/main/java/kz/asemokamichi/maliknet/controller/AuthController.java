package kz.asemokamichi.maliknet.controller;

import kz.asemokamichi.maliknet.data.dto.AuthResponse;
import kz.asemokamichi.maliknet.data.entity.User;
import kz.asemokamichi.maliknet.service.UserService;
import kz.asemokamichi.maliknet.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtService;


    @PostMapping
    public ResponseEntity<?> generateToken() {
        try {
            User user = userService.getCurrentUser();

            String token = jwtService.generateToken(user);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
        }
    }

}

