package com.example.practtestjava.service;

import com.example.practtestjava.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.DataProcessingException;
import org.mockito.Mockito;
import java.time.LocalDate;
import java.util.Optional;
import static org.mockito.Mockito.when;


class UserServiceImplTest {

    private static final Long ID = 1L;
    private UserServiceImpl userService;

    private User testUser;
    private User expectedUser;


    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl();
        userService = Mockito.mock(UserServiceImpl.class);
        testUser = new User(1, "Michail", LocalDate.of(1981, 11, 1));
        expectedUser = new User(ID, "Michail", LocalDate.of(1981, 11, 1));

    }

    @Test
    void save_ok() {
        Mockito.when(userService.save(testUser)).thenReturn(expectedUser);
        User actualUser = userService.save(testUser);
        Assertions.assertNotNull(actualUser);
        assertUsers(expectedUser, actualUser);
    }

    @Test
    void save_notUniq_notOk() {
        when(userService.save(testUser)).thenThrow(DataProcessingException.class);
        DataProcessingException thrown =
                Assertions.assertThrows(DataProcessingException.class,
                        () -> userService.save(testUser),
                        "Expected to receive DataProcessingException");
    }

    @Test
    void findById_ok() {
        Mockito.when(userService.findById(ID)).thenReturn(expectedUser);
        Optional<User> actualOptionalUser = Optional.ofNullable(userService.findById(ID));
        Assertions.assertTrue(actualOptionalUser.isPresent());
        assertUsers(expectedUser, actualOptionalUser.get());
    }


    void assertUsers(User expectedUser, User actualUser) {
        Assertions.assertEquals(expectedUser.getId(), actualUser.getId());
        Assertions.assertEquals(expectedUser.getEmail(), actualUser.getEmail());

    }
}