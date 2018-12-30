package ch.heig.gamification.api.spec.steps;

/**
 * File : CreationSteps.java
 * Authors : Jee Mathieu, Kopp Olivier, Schürch Loïc
 * Last modified on : 29.12.2018
 *
 * Description : This Class is used to create objects in the REST API
 *
 */

import ch.heig.gamification.api.spec.helpers.Environment;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import ch.heig.gamification.ApiException;
import ch.heig.gamification.api.DefaultApi;
import ch.heig.gamification.api.dto.Badge;
import ch.heig.gamification.api.dto.PointScale;
import ch.heig.gamification.api.dto.Rule;
import ch.heig.gamification.api.dto.RuleAwards;
import ch.heig.gamification.api.dto.RuleProperties;
import ch.heig.gamification.api.dto.Event;
import ch.heig.gamification.api.dto.EventProperties;

import static ch.heig.gamification.api.spec.steps.CurrentState.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CreationSteps {

    private Environment environment;
    private DefaultApi api;

    Badge badge;
    PointScale pointScale;
    Rule rule;
    Event event;

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
    public void i_have_a_badge_payload(String badgeName, String appName) {
        badge = new Badge();
        badge.setApiToken(appName);
        badge.setName(badgeName);
    }

    @Given("^I create a badge that already exists$")
    public void i_create_an_existing_badge() {
        badge = badges.get(0);
    }


    @Given("^I have the pointScale (.+) in the app (.+)$")
    public void i_have_a_pointScale_payload(String pointScaleName, String appName) {
        pointScale = new PointScale();
        pointScale.setApiToken(appName);
        pointScale.setName(pointScaleName);
    }

    @Given("^I have the rule (.+) in (.+) application trigger by the (.+) event that give (.+) points to (.+) pointscales and the badges (.+) with (.+) properties$")
    public void i_have_a_rule_payload(String ruleName, String appName, String eventName, String pointAwarded, String pointScalesName, String badges, String properties) {
        rule = new Rule();
        rule.setApiToken(appName);
        rule.setRuleName(ruleName);
        rule.setEventName(eventName);
        RuleAwards ruleAwards = new RuleAwards();
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
            RuleProperties ruleProperties = new RuleProperties();
            String[] propertiesList = s.split(",");
            ruleProperties.setName(propertiesList[0]);
            ruleProperties.setType(propertiesList[1]);
            ruleProperties.setCompareOperator(propertiesList[2]);
            ruleProperties.setValue(Integer.parseInt(propertiesList[3]));
            rule.addPropertiesItem(ruleProperties);
        }
    }

    @Given("^I generate the event (.+) in (.+) application concerning user (.+) with (.+) properties and the timestamp (.+)$")
    public void i_have_a_event_payload(String eventName, String appName, String userName, String properties, String timeStamp) {
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
    public void i_POST_it_to_an_endpoint(String endPoint) {
        try {
            switch (endPoint) {
                case "badges":
                    lastApiResponse = api.createBadgeWithHttpInfo(badge);
                    if (!badges.contains(badge)) {
                        badges.add(badge);
                    }
                    break;
                case "pointScales":
                    lastApiResponse = api.createPointScaleWithHttpInfo(pointScale);
                    if (!pointScales.contains(pointScale)) {
                        pointScales.add(pointScale);
                    }
                    break;
                case "rules":
                    lastApiResponse = api.createRuleWithHttpInfo(rule);
                    if (!rules.contains(rule)) {
                        rules.add(rule);
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
    public void i_receive_a_status_code(int arg1) {
        assertTrue(arg1 == lastStatusCode);
    }

}
