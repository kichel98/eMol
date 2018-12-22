USE emol;
DROP USER 'publisher'@'localhost';
DROP USER 'customer'@'localhost';
DROP USER 'support'@'localhost';

flush privileges;
CREATE USER 'publisher'@'localhost' IDENTIFIED BY '1234';
CREATE USER 'customer'@'localhost' IDENTIFIED BY '1234';
CREATE USER 'support'@'localhost' IDENTIFIED BY '1234';

GRANT ALL PRIVILEGES ON * . * TO 'publisher'@'localhost';
GRANT ALL PRIVILEGES ON * . * TO 'customer'@'localhost';
GRANT ALL PRIVILEGES ON * . * TO 'support'@'localhost';

INSERT INTO user (username, password, user_type_id) VALUES("publisher", "1234", 1);
INSERT INTO user (username, password, user_type_id) VALUES("customer", "1234", 2);
INSERT INTO user (username, password, user_type_id) VALUES("support", "1234", 3);

INSERT INTO customer (user_id, money) VALUES(2, 100);


SELECT * FROM user;
SELECT * FROM publisher;

