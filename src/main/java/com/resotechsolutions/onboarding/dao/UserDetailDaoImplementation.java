package com.resotechsolutions.onboarding.dao;

import com.resotechsolutions.onboarding.entity.UserDTO;
import com.resotechsolutions.onboarding.entity.UserDetails;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDetailDaoImplementation implements UserDetailDao{

    private @Qualifier("entityManagerFactory") EntityManager entityManager;
    private JdbcTemplate jdbcTemplate;

    private Log log = LogFactory.getLog(AppDaoImplementation.class);

    @Autowired
    public UserDetailDaoImplementation(EntityManager entityManager,JdbcTemplate jdbcTemplate){
        this.entityManager = entityManager;
        this.jdbcTemplate=jdbcTemplate;
    }
    @Override
    public void registerUserDetails(UserDTO userDTO) {
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
    public void updateUserDetailsByUserId(UserDTO userDTO) {

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
    public long getUserIdByEmail(String email) {
        TypedQuery<UserDetails> typedQuery = entityManager.createQuery("from UserDetails Where email =:theEmail", UserDetails.class);
        typedQuery.setParameter("theEmail",email);
        List<UserDetails> list = typedQuery.getResultList();
        return list.isEmpty() ? 0 : list.get(0).getUser_id();
    }
}
