package application;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    @JsonProperty("Username")
    private String username;

    @JsonProperty("Password")
    private String password;

    @JsonProperty("Token")
    private String token;

    public User(){

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public void setUsername(String username) {
       this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.token = token;
    }

}
