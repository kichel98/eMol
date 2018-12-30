DELIMITER $$
CREATE TRIGGER addToInventory AFTER INSERT ON book
FOR EACH ROW 
BEGIN 
	INSERT INTO inventory(book_id, quantity) VALUES(NEW.id, 0);
END $$

CREATE TRIGGER checkSale BEFORE UPDATE ON inventory
FOR EACH ROW
BEGIN
	IF NEW.quantity < 0 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "You can't buy so many books";
	END IF;
END $$

CREATE TRIGGER amountOfSaleGreaterThanZero BEFORE INSERT ON sale
FOR EACH ROW
BEGIN
	IF NEW.quantity <= 0 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "You can't buy nonpositive number of books";
	END IF;
END $$

CREATE TRIGGER checkNewBook BEFORE INSERT ON book
FOR EACH ROW
BEGIN
	IF NEW.price < 0 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Book mustn't have negative price";
    END IF;
    
    IF LENGTH(NEW.isbn) <> 10 OR NEW.isbn NOT REGEXP '^[0-9]+$' THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "ISBN must have 10 numbers";
	END IF; 
END $$

CREATE TRIGGER checkNewEbook BEFORE INSERT ON ebook
FOR EACH ROW
BEGIN
	IF NEW.pages < 0 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Ebook mustn't have negative number of pages";
    END IF;
    
    IF NEW.file_size < 0 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Ebook mustn't have negative file size";
    END IF;
END $$

CREATE TRIGGER checkNewPaperback BEFORE INSERT ON paperback
FOR EACH ROW
BEGIN
	IF NEW.pages < 0 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Paperback mustn't have negative number of pages";
    END IF;
END $$

CREATE TRIGGER checkNewAudiobook BEFORE INSERT ON audiobook
FOR EACH ROW
BEGIN
	IF NEW.length < 0 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Audiobook mustn't have negative length";
    END IF;
    
    IF NEW.file_size < 0 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Audiobook mustn't have negative file size";
    END IF;
END $$

DELIMITER ;