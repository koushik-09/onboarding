package com.resotechsolutions.onboarding.service;

import com.resotechsolutions.onboarding.entity.UserDTO;
import com.resotechsolutions.onboarding.entity.User;
import com.resotechsolutions.onboarding.entity.UserDetails;
import com.resotechsolutions.onboarding.response.BaseResponse;

import java.sql.Timestamp;

public interface AppService {

    BaseResponse save(UserDTO userDTO);

    BaseResponse validateUser(String userName, String password);

    BaseResponse validateToken(String token);

    BaseResponse updatePassword(String username,String password,String token);

    BaseResponse checkEmailExists(String email);

    BaseResponse generateOtp(String email);

    BaseResponse forgetPassword(String email, String otp,String password);
    BaseResponse getHeaders();
}
