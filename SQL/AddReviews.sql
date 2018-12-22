INSERT INTO review (book_id, rating, description) VALUES(1, 5, "Good Book");
INSERT INTO review (book_id, rating, description) VALUES(1, 1, "Didn't read it");
INSERT INTO review (book_id, rating, description) VALUES(2, 2, "Poorly written");
INSERT INTO review (book_id, rating, description) VALUES(3, 5, "Life-changing");
INSERT INTO review (book_id, rating, description) VALUES(4, 2, "Sound quility is not so good");

SELECT book.id, title, subtitle, isbn, price, type FROM book LEFT JOIN ebook ON ebook.book_id = book.id LEFT JOIN paperback ON paperback.book_id = book.id LEFT JOIN audiobook ON audiobook.book_id = book.id WHERE title LIKE '%k%' AND ( type='ebook' OR type='paperback' OR type='audiobook' ) AND price>1 AND price<100 ORDER BY price ASC LIMIT 10;
SELECT * FROM book;
