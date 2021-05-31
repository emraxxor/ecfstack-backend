package com.github.emraxxor.fstack.demo.service;

import com.github.emraxxor.fstack.demo.entities.User;
import com.github.emraxxor.fstack.demo.repositories.UserLogRepository;
import com.github.emraxxor.fstack.demo.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserLogRepository userLogRepository;

    @InjectMocks
    private UserService service;

    User validUser;

    @BeforeEach
    void setUp() {
        validUser = User
                .builder()
                .userId(1L)
                .userName("name")
                .userMail("mail")
                .build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findUserByName() {
        BDDMockito.given( userRepository.findByUserName(BDDMockito.any()) ).willReturn( Optional.of(validUser) );
        Assertions.assertThat( service.findUserByName(validUser.getUserName()) ).isEqualTo(Optional.of(validUser));
        BDDMockito.then(userRepository).should().findByUserName(validUser.getUserName());
    }

    @Test
    void findUserByEmail() {
        BDDMockito.given( userRepository.findByUserMail(BDDMockito.any()) ).willReturn( Optional.of(validUser) );
        Assertions.assertThat( service.findUserByEmail(validUser.getUserMail()) ).isEqualTo(Optional.of(validUser));
        BDDMockito.then(userRepository).should().findByUserMail(validUser.getUserMail());
    }

    @Test
    void save() {
        BDDMockito.given( userRepository.save(BDDMockito.any()) ).willReturn( validUser );
        Assertions.assertThat( service.save(validUser) ).isEqualTo(validUser);
        BDDMockito.then(userRepository).should().save(validUser);
    }

    @Test
    void findById() {
        BDDMockito.given( userRepository.findById(BDDMockito.any()) ).willReturn( Optional.of(validUser) );
        Assertions.assertThat( service.findById(1L) ).isEqualTo(Optional.of(validUser));
        BDDMockito.then(userRepository).should().findById(validUser.getUserId());
    }

}