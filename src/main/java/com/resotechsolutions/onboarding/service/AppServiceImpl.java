package com.resotechsolutions.onboarding.service;

import com.resotechsolutions.onboarding.config.TokenGenerator;
import com.resotechsolutions.onboarding.dao.*;
import com.resotechsolutions.onboarding.entity.*;
import com.resotechsolutions.onboarding.entity.dto.BankDto;
import com.resotechsolutions.onboarding.entity.dto.EducationDTO;
import com.resotechsolutions.onboarding.entity.dto.UserDTO;
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
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;

@Service
public class AppServiceImpl implements AppService {

    private AppDaoImplementation appDaoImplementation;

    private UserDetailDaoImplementation userDetailDaoImplementation;
    private UserDaoImplementation userDaoImplementation;
    private AddressDaoImplementation addressDaoImplementation;
    private EducationDaoImplementation educationDaoImplementation;
    private DocumentDaoImplementation documentDaoImplementation;
    private BankDaoImplementation bankDaoImplementation;

    private TokenDaoImplementation tokenDaoImplementation;

    private TokenGenerator tokenGenerator;

    private BCryptPasswordEncoder passwordEncoder;
    private ResponseHandler responseHandler;

    private CustomResponse customResponse;

    private MailServiceImplementation mailService;

    private Log log = LogFactory.getLog(AppServiceImpl.class);

    @Value("${file.url}")
    private String FOLDER_PATH;


    @Autowired
    public AppServiceImpl(AppDaoImplementation theAppDaoImplementation,
                          UserDetailDaoImplementation userDetailDaoImplementation,
                          UserDaoImplementation userDaoImplementation,
                          TokenDaoImplementation tokenDaoImplementation,
                          AddressDaoImplementation addressDaoImplementation,
                          EducationDaoImplementation educationDaoImplementation,
                          DocumentDaoImplementation documentDaoImplementation,
                          BankDaoImplementation bankDaoImplementation,
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
        this.addressDaoImplementation = addressDaoImplementation;
        this.educationDaoImplementation = educationDaoImplementation;
        this.documentDaoImplementation = documentDaoImplementation;
        this.bankDaoImplementation = bankDaoImplementation;
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

            //encrypting the default password using BCRYPT
            String encryptedPassword = passwordEncoder.encode(password);
            userDTO.setPassword(encryptedPassword);

            String userName = userDTO.getFirstName() + userDTO.getLastName();
            userDTO.setUserName(userName);

            //Saving the user details into the database
            userDetailDaoImplementation.registerUserDetails(userDTO);

            //Fetching username of the user from database
            long id = userDetailDaoImplementation.getUserIdByEmail(email);
            String username = ""+userDTO.getFirstName().charAt(0)+userDTO.getLastName().charAt(0)+id;

            //sending a welcome mail to user
            EmailContent emailContent = appDaoImplementation.getEmailTemplateByType("welcome-email");
            mailService.welcomeEmail(userDTO.getEmail(),username,password,emailContent);

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
        addressDaoImplementation.updateAddressByUserId(userDTO);
        return responseHandler.setMessageResponse("Details Updated",1,null);
    }

