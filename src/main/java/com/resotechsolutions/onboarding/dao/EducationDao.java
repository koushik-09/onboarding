package com.resotechsolutions.onboarding.dao;

import com.resotechsolutions.onboarding.entity.Education;
import com.resotechsolutions.onboarding.entity.dto.EducationDTO;

import java.util.List;

public interface EducationDao {
    List<Integer> getIdByUserId(long userId);
    Education getEducationDetailsByUserIdAndType(long userId, int type);

    void updateEducationDetails(EducationDTO educationDTO);

   /* void updateGraduationDetails(EducationDTO education);

    void updateSecondaryDetails(EducationDTO educationDTO);
    void updatePrimaryDetails(EducationDTO educationDTO);*/
}
