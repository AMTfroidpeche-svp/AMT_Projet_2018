package ch.heigvd.amt.uat.fluentlenium.pages;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateApplicationPage extends AbstractAMTGamificationPage {
    private final static String inputAppName = "#appNameField";
    private final static String inputDescription = "#appDescrField"; // this is the HTML id of the menu
    private final static String submitAddApp = "#submitCreateApp";

    public void isAt() {
        assertThat(title()).isEqualTo("Login Page");
    }

    public void typeAppName(String name) {
        fill(inputAppName).with(name);
    }

    public void typeDescription(String description) {
        fill(inputDescription).with(description);
    }

    public void clickCreate() {
        click(submitAddApp);
    }

    public String getUrl() {
        return "/createApp";
    }
}
