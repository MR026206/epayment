# Transfer Rest API

Spring Boot - Rest API for e-payment between two accounts

### Technologies
- Spring Rest API
- MySQL database
- Slf4j
- Embedded Tomcat container
- JDK 1.8
- Postman Client

### Application Architecture
![alt text](https://github.com/MR026206/epayment/blob/master/epayment/src/main/resources/design/ApplicationArchitecture.png "Application Architecture")

### Application Component Model
![alt text](https://github.com/MR026206/epayment/blob/master/epayment/src/main/resources/design/ApplicationComponentModel.PNG "Component Model")

### Sequence Diagram for Account Creation
![alt text](https://github.com/MR026206/epayment/blob/master/epayment/src/main/resources/design/SequenceDiagramForAccountCreation.png "Sequence Diagram")

### Sequence Diagram For TransferMoney
![alt text](https://github.com/MR026206/epayment/blob/master/epayment/src/main/resources/design/SequenceDiagramForTransferMoney.png "Sequence Diagram")

### How to run

### 1/  prerequisites
- Create MySQL DB Schema - ingenico_epayment
- Create Account Table - 

CREATE TABLE ingenico_epayment.`account` (
  `AccountId` int(11) NOT NULL,
  `Name` varchar(45) DEFAULT NULL,
  `Balance` decimal(20,4) DEFAULT NULL,
  `CurrencyCode` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`AccountId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

- Insert Query for Test scenarios 

INSERT INTO `ingenico_epayment`.`account` (`AccountId`,`Name`,`Balance`,`CurrencyCode`)VALUES(3001,"Test User 1",100000.0000,"EUR");
INSERT INTO `ingenico_epayment`.`account` (`AccountId`,`Name`,`Balance`,`CurrencyCode`)VALUES(3002,"Test User 2",100.0000,"EUR");
INSERT INTO `ingenico_epayment`.`account` (`AccountId`,`Name`,`Balance`,`CurrencyCode`)VALUES(3003,"Test User 3",300000.0000,"EUR");
INSERT INTO `ingenico_epayment`.`account` (`AccountId`,`Name`,`Balance`,`CurrencyCode`)VALUES(3004,"Test User 4",50.0000,"EUR");
INSERT INTO `ingenico_epayment`.`account` (`AccountId`,`Name`,`Balance`,`CurrencyCode`)VALUES(3005,"Test User 5",40.0000,"EUR");
INSERT INTO `ingenico_epayment`.`account` (`AccountId`,`Name`,`Balance`,`CurrencyCode`)VALUES(3006,"Test User 6",0.0000,"EUR");

### 2/ Run this app using following approaches
- java -jar path-to-jar
- on Project root , mvn spring-boot:run
### 2.1/ Run 
```sh
java -jar target/transfer-0.0.1-SNAPSHOT.jar
```

### 2.2/ Run 
```sh
mvn spring-boot:run
```

Application starts an embedded tomcat server on localhost port 8092

- http://localhost:8092/account/create
- http://localhost:8092/transfer/money


### Available Services

| HTTP METHOD | PATH | USAGE |
| -----------| ------ | ------ |
| POST | /account/create | with Account object as JSON creates a new account | 
| POST | /transfer/money | with Transaction object as JSON to perform transaction between two accounts | 

### Http Status
- 200 OK: The request has succeeded
- 409 CONFLICT


### Sample JSON for Account creation & Transfer Money

##### Create Account: : 

```sh
{"accountId": 2025, "name":"Test User", "balance":100.000, "currency":"EUR"}
```

#### Transfer Money:
```sh
{"currencyCode": "EUR", "amount":20.0000, "fromAccountId":3001, "toAccountId":3003}
```





