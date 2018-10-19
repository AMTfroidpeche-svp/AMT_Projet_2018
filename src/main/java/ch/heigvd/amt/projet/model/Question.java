package ch.heigvd.amt.projet.model;

public class Question {
    private static int count = 0;
    private int id;
    private String question;

    public Question(String question) {
        id = ++count;
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }
}

