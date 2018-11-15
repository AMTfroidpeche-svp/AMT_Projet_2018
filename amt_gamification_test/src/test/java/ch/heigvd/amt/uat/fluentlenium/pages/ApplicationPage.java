package ch.heigvd.amt.uat.fluentlenium.pages;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.lift.Finders.title;

public class ApplicationPage extends AbstractAMTGamificationPage {

    private final String nextPage = "#linkNextPage";
    private final String previousPage = "#linkPreviousPage";

    public void isAt() {
        assertThat(title()).isEqualTo("My Apps");
    }

    public String getUrl() {
        return "/app";
    }

    public void goToNextPage(){
        click(nextPage);
    }

    public void goToPreviousPage(){
        click(previousPage);
    }

}
