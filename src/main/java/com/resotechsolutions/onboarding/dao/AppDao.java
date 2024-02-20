package com.resotechsolutions.onboarding.dao;


import com.resotechsolutions.onboarding.entity.*;
import com.resotechsolutions.onboarding.entity.form.FormData;

import java.util.List;


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

    //user authentication methods
    void createAuthToken(long userId,String token);

    UserAuthentication getAuthDetailsById(long userId);

    void deleteTokenById(long id);

    EmailContent getEmailTemplateByType(String type);

//    Map<String, String> getHeaders(String name);
    List<FormData> getHeaders(String name);
}
