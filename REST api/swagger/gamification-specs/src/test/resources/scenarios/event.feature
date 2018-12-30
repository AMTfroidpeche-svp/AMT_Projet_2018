Feature: full scenario testing different rule options

  Background:
    Given there is a Gamification server

  Scenario: full scenario
    #creation of badge1 pointScale1 and commentRule
    Given I have the badge badge1 in the app app1
    When I POST it to the /badges endpoint
    Then I receive a 201 status code
    Given I have the pointScale pointScale1 in the app app1
    When I POST it to the /pointScales endpoint
    Then I receive a 201 status code
    Given I have the rule commentRule in app1 application trigger by the comment event that give 10 points to pointScale1 pointscales and the badges badge1 with chars,value,>,100 properties
    When I POST it to the /rules endpoint
    Then I receive a 201 status code

    #generate one event that fulfilled the rule and check that the user have been awarded
    Given I generate the event comment in app1 application concerning user user1 with chars,150 properties and the timestamp null
    When I POST it to the /events endpoint
    Then I receive a 200 status code
    When I GET the user user1 in the app app1
    Then The user user1 should have correct badges : badge1, correct pointScales : pointScale1 with correct amount of points : 10 and correct eventCount : comment with correct amounts : 1

    #generate one event that fulfilled the rule and check that the user have been awarded
    Given I generate the event comment in app1 application concerning user user1 with chars,150 properties and the timestamp null
    When I POST it to the /events endpoint
    Then I receive a 200 status code
    When I GET the user user1 in the app app1
    Then The user user1 should have correct badges : badge1, correct pointScales : pointScale1 with correct amount of points : 20 and correct eventCount : comment with correct amounts : 2

    #create a seconde rule on the same event that check two properties : more than 5 comment and more than 150 characters in the comment
    Given I have the rule numberCommentRule in app1 application trigger by the comment event that give 10 points to pointScale2 pointscales and the badges badge2 with comment,amount,>,5;chars,value,>,150 properties
    When I POST it to the /rules endpoint
    Then I receive a 201 status code

    #generate an event that fulfilled only the first rule
    Given I generate the event comment in app1 application concerning user user1 with chars,150 properties and the timestamp null
    When I POST it to the /events endpoint
    Then I receive a 200 status code

    #check that the user have only the first badge and no point in the second pointScale (as the second rule wasn't fulfilled)
    When I GET the user user1 in the app app1
    Then The user user1 should have correct badges : badge1, correct pointScales : pointScale1;pointScale2 with correct amount of points : 30;0 and correct eventCount : comment with correct amounts : 3

    #generate 3 time the same event
    Given I generate the event comment in app1 application concerning user user1 with chars,150 properties and the timestamp null
    When I POST it to the /events endpoint
    Then I receive a 200 status code

    Given I generate the event comment in app1 application concerning user user1 with chars,150 properties and the timestamp null
    When I POST it to the /events endpoint
    Then I receive a 200 status code

    Given I generate the event comment in app1 application concerning user user1 with chars,150 properties and the timestamp null
    When I POST it to the /events endpoint
    Then I receive a 200 status code

    #the user still don't have the award of the second rule because only one of the two properties was good (he had 6 comment but no enough character in the comment)
    When I GET the user user1 in the app app1
    Then The user user1 should have correct badges : badge1, correct pointScales : pointScale1;pointScale2 with correct amount of points : 60;0 and correct eventCount : comment with correct amounts : 6

    #generate an event that trigger the two rules
    Given I generate the event comment in app1 application concerning user user1 with chars,151 properties and the timestamp null
    When I POST it to the /events endpoint
    Then I receive a 200 status code

    #check that all awards have been added to user1
    When I GET the user user1 in the app app1
    Then The user user1 should have correct badges : badge1;badge2, correct pointScales : pointScale1;pointScale2 with correct amount of points : 70;10 and correct eventCount : comment with correct amounts : 7

    #changing the name of badge1 and pointScale1
    Given I update the badge badge1 in the app app1 with the new badge badge5
    When I PUT it to the /badges endpoint
    Then I receive a 200 status code

    Given I update the pointScale pointScale1 in the app app1 with the new PointScale pointScale5
    When I PUT it to the /pointScales endpoint
    Then I receive a 200 status code

    #check if the change have been apply to the user1 and to the rules
    When I GET the user user1 in the app app1
    Then The user user1 should have correct badges : badge5;badge2, correct pointScales : pointScale5;pointScale2 with correct amount of points : 70;10 and correct eventCount : comment with correct amounts : 7

    When I GET to the /rules endpoint the app app1
    Then I receive correct rules

    #delete the badge5 and pointScale5
    Given I want to delete the badge badge5 in the app app1
    When I DELETE it to the /badges endpoint
    Then I receive a 200 status code

    Given I want to delete the pointScale pointScale5 in the app app1
    When I DELETE it to the /pointScales endpoint
    Then I receive a 200 status code

    #check that user1 doesn't have them anymore
    When I GET the user user1 in the app app1
    Then The user user1 should have correct badges : badge2, correct pointScales : pointScale2 with correct amount of points : 10 and correct eventCount : comment with correct amounts : 7

    #as we deleted all the reward of the first rule, this rule is deleted, we check it here
    When I GET to the /rules endpoint the app app1
    Then I receive correct rules

    #delete the two rules, the first one is already deleted, we should have a 304 status code
    Given I want to delete the rule commentRule in the app app1
    When I DELETE it to the /rules endpoint
    Then I receive a 304 status code
    Given I want to delete the rule numberCommentRule in the app app1
    When I DELETE it to the /rules endpoint
    Then I receive a 200 status code

    #check that there is no rule left
    When I GET to the /rules endpoint the app app1
    Then I receive correct rules

    #as there is no rule related to the comment event anymore, we consider it obsolete and so his counter is deleted for all user
    #check that the user doesn't have the event counter anymore
    When I GET the user user1 in the app app1
    Then The user user1 should have correct badges : badge2, correct pointScales : pointScale2 with correct amount of points : 10 and correct eventCount : null with correct amounts : null
