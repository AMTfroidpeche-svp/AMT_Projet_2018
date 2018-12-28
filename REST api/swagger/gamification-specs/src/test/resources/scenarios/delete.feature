Feature: retrieve badges, pointScales and rules

  Background:
    Given there is a Gamification server

  Scenario: retrieve badges
    Given I create the badge badge1 in the app app1
    When I POST it to the /badges endpoint
    When I GET to the /badges endpoint the app app1
    Then I receive correct badges

  Scenario: retrieve pointScales
    Given I create the pointScale pointScale1 in the app app1
    When I POST it to the /pointScales endpoint
    When I GET to the /pointScales endpoint the app app1
    Then I receive correct pointScales

  Scenario: retrieve rules
    Given I create the rule commentRule in app1 application trigger by the comment event that give 10 points to pointScale1 pointscales and the badges badge1 with chars,value,>,100 properties
    When I POST it to the /rules endpoint
    When I GET to the /rules endpoint the app app1
    Then I receive correct rules