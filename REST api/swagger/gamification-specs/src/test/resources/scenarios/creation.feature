Feature: Creation of badges, pointScales and rules

  Background:
    Given there is a Gamification server

  Scenario: create a badge and try to recreate it
    Given I have the badge badge1 in the app app1
    When I POST it to the /badges endpoint
    Then I receive a 201 status code
    Given I create a badge that already exists
    When I POST it to the /badges endpoint
    Then I receive a 304 status code


  Scenario: create a pointScale
    Given I have the pointScale pointScale1 in the app app1
    When I POST it to the /pointScales endpoint
    Then I receive a 201 status code

  Scenario: create a rule that gives 10 points to pointScale1 and badge badge1 when a user post a comment with more than 100 chars
    Given I have the rule commentRule in app1 application trigger by the comment event that give 10 points to pointScale1 pointscales and the badges badge1 with chars,value,>,100 properties
    When I POST it to the /rules endpoint
    Then I receive a 201 status code
