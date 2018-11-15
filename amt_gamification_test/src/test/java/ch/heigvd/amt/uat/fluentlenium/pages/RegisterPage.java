package ch.heigvd.amt.uat.fluentlenium.pages;

import org.fluentlenium.core.FluentPage;

import static org.assertj.core.api.Assertions.assertThat;

public class RegisterPage extends FluentPage {

    private final static String inputFirstName = "#firstNameField";
    private final static String inputLastName = "#lastNameField";
    private final static String inputEmail = "#emailField";
    private final static String inputPassword = "#passwordField"; // this is the HTML id of the menu
    private final static String inputConfirmPassword = "#passwordConfirmationField";
    private final static String inputQuestion = "#secretQuestionField";
    private final static String inputAnswer = "#secretAnswerField";
    private final static String submitRegister = "#registerSubmit";

    public void isAt() {
        assertThat(title()).isEqualTo("Register");
    }

    public void typeFirstName(String firstName){
        fill(inputFirstName).with(firstName);
    }

    public void typeLastName(String lastname){
        fill(inputLastName).with(lastname);
    }

    public void typeEmail(String email) {
        fill(inputEmail).with(email);
    }

    public void typePassword(String password) {
        fill(inputPassword).with(password);
    }

    public void typeConfirmPassword(String password) {
        fill(inputConfirmPassword).with(password);
    }

    public void typeQuestion(int index) {
        fillSelect(inputQuestion).withIndex(index);
    }

    public void typeAnswer(String answer) {
        fill(inputAnswer).with(answer);
    }

    public void clickRegister() {
        click(submitRegister);
    }

    public String getUrl() {
        return "/registration";
    }
}
