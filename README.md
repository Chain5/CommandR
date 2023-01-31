# CommandR

A service app to create and manage Magic The Gathering tournaments for Commander format.


# SETUP:

This app needs a postgresql database. You need to init db before running this service in order to execute correcly liquibase script.
We suggest to install run a docker container of postgresql. Execute the following command to start:

docker run --name commander-postgres -e POSTGRES_PASSWORD=<YourPassword> -d -p 5432:5432 postgres

This will start postgresql database container on port 5432 in local environment with default username postgres.
Remember to set <YourPassword> to the config file 'application.properties'

