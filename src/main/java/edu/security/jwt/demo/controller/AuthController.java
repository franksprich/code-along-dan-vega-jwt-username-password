package edu.security.jwt.demo.controller;

import edu.security.jwt.demo.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping
    public ResponseEntity<String> home(Principal principal) {
        return ResponseEntity.ok("Hello, " + principal.getName());
    }

    @PostMapping("/token")
    public ResponseEntity<String> token(Authentication authentication) {
        LOG.debug("Token requested for user: {}", authentication.getName());
        String token = tokenService.generateToken(authentication);
        LOG.debug("Token generated {}", token);
        return ResponseEntity.ok(token);
    }
}
