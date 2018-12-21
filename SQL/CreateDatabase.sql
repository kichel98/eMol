DROP DATABASE emol.eMol;
CREATE DATABASE emol.eMol;
USE emol.eMol;

CREATE TABLE user_type(
id INT UNSIGNED AUTO_INCREMENT,
name VARCHAR(20),
PRIMARY KEY(id)
);
INSERT INTO user_type (name) VALUES("Publisher");
INSERT INTO user_type (name) VALUES("Customer");
INSERT INTO user_type (name) VALUES("Support");

CREATE TABLE language(
id INT UNSIGNED AUTO_INCREMENT,
name VARCHAR(20),
PRIMARY KEY(id)
);

CREATE TABLE book_type(
id INT UNSIGNED AUTO_INCREMENT,
name VARCHAR(20),
PRIMARY KEY(id)
);
INSERT INTO book_type (name) VALUES("ebook");
INSERT INTO book_type (name) VALUES("paperback");
INSERT INTO book_type (name) VALUES("audiobook");



CREATE TABLE publisher(
id INT UNSIGNED AUTO_INCREMENT NOT NULL,
name VARCHAR(90),
royalty DOUBLE,
PRIMARY KEY(id)
);

CREATE TABLE user(
id INT UNSIGNED AUTO_INCREMENT NOT NULL,
username VARCHAR(60),
password VARCHAR(60),
user_type_id INT UNSIGNED,
PRIMARY KEY(id),
FOREIGN KEY(user_type_id) REFERENCES user_type(id)
);

CREATE TABLE book(
id INT UNSIGNED AUTO_INCREMENT NOT NULL,
isbn VARCHAR(60) UNIQUE,
title VARCHAR(60),
subtitle VARCHAR(60),
description VARCHAR(60),
author VARCHAR(60),
price DOUBLE,
publisher_id INT UNSIGNED,
date DATE,
type VARCHAR(60) NOT NULL,
language_id INT UNSIGNED,
PRIMARY KEY(id),
FOREIGN KEY(publisher_id) REFERENCES publisher(id),
FOREIGN KEY(language_id) REFERENCES language(id)
);

CREATE TABLE paperback(
book_id INT UNSIGNED NOT NULL,
pages INT,
PRIMARY KEY(book_id),
FOREIGN KEY(book_id) REFERENCES book(id)
);

CREATE TABLE ebook(
book_id INT UNSIGNED NOT NULL,
pages INT,
file_size INT,
PRIMARY KEY(book_id),
FOREIGN KEY(book_id) REFERENCES book(id)
);

CREATE TABLE audiobook(
book_id INT UNSIGNED NOT NULL,
length DOUBLE,
file_size INT,
narrator VARCHAR(90),
PRIMARY KEY(book_id),
FOREIGN KEY(book_id) REFERENCES book(id)
);

CREATE TABLE review(
id INT UNSIGNED AUTO_INCREMENT NOT NULL,
book_id INT UNSIGNED,
rating INT,
description VARCHAR(90),
PRIMARY KEY(id),
FOREIGN KEY(book_id) REFERENCES book(id)
);

CREATE TABLE inventory(
book_id INT UNSIGNED NOT NULL,
quantity INT,
PRIMARY KEY(book_id),
FOREIGN KEY(book_id) REFERENCES book(id)
);

CREATE TABLE customer(
user_id INT UNSIGNED NOT NULL,
money INT,
PRIMARY KEY(user_id),
FOREIGN KEY(user_id) REFERENCES user(id)
);

CREATE TABLE sale(
id INT UNSIGNED AUTO_INCREMENT NOT NULL,
book_id INT UNSIGNED NOT NULL,
quantity INT,
customer_id INT UNSIGNED,
time TIMESTAMP,
PRIMARY KEY(id),
FOREIGN KEY(book_id) REFERENCES book(id),
FOREIGN KEY(customer_id) REFERENCES customer(user_id)
);