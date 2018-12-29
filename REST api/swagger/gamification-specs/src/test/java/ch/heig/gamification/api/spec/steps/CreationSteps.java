package ch.heig.gamification.api.spec.steps;

import ch.heig.gamification.api.spec.helpers.Environment;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import ch.heig.gamification.ApiException;
import ch.heig.gamification.ApiResponse;
import ch.heig.gamification.api.DefaultApi;
import ch.heig.gamification.api.dto.Badge;
import ch.heig.gamification.api.dto.PointScale;
import ch.heig.gamification.api.dto.Rule;
import ch.heig.gamification.api.dto.User;
import ch.heig.gamification.api.dto.Event;
import ch.heig.gamification.api.dto.EventProperties;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Olivier Kopp on 27/12/18.
 */
public class CreationSteps {

    private Environment environment;
    private DefaultApi api;

    Badge badge;
    PointScale pointScale;
    Rule rule;
    User user;
    Event event;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    public CreationSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Given("^there is a Gamification server$")
    public void there_is_a_Gamification_server() throws Throwable {
        assertNotNull(api);
        api.dropDatabase();
    }

    @Given("^I have the badge (.+) in the app (.+)$")
    public void i_have_a_badge_payload(String badgeName, String appName) throws Throwable {
        badge = new Badge();
        badge.setApiToken(appName);
        badge.setName(badgeName);
    }

    @Given("^I create a badge that already exists$")
    public void i_create_an_existing_badge() throws Throwable {
        badge = CurrentState.badges.get(0);
    }


    @Given("^I have the pointScale (.+) in the app (.+)$")
    public void i_have_a_pointScale_payload(String pointScaleName, String appName) throws Throwable {
        pointScale = new PointScale();
        pointScale.setApiToken(appName);
        pointScale.setName(pointScaleName);
    }

    @Given("^I have the rule (.+) in (.+) application trigger by the (.+) event that give (.+) points to (.+) pointscales and the badges (.+) with (.+) properties$")
    public void i_have_a_rule_payload(String ruleName, String appName, String eventName, String pointAwarded, String pointScalesName, String badges, String properties) throws Throwable {
        rule = new Rule();
        rule.setApiToken(appName);
        rule.setRuleName(ruleName);
        rule.setEventName(eventName);
        ch.heig.gamification.api.dto.RuleAwards ruleAwards = new ch.heig.gamification.api.dto.RuleAwards();
        for (String s : badges.split(";")) {
            ruleAwards.addBadgeItem(s);
        }
        for (String s : pointScalesName.split(";")) {
            ruleAwards.addPointItem(s);
        }
        for (String s : pointAwarded.split(";")) {
            ruleAwards.addAmountofPointItem(Integer.parseInt(s));
        }
        rule.setAwards(ruleAwards);
        for (String s : properties.split(";")) {
            ch.heig.gamification.api.dto.RuleProperties ruleProperties = new ch.heig.gamification.api.dto.RuleProperties();
            String[] propertiesList = s.split(",");
            ruleProperties.setName(propertiesList[0]);
            ruleProperties.setType(propertiesList[1]);
            ruleProperties.setCompareOperator(propertiesList[2]);
            ruleProperties.setValue(Integer.parseInt(propertiesList[3]));
            rule.addPropertiesItem(ruleProperties);
        }
    }

    @Given("^I generate the event (.+) in (.+) application concerning user (.+) with (.+) properties and the timestamp (.+)$")
    public void i_have_a_event_payload(String eventName, String appName, String userName, String properties, String timeStamp) throws Throwable {
        event = new Event();
        event.setApiToken(appName);
        event.setUserId(userName);
        event.setName(eventName);
        event.setTimestamp(null);
        EventProperties eventProperties = new EventProperties();
        for (String s : properties.split(";")) {
            String[] propertiesList = s.split(",");
            eventProperties.setName(propertiesList[0]);
            eventProperties.setValue(Integer.parseInt(propertiesList[1]));
            event.addPropertiesItem(eventProperties);
        }
    }

    @When("^I POST it to the /(.+) endpoint$")
    public void i_POST_it_to_an_endpoint(String endPoint) throws Throwable {
        try {
            switch (endPoint) {
                case "badges":
                    lastApiResponse = api.createBadgeWithHttpInfo(badge);
                    if (!CurrentState.badges.contains(badge)) {
                        CurrentState.badges.add(badge);
                    }
                    break;
                case "pointScales":
                    lastApiResponse = api.createPointScaleWithHttpInfo(pointScale);
                    if (!CurrentState.pointScales.contains(pointScale)) {
                        CurrentState.pointScales.add(pointScale);
                    }
                    break;
                case "rules":
                    lastApiResponse = api.createRuleWithHttpInfo(rule);
                    if (!CurrentState.rules.contains(rule)) {
                        CurrentState.rules.add(rule);
                    }
                    break;
                case "events":
                    lastApiResponse = api.generateEventWithHttpInfo(event);
                    break;
                default:
                    break;

            }
            lastApiCallThrewException = false;
            lastApiException = null;
            lastStatusCode = lastApiResponse.getStatusCode();
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }

    }


    @Then("^I receive a (\\d+) status code$")
    public void i_receive_a_status_code(int arg1) throws Throwable {
        assertTrue(arg1 == lastStatusCode);
    }

}
