package com.resotechsolutions.onboarding.dao;

import com.resotechsolutions.onboarding.entity.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.HashMap;
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
    public void save(UserDTO userDTO) {
        String USER_DETAIL_INSERT_QUERY =
                "INSERT INTO user_details (first_name,last_name,user_name,email) VALUES " +
                        "( :firstName, :lastName, :userName,:email)";

        String USER_QUERY =
                "INSERT INTO user (user_id,password) VALUES " +
                        "((SELECT user_id FROM user_details WHERE user_name = :userName),:password)";

        String USER_DETAIL_UPDATE_QUERY =
                "UPDATE user_details SET user_name = CONCAT(:name," +
                        "(SELECT MAX(user_id) FROM user)) " +
                        "WHERE email = :theEmail";

        String USER_TOKEN_QUERY =
                "INSERT INTO token(user_id) VALUES" +
                        " ((SELECT user_id FROM user_details WHERE email = :theEmail))";

        entityManager.createNativeQuery(USER_DETAIL_INSERT_QUERY)
                .setParameter("firstName",userDTO.getFirstName())
                .setParameter("lastName",userDTO.getLastName())
                .setParameter("userName",userDTO.getUserName())
                .setParameter("email",userDTO.getEmail())
                .executeUpdate();
        //
        entityManager.createNativeQuery(USER_QUERY)
                .setParameter("userName",userDTO.getUserName())
                .setParameter("password",userDTO.getPassword())
                .executeUpdate();

        String str = userDTO.getFirstName().substring(0,1)+userDTO.getLastName().substring(0,1);
        entityManager.createNativeQuery(USER_DETAIL_UPDATE_QUERY)
                        .setParameter("name",str)
                        .setParameter("theEmail",userDTO.getEmail())
                        .executeUpdate();
        entityManager.createNativeQuery(USER_TOKEN_QUERY)
                .setParameter("theEmail",userDTO.getEmail())
                .executeUpdate();
    }

    @Override
    public void saveToken(long user_id, String token) {
        String query =
                "UPDATE token SET token = :theToken , updated_on = :currentTime WHERE user_id = :userId";
        entityManager.createNativeQuery(query)
                .setParameter("theToken",token)
                .setParameter("userId",user_id)
                .setParameter("currentTime",new Timestamp(System.currentTimeMillis()))
                .executeUpdate();
    }

    @Override
    public User getUserByUserId(long userId) {
        TypedQuery<User> typedQuery = entityManager.createQuery("from User where userDetails.user_id=:theData", User.class);
        typedQuery.setParameter("theData",userId);
        List<User> list = typedQuery.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public long getUserIdByEmail(String email) {
        TypedQuery<UserDetails> typedQuery = entityManager.createQuery("from UserDetails Where email =:theEmail", UserDetails.class);
        typedQuery.setParameter("theEmail",email);
        List<UserDetails> list = typedQuery.getResultList();
        return list.isEmpty() ? 0 : list.get(0).getUser_id();
    }

    @Override
    public Token getTokenDataByToken(String token) {
        TypedQuery<Token> typedQuery = entityManager.createQuery("from Token WHERE token = :theToken", Token.class);
        typedQuery.setParameter("theToken",token);
        List<Token> tokenList = typedQuery.getResultList();
        return tokenList.isEmpty() ? null : tokenList.get(0);
    }

    @Override
    public UserDetails getUserDetailsByUserName(String userName) {
        TypedQuery<UserDetails> typedQuery = entityManager.createQuery("from UserDetails where userName = :username", UserDetails.class);
        typedQuery.setParameter("username",userName);
        List<UserDetails> list = typedQuery.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public UserDetails findUserDetailByEmail(String email) {
        TypedQuery<UserDetails> typedQuery = entityManager.createQuery("from UserDetails where email = :theEmail", UserDetails.class);
        typedQuery.setParameter("theEmail",email);
        List<UserDetails> list = typedQuery.getResultList();
         return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public UserDetails getUserDetailsByUserId(long id) {
        TypedQuery<UserDetails> typedQuery = entityManager.createQuery("from UserDetails where user_id = :theId", UserDetails.class);
        typedQuery.setParameter("theId",id);
        List<UserDetails> list = typedQuery.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public void updatePasswordByUserId(long userId, String password) {
        String query =
                "UPDATE user SET password = :newPassword WHERE user_id = :userId";

        String update_query =
                "UPDATE user_details SET isPasswordUpdated = true WHERE user_id = :userId";
        entityManager.createNativeQuery(query)
                .setParameter("newPassword",password)
                .setParameter("userId",userId)
                .executeUpdate();
        entityManager.createNativeQuery(update_query)
                .setParameter("userId",userId)
                .executeUpdate();
    }

    @Override
    public Token getTokenByUserId(long userId) {

        TypedQuery<Token> typedQuery = entityManager.createQuery("from Token where userDetails.user_id = :theData",Token.class);
        typedQuery.setParameter("theData",userId);
        List<Token> list = typedQuery.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

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
    public Map<String, String> getHeaders(String name) {
        String primary_detail_query =
                "select look_up.value ,look_up.actual_value from look_up where column_name = ? ";

        List<Map<String,Object>> list = jdbcTemplate.queryForList(primary_detail_query,name);
        Map<String,String> map = new HashMap<>();
        List<Object[]> list1 = convertList(list);
        for(Object[] obj : list1){
            map.put((String) obj[0], (String) obj[1]);
        }
        return map;
    }
    private List<Object[]> convertList(List<Map<String, Object>> resultList) {
        List<Object[]> result = resultList.stream()
                .map(map -> map.values().toArray())
                .collect(Collectors.toList());

        return result;
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }
}
