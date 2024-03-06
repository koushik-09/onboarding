package com.resotechsolutions.onboarding.dao;


import com.resotechsolutions.onboarding.entity.*;
import com.resotechsolutions.onboarding.entity.form.DynamicForm;

import java.util.List;


public interface AppDao {

    //user authentication methods
    void createAuthToken(long userId,String token);

    UserAuthentication getAuthDetailsById(long userId);

    EmailContent getEmailTemplateByType(String type);

    List<DynamicForm> getFormData(String name);

    LookUp getTypeByIdentifier(String identifier);

}
