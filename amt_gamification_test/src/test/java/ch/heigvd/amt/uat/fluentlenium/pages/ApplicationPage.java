package ch.heigvd.amt.uat.fluentlenium.pages;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.lift.Finders.title;

public class ApplicationPage extends AbstractAMTGamificationPage {

    public void isAt() {
        assertThat(title()).isEqualTo("Profile");
    }

    public String getUrl() {
        return "/app";
    }


}
