package com.jerorodriguez.springbootjwt.demo;

import com.jerorodriguez.springbootjwt.entities.User;
import com.jerorodriguez.springbootjwt.entities.UserInfoResponse;
import com.jerorodriguez.springbootjwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class DemoController {

    private final UserRepository userRepository;

    @PostMapping(value = "demo")
    public String welcome() {
        // Obtener la autenticación actual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Obtener el nombre de usuario
        String username = authentication.getName();

        // Buscar información adicional del usuario en la base de datos
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return "Hello World from secure endpoint, " + user.getFirstname() + " " + user.getLastname() + "!";
        } else {
            return "Hello World from secure endpoint, " + username + "!";
        }
    }

    @GetMapping(value = "user-info")
    public ResponseEntity<UserInfoResponse> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        UserInfoResponse response = new UserInfoResponse(
                user.getUsername(),
                user.getFirstname(),
                user.getLastname(),
                user.getCountry(),
                user.getRole().toString()
        );

        return ResponseEntity.ok(response);
    }
}
