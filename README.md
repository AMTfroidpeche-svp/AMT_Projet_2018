# Teaching-HEIGVD-AMT-2018-Project
## Deploy the app

1. Clone this repository
2. modify the file configSMTP.utf8 in amt_gamification/src/main/src to replace the xxx by an email address and a password of a valid gmail address (you can also modify the other parameters but we only try sending mail with gmail)
3. modify the username and password of the database if it doesn't match yours (in docker/app/Dockerfile and docker-compose.yml)
4. execute docker-compose up in the root directory of the repo



## Automated test 

Once the project is launch, you can launch the automated test by opening the project under amt_gamification_test.

You have to configure in the test file the base url (it change if you run the app locally or with docker) and the path to the driver. These two parameters are located at the beginning of the file AMTGamificationFluentTest.java



## PHPMyAdmin 

The docker compose include a phpMyAdmin image, that can be accessed from the port 8000.