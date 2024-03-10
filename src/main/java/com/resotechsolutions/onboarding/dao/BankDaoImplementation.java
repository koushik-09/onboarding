package com.resotechsolutions.onboarding.dao;

import com.resotechsolutions.onboarding.entity.Bank;
import com.resotechsolutions.onboarding.entity.dto.BankDto;
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
public class BankDaoImplementation implements BankDao {
    private @Qualifier("entityManagerFactory") EntityManager entityManager;
    private JdbcTemplate jdbcTemplate;

    private Log log = LogFactory.getLog(AppDaoImplementation.class);

    @Autowired
    public BankDaoImplementation(EntityManager entityManager, JdbcTemplate jdbcTemplate) {
        this.entityManager = entityManager;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Bank getIdByUserId(long userId) {
        TypedQuery<Bank> typedQuery = entityManager.createQuery("from Bank where userDetails.user_id = :theId", Bank.class);
        typedQuery.setParameter("theId",userId);
        List<Bank> list = typedQuery.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public void updateBankDetailsByUserId(BankDto bankDto) {
        String insert_query =
                "insert into bank_details(user_id,account_number,ifsc,bank_name,url) values (:theId,:accNo,:ifsc,:name,:url)";

        String update_query =
                "update bank_details set account_number = :accNo,ifsc = :ifsc, updated_on = :time, bank_name = :name, url=:url where user_id = :theId";
        if(getIdByUserId(bankDto.getId())==null){
            entityManager.createNativeQuery(insert_query)
                    .setParameter("theId",bankDto.getId())
                    .setParameter("accNo",bankDto.getAccountNumber())
                    .setParameter("ifsc",bankDto.getIfscCode())
                    .setParameter("name",bankDto.getBankName())
                    .setParameter("url",bankDto.getBank())
                    .executeUpdate();
        }else {
            entityManager.createNativeQuery(update_query)
                    .setParameter("theId",bankDto.getId())
                    .setParameter("accNo",bankDto.getAccountNumber())
                    .setParameter("ifsc",bankDto.getIfscCode())
                    .setParameter("name",bankDto.getBankName())
                    .setParameter("time",new Timestamp(System.currentTimeMillis()))
                    .setParameter("url",bankDto.getBank())
                    .executeUpdate();
        }
    }
}
