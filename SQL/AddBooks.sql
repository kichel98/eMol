USE emol;
INSERT INTO book (isbn, title, subtitle, description, author, price, publisher_id, date, language_id, type)
 VALUES("9999999999", "Keto Diet", "How to lose 5 pounds in two weeks", 
 "This is the book that will change your life", "Joe Doe", 14.99, 2, "2018/10/8", 1, 1);
 INSERT INTO ebook (book_id, pages, file_size) VALUES(1, 120, 4);
 
 INSERT INTO book (isbn, title, subtitle, description, author, price, publisher_id, date, language_id, type)
 VALUES("9999999998", "Yoga For Beginners", "Relaxe Your Body With This 5 Step Program", 
 "This is the only book you need", "Sarah Harrison", 11.99, 2, "2018/12/30", 1, 2);
  INSERT INTO paperback (book_id, pages) VALUES(2, 250);

 INSERT INTO book (isbn, title, subtitle, description, author, price, publisher_id, date, language_id, type)
 VALUES("9999999997", "Awakening", "How to Open Your Chakras for Better Life", 
 "Find peace in your life", "Tom Morris", 29.99, 1, "1997/3/27", 1, 2);
   INSERT INTO paperback (book_id, pages) VALUES(3, 120);
 
  INSERT INTO book (isbn, title, subtitle, description, author, price, publisher_id, date, language_id, type)
 VALUES("9999999996", "Kochbuch", "Kontakt Grill", 
 "Ja, Ja, naturlich", "Hans Schmidt", 8.99, 1, "2004/2/13", 2, 3);
   INSERT INTO audiobook (book_id, length, file_size, narrator) VALUES(4, 1.5, 25, "Hanna Den");
 
 
 
 INSERT INTO book (isbn, title, subtitle, description, author, price, publisher_id, date, language_id, type)
 VALUES("9999999995", "Keto Diet", "How to lose 5 pounds in two weeks", 
 "This is the book that will change your life", "Joe Doe", 14.99, 2, "2018/10/8", 1, 1);
 INSERT INTO ebook (book_id, pages, file_size) VALUES(9, 120, 4);
 
 INSERT INTO book (isbn, title, subtitle, description, author, price, publisher_id, date, language_id, type)
 VALUES("9999999994", "Yoga For Beginners", "Relaxe Your Body With This 5 Step Program", 
 "This is the only book you need", "Sarah Harrison", 11.99, 2, "2018/12/30", 1, 2);
  INSERT INTO paperback (book_id, pages) VALUES(10, 250);

 INSERT INTO book (isbn, title, subtitle, description, author, price, publisher_id, date, language_id, type)
 VALUES("9999999993", "Awakening", "How to Open Your Chakras for Better Life", 
 "Find peace in your life", "Tom Morris", 29.99, 1, "1997/3/27", 1, 2);
   INSERT INTO paperback (book_id, pages) VALUES(7, 120);
 
  INSERT INTO book (isbn, title, subtitle, description, author, price, publisher_id, date, language_id, type)
 VALUES("9999999992", "Kochbuch", "Kontakt Grill", 
 "Ja, Ja, naturlich", "Hans Schmidt", 8.99, 1, "2004/2/13", 2, 3);
   INSERT INTO audiobook (book_id, length, file_size, narrator) VALUES(8, 1.5, 25, "Hanna Den");
INSERT INTO ebook (book_id, pages, file_size) VALUES(8, 134, 25);

SELECT book.id, title, subtitle, isbn, price, type FROM book LEFT JOIN ebook ON ebook.book_id = book.id LEFT JOIN paperback ON paperback.book_id = book.id LEFT JOIN audiobook ON audiobook.book_id = book.id WHERE title LIKE '%k%' AND ( type='ebook' OR type='paperback' OR type='audiobook' ) AND price>1 AND price<100 ORDER BY price ASC LIMIT 10
SELECT book.id, title, subtitle, isbn, price, type FROM book LEFT JOIN ebook ON ebook.book_id = book.id LEFT JOIN paperback ON paperback.book_id = book.id LEFT JOIN audiobook ON audiobook.book_id = book.id;