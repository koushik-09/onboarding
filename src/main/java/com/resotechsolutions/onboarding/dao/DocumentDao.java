package com.resotechsolutions.onboarding.dao;

import com.resotechsolutions.onboarding.entity.Documents;
import com.resotechsolutions.onboarding.entity.dto.DocumentDto;

public interface DocumentDao {

    Documents getDocumentsByUserIdAndType(long userId,int type);
    void updateUserDocuments(String path,long userId,int type);

    void updateUrl(String path,long userId);

    void updateUserDocuments(DocumentDto documentDto);

    void changeActiveStatus(DocumentDto documentDto);
}
