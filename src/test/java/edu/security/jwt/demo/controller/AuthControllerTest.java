package edu.security.jwt.demo.controller;

import edu.security.jwt.demo.config.SecurityConfig;
import edu.security.jwt.demo.service.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * {DESCRIPTION}
 *
 * Credits got to Dan Vega
 *
 * @author Frank Sprich
 */
@WebMvcTest({AuthController.class, AuthController.class})
@Import({SecurityConfig.class, TokenService.class})
class AuthControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void rootWhenUnauthenticatedThen401() throws Exception {
        this.mvc.perform(get("/")).andExpect(status().isUnauthorized());
    }

    @Test
    void rootWhenAuthenticatedThenSaysHelloUser() throws Exception {
        // Post request to retrieve the token
        MvcResult result = this.mvc.perform(post("/token").with(httpBasic("frank", "password")))
                .andExpect(status().isOk())
                .andReturn();

        String token = result.getResponse().getContentAsString();

        // Get request to use the created token
        this.mvc.perform(get("/").header("Authorization", "Bearer " + token))
                .andExpect(content().string("Hello, frank"));
    }

    @Test
    @WithMockUser
    public void rootWithMockUserStatusIsOk() throws Exception {
        this.mvc.perform(get("/")).andExpect(status().isOk());
    }

}
