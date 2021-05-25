package com.revature.dtos;

public class CredentialDTO {

    private String username;
    private String password;

    public CredentialDTO () {

    }

    public CredentialDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
