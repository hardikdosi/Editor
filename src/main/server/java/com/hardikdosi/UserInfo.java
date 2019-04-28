package com.hardikdosi;

/**
 * Created by Hardik Dosi on 11/18/2016.
 */
public class UserInfo {
    private String email;
    private String username;
    private String name;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return new StringBuffer(" username : ").append(this.username)
                .append(" name : ").append(this.name)
                .append(" email : ").append(this.email)
                .append(" password : ").append(this.password).toString();
    }
}
