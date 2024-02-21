package com.resotechsolutions.onboarding.service;

import com.resotechsolutions.onboarding.config.TokenGenerator;
import com.resotechsolutions.onboarding.dao.AppDaoImplementation;
import com.resotechsolutions.onboarding.dao.TokenDaoImplementation;
import com.resotechsolutions.onboarding.dao.UserDaoImplementation;
import com.resotechsolutions.onboarding.dao.UserDetailDaoImplementation;
import com.resotechsolutions.onboarding.entity.*;
import com.resotechsolutions.onboarding.entity.form.DocumentForm;
import com.resotechsolutions.onboarding.entity.form.Education;
import com.resotechsolutions.onboarding.entity.form.Forms;
import com.resotechsolutions.onboarding.mail.MailServiceImplementation;
import com.resotechsolutions.onboarding.response.BaseResponse;
import com.resotechsolutions.onboarding.response.CustomResponse;
import com.resotechsolutions.onboarding.response.ResponseHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;

@Service
public class AppServiceImpl implements AppService {

    @Value("${form.name}")
    private String[] headerData;
    private AppDaoImplementation appDaoImplementation;

    private UserDetailDaoImplementation userDetailDaoImplementation;
    private UserDaoImplementation userDaoImplementation;

    private TokenDaoImplementation tokenDaoImplementation;

    private TokenGenerator tokenGenerator;

    private BCryptPasswordEncoder passwordEncoder;
    private ResponseHandler responseHandler;

    private CustomResponse customResponse;

    private MailServiceImplementation mailService;

    private Log log = LogFactory.getLog(AppServiceImpl.class);


    @Autowired
    public AppServiceImpl(AppDaoImplementation theAppDaoImplementation,
                          UserDetailDaoImplementation userDetailDaoImplementation,
                          UserDaoImplementation userDaoImplementation,
                          TokenDaoImplementation tokenDaoImplementation,
                          TokenGenerator theTokenGenerator,
                          BCryptPasswordEncoder thePasswordEncoder,
                          ResponseHandler theResponseHandler,
                          CustomResponse customResponse,
                          MailServiceImplementation mailService
                          ){
        this.appDaoImplementation = theAppDaoImplementation;
        this.userDetailDaoImplementation = userDetailDaoImplementation;
        this.userDaoImplementation = userDaoImplementation;
        this.tokenDaoImplementation = tokenDaoImplementation;
        this.tokenGenerator = theTokenGenerator;
        this.passwordEncoder = thePasswordEncoder;
        this.responseHandler= theResponseHandler;
        this.customResponse = customResponse;
        this.mailService = mailService;
    }


    @Override
    @Transactional
    public BaseResponse registerUserDetails(UserDTO userDTO) {

        String email = userDTO.getEmail();
        //Fetching user details based on email from database
        UserDetails userDetails = userDetailDaoImplementation.findUserDetailByEmail(email);
        //check if a user already exists with the email
        //if not exists
        if(userDetails == null) {
            //Generating a default password based on first name and last name
            String password = userDTO.getFirstName() + userDTO.getLastName() + "000";
            log.info(password);

            //encrypting the default password using BCRYPT
            String encryptedPassword = passwordEncoder.encode(password);
            userDTO.setPassword(encryptedPassword);

            String userName = userDTO.getFirstName() + userDTO.getLastName();
            userDTO.setUserName(userName);

            //Saving the user details into the database
            userDetailDaoImplementation.registerUserDetails(userDTO);

            //sending a welcome mail to user
            mailService.welcomeEmail(userDTO.getEmail());

            //Fetching username of the user from database
            long id = userDetailDaoImplementation.getUserIdByEmail(email);
            String username = ""+userDTO.getFirstName().charAt(0)+userDTO.getLastName().charAt(0)+id;
            //returning back response
            return responseHandler.setMessageResponse("Registration Successful",1,username);
        }
        //if user already exists
        String message = "User already exists with email "+email;
        return responseHandler.setMessageResponse(message,-4,null);
    }

