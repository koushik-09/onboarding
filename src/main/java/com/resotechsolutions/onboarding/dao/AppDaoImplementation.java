package com.resotechsolutions.onboarding.dao;

import com.resotechsolutions.onboarding.entity.*;
import com.resotechsolutions.onboarding.entity.form.DynamicForm;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class AppDaoImplementation implements AppDao{


    @Autowired
    private @Qualifier("entityManagerFactory") EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Log log = LogFactory.getLog(AppDaoImplementation.class);


    @Override
    public void createAuthToken(long userId, String token) {
        TypedQuery<UserAuthentication> typedQuery = entityManager.createQuery("from UserAuthentication where userId = :theId",UserAuthentication.class);
        typedQuery.setParameter("theId",userId);
        List<UserAuthentication> list = typedQuery.getResultList();
        if(list.isEmpty()){
            String insert_query =
                    "INSERT into user_auth (user_id,auth_token) VALUES (:theId,:theToken)";
            entityManager.createNativeQuery(insert_query)
                    .setParameter("theId",userId)
                    .setParameter("theToken",token)
                    .executeUpdate();
        }
        else{
            String update_query =
                    "UPDATE user_auth SET auth_token = :theToken , created_on = :currentTime WHERE user_id = :userId";
            entityManager.createNativeQuery(update_query)
                    .setParameter("theToken",token)
                    .setParameter("userId",userId)
                    .setParameter("currentTime",new Timestamp(System.currentTimeMillis()))
                    .executeUpdate();
        }
    }

    @Override
    public UserAuthentication getAuthDetailsById(long userId) {
        TypedQuery<UserAuthentication> typedQuery = entityManager.createQuery("from UserAuthentication where userId = :theId", UserAuthentication.class);
        typedQuery.setParameter("theId",userId);
        List<UserAuthentication> list = typedQuery.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }



    @Override
    public EmailContent getEmailTemplateByType(String type) {
        TypedQuery<EmailContent> typedQuery = entityManager.createQuery("from EmailContent where type = :theType", EmailContent.class);
        typedQuery.setParameter("theType",type);
        List<EmailContent> list = typedQuery.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<DynamicForm> getFormData(String name) {
        TypedQuery<DynamicForm> typedQuery = entityManager.createQuery("from DynamicForm where columnName = :theName order by order asc", DynamicForm.class);
        typedQuery.setParameter("theName",name);
        List<DynamicForm> list = typedQuery.getResultList();
        return list;
    }

    @Override
    public LookUp getTypeByIdentifier(String identifier) {
        TypedQuery<LookUp> typedQuery = entityManager.createQuery("from LookUp where identifier = :theIdentifier",LookUp.class);
        typedQuery.setParameter("theIdentifier",identifier);
        List<LookUp> list = typedQuery.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }
}
