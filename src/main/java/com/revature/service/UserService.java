package com.revature.service;

import com.revature.daos.UserDAO;

public class UserService {

    private UserDAO userDao;

    public UserService(UserDAO userDao) {
        this.userDao = userDao;
    }
}
