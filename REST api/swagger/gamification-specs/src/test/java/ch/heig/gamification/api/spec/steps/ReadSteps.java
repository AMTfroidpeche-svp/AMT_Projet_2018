package ch.heig.gamification.api.spec.steps;

/**
 * File : Environment.java
 * Authors : Jee Mathieu, Kopp Olivier, Schürch Loïc
 * Last modified on : 29.12.2018
 *
 * Description : This Class is used to retrieve objects from the REST API
 *
 */

import ch.heig.gamification.ApiException;
import ch.heig.gamification.ApiResponse;
import ch.heig.gamification.api.DefaultApi;
import ch.heig.gamification.api.dto.UserEventCount;
import ch.heig.gamification.api.dto.UserPointScale;
import ch.heig.gamification.api.dto.LinkTableId;
import ch.heig.gamification.api.dto.Badge;
import ch.heig.gamification.api.dto.PointScale;
import ch.heig.gamification.api.dto.Rule;
import ch.heig.gamification.api.dto.User;
import ch.heig.gamification.api.spec.helpers.Environment;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.ArrayList;
import java.util.List;

import static ch.heig.gamification.api.spec.steps.CurrentState.*;
import static org.junit.Assert.*;

public class ReadSteps {

    private Environment environment;
    private DefaultApi api;

    User user;

    List<Badge> badges;
    List<PointScale> pointScales;
    List<Rule> rules;

    public ReadSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @When("^I GET to the /(.+) endpoint the app (.+)$")
    public void i_GET_from_the_badges_endpoint(String endPoint, String appName) {
        try {
            switch (endPoint) {
                case "badges":
                    badges = api.getBadges(appName);
                    break;
                case "pointScales":
                    pointScales = api.getPointScales(appName);
                    break;
                case "rules":
                    rules = api.getRules(appName);
                    break;
                default:
                    break;

            }
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }

    }

    @When("^I GET the user (.+) in the app (.+)$")
    public void i_GET_from_the_users_endpoint(String userName, String appName) {
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
    public void i_receive_correct_objects(String type) {
        switch (type) {
            case "badges":
                for(Badge b : badges){
                    System.out.println(b.getApiToken() + " " + b.getName() + " " + badges.get(0).getName());
                    assertTrue(badges.contains(b));
                }
                break;
            case "pointScales":
                for(PointScale p : pointScales){
                    assertTrue(pointScales.contains(p));
                }
                break;
            case "rules":
                for(Rule r : rules){
                    assertTrue(rules.contains(r));
                }
                break;
            default:
                assertTrue(false);
        }
    }

    @Then("^The user (.+) should have correct badges : (.+), correct pointScales : (.+) with correct amount of points : (.+) and correct eventCount : (.+) with correct amounts : (.+)$")
    public void check_user_infos(String userName, String badges, String pointScales, String points, String eventCounts, String eventCountsAmounts) {
        assertEquals(user.getName(), userName);
        List<Badge> badgesList = new ArrayList<>();
        List<PointScale> pointScaleList = new ArrayList<>();
        List<UserPointScale> userPointScaleList = new ArrayList<>();
        List<UserEventCount> userEventCountList  = new ArrayList<>();

        String[] badgesTab = badges.split(";");
        String[] pointScalesTab = pointScales.split(";");
        String[] pointsTab = points.split(";");
        String[] userEventCountTab = eventCounts.split(";");
        String[] userEventCountAmountsTab = eventCountsAmounts.split(";");

        if(pointScalesTab.length != pointsTab.length){
            assertTrue(false);
        }

        for(int i = 0; i < badgesTab.length; i++){
            if(badgesTab[i].isEmpty()){
                break;
            }
            if(badgesTab[0].equals("null")){
                assertTrue(user.getBadges().isEmpty());
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
            if(pointScalesTab[0].equals("null")){
                assertTrue(user.getUserPointScale().isEmpty());
                assertTrue(user.getPointScales().isEmpty());
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

        for(int i = 0; i < userEventCountTab.length; i++){
            if(userEventCountTab[i].isEmpty() ^ userEventCountAmountsTab[i].isEmpty()){
                assertTrue(false);
            }
            if(userEventCountTab[i].isEmpty() && userEventCountAmountsTab[i].isEmpty()){
                break;
            }
            if(userEventCountTab[0].equals("null")){
                assertTrue(user.getUserEventCount().isEmpty());
                break;
            }
            UserEventCount tempUserEventCount = new UserEventCount();
            LinkTableId linkTableId = new LinkTableId();
            linkTableId.setApiToken(user.getApiToken());
            linkTableId.setTable1Id(user.getName());
            linkTableId.setTable2Id(userEventCountTab[i]);
            tempUserEventCount.setLinkTableId(linkTableId);
            tempUserEventCount.setValue(Integer.parseInt(userEventCountAmountsTab[i]));

            userEventCountList.add(tempUserEventCount);
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

        for(UserEventCount uec : user.getUserEventCount()){
            assertTrue(userEventCountList.contains(uec));
        }
    }
}
