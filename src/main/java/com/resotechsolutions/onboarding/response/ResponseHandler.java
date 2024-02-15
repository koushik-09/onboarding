package com.resotechsolutions.onboarding.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResponseHandler {

    private Status status;

    private BaseResponse baseResponse;

    @Autowired
    public ResponseHandler(Status theStatus,BaseResponse theBaseResponse){
        this.status = theStatus;
        this.baseResponse = theBaseResponse;
    }

    public BaseResponse setMessageResponse(String message,int code,Object data){
        status.setStatusCode(code);
        status.setStatusMessage(message);
        baseResponse.setStatus(status);
        baseResponse.setData(data);
        return baseResponse;
    }

    public BaseResponse setMessageResponse(int code){
        if(code == -1){
            status.setStatusCode(code);
            status.setStatusMessage("Registration Failure");
            baseResponse.setStatus(status);
            return baseResponse;
        }
        if(code == -2){
            status.setStatusCode(code);
            status.setStatusMessage("Login Failure");
            baseResponse.setStatus(status);
            return baseResponse;
        }
        if(code == -3){
            status.setStatusCode(code);
            status.setStatusMessage("Token Validation Failure");
            baseResponse.setStatus(status);
            return baseResponse;
        }
        if(code == -4){
            status.setStatusCode(code);
            status.setStatusMessage("Password Update Failure");
            baseResponse.setStatus(status);
            return baseResponse;
        }
        if(code == 200){
            status.setStatusCode(code);
            status.setStatusMessage("Request Success");
            baseResponse.setStatus(status);
            return baseResponse;
        }
        if(code == 201){
            status.setStatusCode(code);
            status.setStatusMessage("Created");
            baseResponse.setStatus(status);
            return baseResponse;
        }
        if(code == 202){
            status.setStatusCode(code);
            status.setStatusMessage("Request Accepted");
            baseResponse.setStatus(status);
            return baseResponse;
        }
        if(code == 204){
            status.setStatusCode(code);
            status.setStatusMessage("Content Not Available");
            baseResponse.setStatus(status);
            return baseResponse;
        }
        if(code == 400){
            status.setStatusCode(code);
            status.setStatusMessage("Bad Request");
            baseResponse.setStatus(status);
            return baseResponse;
        }
        if(code == 401){
            status.setStatusCode(code);
            status.setStatusMessage("Un-Authorized");
            baseResponse.setStatus(status);
            return baseResponse;
        }
        if(code == 404){
            status.setStatusCode(code);
            status.setStatusMessage("Not Found");
            baseResponse.setStatus(status);
            return baseResponse;
        }
        if(code == 409){
            status.setStatusCode(code);
            status.setStatusMessage("Conflict");
            baseResponse.setStatus(status);
            return baseResponse;
        }
        if(code == 500){
            status.setStatusCode(code);
            status.setStatusMessage("Internal Server Error");
            baseResponse.setStatus(status);
            return baseResponse;
        }
        if(code == 502){
            status.setStatusCode(code);
            status.setStatusMessage("Bad Gateway");
            baseResponse.setStatus(status);
            return baseResponse;
        }
        return baseResponse;
    }
}
