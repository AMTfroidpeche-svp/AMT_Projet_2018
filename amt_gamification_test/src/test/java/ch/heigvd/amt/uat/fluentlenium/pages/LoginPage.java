package ch.heigvd.amt.uat.fluentlenium.pages;

import org.fluentlenium.core.FluentPage;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginPage extends FluentPage {

    private final static String inputEmail = "#emailField";
    private final static String inputPassword = "#passwordField"; // this is the HTML id of the menu
    private final static String submitLogin = "#submitLogin";
    private final static String passwordForgottenLink = "#linkPasswordForgotten";
    private final static String createAccountLink = "#linkRegisterAccount";

    public void isAt() {
        assertThat(title()).isEqualTo("Login");
    }

    public void typeEmail(String email) {
        fill(inputEmail).with(email);
    }

    public void typePassword(String password) {
        fill(inputPassword).with(password);
    }

    public void clickSignin() {
        click(submitLogin);
    }

    public void clickPasswordForgotten(){click(passwordForgottenLink);}

    public void clickCreateAccount(){click(createAccountLink);}

    public String getUrl() {
        return "/login";
    }
}
