CREATE DATABASE `fitnessclub`;

use 'fitnessclub';

CREATE TABLE `employee` (
                            `idemployee` int NOT NULL AUTO_INCREMENT,
                            `idperson` int NOT NULL,
                            `employeetype` varchar(45) NOT NULL,
                            `baseamount` int NOT NULL,
                            UNIQUE KEY `idemployee_UNIQUE` (`idemployee`)
);

CREATE TABLE `member` (
                          `idmember` int NOT NULL AUTO_INCREMENT,
                          `idperson` int NOT NULL,
                          `membershiptype` varchar(45) DEFAULT 'BASIC',
                          PRIMARY KEY (`idmember`),
                          UNIQUE KEY `idmember_UNIQUE` (`idmember`)
);

CREATE TABLE `month` (
                         `idmonth` int NOT NULL AUTO_INCREMENT,
                         `idperson` int NOT NULL,
                         `year` int NOT NULL,
                         `month` int NOT NULL,
                         `notedhours` int DEFAULT NULL,
                         PRIMARY KEY (`idmonth`),
                         UNIQUE KEY `idmonth_UNIQUE` (`idmonth`)
);

CREATE TABLE `person` (
                          `idperson` int NOT NULL AUTO_INCREMENT,
                          `name` varchar(45) DEFAULT NULL,
                          `cpr` varchar(45) DEFAULT NULL,
                          PRIMARY KEY (`idperson`),
                          UNIQUE KEY `idperson_UNIQUE` (`idperson`)
);
