package ch.heig.gamification.api.spec.steps;

/**
 * File : Environment.java
 * Authors : Jee Mathieu, Kopp Olivier, Schürch Loïc
 * Last modified on : 29.12.2018
 *
 * Description : This Class is used to update objects in the REST API
 *
 */

import ch.heig.gamification.ApiException;
import ch.heig.gamification.ApiResponse;
import ch.heig.gamification.api.DefaultApi;
import ch.heig.gamification.api.dto.Badge;
import ch.heig.gamification.api.dto.PointScale;
import ch.heig.gamification.api.dto.Rule;
import ch.heig.gamification.api.dto.RuleAwards;
import ch.heig.gamification.api.dto.RuleProperties;
import ch.heig.gamification.api.dto.UpdateBadge;
import ch.heig.gamification.api.dto.UpdateRule;
import ch.heig.gamification.api.dto.UpdatePointScale;
import ch.heig.gamification.api.spec.helpers.Environment;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class UpdateSteps {

    private Environment environment;
    private DefaultApi api;

    UpdateBadge badge = new UpdateBadge();
    UpdatePointScale pointScale = new UpdatePointScale();
    UpdateRule rule = new UpdateRule();

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    public UpdateSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Given("^I update the badge (.+) in the app (.+) with the new badge (.+)$")
    public void i_update_a_badge(String oldBadgeName, String appName, String newBadgeName) {
        Badge oldBadge = new Badge();
        oldBadge.setApiToken(appName);
        oldBadge.setName(oldBadgeName);
        Badge newBadge = new Badge();
        newBadge.setApiToken(appName);
        newBadge.setName(newBadgeName);
        badge.setOldName(oldBadgeName);
        badge.setNewBadge(newBadge);
        CurrentState.badges.get(CurrentState.badges.indexOf(oldBadge)).setName(newBadgeName);
        for(Rule r : CurrentState.rules){
            for(int i = 0; i < r.getAwards().getBadge().size(); i++){
                if(r.getAwards().getBadge().get(i).equals(oldBadgeName)){
                    r.getAwards().getBadge().set(i, newBadgeName);
                }
            }
        }
    }

    @When("^I PUT it to the /(.+) endpoint$")
    public void i_PUT_it_to_an_endpoint(String endPoint) {
        try {
            switch (endPoint) {
                case "badges":
                    lastApiResponse = api.updateBadgeWithHttpInfo(badge);
                    break;
                case "pointScales":
                    lastApiResponse = api.updatePointScaleWithHttpInfo(pointScale);
                    break;
                case "rules":
                    lastApiResponse = api.updateRuleWithHttpInfo(rule);
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

    @Given("^I update the pointScale (.+) in the app (.+) with the new PointScale (.+)$")
    public void i_update_a_pointScale(String oldPointScaleName, String appName, String newPointScaleName) {
        PointScale oldPointScale = new PointScale();
        oldPointScale.setApiToken(appName);
        oldPointScale.setName(oldPointScaleName);
        PointScale newPointScale = new PointScale();
        newPointScale.setApiToken(appName);
        newPointScale.setName(newPointScaleName);
        pointScale.setOldName(oldPointScaleName);
        pointScale.setNewPointScale(newPointScale);
        CurrentState.pointScales.set(CurrentState.pointScales.indexOf(oldPointScale),newPointScale);
        for(Rule r : CurrentState.rules){
            for(int i = 0; i < r.getAwards().getPoint().size(); i++){
                if(r.getAwards().getPoint().get(i).equals(oldPointScaleName)){
                    r.getAwards().getPoint().set(i, newPointScaleName);
                }
            }
        }
    }

    @Given("^I update the rule (.+) in (.+) application by the new rule (.+) trigger by the (.+) event that give (.+) points to (.+) pointscales and the badges (.+) with (.+) properties$")
    public void i_update_a_rule_payload(String oldRuleName, String appName, String newRuleName, String eventName, String pointAwarded, String pointScalesName, String badges, String properties) {
        Rule newRule = new Rule();
        newRule.setApiToken(appName);
        newRule.setRuleName(newRuleName);
        newRule.setEventName(eventName);
        RuleAwards ruleAwards = new RuleAwards();
        for(String s : badges.split(";")){
            ruleAwards.addBadgeItem(s);
        }
        for(String s : pointScalesName.split(";")){
            ruleAwards.addPointItem(s);
        }
        for(String s : pointAwarded.split(";")){
            ruleAwards.addAmountofPointItem(Integer.parseInt(s));
        }
        newRule.setAwards(ruleAwards);
        for(String s : properties.split(";")){
            RuleProperties ruleProperties = new RuleProperties();
            String[] propertiesList = s.split(",");
            ruleProperties.setName(propertiesList[0]);
            ruleProperties.setType(propertiesList[1]);
            ruleProperties.setCompareOperator(propertiesList[2]);
            ruleProperties.setValue(Integer.parseInt(propertiesList[3]));
            newRule.addPropertiesItem(ruleProperties);
        }
        rule.setOldName(oldRuleName);
        rule.setNewRule(newRule);
        for(int i = 0; i < CurrentState.rules.size(); i++){
            if(CurrentState.rules.get(i).getApiToken().equals(appName) && CurrentState.rules.get(i).getRuleName().equals(oldRuleName)){
                CurrentState.rules.set(i, newRule);
            }
        }

    }

}
