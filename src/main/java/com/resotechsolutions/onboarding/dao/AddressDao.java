package com.resotechsolutions.onboarding.dao;

import com.resotechsolutions.onboarding.entity.dto.UserDTO;

public interface AddressDao {

    long getIdByUserId(long userId);

    void updateAddressByUserId(UserDTO userDTO);
}
