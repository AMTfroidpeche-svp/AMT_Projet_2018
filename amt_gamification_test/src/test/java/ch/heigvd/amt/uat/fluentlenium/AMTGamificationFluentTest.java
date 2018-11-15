package ch.heigvd.amt.uat.fluentlenium;

import ch.heigvd.amt.uat.fluentlenium.pages.*;
import io.probedock.client.annotations.ProbeTest;
import org.fluentlenium.adapter.FluentTest;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.fluentlenium.core.annotation.Page;

/**
 *
 * @author Olivier Kopp
 */
public class AMTGamificationFluentTest extends FluentTest {

  private final String baseUrl = "http://localhost:8080/AMT_Projet_2018";

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
    goTo(baseUrl + "/registration");
    registerPage.isAt();
    registerPage.typeFirstName("firstName");
    registerPage.typeLastName("lastname");
    registerPage.typeEmail("firstname.lastname@test.test");
    registerPage.typeQuestion(1);
    registerPage.typeAnswer("yverdon");
    registerPage.clickRegister();
  }


  @Test
  @ProbeTest(tags = "WebUI")
  public void itShouldNotBePossibleToSigninWithAnInvalidEmail() {
    goTo(baseUrl + "/login");
    loginPage.isAt();
    loginPage.typeEmail("not a valid email");
    loginPage.typePassword("any password");
    loginPage.clickSignin();
    loginPage.isAt();
  }
  @Override
  public WebDriver getDefaultDriver() {
    //return new FirefoxDriver();
    System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
    return new ChromeDriver();
  }

  @Override
  public String getDefaultBaseUrl() {
    return baseUrl;
  }
}
