package ch.heigvd.amt.uat.fluentlenium.pages;

import org.fluentlenium.core.FluentPage;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordForgottenPage extends FluentPage {

    private final static String inputEmail = "#emailField";
    private final static String submitResetPassword = "#submitPasswordForgotten";

    public void isAt() {
        assertThat(title()).isEqualTo("Password Forgotten");
    }

    public void typeEmail(String email) {
        fill(inputEmail).with(email);
    }

    public void clickReset() {
        click(submitResetPassword);
    }

    public String getUrl() {
        return "/newPassword";
    }

}
