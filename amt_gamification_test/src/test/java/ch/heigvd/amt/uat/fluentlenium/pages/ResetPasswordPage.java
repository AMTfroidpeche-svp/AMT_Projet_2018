package ch.heigvd.amt.uat.fluentlenium.pages;

import org.fluentlenium.core.FluentPage;

import static org.assertj.core.api.Assertions.assertThat;

public class ResetPasswordPage extends FluentPage {
    private final static String inputToken = "#appNameField";
    private final static String inputNewPassword = "#appDescrField"; // this is the HTML id of the menu
    private final static String submitChangePassword = "#submitCreateApp";

    public void isAt() {
        assertThat(title()).isEqualTo("Reset password");
    }

    public void typeToken(String token) {
        fill(inputToken).with(token);
    }

    public void typeNewPassword(String newPassword) {
        fill(inputNewPassword).with(newPassword);
    }

    public void clickChangePassword() {
        click(submitChangePassword);
    }

    public String getUrl() {
        return "/createApp";
    }
}
