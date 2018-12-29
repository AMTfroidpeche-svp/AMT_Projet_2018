Feature: update badges, pointScales and rules

  Background:
    Given there is a Gamification server

  Scenario: update a badge
    Given I have the badge badge1 in the app app1
    When I POST it to the /badges endpoint
    Then I receive a 201 status code
    Given I update the badge badge1 in the app app1 with the new badge badge5
    When I PUT it to the /badges endpoint
    When I GET to the /badges endpoint the app app1
    Then I receive correct badges

  Scenario: update a pointScale
    Given I have the pointScale pointScale1 in the app app1
    When I POST it to the /pointScales endpoint
    Then I receive a 201 status code
    Given I update the pointScale pointScale1 in the app app1 with the new PointScale pointScale5
    When I PUT it to the /pointScales endpoint
    When I GET to the /pointScales endpoint the app app1
    Then I receive correct pointScales

  Scenario: update a rule
    Given I have the rule commentRule in app1 application trigger by the comment event that give 10 points to pointScale5 pointscales and the badges badge5 with chars,value,>,100 properties
    When I POST it to the /rules endpoint
    Then I receive a 201 status code
    Given I update the rule commentRule in app1 application by the new rule newCommentRule trigger by the comment event that give 20 points to pointScale5 pointscales and the badges badge5 with chars,value,>,150 properties
    When I PUT it to the /rules endpoint
    When I GET to the /rules endpoint the app app1
    Then I receive correct rules