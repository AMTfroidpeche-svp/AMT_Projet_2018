# Teaching-HEIGVD-AMT-2018-Project

## WP 2: the core engine and its robust REST APIs

### How to use the REST API 

The deployment is the same as in WP1, simply run `docker-compose up` at the root of the github repository. You can make request on the REST API at  `{url of your server}/AMT_2018_REST_API-1.0.0/` 

Available endpoints are : 

- /rules : CRUD operations on rules
- /badges : CRUD operations on badges
- /pointScales : CRUD operations on pointScales
- /events : POST an event
- /users : GET a user to verify if everything went well
- /applications : DELETE all applications (useful at the beginning of tests scenario)

### TESTS

You can launch the tests from intellij when the application is running. They can be launch if your application is running locally or in docker, you simply need to change the address in the pom.xml of the tests project (around the line 20)

We have tested all CRUD operations on the three principals endpoints as well as a complete scenario.

### LOAD TESTS

We wrote a jMeter test in order to check if the REST API is still working with concurrent access, the script simply create a rule, then 100 threads send 30 event requests, and at the end, we do a get request on the user to check if he get all his reward.

We choose not to store the event in the database and to calculate the pointScales and badges of a user at each event. This is not the best solution as it is pretty bad in term of performance. Indeed, we have to synchronized all the controllers method in order to support concurrency access in the database, and we also have to get a lock on the application we are modifying during the whole function ( which can be huge if we have a lot of rule to evaluate).

A better approach would have been to store the event in the database and to flush them on a regular basis, this way, we can have good performance as all request are independent, with a good performance when retrieving the users data as they are flush regularly and we don't have to calculate a lot of old events.

