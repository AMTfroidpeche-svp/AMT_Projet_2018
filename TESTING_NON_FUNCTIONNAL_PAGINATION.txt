# Automated test

In order to execute the automated functional tests, you have to run the project either locally or with docker compose. 
Then, you need to open the test project in your IDE and modify the AMTGamificationFluentTest file, by setting the base url (depending if you use docker or not) and the driver path on your computer.

We can see that if we change the code, for example by setting the number of apps per page to 15, the test fails.

![Alt text](img/modifyAppPerPage.png)

![Alt text](img/ScenarioFail.png)



