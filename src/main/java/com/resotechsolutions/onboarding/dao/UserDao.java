package com.resotechsolutions.onboarding.dao;

import com.resotechsolutions.onboarding.entity.User;

public interface UserDao {

    User getUserByUserId(long userId);
    void updatePasswordByUserId(long userId,String password);
}
