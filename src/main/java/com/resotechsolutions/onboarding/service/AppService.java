package com.resotechsolutions.onboarding.service;

import com.resotechsolutions.onboarding.entity.UserDTO;
import com.resotechsolutions.onboarding.entity.User;
import com.resotechsolutions.onboarding.entity.UserDetails;
import com.resotechsolutions.onboarding.response.BaseResponse;

public interface AppService {

    BaseResponse save(UserDTO userDTO);

    BaseResponse validateUser(String userName, String password);

    BaseResponse validateToken(String token);

}
