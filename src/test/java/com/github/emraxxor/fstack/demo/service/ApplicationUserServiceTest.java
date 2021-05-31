package com.github.emraxxor.fstack.demo.service;

import com.github.emraxxor.fstack.demo.config.ApplicationUser;
import com.github.emraxxor.fstack.demo.config.ApplicationUserRole;
import com.github.emraxxor.fstack.demo.entities.User;
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
class ApplicationUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ApplicationUserService service;

    private User validUser;

    @BeforeEach
    void setUp() {
        validUser = User
                .builder()
                .userId(1L)
                .userName("name")
                .userMail("mail")
                .role(ApplicationUserRole.USER)
                .build();
    }


    @AfterEach
    void tearDown() {
    }

    @Test
    void loadUserByUsername() {
        var expectedRole = new ApplicationUser(ApplicationUserRole.USER.grantedAuthorities(),validUser);
        BDDMockito.given( userRepository.findByUserName(BDDMockito.any()) ).willReturn( Optional.of(validUser) );
        Assertions.assertThat( service.loadUserByUsername(validUser.getUserName()) ).isEqualTo(expectedRole);
        BDDMockito.then(userRepository).should().findByUserName(validUser.getUserName());
    }

}