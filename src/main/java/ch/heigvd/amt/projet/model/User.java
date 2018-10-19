package ch.heigvd.amt.projet.model;

import java.util.Date;

public class User {
    private String mail;
    private String firstName;
    private String lastName;
    private String password;
    private int IDQuestion;
    private String responseQuestion;
    private int permissionsLevel = 0;
    private String token = "";
    private Date tokenGeneration;

    public User(String firstName, String lastName, String password, String mail, int IDQuestion, String responseQuestion) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.mail = mail;
        this.IDQuestion = IDQuestion;
        this.responseQuestion = responseQuestion;
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

    public String getMail() {
        return mail;
    }

    public int getIDQuestion() {
        return IDQuestion;
    }

    public int getPermissionsLevel() {
        return permissionsLevel;
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


    public void setPermissionsLevel(int permissionsLevel) {
        this.permissionsLevel = permissionsLevel;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMail(String email) {
        this.mail = mail;
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

}
