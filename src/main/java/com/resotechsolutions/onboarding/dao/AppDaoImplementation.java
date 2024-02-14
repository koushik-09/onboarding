package com.resotechsolutions.onboarding.dao;

import com.resotechsolutions.onboarding.entity.Token;
import com.resotechsolutions.onboarding.entity.User;
import com.resotechsolutions.onboarding.entity.UserDTO;
import com.resotechsolutions.onboarding.entity.UserDetails;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class AppDaoImplementation implements AppDao{


    @Autowired
    private@Qualifier("entityManagerFactory") EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;


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
                "UPDATE Token SET token = :theToken , updated_on = :currentTime WHERE user_id = :userId";
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
        entityManager.createNativeQuery(query)
                .setParameter("newPassword",password)
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
    public void save(User user) {
        entityManager.persist(user);
    }
}
