package com.resotechsolutions.onboarding.dao;

import com.resotechsolutions.onboarding.entity.User;
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
public class UserDaoImplementation implements UserDao{
    private @Qualifier("entityManagerFactory") EntityManager entityManager;
    private JdbcTemplate jdbcTemplate;

    private Log log = LogFactory.getLog(AppDaoImplementation.class);

    @Autowired
    public UserDaoImplementation(EntityManager entityManager,JdbcTemplate jdbcTemplate){
        this.entityManager = entityManager;
        this.jdbcTemplate=jdbcTemplate;
    }
    @Override
    public User getUserByUserId(long userId) {
        TypedQuery<User> typedQuery = entityManager.createQuery("from User where userDetails.user_id=:theData", User.class);
        typedQuery.setParameter("theData",userId);
        List<User> list = typedQuery.getResultList();
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
}
