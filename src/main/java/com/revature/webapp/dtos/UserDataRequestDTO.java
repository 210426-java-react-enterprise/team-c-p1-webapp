package com.revature.webapp.dtos;

public class UserDataRequestDTO {
    
    private String userField;
    private String userFieldValue;
    
    public String getUserField() {
        return userField;
    }
    
    public void setUserField(String userField) {
        this.userField = userField;
    }
    
    public String getUserFieldValue() {
        return userFieldValue;
    }
    
    public void setUserFieldValue(String userFieldValue) {
        this.userFieldValue = userFieldValue;
    }
}
