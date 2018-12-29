Feature: full scenario testing different rule option

  Background:
    Given there is a Gamification server

  Scenario: full scenario
    Given I have the badge badge1 in the app app1
    When I POST it to the /badges endpoint
    Then I receive a 201 status code
    Given I have the pointScale pointScale1 in the app app1
    When I POST it to the /pointScales endpoint
    Then I receive a 201 status code
    Given I have the rule commentRule in app1 application trigger by the comment event that give 10 points to pointScale1 pointscales and the badges badge1 with chars,value,>,100 properties
    When I POST it to the /rules endpoint
    Then I receive a 201 status code

    Given I generate the event comment in app1 application concerning user user1 with chars,150 properties and the timestamp null
    When I POST it to the /events endpoint
    Then I receive a 200 status code
    When I GET the user user1 in the app app1
    Then The user user1 should have correct badges : badge1, correct pointScales : pointScale1 with correct ammount of points : 10 and correct eventCount : comment with correct amounts : 1

    Given I generate the event comment in app1 application concerning user user1 with chars,150 properties and the timestamp null
    When I POST it to the /events endpoint
    Then I receive a 200 status code
    When I GET the user user1 in the app app1
    Then The user user1 should have correct badges : badge1, correct pointScales : pointScale1 with correct ammount of points : 20 and correct eventCount : comment with correct amounts : 2

    Given I have the rule numberCommentRule in app1 application trigger by the comment event that give 10 points to pointScale2 pointscales and the badges badge2 with comment,amount,>,5;chars,value,>,150 properties
    When I POST it to the /rules endpoint
    Then I receive a 201 status code

    Given I generate the event comment in app1 application concerning user user1 with chars,150 properties and the timestamp null
    When I POST it to the /events endpoint
    Then I receive a 200 status code

    When I GET the user user1 in the app app1
    Then The user user1 should have correct badges : badge1, correct pointScales : pointScale1;pointScale2 with correct ammount of points : 30;0 and correct eventCount : comment with correct amounts : 3

    Given I generate the event comment in app1 application concerning user user1 with chars,150 properties and the timestamp null
    When I POST it to the /events endpoint
    Then I receive a 200 status code

    Given I generate the event comment in app1 application concerning user user1 with chars,150 properties and the timestamp null
    When I POST it to the /events endpoint
    Then I receive a 200 status code

    Given I generate the event comment in app1 application concerning user user1 with chars,150 properties and the timestamp null
    When I POST it to the /events endpoint
    Then I receive a 200 status code

    When I GET the user user1 in the app app1
    Then The user user1 should have correct badges : badge1, correct pointScales : pointScale1;pointScale2 with correct ammount of points : 60;0 and correct eventCount : comment with correct amounts : 6

    Given I generate the event comment in app1 application concerning user user1 with chars,151 properties and the timestamp null
    When I POST it to the /events endpoint
    Then I receive a 200 status code

    When I GET the user user1 in the app app1
    Then The user user1 should have correct badges : badge1;badge2, correct pointScales : pointScale1;pointScale2 with correct ammount of points : 70;10 and correct eventCount : comment with correct amounts : 7



    Given I update the badge badge1 in the app app1 with the new badge badge5
    When I PUT it to the /badges endpoint
    Then I receive a 200 status code

    Given I update the pointScale pointScale1 in the app app1 with the new PointScale pointScale5
    When I PUT it to the /pointScales endpoint
    Then I receive a 200 status code

    When I GET the user user1 in the app app1
    Then The user user1 should have correct badges : badge5;badge2, correct pointScales : pointScale5;pointScale2 with correct ammount of points : 70;10 and correct eventCount : comment with correct amounts : 7

    When I GET to the /rules endpoint the app app1
    Then I receive correct rules


    Given I want to delete the badge badge5 in the app app1
    When I DELETE it to the /badges endpoint
    Then I receive a 200 status code

    Given I want to delete the pointScale pointScale5 in the app app1
    When I DELETE it to the /pointScales endpoint
    Then I receive a 200 status code

    When I GET the user user1 in the app app1
    Then The user user1 should have correct badges : badge2, correct pointScales : pointScale2 with correct ammount of points : 10 and correct eventCount : comment with correct amounts : 7

    When I GET to the /rules endpoint the app app1
    Then I receive correct rules


    Given I want to delete the rule commentRule in the app app1
    When I DELETE it to the /rules endpoint
    Then I receive a 200 status code
    Given I want to delete the rule numberCommentRule in the app app1
    When I DELETE it to the /rules endpoint
    Then I receive a 200 status code

    When I GET to the /rules endpoint the app app1
    Then I receive correct rules

    When I GET the user user1 in the app app1
    Then The user user1 should have correct badges : badge2, correct pointScales : pointScale2 with correct ammount of points : 10 and correct eventCount : null with correct amounts : null
