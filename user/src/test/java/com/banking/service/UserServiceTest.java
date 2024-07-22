package com.banking.service;

import com.banking.config.SpringConfig;
import com.banking.user.entity.User;
import com.banking.user.exception.UserNotFoundException;
import com.banking.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SpringConfig.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void testCreateUser() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals("Test User", createdUser.getName());
    }

    @Test
    void testGetUserById() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");

        User createdUser = userService.createUser(user);
        User foundUser = userService.getUserById(createdUser.getId());

        assertNotNull(foundUser);
        assertEquals(createdUser.getId(), foundUser.getId());
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");

        User createdUser = userService.createUser(user);
        createdUser.setName("Updated User");

        User updatedUser = userService.updateUser(createdUser.getId(), createdUser);

        assertNotNull(updatedUser);
        assertEquals("Updated User", updatedUser.getName());
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");

        User createdUser = userService.createUser(user);
        userService.deleteUser(createdUser.getId());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(createdUser.getId()));
    }
}
