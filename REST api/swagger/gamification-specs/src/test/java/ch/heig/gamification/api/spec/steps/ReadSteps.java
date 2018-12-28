package ch.heig.gamification.api.spec.steps;

import ch.heig.gamification.ApiException;
import ch.heig.gamification.ApiResponse;
import ch.heig.gamification.api.DefaultApi;
import ch.heig.gamification.api.dto.Badge;
import ch.heig.gamification.api.dto.PointScale;
import ch.heig.gamification.api.dto.Rule;
import ch.heig.gamification.api.dto.User;
import ch.heig.gamification.api.dto.UserPointScale;
import ch.heig.gamification.api.dto.UserInfos;
import ch.heig.gamification.api.dto.AppInfos;
import ch.heig.gamification.api.dto.LinkTableId;
import ch.heig.gamification.api.spec.helpers.Environment;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Olivier Liechti on 27/07/17.
 */
public class ReadSteps {

    private Environment environment;
    private DefaultApi api;

    Badge badge;
    PointScale pointScale;
    Rule rule;
    User user;
    AppInfos appInfos = new AppInfos();


    List<Badge> badges = new ArrayList<>();
    List<PointScale> pointScales = new ArrayList<>();
    List<Rule> rules = new ArrayList<>();

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    public ReadSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @When("^I GET to the /badges endpoint the app (.+)$")
    public void i_GET_from_the_badges_endpoint(String appName) throws Throwable {
        try {
            badges = api.getBadges(appName);
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }

    }

    @When("^I GET to the /pointScales endpoint the app (.+)$")
    public void i_GET_from_the_pointScales_endpoint(String appName) throws Throwable {
        try {
            pointScales = api.getPointScales(appName);
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }

    }

    @When("^I GET to the /rules endpoint the app (.+)$")
    public void i_GET_from_the_rules_endpoint(String appName) throws Throwable {
        try {
            rules = api.getRules(appName);
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }

    }

    @When("^I GET to the /users endpoint the app (.+) and the user (.+)$")
    public void i_GET_from_the_users_endpoint(String appName, String userName) throws Throwable {
        try {
            user = api.getUser(appName, userName);
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }

    }

    @Then("^I receive correct (.+)$")
    public void i_receive_a_status_code(String type) throws Throwable {
        switch (type) {
            case "badges":
                for(Badge b : CurrentState.badges){
                    assertTrue(badges.contains(b));
                }
                break;
            case "pointScales":
                for(PointScale p : CurrentState.pointScales){
                    assertTrue(pointScales.contains(p));
                }
                break;
            case "rules":
                for(Rule r : CurrentState.rules){
                    assertTrue(rules.contains(r));
                }
                break;
            default:
                assertTrue(false);
        }
    }

    @Then("^The user (.+) should have correct badges : (.+) and correct pointScales : (.+) with correct ammount of points : (.+)$")
    public void check_user_infos(String userName, String badges, String pointScales, String points) throws Throwable {
        assertEquals(user.getName(), userName);
        List<Badge> badgesList = new ArrayList<>();
        List<PointScale> pointScaleList = new ArrayList<>();
        List<UserPointScale> userPointScaleList = new ArrayList<>();
        String[] badgesTab = badges.split(";");
        String[] pointScalesTab = pointScales.split(";");
        String[] pointsTab = points.split(";");
        if(pointScalesTab.length != pointsTab.length){
            assertTrue(false);
        }

        for(int i = 0; i < badgesTab.length; i++){
            if(badgesTab[i].isEmpty()){
                break;
            }
            Badge tempBadge = new Badge();
            tempBadge.setApiToken(user.getApiToken());
            tempBadge.setName(badgesTab[i]);
            badgesList.add(tempBadge);
        }

        for(int i = 0; i < pointScalesTab.length; i++){
            if(pointScalesTab[i].isEmpty() ^ pointsTab[i].isEmpty()){
                assertTrue(false);
            }
            if(pointScalesTab[i].isEmpty() && pointsTab[i].isEmpty()){
                break;
            }
            PointScale tempPointcale = new PointScale();
            tempPointcale.setApiToken(user.getApiToken());
            tempPointcale.setName(pointScalesTab[i]);
            UserPointScale tempUserPointScale = new UserPointScale();
            LinkTableId linkTableId = new LinkTableId();
            linkTableId.setApiToken(user.getApiToken());
            linkTableId.setTable1Id(user.getName());
            linkTableId.setTable2Id(pointScalesTab[i]);
            tempUserPointScale.setValue(Integer.parseInt(pointsTab[i]));
            tempUserPointScale.setLinkTableId(linkTableId);

            pointScaleList.add(tempPointcale);
            userPointScaleList.add(tempUserPointScale);
        }


        for(Badge b : user.getBadges()){
            assertTrue(badgesList.contains(b));
        }

        for(PointScale p : user.getPointScales()){
            assertTrue(pointScaleList.contains(p));
        }

        for(UserPointScale ups : user.getUserPointScale()){
            assertTrue(userPointScaleList.contains(ups));
        }
    }
}
