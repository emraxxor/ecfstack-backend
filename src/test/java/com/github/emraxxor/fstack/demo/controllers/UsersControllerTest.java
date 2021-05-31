package com.github.emraxxor.fstack.demo.controllers;

import com.github.emraxxor.fstack.demo.data.type.SimpleUser;
import com.github.emraxxor.fstack.demo.data.type.UserPasswordFormElement;
import com.github.emraxxor.fstack.demo.entities.User;
import com.github.emraxxor.fstack.demo.service.ProfileStorageService;
import com.github.emraxxor.fstack.demo.service.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UsersControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private ProfileStorageService profileStorage;

    @Mock
    private ModelMapper mapper;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UsersController usersController;

    private MockMvc mockMvc;

    private User validUser;

    @BeforeEach
    void setUp() {
        validUser = User
                .builder()
                .userId(1L)
                .userName("name")
                .userMail("mail")
                .userPassword("old")
                .build();

        mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void info() throws Exception {
        given(userService.curr()).willReturn(validUser);
        mockMvc.perform(
                get("/api/user/info" )
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON) )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId").value(validUser.getUserId()));

    }

    @Test
    void image() {
    }

    @Test
    void testImage() {
    }

    @Test
    void update() throws Exception {
        var simpleUser = new SimpleUser(validUser);
        when(userService.curr()).thenReturn(validUser);
        when(userService.principal()).thenReturn(simpleUser);
        given(userService.save(validUser)).willReturn(validUser);

        mockMvc.perform(
                put("/api/user" )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(simpleUser))
                        .accept(MediaType.APPLICATION_JSON) )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(1));

    }

    @Test
    void updatePassword() throws Exception {
        when(userService.curr()).thenReturn(validUser);
        when(encoder.matches(BDDMockito.any(), BDDMockito.anyString())).thenReturn(true);

        mockMvc.perform(
                put("/api/user/password" )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(
                                UserPasswordFormElement
                                        .builder()
                                        .oldPassword("old")
                                        .newPassword("new")
                                        .newPasswordConfirm("new")
                                        .build()
                        ))
                        .accept(MediaType.APPLICATION_JSON) )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }
}