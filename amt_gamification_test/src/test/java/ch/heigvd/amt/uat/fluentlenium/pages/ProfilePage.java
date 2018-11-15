package ch.heigvd.amt.uat.fluentlenium.pages;

import org.fluentlenium.core.FluentPage;

import static org.assertj.core.api.Assertions.assertThat;

public class ProfilePage extends AbstractAMTGamificationPage {
    private final static String fullName = "#fullName";
    private final static String email = "#email";

    public void isAt() {
        assertThat(title()).isEqualTo("Login Page");
    }

    public String getUrl() {
        return "/createApp";
    }
}
