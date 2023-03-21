package edu.security.jwt.demo.controller;

import edu.security.jwt.demo.model.LoginRequest;
import edu.security.jwt.demo.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * {DESCRIPTION}
 *
 * @author Frank Sprich
 */
@RestController
@RequestMapping("/")
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping
    public ResponseEntity<String> home(Principal principal) {
        return ResponseEntity.ok("Hello, " + principal.getName());
    }


    @PostMapping("/token")
    public ResponseEntity<String> token(@RequestBody LoginRequest userLogin) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.username(), userLogin.password()));
        String token = tokenService.generateToken(authentication);
        return ResponseEntity.ok(token);
    }
}
