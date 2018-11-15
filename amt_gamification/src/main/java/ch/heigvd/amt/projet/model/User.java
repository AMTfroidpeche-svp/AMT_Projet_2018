package ch.heigvd.amt.projet.model;

import ch.heigvd.amt.projet.business.CipherUtil;

import java.util.Date;

public class User {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private int IDQuestion;
    private String responseQuestion;
    private int permissionLevel = 0;
    private String token = "";
    private Date tokenGeneration;
    private boolean isActive = true;
    private boolean hasToChangedPassword = true;

    public User() {
    }

    public User(String firstName, String lastName, String password, String email, int IDQuestion, String responseQuestion, boolean isActive, boolean hasTochangePassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = CipherUtil.sha2Generator(password);
        this.email = email;
        this.IDQuestion = IDQuestion;
        this.responseQuestion = responseQuestion;
        this.isActive = isActive;
        this.hasToChangedPassword = hasTochangePassword;
    }

    public User(String firstName, String lastName, String password, String email, int IDQuestion, String responseQuestion) {
        this(firstName, lastName, password, email, IDQuestion, responseQuestion, true, false);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getIDQuestion() {
        return IDQuestion;
    }

    public int getPermissionLevel() {
        return permissionLevel;
    }

    public String getResponseQuestion() {
        return responseQuestion;
    }

    public String getToken() {
        return token;
    }

    public Date getTokenGeneration() {
        return tokenGeneration;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean hasToChangedPassword() {
        return hasToChangedPassword;
    }

    public void setPermissionLevel(int permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setTokenGeneration(Date tokenGeneration) {
        this.tokenGeneration = tokenGeneration;
    }

    public void setIDQuestion(int IDQuestion) {
        this.IDQuestion = IDQuestion;
    }

    public void setResponseQuestion(String responseQuestion) { this.responseQuestion = responseQuestion; }

    public void setHasToChangedPassword(boolean hasToChangedPassword) {
        this.hasToChangedPassword = hasToChangedPassword;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
