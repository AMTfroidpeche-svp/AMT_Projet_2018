package ch.heigvd.amt.uat.fluentlenium;

import ch.heigvd.amt.uat.fluentlenium.pages.*;
import io.probedock.client.annotations.ProbeTest;
import org.fluentlenium.adapter.FluentTest;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.fluentlenium.core.annotation.Page;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author Olivier Kopp
 */
public class AMTGamificationFluentTest extends FluentTest {

  private final String baseUrl = "http://localhost:8080/AMT_Projet_2018";
  private final String driverPath = "C:\\chromedriver.exe";
  private final String firstName = "firstname";
  private final String lastName = "lastname";
  private final String email = "firstname.lastname@test.test";
  private final String password = "password";
  private final String response = "response";
  private final int MAX_APP = 25;
  private final int MAX_USER = 25;

  @Page
  public ApplicationPage appPage;

  @Page
  public CreateApplicationPage createAppPage;

  @Page
  public LoginPage loginPage;
  
  @Page
  public PasswordForgottenPage passwordForgottenPage;
  
  @Page
  public ProfilePage profilePage;

  @Page
  public QuestionAuthPage questionAuthPage;

  @Page
  public RegisterPage registerPage;

  @Page
  public ResetPasswordPage resetPasswordPage;

  @Test
  @ProbeTest(tags = "WebUI")
  public void itShouldBePossibleToRegister() {
    goTo(baseUrl + registerPage.getUrl());
    registerPage.isAt();
    registerPage.typeFirstName(firstName);
    registerPage.typeLastName(lastName);
    registerPage.typeEmail(email);
    registerPage.typePassword(password);
    registerPage.typeConfirmPassword(password);
    registerPage.typeQuestion(1);
    registerPage.typeAnswer(response);
    registerPage.clickRegister();
  }

    @Test
    @ProbeTest(tags = "WebUI")
    public void itShouldBePossibleToRegisterSomeUsers() {
        goTo(baseUrl + registerPage.getUrl());
        registerPage.isAt();
        for(int i = 0; i < MAX_USER; i++) {
            registerPage.typeFirstName(firstName + String.valueOf(i));
            registerPage.typeLastName(lastName + String.valueOf(i));
            registerPage.typeEmail(email + String.valueOf(i));
            registerPage.typePassword(password + String.valueOf(i));
            registerPage.typeConfirmPassword(password + String.valueOf(i));
            registerPage.typeQuestion(1);
            registerPage.typeAnswer(response + String.valueOf(i));
            registerPage.clickRegister();
        }
    }

    @Test
    @ProbeTest(tags = "WebUI")
    public void itShouldNotBePossibleToRegisterIfWeDontFillAllField() {
        goTo(baseUrl + registerPage.getUrl());
        registerPage.isAt();
        registerPage.typeFirstName(firstName);
        registerPage.typeEmail(email);
        registerPage.typePassword(password);
        registerPage.typeConfirmPassword(password);
        registerPage.typeQuestion(1);
        registerPage.typeAnswer(response);
        registerPage.clickRegister();
    }

    @Test
    @ProbeTest(tags = "WebUI")
    public void itShouldNotBePossibleToRegisterIfPasswordsAreDifferent() {
        goTo(baseUrl + registerPage.getUrl());
        registerPage.isAt();
        registerPage.typeFirstName(firstName);
        registerPage.typeLastName(lastName);
        registerPage.typeEmail(email);
        registerPage.typePassword(password);
        registerPage.typeConfirmPassword(password + "fail");
        registerPage.typeQuestion(1);
        registerPage.typeAnswer(response);
        registerPage.clickRegister();
    }


  @Test
  @ProbeTest(tags = "WebUI")
  public void itShouldNotBePossibleToSigninWithAnInvalidEmail() {
    goTo(baseUrl + loginPage.getUrl());
    loginPage.isAt();
    loginPage.typeEmail("not a valid email");
    loginPage.typePassword("any password");
    loginPage.clickSignin();
    loginPage.isAt();
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void basicScenario() {
    goTo(baseUrl + loginPage.getUrl());
    loginPage.isAt();
    loginPage.typeEmail(email);
    loginPage.typePassword(password);
    loginPage.clickSignin();
    appPage.isAt();
    for(int i = 0; i < MAX_APP; i++){
        appPage.goToCreateAppPageViaMenu();
        createAppPage.isAt();
        createAppPage.typeAppName("test" + String.valueOf(i));
        createAppPage.typeDescription("test" + String.valueOf(i));
        createAppPage.clickCreate();
        await().atMost(2, TimeUnit.SECONDS).untilPage(appPage).isLoaded();
    }
    appPage.isAt();
    appPage.checkNumberOfApp(10);
    appPage.goToNextPage();
    appPage.checkNumberOfApp(10);
    appPage.goToNextPage();
    appPage.checkNumberOfApp(5);
    appPage.goToPreviousPage();
    appPage.goToPreviousPage();
    appPage.goToLogoutPageViaMenu();
    loginPage.isAt();
    goTo(baseUrl + appPage.getUrl());
    loginPage.isAt();

  }

  @Override
  public WebDriver getDefaultDriver() {
    //return new FirefoxDriver();
    System.setProperty("webdriver.chrome.driver", driverPath);
    return new ChromeDriver();
  }

  @Override
  public String getDefaultBaseUrl() {
    return baseUrl;
  }
}
