Feature: full scenario testing different rule option

  Background:
    Given there is a Gamification server

  Scenario: full scenario
    Given I create the badge badge1 in the app app1
    When I POST it to the /badges endpoint
    Then I receive a 201 status code
    Given I create the pointScale pointScale1 in the app app1.
    When I POST it to the /pointScales endpoint
    Then I receive a 201 status code
    Given I create the rule commentRule in app1 application trigger by the comment event that give 10 points to pointScale1 pointscales and the badges badge1 with chars,value,>,100 properties
    When I POST it to the /rules endpoint
    Then I receive a 201 status code

    Given I generate the event comment in app1 application concerning user user1 with chars,150 properties and the timestamp null
    When I POST it to the /events endpoint
    Then I receive a 200 status code
    When I GET to the /users endpoint the app app1 and the user user1
    Then The user user1 should have correct badges : badge1 and correct pointScales : pointScale1 with correct ammount of points : 10

    Given I generate the event comment in app1 application concerning user user1 with chars,150 properties and the timestamp null
    When I POST it to the /events endpoint
    Then I receive a 200 status code
    When I GET to the /users endpoint the app app1 and the user user1
    Then The user user1 should have correct badges : badge1 and correct pointScales : pointScale1 with correct ammount of points : 20

    Given I create the rule numberCommentRule in app1 application trigger by the comment event that give 10 points to pointScale2 pointscales and the badges badge2 with comment,amount,>,5 properties
    When I POST it to the /rules endpoint
    Then I receive a 201 status code

    Given I generate the event comment in app1 application concerning user user1 with chars,150 properties and the timestamp null
    When I POST it to the /events endpoint
    Then I receive a 200 status code

    When I GET to the /users endpoint the app app1 and the user user1
    Then The user user1 should have correct badges : badge1 and correct pointScales : pointScale1,pointScale2 with correct ammount of points : 20,0

    Given I generate the event comment in app1 application concerning user user1 with chars,150 properties and the timestamp null
    When I POST it to the /events endpoint
    Then I receive a 200 status code

    Given I generate the event comment in app1 application concerning user user1 with chars,150 properties and the timestamp null
    When I POST it to the /events endpoint
    Then I receive a 200 status code

    Given I generate the event comment in app1 application concerning user user1 with chars,150 properties and the timestamp null
    When I POST it to the /events endpoint
    Then I receive a 200 status code

    Given I generate the event comment in app1 application concerning user user1 with chars,150 properties and the timestamp null
    When I POST it to the /events endpoint
    Then I receive a 200 status code

    When I GET to the /users endpoint the app app1 and the user user1
    Then The user user1 should have correct badges : badge1;badge2 and correct pointScales : pointScale1;pointScale2 with correct ammount of points : 20;10

