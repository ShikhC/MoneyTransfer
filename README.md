# MoneyTransfer

How to start the MoneyTransfer application
---

1. Run `mvn clean install` to build your application
2. Start application with `java -jar api/target/money-transfer-1.0-SNAPSHOT.jar server api/src/main/resources/config.yml`
3. To check that your application is running enter url `http://localhost:9000`

Health Check
---

To see your applications health enter url `http://localhost:9001/healthcheck`
