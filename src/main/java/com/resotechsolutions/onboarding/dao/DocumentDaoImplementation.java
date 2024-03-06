package com.resotechsolutions.onboarding.dao;

import com.resotechsolutions.onboarding.entity.Documents;
import com.resotechsolutions.onboarding.entity.Education;
import com.resotechsolutions.onboarding.entity.dto.DocumentDto;
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
public class DocumentDaoImplementation implements DocumentDao{
    private @Qualifier("entityManagerFactory") EntityManager entityManager;
    private JdbcTemplate jdbcTemplate;

    private Log log = LogFactory.getLog(AppDaoImplementation.class);

    @Autowired
    public DocumentDaoImplementation(EntityManager entityManager, JdbcTemplate jdbcTemplate) {
        this.entityManager = entityManager;
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Documents getDocumentsByUserIdAndType(long userId, int type) {
        TypedQuery<Documents> typedQuery = entityManager.createQuery("from Documents where userDetails.user_id = :theId and type = :type", Documents.class);
        typedQuery.setParameter("theId",userId);
        typedQuery.setParameter("type",type);
        List<Documents> list = typedQuery.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public void updateUserDocuments(String path, long userId,int type) {
        String insert_query =
                "Insert into documents(user_id,type,url) values (:theId,:type,:url)";
        String update_query =
                "update documents set type = :type , url = :url,updated_on = :time where user_id = :theId and type = :type";
        if(getDocumentsByUserIdAndType(userId,type)==null){
            entityManager.createNativeQuery(insert_query)
                    .setParameter("theId",userId)
                    .setParameter("type",type)
                    .setParameter("url",path)
                    .executeUpdate();
        }else{
            entityManager.createNativeQuery(update_query)
                    .setParameter("theId",userId)
                    .setParameter("type",type)
                    .setParameter("url",path)
                    .setParameter("time",new Timestamp(System.currentTimeMillis()))
                    .executeUpdate();
        }
    }

    @Override
    public void updateUrl(String path, long userId) {
        String insert_query =
                "insert into document_url(user_id, url) values (:theId, :url)";

        entityManager.createNativeQuery(insert_query)
                .setParameter("theId",userId)
                .setParameter("url",path)
                .executeUpdate();
    }

    @Override
    public void updateUserDocuments(DocumentDto documentDto) {
        String insert_query =
                "Insert into documents(user_id,type,url,number) values (:theId,:type,:url,:number)";
        String update_query =
                "update documents set type = :type , url = :url,number = :number,updated_on = :time where user_id = :theId and type = :type";
        if(getDocumentsByUserIdAndType(documentDto.getId(),documentDto.getType())==null){
            entityManager.createNativeQuery(insert_query)
                    .setParameter("theId",documentDto.getId())
                    .setParameter("type",documentDto.getType())
                    .setParameter("url",documentDto.getPath())
                    .setParameter("number",documentDto.getNumber())
                    .executeUpdate();
        }else{
            entityManager.createNativeQuery(update_query)
                    .setParameter("theId",documentDto.getId())
                    .setParameter("type",documentDto.getType())
                    .setParameter("number",documentDto.getNumber())
                    .setParameter("url",documentDto.getPath())
                    .setParameter("time",new Timestamp(System.currentTimeMillis()))
                    .executeUpdate();
        }
        changeActiveStatus(documentDto);
    }

    @Override
    public void changeActiveStatus(DocumentDto documentDto) {
        String update_query=
                "update document_url set active = :status where url = :url";
        entityManager.createNativeQuery(update_query)
                .setParameter("status",true)
                .setParameter("url",documentDto.getPath())
                .executeUpdate();
    }
}
