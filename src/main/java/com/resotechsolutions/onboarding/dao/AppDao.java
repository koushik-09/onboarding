package com.resotechsolutions.onboarding.dao;


import com.resotechsolutions.onboarding.entity.Token;
import com.resotechsolutions.onboarding.entity.User;
import com.resotechsolutions.onboarding.entity.UserDTO;
import com.resotechsolutions.onboarding.entity.UserDetails;


public interface AppDao {

    void save(UserDTO userDTO);

    User getUserByUserId(long userId);

    UserDetails getUserDetailsByUserName(String userName);

    UserDetails findUserDetailByEmail(String email);

    UserDetails getUserDetailsByUserId(long id);

    void updatePasswordByUserId(long userId,String password);

    void saveToken(long user_id,String token);


    void save(User user);

    long getUserIdByEmail(String email);

    Token getTokenDataByToken(String token);


    Token getTokenByUserId(long userId);
}
