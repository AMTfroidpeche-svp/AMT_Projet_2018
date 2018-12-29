package ch.heig.gamification.api.spec.steps;

import ch.heig.gamification.ApiException;
import ch.heig.gamification.ApiResponse;
import ch.heig.gamification.api.DefaultApi;
import ch.heig.gamification.api.dto.Badge;
import ch.heig.gamification.api.dto.PointScale;
import ch.heig.gamification.api.dto.Rule;
import ch.heig.gamification.api.dto.UpdateBadge;
import ch.heig.gamification.api.spec.helpers.Environment;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Olivier Kopp on 27/12/18.
 */
public class UpdateSteps {

    private Environment environment;
    private DefaultApi api;

    ch.heig.gamification.api.dto.UpdateBadge badge = new UpdateBadge();
    ch.heig.gamification.api.dto.UpdatePointScale pointScale = new ch.heig.gamification.api.dto.UpdatePointScale();
    ch.heig.gamification.api.dto.UpdateRule rule = new ch.heig.gamification.api.dto.UpdateRule();
    ch.heig.gamification.api.dto.User user;
    ch.heig.gamification.api.dto.Event event;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    public UpdateSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Given("^I update the badge (.+) in the app (.+) with the new badge (.+)$")
    public void i_update_a_badge(String oldBadgeName, String appName, String newBadgeName) throws Throwable {
        ch.heig.gamification.api.dto.Badge oldBadge = new Badge();
        oldBadge.setApiToken(appName);
        oldBadge.setName(oldBadgeName);
        ch.heig.gamification.api.dto.Badge newBadge = new Badge();
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
    public void i_PUT_it_to_an_endpoint(String endPoint) throws Throwable {
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
    public void i_update_a_pointScale(String oldPointScaleName, String appName, String newPointScaleName) throws Throwable {
        ch.heig.gamification.api.dto.PointScale oldPointScale = new PointScale();
        oldPointScale.setApiToken(appName);
        oldPointScale.setName(oldPointScaleName);
        ch.heig.gamification.api.dto.PointScale newPointScale = new PointScale();
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
    public void i_update_a_rule_payload(String oldRuleName, String appName, String newRuleName, String eventName, String pointAwarded, String pointScalesName, String badges, String properties) throws Throwable {
        Rule newRule = new Rule();
        newRule.setApiToken(appName);
        newRule.setRuleName(newRuleName);
        newRule.setEventName(eventName);
        ch.heig.gamification.api.dto.RuleAwards ruleAwards = new ch.heig.gamification.api.dto.RuleAwards();
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
            ch.heig.gamification.api.dto.RuleProperties ruleProperties = new ch.heig.gamification.api.dto.RuleProperties();
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