    @Override
    @Transactional
    public BaseResponse updateUserDetails(UserDTO userDTO) {
        Token token = tokenDaoImplementation.getTokenDataByToken(userDTO.getToken());
        if (token == null){
            return responseHandler
                    .setMessageResponse("Invalid Token"
                            ,-1, null);
        }
        userDTO.setId(token.getUserDetails().getUser_id());
        userDetailDaoImplementation.updateUserDetailsByUserId(userDTO);
        return responseHandler.setMessageResponse("Details Updated",1,null);
    }

    @Override
    @Transactional
    public BaseResponse validateUser(String userName, String password) {
        UserDetails userDetails = userDetailDaoImplementation.getUserDetailsByUserName(userName);
        if(userDetails == null){
            String message = "User Does not exist with user-name "+userName;
            return responseHandler.setMessageResponse(message,3,null);
        }
        User user = userDaoImplementation.getUserByUserId(userDetails.getUser_id());
        if(passwordEncoder.matches(password,user.getPassword())){
            Token token = tokenDaoImplementation.getTokenByUserId(userDetails.getUser_id());
            userDetails = user.getUserDetails();
            userDetails.setUser(null);
            //generating a new token for user
            String userToken = tokenGenerator.generateToken();
            //storing token into database
            tokenDaoImplementation.saveToken(userDetails.getUser_id(),userToken);
            userDetails.setUserToken(userToken);
//            return responseHandler.setMessageResponse("Login Successful",1,userDetails);
            //returning back response
            return responseHandler.setMessageResponse("Login Successful",1,customResponse.loginResponse(userDetails));
        }
        return responseHandler.setMessageResponse("Invalid Password",-1,null);
    }

    @Override
    public BaseResponse landingPage(String token) {
        Token userToken = tokenDaoImplementation.getTokenDataByToken(token);
        if (userToken == null ){
            return responseHandler.setMessageResponse("Invalid token",-1,null);
        }
        UserDetails userDetails = userToken.getUserDetails();
        userDetails.setUserToken(token);
        return responseHandler.setMessageResponse("Validated",1,customResponse.landingResponse(userDetails));
    }


    @Override
    public BaseResponse validateToken(String token) {
        Token tempToken = tokenDaoImplementation.getTokenDataByToken(token);
        if(tempToken == null){
            return responseHandler
                    .setMessageResponse("Token Does not match"
                            ,-1, null);
        }
        long userId = tempToken.getUserDetails().getUser_id();
        UserDetails userDetails = userDetailDaoImplementation.getUserDetailsByUserId(userId);
        userDetails.setUserToken(token);
        return responseHandler
                .setMessageResponse("Token Validation Success"
                                        ,1,
                                        customResponse.loginResponse(userDetails));

    }

    @Override
    @Transactional
    public BaseResponse updatePassword(String userName, String password,String token) {
        UserDetails userDetails = userDetailDaoImplementation.getUserDetailsByUserName(userName);
        if(userDetails == null){
            String message = "User Does not exist with user-name "+userName;
            return responseHandler.setMessageResponse(message,3,null);
        }
        Token userToken = tokenDaoImplementation.getTokenDataByToken(token);
        if(userToken == null){
            return responseHandler.setMessageResponse("Token is Invalid",-1,null);
        }
        if(userDetails.getUser_id() == userToken.getUserDetails().getUser_id()){
            String encryptedPassword = passwordEncoder.encode(password);
            userDaoImplementation.updatePasswordByUserId(userDetails.getUser_id(),encryptedPassword);
            return responseHandler.setMessageResponse("Password Updated login to continue",1, null);
        }
        return responseHandler.setMessageResponse("Token is Invalid",-1,null);
    }

    @Override
    @Transactional
    public BaseResponse changePassword(String email, String password) {
        UserDetails details = userDetailDaoImplementation.findUserDetailByEmail(email);
        if(details == null){
            return responseHandler.setMessageResponse("Email does not exists",3,false);
        }
        String encryptedPassword = passwordEncoder.encode(password);
        userDaoImplementation.updatePasswordByUserId(details.getUser_id(),encryptedPassword);
        return responseHandler.setMessageResponse("Password Updated login to continue",1, null);
    }

