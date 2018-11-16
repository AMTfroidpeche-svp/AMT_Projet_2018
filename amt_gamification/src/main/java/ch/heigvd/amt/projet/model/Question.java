package ch.heigvd.amt.projet.model;

public class Question {
    private int id;
    private String question;

    public Question(String question, int id) {
        this.id = id;
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public int getId() {
        return id;
    }
}

