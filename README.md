# CommandR

A service app to create and manage Magic The Gathering tournaments for Commander format.  
**WORK IN PROGESS**

# SETUP:

This app has been deployed using Java 11 and needs a postgresql database. You need to init db before running this service in order to execute correctly liquibase script.
We suggest to install run a docker container of postgresql. Execute the following command to start:

docker run --name commander-postgres -e POSTGRES_PASSWORD=YOUR_PASSWORD -d -p 5432:5432 postgres

This will start postgresql database container on port 5432 in local environment with default username postgres.
Remember to set YOUR_PASSWORD to the config file 'application.properties'

# Work with me:
If you would like to contribute to the project, feel free to create a branch and make your own changes.  
Once finished, create the pull request to merge onto the master.
I'll try to review it as soon as possible! :)  

**For codestyle:** please use the checkstyle.xml in 'z_ext' folder.  
**Postman collection:** you can find a ready-made postman collection to test it in local in the commander-service/external-files/postman-collection directory.

