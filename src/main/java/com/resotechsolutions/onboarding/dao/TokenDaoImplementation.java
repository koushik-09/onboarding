package com.resotechsolutions.onboarding.dao;

import com.resotechsolutions.onboarding.entity.Token;
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

@Repository
public class TokenDaoImplementation implements TokenDao{
    private @Qualifier("entityManagerFactory") EntityManager entityManager;
    private JdbcTemplate jdbcTemplate;

    private Log log = LogFactory.getLog(AppDaoImplementation.class);

    @Autowired
    public TokenDaoImplementation(EntityManager entityManager,JdbcTemplate jdbcTemplate){
        this.entityManager = entityManager;
        this.jdbcTemplate=jdbcTemplate;
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
    public Token getTokenDataByToken(String token) {
        TypedQuery<Token> typedQuery = entityManager.createQuery("from Token WHERE token = :theToken", Token.class);
        typedQuery.setParameter("theToken",token);
        List<Token> tokenList = typedQuery.getResultList();
        return tokenList.isEmpty() ? null : tokenList.get(0);
    }
    @Override
    public Token getTokenByUserId(long userId) {

        TypedQuery<Token> typedQuery = entityManager.createQuery("from Token where userDetails.user_id = :theData",Token.class);
        typedQuery.setParameter("theData",userId);
        List<Token> list = typedQuery.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }
    @Override
    public void deleteTokenById(long id) {
        String delete_query =
                "update token set token = NULL where user_id = :theId";
        entityManager.createNativeQuery(delete_query)
                .setParameter("theId",id)
                .executeUpdate();
    }
}
