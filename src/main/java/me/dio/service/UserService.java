package me.dio.service;

import me.dio.domain.model.User;

public interface UserService {

    User findById(Long id);

    User create(User userToCreate);

    Double getUserBalance(Long userId);

    List<News> getUserNews(Long userId);
}
