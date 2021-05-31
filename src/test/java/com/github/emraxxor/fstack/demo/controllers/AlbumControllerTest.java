package com.github.emraxxor.fstack.demo.controllers;

import com.github.emraxxor.fstack.demo.repositories.AlbumRepository;
import com.github.emraxxor.fstack.demo.service.PictureService;
import com.google.gson.Gson;
import com.github.emraxxor.fstack.demo.data.mapper.AlbumFormElementMapper;
import com.github.emraxxor.fstack.demo.data.type.AlbumFormElement;
import com.github.emraxxor.fstack.demo.data.type.AlbumType;
import com.github.emraxxor.fstack.demo.entities.Album;
import com.github.emraxxor.fstack.demo.entities.User;
import com.github.emraxxor.fstack.demo.service.AlbumService;
import com.github.emraxxor.fstack.demo.service.PictureStorageService;
import com.github.emraxxor.fstack.demo.service.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.collections.Sets;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AlbumControllerTest {

    @Mock
    private AlbumService albumService;

    @Mock
    private UserService userService;

    @Mock
    private PictureService pictureService;

    @Mock
    private PictureStorageService pictureStorageService;

    @Mock
    private AlbumFormElementMapper albumFormElementMapper;

    @Mock
    private AlbumRepository albumRepository;

    @InjectMocks
    private AlbumController controller;

    private User validUser;

    private Album validAlbum;

    private MockMvc mockMvc;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() {
        validUser = User
                    .builder()
                    .userId(1L)
                    .userName("name")
                    .userMail("mail")
                    .build();

        validAlbum = Album
                            .builder()
                            .id(1L)
                            .users(Sets.newSet(validUser))
                            .build();
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void create() throws Exception {
        given(userService.current()).willReturn(Optional.of(validUser));
        given(albumService.save(BDDMockito.any())).willReturn(validAlbum);

        mockMvc.perform(
                post("/api/album" )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(
                                AlbumFormElement
                                        .builder()
                                        .albumName("AlbumName")
                                        .albumType(AlbumType.PUBLIC)
                                        .description("Description")
                                        .build())
                        )
                        .accept(MediaType.APPLICATION_JSON) )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(1));
    }

    @Test
    void update() throws Exception {
        when(albumService.repository()).thenReturn(albumRepository);
        when(albumService.save(BDDMockito.any())).thenReturn(validAlbum);
        given(userService.current()).willReturn(Optional.of(validUser));
        given(albumService.repository().findAlbumByUsers_userIdAndId(BDDMockito.anyLong(), BDDMockito.anyLong())).willReturn(Optional.of(validAlbum));

        mockMvc.perform(
                put("/api/album" )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(
                                AlbumFormElement
                                        .builder()
                                        .id(1L)
                                        .albumName("AlbumName")
                                        .albumType(AlbumType.PUBLIC)
                                        .description("Description")
                                        .build())
                        )
                        .accept(MediaType.APPLICATION_JSON) )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(1));
    }


    @Test
    void get() throws Exception {
        when(albumService.find(anyLong())).thenReturn(Optional.of(validAlbum));
        when(albumFormElementMapper.toAlbumFormElement(BDDMockito.any())).thenReturn(new AlbumFormElement());
        given(userService.current()).willReturn(Optional.of(validUser));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/album/" + 1 )
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON) )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(1));
    }
}