    @Override
    @Transactional
    public BaseResponse updateEducationDetails(String token, EducationDTO educationDTO) {
        Token userToken = tokenDaoImplementation.getTokenDataByToken(token);
        if(userToken == null){
            return responseHandler.setMessageResponse("Invalid token",-1,null);
        }
        educationDTO.setId(userToken.getUserDetails().getUser_id());
        educationDaoImplementation.updateEducationDetails(educationDTO);
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
            System.out.println(token.getToken());
            userDetails = user.getUserDetails();
            userDetails.setUser(null);
            //generating a new token for user
            String userToken = tokenGenerator.generateToken();
            System.out.println(userToken);
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
    public BaseResponse getUserDetails(String token) {
        Token userToken = tokenDaoImplementation.getTokenDataByToken(token);
        if (userToken == null ){
            return responseHandler.setMessageResponse("Invalid token",-1,null);
        }
        UserDetails userDetails = userDetailDaoImplementation.getUserDetailsByUserId(userToken.getUserDetails().getUser_id());
        userDetails.setUserToken(token);
        return responseHandler.setMessageResponse("Success",1,customResponse.userDetailsResponse(userDetails));
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
    public BaseResponse updateUserDocuments(MultipartFile file, String token,String documentType) {
        Token userToken = tokenDaoImplementation.getTokenDataByToken(token);
        if (userToken == null ){
            return responseHandler.setMessageResponse("Invalid token",-1,null);
        }
        long userId = userToken.getUserDetails().getUser_id();
        String dir =FOLDER_PATH +  documentType.toLowerCase();
        try{
            Files.createDirectories(Paths.get(dir));
            String timestamp = String.valueOf(new Timestamp(System.currentTimeMillis()));
            String fileName = userId + "_"+file.getOriginalFilename();
            String filePath = dir + "/" + fileName;
//            file.transferTo(Paths.get(filePath)); documentType.equalsIgnoreCase("pan")
            Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
            if(documentType.equalsIgnoreCase("pan")){
                documentDaoImplementation.updateUserDocuments(filePath,userId,1);
            }else if(documentType.equalsIgnoreCase("aadhar")){
                documentDaoImplementation.updateUserDocuments(filePath,userId,2);
            }else if(documentType.equalsIgnoreCase("marksheet")){
                documentDaoImplementation.updateUserDocuments(filePath,userId,3);
            }
            return responseHandler.setMessageResponse("Upload Success",1,filePath);
        } catch (IOException e) {
            log.warn(e.toString());
            return responseHandler.setMessageResponse("Upload Failure",-2,null);
        }
    }

    @Override
    @Transactional
    public BaseResponse updateMultipleUserDocuments(MultipartFile file, String token) {
        Token userToken = tokenDaoImplementation.getTokenDataByToken(token);
        if (userToken == null ){
            return responseHandler.setMessageResponse("Invalid token",-1,null);
        }
        long userId = userToken.getUserDetails().getUser_id();
        String dir = FOLDER_PATH;
        if(file.getOriginalFilename().toLowerCase().contains("pan")){
            dir += "pan";
        }else if(file.getOriginalFilename().toLowerCase().contains("aadhar")){
            dir += "aadhar";
        }else if(file.getOriginalFilename().toLowerCase().contains("marksheet")){
            dir += "marksheet";
        }
        try{
            Files.createDirectories(Paths.get(dir));
            String timestamp = String.valueOf(new Timestamp(System.currentTimeMillis()));
            String fileName = userId + "_"+file.getOriginalFilename();
            String filePath = dir + "/" + fileName;
//            file.transferTo(Paths.get(filePath)); documentType.equalsIgnoreCase("pan")
            Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
            if(file.getOriginalFilename().toLowerCase().contains("pan")){
                documentDaoImplementation.updateUserDocuments(filePath,userId,1);
            }else if(file.getOriginalFilename().toLowerCase().contains("aadhar")){
                documentDaoImplementation.updateUserDocuments(filePath,userId,2);
            }else if(file.getOriginalFilename().toLowerCase().contains("marksheet")){
                documentDaoImplementation.updateUserDocuments(filePath,userId,3);
            }
            return responseHandler.setMessageResponse("Upload Success",1,filePath);
        } catch (IOException e) {
            log.warn(e.toString());
            return responseHandler.setMessageResponse("Upload Failure",-2,null);
        }
    }

    @Override
    @Transactional
    public BaseResponse updateBankDetails(String token, BankDto bankDto) {
        Token userToken = tokenDaoImplementation.getTokenDataByToken(token);
        if (userToken == null ){
            return responseHandler.setMessageResponse("Invalid token",-1,null);
        }
        bankDto.setId(userToken.getUserDetails().getUser_id());
        bankDaoImplementation.updateBankDetailsByUserId(bankDto);
        return responseHandler.setMessageResponse("Details Updated",1,null);
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
        appDaoImplementation.createAuthToken(details.getUser_id(),otp);
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
        forms.setPersonalDetails(appDaoImplementation.getFormData("personal_details"));
        forms.setEducation(appDaoImplementation.getFormData("education"));
        forms.setPanDetails(appDaoImplementation.getFormData("pan_details"));
        forms.setAadharDetails(appDaoImplementation.getFormData("aadhar_details"));
        forms.setMarksheetDetails(appDaoImplementation.getFormData("marksheet_details"));
        forms.setAgreementDetails(appDaoImplementation.getFormData("agreement"));
        forms.setBankDetails(appDaoImplementation.getFormData("bank_details"));
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
