package com.resotechsolutions.onboarding.dao;

import com.resotechsolutions.onboarding.entity.Address;
import com.resotechsolutions.onboarding.entity.dto.UserDTO;
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
public class AddressDaoImplementation implements AddressDao{
    private @Qualifier("entityManagerFactory") EntityManager entityManager;
    private JdbcTemplate jdbcTemplate;

    private Log log = LogFactory.getLog(AppDaoImplementation.class);

    @Autowired
    public AddressDaoImplementation(EntityManager entityManager,JdbcTemplate jdbcTemplate){
        this.entityManager = entityManager;
        this.jdbcTemplate=jdbcTemplate;
    }

    @Override
    public long getIdByUserId(long userId) {
        TypedQuery<Address> typedQuery = entityManager.createQuery("from Address where userDetails.user_id = :theId", Address.class);
        typedQuery.setParameter("theId",userId);
        List<Address> list = typedQuery.getResultList();
        return list.isEmpty() ? -1 : list.get(0).getId();
    }

    @Override
    public void updateAddressByUserId(UserDTO userDTO) {
        String query;
        if(getIdByUserId(userDTO.getId())== -1){
            query =
                    "insert into address(user_id, street, city, state, country, pin_code,updated_on) VALUES (:id,:street,:city,:state,:country,:pincode,:currentTime)";
        }
        else {
            query = "UPDATE address SET street = :street, city = :city," +
                    " state = :state, country = :country, pin_code = :pincode, updated_on = :currentTime " +
                    "WHERE user_id = :id";
        }
        entityManager.createNativeQuery(query)
                .setParameter("street",userDTO.getStreet())
                .setParameter("city",userDTO.getCity())
                .setParameter("state",userDTO.getState())
                .setParameter("country",userDTO.getCountry())
                .setParameter("pincode",userDTO.getPinCode())
                .setParameter("currentTime",new Timestamp(System.currentTimeMillis()))
                .setParameter("id",userDTO.getId())
                .executeUpdate();
    }
}
