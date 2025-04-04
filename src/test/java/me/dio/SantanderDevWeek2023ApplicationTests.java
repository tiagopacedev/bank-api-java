package me.dio;

import me.dio.controller.UserController;
import me.dio.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SantanderDevWeek2023ApplicationTests {

    @Autowired
    private UserController userController;

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
    }

    @Test
    void userControllerLoads() {
        assertThat(userController).isNotNull();
    }

    @Test
    void userServiceLoads() {
        assertThat(userService).isNotNull();
    }
}