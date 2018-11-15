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
    registerPage.typeFirstName("firstName");
    registerPage.typeLastName("lastname");
    registerPage.typeEmail("firstname.lastname@test.test");
    registerPage.typePassword("password");
    registerPage.typeConfirmPassword("password");
    registerPage.typeQuestion(1);
    registerPage.typeAnswer("yverdon");
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
    goTo(baseUrl + registerPage.getUrl());
    registerPage.typeFirstName("toto");
    registerPage.typeLastName("tata");
    registerPage.typeEmail("toto@tata.tutu");
    registerPage.typePassword("tutu");
    registerPage.typeConfirmPassword("tutu");
    registerPage.typeQuestion(1);
    registerPage.typeAnswer("response");
    registerPage.clickRegister();
    goTo(baseUrl + loginPage.getUrl());
    loginPage.isAt();
    loginPage.typeEmail("toto@tata.tutu");
    loginPage.typePassword("tutu");
    loginPage.clickSignin();
    appPage.isAt();
    for(int i = 0; i < 25; i++){
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
