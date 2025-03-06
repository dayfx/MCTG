package application;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    @JsonProperty("Username")
    private String username;

    @JsonProperty("Password")
    private String password;

    @JsonProperty("Token")
    private String token;

    @JsonProperty("Bio")
    private String bio;

    @JsonProperty("Image")
    private String image;

    @JsonProperty("Name") // Name, NOT Username for JSON Body for user data requests (CURL line 264...)
    private String name;
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

    public String getBio() {
        return bio;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setBio(String bio) {
        this.bio = bio;
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
