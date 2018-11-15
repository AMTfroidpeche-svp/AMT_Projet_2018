package ch.heigvd.amt.uat.fluentlenium.pages;

import org.fluentlenium.core.FluentPage;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionAuthPage extends FluentPage {

    private final static String inputResponse = "#responseField";
    private final static String submitResponse = "#submitQuestionAuth";

    public void isAt() {
        assertThat(title()).isEqualTo("Question Auth");
    }

    public void typeResponse(String response) {
        fill(inputResponse).with(response);
    }

    public void clickResponse() {
        click(submitResponse);
    }

    public String getUrl() {
        return "/questionAuth";
    }
}
