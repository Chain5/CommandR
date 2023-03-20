# CommandR

A service app to create and manage Magic The Gathering tournaments for Commander format.  
**WORK IN PROGESS**

# SETUP:

This app needs a postgresql database. You need to init db before running this service in order to execute correctly liquibase script.
We suggest to install run a docker container of postgresql. Execute the following command to start:

docker run --name commander-postgres -e POSTGRES_PASSWORD=YOUR_PASSWORD -d -p 5432:5432 postgres

This will start postgresql database container on port 5432 in local environment with default username postgres.
Remember to set YOUR_PASSWORD to the config file 'application.properties'

