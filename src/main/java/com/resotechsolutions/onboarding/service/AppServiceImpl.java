package com.resotechsolutions.onboarding.service;

import com.resotechsolutions.onboarding.config.TokenGenerator;
import com.resotechsolutions.onboarding.dao.AppDaoImplementation;
import com.resotechsolutions.onboarding.entity.Token;
import com.resotechsolutions.onboarding.entity.User;
import com.resotechsolutions.onboarding.entity.UserDTO;
import com.resotechsolutions.onboarding.entity.UserDetails;
import com.resotechsolutions.onboarding.mail.MailServiceImplementation;
import com.resotechsolutions.onboarding.response.BaseResponse;
import com.resotechsolutions.onboarding.response.CustomResponse;
import com.resotechsolutions.onboarding.response.ResponseHandler;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppServiceImpl implements AppService {

    private AppDaoImplementation appDaoImplementation;

    private TokenGenerator tokenGenerator;

    private BCryptPasswordEncoder passwordEncoder;
    private ResponseHandler responseHandler;

    private CustomResponse customResponse;

    private MailServiceImplementation mailService;


    @Autowired
    public AppServiceImpl(AppDaoImplementation theAppDaoImplementation,
                          TokenGenerator theTokenGenerator,
                          BCryptPasswordEncoder thePasswordEncoder,
                          ResponseHandler theResponseHandler,
                          CustomResponse customResponse,
                          MailServiceImplementation mailService
                          ){
        this.appDaoImplementation = theAppDaoImplementation;
        this.tokenGenerator = theTokenGenerator;
        this.passwordEncoder = thePasswordEncoder;
        this.responseHandler= theResponseHandler;
        this.customResponse = customResponse;
        this.mailService = mailService;
    }

    Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);

    @Override
    @Transactional
    public BaseResponse save(UserDTO userDTO) {

        String email = userDTO.getEmail();
        //Fetching user details based on email from database
        UserDetails userDetails = appDaoImplementation.findUserDetailByEmail(email);
        //check if a user already exists with the email
        //if not exists
        if(userDetails == null) {
            //Generating a default password based on first name and last name
            String password = userDTO.getFirstName() + userDTO.getLastName() + "000";
            logger.info(password);

            //encrypting the default password using BCRYPT
            String encryptedPassword = passwordEncoder.encode(password);
            userDTO.setPassword(encryptedPassword);

            String userName = userDTO.getFirstName() + userDTO.getLastName();
            userDTO.setUserName(userName);

            //Saving the user details into the database
            appDaoImplementation.save(userDTO);

            //sending a welcome mail to user
            mailService.welcomeEmail(userDTO.getEmail());

            //Fetching username of the user from database
            long id = appDaoImplementation.getUserIdByEmail(email);
            String username = ""+userDTO.getFirstName().charAt(0)+userDTO.getLastName().charAt(0)+id;
            //returning back response
            return responseHandler.setMessageResponse("Registration Successful",HttpStatus.CREATED.value(),username);
        }
        //if user already exists
        String message = "User already exists with email "+email;
        return responseHandler.setMessageResponse(message,HttpStatus.CONFLICT.value(),null);
    }

    @Override
    @Transactional
    public BaseResponse validateUser(String userName, String password) {
        UserDetails userDetails = appDaoImplementation.getUserDetailsByUserName(userName);
        if(userDetails == null){
            String message = "User Does not exist with user-name "+userName;
            return responseHandler.setMessageResponse(message,HttpStatus.NOT_FOUND.value(),null);
        }
        User user = appDaoImplementation.getUserByUserId(userDetails.getUser_id());
        if(passwordEncoder.matches(password,user.getPassword())){
            Token token = appDaoImplementation.getTokenByUserId(userDetails.getUser_id());
            userDetails = user.getUserDetails();
            userDetails.setUser(null);
            //generating a new token for user
            String userToken = tokenGenerator.generateToken();
            //storing token into database
            appDaoImplementation.saveToken(userDetails.getUser_id(),userToken);
            userDetails.setUserToken(userToken);
            //returning back response
            return responseHandler.setMessageResponse("Login Successful",HttpStatus.OK.value(),customResponse.loginResponse(userDetails));
        }
        return responseHandler.setMessageResponse("Invalid Password",HttpStatus.UNAUTHORIZED.value(),null);

    }

    @Override
    public BaseResponse validateToken(String token) {
        Token tempToken = appDaoImplementation.getTokenDataByToken(token);
        if(tempToken == null){
            return responseHandler
                    .setMessageResponse("Token Does not match"
                            ,HttpStatus.NOT_FOUND.value(), null);
        }
        long userId = tempToken.getUserDetails().getUser_id();
        UserDetails userDetails = appDaoImplementation.getUserDetailsByUserId(userId);
        userDetails.setUserToken(token);
        return responseHandler
                .setMessageResponse("Token Validation Success"
                                        ,HttpStatus.OK.value(),
                                        customResponse.loginResponse(userDetails));

    }

    @Override
    @Transactional
    public BaseResponse updatePassword(String userName, String password) {
        UserDetails userDetails = appDaoImplementation.getUserDetailsByUserName(userName);
        if(userDetails == null){
            String message = "User Does not exist with user-name "+userName;
            return responseHandler.setMessageResponse(message,HttpStatus.NOT_FOUND.value(),null);
        }
        String encryptedPassword = passwordEncoder.encode(password);
        appDaoImplementation.updatePasswordByUserId(userDetails.getUser_id(),encryptedPassword);
        return responseHandler.setMessageResponse("Password Updated login to continue",HttpStatus.OK.value(), null);
    }

}
