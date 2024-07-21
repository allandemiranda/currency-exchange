# currency-exchange
## Introduction
- Define and implement a RESTful API that performs finds transfer between two accounts with currency exchange
## Technologies
- Java 22
- Spring 3
- H2
## To compile
```
sh mvnw compile
```
## To run
```
java -classpath "target/classes" app.bank.dummy.CurrencyExchangeApplication
```
## Api docs
- Open API available on http://localhost:8080/api/v1/
- Swagger UI on http://localhost:8080/api/v1/swagger-ui/index.html [Swagger UI not is full configured]
- Insomnia JSON import available on root folder.
## Main features
- Create a Client: http://localhost:8080/api/v1/swagger-ui/index.html#/client-controller/createClient
- Create an Account to the Client: http://localhost:8080/api/v1/swagger-ui/index.html#/client-controller/createClientAccount
- You can create many Accounts with different or same Currency and initial balance for the same Client.
- Currency are limited by [AUD, CHF, EUR, JPY, GBP, USD, NZD, SEK, CNH, CAD].
- Create a Transaction between Accounts: http://localhost:8080/api/v1/swagger-ui/index.html#/client-controller/createClientAccountTransaction
- You can transfer money between Accounts from different clients and from the same Client.
- You can see the Transaction historic by Account on http://localhost:8080/api/v1/swagger-ui/index.html#/client-controller/getClientAccountTransactions or the stack of Transactions on http://localhost:8080/api/v1/swagger-ui/index.html#/transaction-controller/getTransactions
- The system also includes an option to Close an Account, means it can no longer be used for operations, and deactivate a Client, means that all open Accounts associated with this Client will close and the Client can no longer operate on the application.
## Final considerations
- I didn't follow the Git commit practices [wasn't on the requirements on the email].
- I didn't create Unit Tests for all methods on the project, I focused on the business part [controllers and services].
- I didn't create Java Doc for all methods on this project [wasn't on the requirements on the email].

By Allan Silva