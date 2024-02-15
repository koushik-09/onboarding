package com.resotechsolutions.onboarding.response;

import com.resotechsolutions.onboarding.entity.UserDetails;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CustomResponse {

    private Map<String,Object> responseMap = new LinkedHashMap<>();


    public Map<String,Object> loginResponse(UserDetails userDetails){
        responseMap.put("user_id",userDetails.getUser_id());
        responseMap.put("username",userDetails.getUserName());
        responseMap.put("firstName",userDetails.getFirstName());
        responseMap.put("lastName",userDetails.getLastName());
        responseMap.put("email",userDetails.getEmail());
        responseMap.put("token",userDetails.getUserToken());
        responseMap.put("active",userDetails.isActive());
        responseMap.put("admin",userDetails.isAdmin());
        responseMap.put("passwordUpdated",userDetails.isPasswordUpdated());
        return responseMap;
    }
}
