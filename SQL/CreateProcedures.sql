DELIMITER $$
DROP PROCEDURE IF EXISTS fillRandomBooks;
CREATE PROCEDURE fillRandomBooks (count INT)
BEGIN
	DECLARE isbn VARCHAR(70);
    DECLARE book_title VARCHAR(70);
    DECLARE book_subtitle VARCHAR(70);
    DECLARE book_description VARCHAR(80);
    DECLARE book_author VARCHAR(60);
    DECLARE price DOUBLE;
    DECLARE publisher_id INT UNSIGNED;
    DECLARE date DATE;
    DECLARE type INT UNSIGNED;
    DECLARE language_id INT UNSIGNED;
	DECLARE book_id INT UNSIGNED;
    DECLARE pages INT;
    DECLARE file_size INT;
    DECLARE length DOUBLE;
    DECLARE book_narrator VARCHAR(90);
	
    DECLARE ctr1 INT;
	DECLARE ctr2 INT;
	DECLARE countOfWords INT;
    
    SET ctr1 = 0;
    WHILE ctr1 < count DO
		-- ISBN
		SET isbn = '';
		SET ctr2 = 0;
		WHILE ctr2 < 10 DO
			SET isbn = CONCAT(isbn, CONVERT(FLOOR(RAND()*10), CHAR(1)));
			SET ctr2 = ctr2 + 1;
		END WHILE;
		
		-- title, subtitle, description
		SET book_title = '';
		SET book_subtitle = '';
		SET book_description = '';
		SET ctr2 = 0;
		SET countOfWords = FLOOR(RAND()*(5-2+1))+2;
		WHILE ctr2 < 5 DO
			SELECT CONCAT(book_title, (SELECT SUBSTRING_INDEX(`title`, ' ', 1) FROM book ORDER BY RAND() LIMIT 1), ' ') INTO book_title;
			SELECT CONCAT(book_subtitle, (SELECT SUBSTRING_INDEX(`subtitle`, ' ', 1) FROM book ORDER BY RAND() LIMIT 1), ' ') INTO book_subtitle;
			SELECT CONCAT(book_description, (SELECT SUBSTRING_INDEX(`title`, ' ', 1) FROM book ORDER BY RAND() LIMIT 1), ' ') INTO book_description;
			SET ctr2 = ctr2 + 1;
		END WHILE;  

		-- author
		SELECT author FROM book ORDER BY RAND() LIMIT 1 INTO book_author; 
		
		-- price
		SET price = FLOOR(RAND()*(200-1+1))+1 + ROUND(RAND(), 2); 
		
		-- publisher
		SELECT id FROM publisher ORDER BY RAND() LIMIT 1 INTO publisher_id;
		
		-- date
		SET date = CURDATE() - INTERVAL FLOOR(RAND()*(20000-200+1))+200 DAY;
		
		-- type
		SELECT id FROM book_type ORDER BY RAND() LIMIT 1 INTO type;
		
		-- language_id
		SELECT id FROM language ORDER BY RAND() LIMIT 1 INTO language_id;
		
		SET autocommit = 0;
		START TRANSACTION;
		
		INSERT INTO book(isbn,title,subtitle,description,author,price,publisher_id,date,type,language_id) 
			VALUES(isbn, book_title, book_subtitle, book_description, book_author, price, publisher_id, date, type, language_id);
		
		-- EBOOK
		IF type = 1 THEN
			SELECT id FROM book WHERE book.isbn = isbn INTO book_id;
			SET pages = FLOOR(RAND()*(700-30+1))+30;
			SET file_size = FLOOR(RAND()*(30000-500+1))+500;
			INSERT INTO ebook(book_id, pages, file_size) VALUES(book_id, pages, file_size);
		END IF;
		
		-- PAPERBACK
		IF type = 2 THEN
			SELECT id FROM book WHERE book.isbn = isbn INTO book_id;
			SET pages = FLOOR(RAND()*(700-30+1))+30;
			INSERT INTO paperback(book_id, pages) VALUES(book_id, pages);
		END IF;
		
		-- AUDIOBOOK
		IF type = 3 THEN
			SELECT id FROM book WHERE book.isbn = isbn INTO book_id;
			SET length = FLOOR(RAND()*(15-0+1))+0 + ROUND(RAND(), 2); 
			SET file_size = FLOOR(RAND()*(30000-500+1))+500;
			SELECT narrator FROM audiobook ORDER BY RAND() LIMIT 1 INTO book_narrator;
			INSERT INTO audiobook(book_id, length, file_size, narrator) VALUES(book_id, length, file_size, book_narrator);
		END IF;
	   
		COMMIT;
		SET ctr1 = ctr1 + 1;
	END WHILE;
    
END $$

DELIMITER ;