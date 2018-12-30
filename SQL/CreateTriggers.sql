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
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "you can't buy so many books";
	END IF;
END $$

DELIMITER ;