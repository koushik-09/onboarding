package com.resotechsolutions.onboarding.dao;

import com.resotechsolutions.onboarding.entity.Bank;
import com.resotechsolutions.onboarding.entity.dto.BankDto;

public interface BankDao {
    Bank getIdByUserId(long userId);

    void updateBankDetailsByUserId(BankDto bankDto);
}
