package me.dio.service.impl;

import me.dio.domain.model.Account;
import me.dio.domain.model.News;
import me.dio.domain.model.User;
import me.dio.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserRepository userRepository;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void shouldFindUserById() {
        // Arrange
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        User result = userService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundById() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> userService.findById(1L));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void shouldCreateUser() {
        // Arrange
        User user = new User();
        Account account = new Account();
        account.setNumber("12345");
        user.setAccount(account);

        when(userRepository.existsByAccountNumber("12345")).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User result = userService.create(user);

        // Assert
        assertNotNull(result);
        verify(userRepository, times(1)).existsByAccountNumber("12345");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void shouldThrowExceptionWhenAccountNumberExists() {
        // Arrange
        User user = new User();
        Account account = new Account();
        account.setNumber("12345");
        user.setAccount(account);

        when(userRepository.existsByAccountNumber("12345")).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userService.create(user));
        verify(userRepository, times(1)).existsByAccountNumber("12345");
        verify(userRepository, never()).save(user);
    }

    @Test
    void shouldReturnUserBalance() {
        // Arrange
        Account account = new Account();
        account.setBalance(1000.0);

        User user = new User();
        user.setAccount(account);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        Double balance = userService.getUserBalance(1L);

        // Assert
        assertEquals(1000.0, balance);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundForBalance() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.getUserBalance(1L));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void shouldReturnUserNews() {
        // Arrange
        News news1 = new News();
        news1.setDescription("News 1");

        News news2 = new News();
        news2.setDescription("News 2");

        User user = new User();
        user.setNews(Arrays.asList(news1, news2));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        List<News> newsList = userService.getUserNews(1L);

        // Assert
        assertEquals(2, newsList.size());
        assertEquals("News 1", newsList.get(0).getDescription());
        assertEquals("News 2", newsList.get(1).getDescription());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundForNews() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.getUserNews(1L));
        verify(userRepository, times(1)).findById(1L);
    }
}