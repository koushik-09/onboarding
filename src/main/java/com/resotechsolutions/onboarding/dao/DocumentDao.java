package com.resotechsolutions.onboarding.dao;

import com.resotechsolutions.onboarding.entity.Documents;

public interface DocumentDao {


    Documents getDocumentsByUserIdAndType(long userId,int type);
    public void updateUserDocuments(String path,long userId,int type);
}
