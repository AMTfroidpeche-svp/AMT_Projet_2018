package ch.heig.gamification.api.spec.steps;

/**
 * File : Environment.java
 * Authors : Jee Mathieu, Kopp Olivier, Schürch Loïc
 * Last modified on : 29.12.2018
 *
 * Description : This Class is used to delete objects in the REST API
 *
 */

import ch.heig.gamification.ApiException;
import ch.heig.gamification.api.DefaultApi;
import ch.heig.gamification.api.dto.Badge;
import ch.heig.gamification.api.dto.PointScale;
import ch.heig.gamification.api.dto.RuleInfos;
import ch.heig.gamification.api.spec.helpers.Environment;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

import static ch.heig.gamification.api.spec.steps.CurrentState.*;

public class DeleteSteps {

    private Environment environment;
    private DefaultApi api;

    Badge badge;
    PointScale pointScale;
    RuleInfos ruleInfos;



    public DeleteSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Given("^I want to delete the badge (.+) in the app (.+)$")
    public void i_want_to_delete_a_badge(String badgeName, String appName) {
        badge = new Badge();
        badge.setApiToken(appName);
        badge.setName(badgeName);
    }

    @Given("^I want to delete the pointScale (.+) in the app (.+)$")
    public void i_want_to_delete_a_PointScale(String pointScaleName, String appName) {
        pointScale = new PointScale();
        pointScale.setApiToken(appName);
        pointScale.setName(pointScaleName);
    }

    @Given("^I want to delete the rule (.+) in the app (.+)$")
    public void i_want_to_delete_a_rule(String ruleName, String appName) {
        ruleInfos = new RuleInfos();
        ruleInfos.setApiToken(appName);
        ruleInfos.setName(ruleName);
    }

    @When("^I DELETE it to the /(.+) endpoint$")
    public void i_DELETE_it_to_an_endpoint(String endPoint) {
        try {
            switch (endPoint) {
                case "badges":
                    lastApiResponse = api.deleteBadgeWithHttpInfo(badge);
                    if (badges.contains(badge)) {
                        badges.remove(badge);
                        for(int i = 0; i < rules.size(); i++){
                            rules.get(i).getAwards().getBadge().remove(badge.getName());
                            if(rules.get(i).getAwards().getBadge().isEmpty() && rules.get(i).getAwards().getPoint().isEmpty()){
                                rules.remove(i);
                                i--;
                            }
                        }
                    }
                    break;
                case "pointScales":
                    lastApiResponse = api.deletePointScaleWithHttpInfo(pointScale);
                    if (pointScales.contains(pointScale)) {
                        pointScales.remove(pointScale);
                        for(int i = 0; i < rules.size(); i++){
                            rules.get(i).getAwards().getPoint().remove(pointScale.getName());
                            if(rules.get(i).getAwards().getBadge().isEmpty() && rules.get(i).getAwards().getPoint().isEmpty()){
                                rules.remove(i);
                                i--;
                            }
                        }
                    }
                    break;
                case "rules":
                    lastApiResponse = api.deleteRuleWithHttpInfo(ruleInfos);
                    for(int i = 0; i < rules.size(); i++){
                        if(rules.get(i).getApiToken().equals(ruleInfos.getApiToken()) && rules.get(i).getRuleName().equals(ruleInfos.getName())){
                            rules.remove(i);
                        }
                    }
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

}
