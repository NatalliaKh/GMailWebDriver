package com.epam.gmailwebdriver.bo;

public class User {

    private final String username;
    private final String password;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUserName() {
        return username;
    }
    public String getPassword() {
        return password;
    }

}
