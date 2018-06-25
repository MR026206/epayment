-- DROP Table 

DROP TABLE IF EXISTS ingenico_epayment.account;

-- Create Account Table

CREATE TABLE ingenico_epayment.`account` (
  `AccountId` int(11) NOT NULL,
  `Name` varchar(45) DEFAULT NULL,
  `Balance` decimal(20,4) DEFAULT NULL,
  `CurrencyCode` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`AccountId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Insert Dummy Data
INSERT INTO `ingenico_epayment`.`account` (`AccountId`,`Name`,`Balance`,`CurrencyCode`)VALUES(3001,"Mukesh R",100000.0000,"EUR");
INSERT INTO `ingenico_epayment`.`account` (`AccountId`,`Name`,`Balance`,`CurrencyCode`)VALUES(3002,"Sumit B",100.0000,"EUR");
INSERT INTO `ingenico_epayment`.`account` (`AccountId`,`Name`,`Balance`,`CurrencyCode`)VALUES(3003,"Deepak N",300000.0000,"EUR");
INSERT INTO `ingenico_epayment`.`account` (`AccountId`,`Name`,`Balance`,`CurrencyCode`)VALUES(3004,"Huns B",50.0000,"EUR");
INSERT INTO `ingenico_epayment`.`account` (`AccountId`,`Name`,`Balance`,`CurrencyCode`)VALUES(3005,"Roel T",40.0000,"EUR");
INSERT INTO `ingenico_epayment`.`account` (`AccountId`,`Name`,`Balance`,`CurrencyCode`)VALUES(3006,"Test Blank Ac",0.0000,"EUR");