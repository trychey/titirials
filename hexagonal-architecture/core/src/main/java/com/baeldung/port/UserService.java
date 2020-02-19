package com.baeldung.port;

import java.util.List;
import java.util.UUID;

import com.baeldung.domain.exceptions.UserNotFoundException;
import com.baeldung.domain.entity.user.User;

public interface UserService {
    User createUser(User user);

    boolean deleteUser(UUID userId) throws UserNotFoundException;

    List<User> listUsers();
}