    @Override
    public BaseResponse checkEmailExists(String email) {
        UserDetails details = userDetailDaoImplementation.findUserDetailByEmail(email);
        if(details == null){
            return responseHandler.setMessageResponse("Email does not exists",3,false);
        }
        return responseHandler.setMessageResponse("Email Exists",1,true);
    }

    @Override
    @Transactional
    public BaseResponse generateOtp(String email) {
        UserDetails details = userDetailDaoImplementation.findUserDetailByEmail(email);
        if(details == null){
            return responseHandler.setMessageResponse("Email does not exists",3,false);
        }
        String otp = tokenGenerator.generateOTP();
        UserDetails userDetails = userDetailDaoImplementation.findUserDetailByEmail(email);
        appDaoImplementation.createAuthToken(userDetails.getUser_id(),otp);
        EmailContent emailContent = appDaoImplementation.getEmailTemplateByType("password-reset");
        String response = mailService.passwordResetMail(email,otp,emailContent);
        log.info(response);
        return responseHandler.setMessageResponse(response,1,null);
    }

    @Override
    public BaseResponse validateOtp(String email, String otp) {
        UserDetails details = userDetailDaoImplementation.findUserDetailByEmail(email);
        UserAuthentication authDetails = appDaoImplementation.getAuthDetailsById(details.getUser_id());
        Timestamp time1 = authDetails.getCreatedOn();
        Timestamp time2 = new Timestamp(System.currentTimeMillis());
        long difference = time2.getTime() - time1.getTime();
        long minutes = difference / (60 * 1000);
        if(minutes>10){
            return responseHandler.setMessageResponse("OTP expired request new OTP",-1,null);
        }
        if(otp.equals(authDetails.getToken())){
            return responseHandler.setMessageResponse("OTP verified",1,true);
        }
        return responseHandler.setMessageResponse("Invalid OTP",-1,false);
    }

    @Override
    @Transactional
    public BaseResponse forgetPassword(String email, String otp,String password) {
        UserDetails details = userDetailDaoImplementation.findUserDetailByEmail(email);
        UserAuthentication authDetails = appDaoImplementation.getAuthDetailsById(details.getUser_id());
        Timestamp time1 = authDetails.getCreatedOn();
        Timestamp time2 = new Timestamp(System.currentTimeMillis());
        long difference = time2.getTime() - time1.getTime();
        long minutes = difference / (60 * 1000);
        if(minutes>10){
            return responseHandler.setMessageResponse("OTP expired request new OTP",-1,null);
        }
        if(otp.equals(authDetails.getToken())){
            String encryptedPassword = passwordEncoder.encode(password);
            userDaoImplementation.updatePasswordByUserId(details.getUser_id(),encryptedPassword);
            return responseHandler.setMessageResponse("Password Updated login to continue",1, null);
//            return responseHandler.setMessageResponse("OTP validated successfully",1,details.getUserName());
        }
        return responseHandler.setMessageResponse("OTP does not match",-1,null);
    }

    @Override
    public BaseResponse getHeaders() {
        Forms forms = new Forms();

        Education education = new Education();
        education.setGraduation(appDaoImplementation.getHeaders("education_graduation"));
        education.setSecondary(appDaoImplementation.getHeaders("education_secondary"));
        education.setPrimary(appDaoImplementation.getHeaders("education_primary"));

        forms.setPrimaryDetails(appDaoImplementation.getHeaders("primary_details"));
        forms.setEducation(education);

        DocumentForm documents = new DocumentForm();
        documents.setPan(appDaoImplementation.getHeaders("pan_card"));
        documents.setAadhar(appDaoImplementation.getHeaders("aadhar_card"));
        forms.setDocuments(documents);
        return responseHandler.setMessageResponse("done",1,forms);
    }

    @Override
    @Transactional
    public BaseResponse logout(String token) {
        Token userToken = tokenDaoImplementation.getTokenDataByToken(token);
        if (userToken == null ){
            return responseHandler.setMessageResponse("Invalid token",-1,null);
        }
        tokenDaoImplementation.deleteTokenById(userToken.getUserDetails().getUser_id());
        return responseHandler.setMessageResponse("Logout Successful",1,null);
    }
}
