package com.resotechsolutions.onboarding.dao;

import com.resotechsolutions.onboarding.entity.Token;

public interface TokenDao {
    void saveToken(long user_id,String token);
    Token getTokenDataByToken(String token);
    Token getTokenByUserId(long userId);
    void deleteTokenById(long id);

}
