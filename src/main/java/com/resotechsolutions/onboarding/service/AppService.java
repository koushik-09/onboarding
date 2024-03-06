package com.resotechsolutions.onboarding.service;

import com.resotechsolutions.onboarding.entity.dto.BankDto;
import com.resotechsolutions.onboarding.entity.dto.DocumentDto;
import com.resotechsolutions.onboarding.entity.dto.EducationDTO;
import com.resotechsolutions.onboarding.entity.dto.UserDTO;
import com.resotechsolutions.onboarding.response.BaseResponse;
import org.springframework.web.multipart.MultipartFile;

public interface AppService {

    BaseResponse registerUserDetails(UserDTO userDTO);

    BaseResponse updateUserDetails(UserDTO userDTO);

    BaseResponse updateEducationDetails(String token,EducationDTO educationDTO);

    BaseResponse validateUser(String userName, String password);

    BaseResponse landingPage(String token);

    BaseResponse getUserDetails(String token);

    BaseResponse validateToken(String token);

    BaseResponse updatePassword(String username,String password,String token);

    BaseResponse updateUserDocuments(MultipartFile file,String token,String documentType);

    BaseResponse updateMultipleUserDocuments(MultipartFile file,String token);

    BaseResponse updateBankDetails(String token, BankDto bankDto);

    BaseResponse updateDocumentDetails(String token,DocumentDto documentDto);

//    BaseResponse downloadAgreement(String token);

    BaseResponse changePassword(String email,String password);

    BaseResponse checkEmailExists(String email);

    BaseResponse generateOtp(String email);

    BaseResponse validateOtp(String email,String otp);

    BaseResponse forgetPassword(String email, String otp,String password);

    BaseResponse getHeaders();

    BaseResponse logout(String token);

    BaseResponse changePasswordUpdated(String token);
}
