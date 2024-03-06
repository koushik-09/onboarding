package com.resotechsolutions.onboarding.dao;

import com.resotechsolutions.onboarding.entity.dto.UserDTO;
import com.resotechsolutions.onboarding.entity.UserDetails;

public interface UserDetailDao {

    void registerUserDetails(UserDTO userDTO);

    void updateUserDetailsByUserId(UserDTO userDTO);
    UserDetails getUserDetailsByUserName(String userName);
    UserDetails findUserDetailByEmail(String email);
    UserDetails getUserDetailsByUserId(long id);
    long getUserIdByEmail(String email);

    void changePasswordUpdated(long userId);
}
