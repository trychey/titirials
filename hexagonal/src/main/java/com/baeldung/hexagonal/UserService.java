package com.baeldung.hexagonal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    @Autowired
    private UserDaoInterface userDao;

    public User find(String username) {
        return userDao.find(username);
    }
}
