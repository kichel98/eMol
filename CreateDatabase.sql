DROP DATABASE eMol;
CREATE DATABASE eMol;
USE eMol;

CREATE TABLE UserType(
id INT UNSIGNED AUTO_INCREMENT,
userType VARCHAR(20),
PRIMARY KEY(id)
);

CREATE TABLE Users(
id INT UNSIGNED AUTO_INCREMENT NOT NULL,
username VARCHAR(60),
password VARCHAR(60),
userType INT UNSIGNED,
PRIMARY KEY(id),
FOREIGN KEY(userType) REFERENCES UserType(id)
);

INSERT INTO UserType (userType) VALUES("Publisher");
INSERT INTO UserType (userType) VALUES("Customer");
INSERT INTO UserType (userType) VALUES("Support");

#CREATE USER 'publisher'@'localhost' IDENTIFIED BY '1234';
#CREATE USER 'customer'@'localhost' IDENTIFIED BY '1234';
#CREATE USER 'support'@'localhost' IDENTIFIED BY '1234';

GRANT ALL PRIVILEGES ON * . * TO 'publisher'@'localhost';
GRANT ALL PRIVILEGES ON * . * TO 'customer'@'localhost';
GRANT ALL PRIVILEGES ON * . * TO 'support'@'localhost';

INSERT INTO Users (username, password, userType) VALUES("publisher", "1234", 1);
INSERT INTO Users (username, password, userType) VALUES("customer", "1234", 2);
INSERT INTO Users (username, password, userType) VALUES("support", "1234", 3);

SELECT Users.id, UserType.userType FROM Users JOIN UserType
ON Users.userType=UserType.id WHERE username LIKE 'publisher' AND password='1234';
