package ch.heigvd.amt.uat.fluentlenium.pages;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.lift.Finders.title;

public class ApplicationPage extends AbstractAMTGamificationPage {

    private final String nextPage = "#linkNextPage";
    private final String previousPage = "#linkPreviousPage";

    @FindBy(css = ".app-container")
    private FluentList<FluentWebElement> apps;

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

    public void checkNumberOfApp(int expected){
        assertThat(getDriver().findElement(By.className("app-container")).getSize().width == expected);
    }

}
