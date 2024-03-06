package com.resotechsolutions.onboarding.dao;

import com.resotechsolutions.onboarding.entity.Education;
import com.resotechsolutions.onboarding.entity.dto.EducationDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EducationDaoImplementation implements EducationDao {
    private @Qualifier("entityManagerFactory") EntityManager entityManager;
    private JdbcTemplate jdbcTemplate;

    private Log log = LogFactory.getLog(AppDaoImplementation.class);

    @Autowired
    public EducationDaoImplementation(EntityManager entityManager, JdbcTemplate jdbcTemplate) {
        this.entityManager = entityManager;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Integer> getIdByUserId(long userId) {
        TypedQuery<Education> typedQuery = entityManager.createQuery("from Education where userDetails.user_id = :theId", Education.class);
        typedQuery.setParameter("theId", userId);
        List<Education> education = typedQuery.getResultList();
        List<Integer> list = new ArrayList<>();

        if (education.isEmpty()) return null;

        for (Education edu : education) {
            list.add(edu.getType());
        }
        return list;
    }

    @Override
    public Education getEducationDetailsByUserIdAndType(long userId, int type) {
        String queryString = "SELECT e FROM Education e WHERE e.type = :theType AND e.userDetails.user_id = :theId";
        TypedQuery<Education> typedQuery = entityManager.createQuery(queryString, Education.class);
        typedQuery.setParameter("theType", type);
        typedQuery.setParameter("theId", userId);
        List<Education> list = typedQuery.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public void updateEducationDetails(EducationDTO education) {
        String insert_query =
                "insert into education (user_id, type, degree_name, major, institution_name, cgpa, start, end,url) " +
                        "values (:theId,:type,:degree,:major,:inst,:cgpa,:start,:end,:url)";
        String update_query =
                "update education set type = :type,degree_name =:degree ,major = :major" +
                        " ,institution_name = :inst,cgpa = :cgpa,start = :start,end = :end,url = :url where user_id = :theId and type = :type";
        if (getEducationDetailsByUserIdAndType(education.getId(), 1) == null) {
            entityManager.createNativeQuery(insert_query)
                    .setParameter("theId", education.getId())
                    .setParameter("type", 1)
                    .setParameter("degree", education.getGradDegree())
                    .setParameter("major", education.getGradMajor())
                    .setParameter("inst", education.getGradInstitution())
                    .setParameter("cgpa", education.getGradCgpa())
                    .setParameter("start", education.getGradStart())
                    .setParameter("end", education.getGradEnd())
                    .setParameter("url",education.getGradMemoUrl())
                    .executeUpdate();
        } else {
            entityManager.createNativeQuery(update_query)
                    .setParameter("theId", education.getId())
                    .setParameter("type", 1)
                    .setParameter("degree", education.getGradDegree())
                    .setParameter("major", education.getGradMajor())
                    .setParameter("inst", education.getGradInstitution())
                    .setParameter("cgpa", education.getGradCgpa())
                    .setParameter("start", education.getGradStart())
                    .setParameter("end", education.getGradEnd())
                    .setParameter("url",education.getGradMemoUrl())
                    .executeUpdate();
        }
        if (getEducationDetailsByUserIdAndType(education.getId(), 2) == null) {
            entityManager.createNativeQuery(insert_query)
                    .setParameter("theId", education.getId())
                    .setParameter("type", 2)
                    .setParameter("degree", education.getSecondDegree())
                    .setParameter("major", education.getSecondMajor())
                    .setParameter("inst", education.getSecondInstitution())
                    .setParameter("cgpa", education.getSecondCgpa())
                    .setParameter("start", education.getSecondStart())
                    .setParameter("end", education.getSecondEnd())
                    .setParameter("url",education.getSecondMemoUrl())
                    .executeUpdate();
        } else {
            entityManager.createNativeQuery(update_query)
                    .setParameter("theId", education.getId())
                    .setParameter("type", 2)
                    .setParameter("degree", education.getSecondDegree())
                    .setParameter("major", education.getSecondMajor())
                    .setParameter("inst", education.getSecondInstitution())
                    .setParameter("cgpa", education.getSecondCgpa())
                    .setParameter("start", education.getSecondStart())
                    .setParameter("end", education.getSecondEnd())
                    .setParameter("url",education.getSecondMemoUrl())
                    .executeUpdate();
        }
        if (getEducationDetailsByUserIdAndType(education.getId(), 3) == null) {
            entityManager.createNativeQuery(insert_query)
                    .setParameter("theId", education.getId())
                    .setParameter("type", 3)
                    .setParameter("degree", education.getPrimDegree())
                    .setParameter("major", education.getPrimMajor())
                    .setParameter("inst", education.getPrimInstitution())
                    .setParameter("cgpa", education.getPrimCgpa())
                    .setParameter("start", education.getPrimStart())
                    .setParameter("end", education.getPrimEnd())
                    .setParameter("url",education.getPrimMemoUrl())
                    .executeUpdate();
        } else {
            entityManager.createNativeQuery(update_query)
                    .setParameter("theId", education.getId())
                    .setParameter("type", 3)
                    .setParameter("degree", education.getPrimDegree())
                    .setParameter("major", education.getPrimMajor())
                    .setParameter("inst", education.getPrimInstitution())
                    .setParameter("cgpa", education.getPrimCgpa())
                    .setParameter("start", education.getPrimStart())
                    .setParameter("end", education.getPrimEnd())
                    .setParameter("url",education.getPrimMemoUrl())
                    .executeUpdate();
        }
    }
}

    /*@Override
    public void updateGraduationDetails(EducationDTO education) {
        String insert_query =
                "insert into education (user_id, type, degree_name, major, institution_name, cgpa, start, end) " +
                            "values (:theId,:type,:degree,:major,:inst,:cgpa,:start,:end)";
        String update_query =
                "update education set type = :type,degree_name =:degree ,major = :major" +
                        " ,institution_name = :inst,cgpa = :cgpa,start = :start,end = :end where user_id = :theId and type = :type";
        if(getEducationDetailsByUserIdAndType(education.getId(),1) == null){
           entityManager.createNativeQuery(insert_query)
                   .setParameter("theId",education.getId())
                   .setParameter("type",1)
                   .setParameter("degree",education.getGradDegree())
                   .setParameter("major",education.getGradMajor())
                   .setParameter("inst",education.getGradInstitution())
                   .setParameter("cgpa",education.getGradCgpa())
                   .setParameter("start",education.getGradStart())
                   .setParameter("end",education.getGradEnd())
                   .executeUpdate();
        }else{
            entityManager.createNativeQuery(update_query)
                    .setParameter("theId",education.getId())
                    .setParameter("type",1)
                    .setParameter("degree",education.getGradDegree())
                    .setParameter("major",education.getGradMajor())
                    .setParameter("inst",education.getGradInstitution())
                    .setParameter("cgpa",education.getGradCgpa())
                    .setParameter("start",education.getGradStart())
                    .setParameter("end",education.getGradEnd())
                    .executeUpdate();
        }

    }

    @Override
    public void updateSecondaryDetails(EducationDTO education) {
        String insert_query =
                "insert into education (user_id, type, degree_name, major, institution_name, cgpa, start, end) " +
                        "values (:theId,:Stype,:Sdegree,:Smajor,:Sinst,:Scgpa,:Sstart,:Send)";
        String update_query =
                "update education set type = :Stype,degree_name =:Sdegree ,major = :Smajor" +
                        " ,institution_name = :Sinst,cgpa = :Scgpa,start = :Sstart,end = :Send where user_id = :theId and type = :Stype";
        if(getEducationDetailsByUserIdAndType(education.getId(),2) == null){
            entityManager.createNativeQuery(insert_query)
                    .setParameter("theId",education.getId())
                    .setParameter("Stype",2)
                    .setParameter("Sdegree",education.getSecondDegree())
                    .setParameter("Smajor",education.getSecondMajor())
                    .setParameter("Sinst",education.getSecondInstitution())
                    .setParameter("Scgpa",education.getSecondCgpa())
                    .setParameter("Sstart",education.getSecondStart())
                    .setParameter("Send",education.getSecondEnd())
                    .executeUpdate();
        }else{
            entityManager.createNativeQuery(update_query)
                    .setParameter("theId",education.getId())
                    .setParameter("Stype",2)
                    .setParameter("Sdegree",education.getSecondDegree())
                    .setParameter("Smajor",education.getSecondMajor())
                    .setParameter("Sinst",education.getSecondInstitution())
                    .setParameter("Scgpa",education.getSecondCgpa())
                    .setParameter("Sstart",education.getSecondStart())
                    .setParameter("Send",education.getSecondEnd())
                    .executeUpdate();
        }
    }

    @Override
    public void updatePrimaryDetails(EducationDTO education) {
        String insert_query =
                "insert into education (user_id, type, degree_name, major, institution_name, cgpa, start, end) " +
                        "values (:theId,:Ptype,:Pdegree,:Pmajor,:Pinst,:Pcgpa,:Pstart,:Pend)";
        String update_query =
                "update education set type = :Ptype,degree_name =:Pdegree ,major = :Pmajor" +
                        " ,institution_name = :Pinst,cgpa = :Pcgpa,start = :Pstart,end = :Pend where user_id = :theId and type = :Ptype";
        if(getEducationDetailsByUserIdAndType(education.getId(),3) == null){
            entityManager.createNativeQuery(insert_query)
                    .setParameter("theId",education.getId())
                    .setParameter("Ptype",3)
                    .setParameter("Pdegree",education.getPrimDegree())
                    .setParameter("Pmajor",education.getPrimMajor())
                    .setParameter("Pinst",education.getPrimInstitution())
                    .setParameter("Pcgpa",education.getPrimCgpa())
                    .setParameter("Pstart",education.getPrimStart())
                    .setParameter("Pend",education.getPrimEnd())
                    .executeUpdate();
        }else{
            entityManager.createNativeQuery(update_query)
                    .setParameter("theId",education.getId())
                    .setParameter("Ptype",3)
                    .setParameter("Pdegree",education.getPrimDegree())
                    .setParameter("Pmajor",education.getPrimMajor())
                    .setParameter("Pinst",education.getPrimInstitution())
                    .setParameter("Pcgpa",education.getPrimCgpa())
                    .setParameter("Pstart",education.getPrimStart())
                    .setParameter("Pend",education.getPrimEnd())
                    .executeUpdate();
        }
    }
}*/
