# MoneyTransfer

How to start the MoneyTransfer application
---

1. Run `mvn clean install` to build your application
2. Start application with `java -jar api/target/money-transfer-1.0-SNAPSHOT.jar server api/src/main/resources/config.yml`
3. To check that your application is running enter url `http://localhost:9000`

Health Check
---

To see your applications health enter url `http://localhost:9001/healthcheck`


APIs

    GET     /account/{accountId} (com.moneytransfer.demo.resource.AccountResource)
    POST    /account/{accountId}/addMoney/{amount} (com.moneytransfer.demo.resource.AccountResource)
    POST    /account/{accountId}/transferMoney/{toAccountId}/{amount} (com.moneytransfer.demo.resource.AccountResource)
    POST    /account/{userId}/create (com.moneytransfer.demo.resource.AccountResource)
    GET     /swagger (io.federecio.dropwizard.swagger.SwaggerResource)
    GET     /swagger.{type:json|yaml} (io.swagger.jaxrs.listing.ApiListingResource)
    GET     /user/{userId} (com.moneytransfer.demo.resource.UserResource)
    POST    /user/{userId}/create (com.moneytransfer.demo.resource.UserResource)
    GET     /user/{userId}/detailed (com.moneytransfer.demo.resource.UserResource)
    GET     /user/{userId}/transactions/{limit} (com.moneytransfer.demo.resource.UserResource)
    POST    /tasks/log-level (io.dropwizard.servlets.tasks.LogConfigurationTask)
    POST    /tasks/gc (io.dropwizard.servlets.tasks.GarbageCollectionTask)