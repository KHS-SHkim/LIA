SELECT * FROM  address;
SELECT * FROM  user_authorities;
SELECT * FROM  authority;
SELECT * FROM  book_img;
SELECT * FROM  Declaration;
SELECT * FROM  note;
SELECT * FROM rental_info;
SELECT * FROM book;
SELECT * FROM commen_code;
SELECT * FROM qna;
SELECT * FROM user;


INSERT INTO user_authorities VALUES (2,1);


DELETE FROM address;
ALTER TABLE address  AUTO_INCREMENT = 1;
DELETE FROM user_authoriy;
ALTER TABLE user_authority  AUTO_INCREMENT = 1;
DELETE FROM book_img ;
ALTER TABLE book_img  AUTO_INCREMENT = 1;
DELETE FROM declaration ;
ALTER TABLE declaration  AUTO_INCREMENT = 1;
DELETE FROM note;
ALTER TABLE note AUTO_INCREMENT = 1;
DELETE FROM rental_info;
ALTER TABLE rental_into  AUTO_INCREMENT = 1;
DELETE FROM book;ALTER TABLE book AUTO_INCREMENT = 1;
DELETE FROM commen_code ;
ALTER TABLE commen_code  AUTO_INCREMENT = 1;
DELETE FROM questionandanswer ;
ALTER TABLE questionandanswer  AUTO_INCREMENT = 1;
DELETE FROM USER;
ALTER TABLE USER  AUTO_INCREMENT = 1;





INSERT INTO authority values(1,"ROLE_MEMBER");
INSERT INTO authority values(2,"ROLE_ADMIN");

INSERT INTO user (username, password, name, email, phone, reg_date) VALUES
('johnDoe', 'encryptedpassword123', 'John Doe', 'johndoe@email.com', '123-456-7890', NOW());


INSERT INTO book (name, cate, price, rental_spot, book_detail, rental_stat, rental_date, reg_date) VALUES
('The Great Gatsby', 'Novel', 10, 'Downtown Library', 'Classic novel by F. Scott Fitzgerald', 'Available', 0, NOW());


INSERT INTO address (user_id, address, address_detail, post_num, stat, reg_date) VALUES
(1, '123 Main St', 'Apt 4B', '123456', 'Active', NOW());

INSERT INTO book_img (book_id, img_src) VALUES
(1, 'link_to_great_gatsby_image.jpg');

INSERT INTO commen_code (code, code_name) VALUES
('A001', 'Sample Code');

INSERT INTO Declaration (user_id, book_id, reporter_id, report_type, report_content, answer_content, reg_date, answer_date) VALUES
(1, 1, 1, 'Damage', 'The book cover is torn.', 'Noted and will address', NOW(), NOW());

INSERT INTO note (user_id, book_id, receiver_id, contents, reg_date, reception_chk) VALUES
(1, 1, 2, 'Would you recommend this book?', NOW(), 0);

INSERT INTO rental_info (user_id, book_id, rental_spot, rental_stat, rental_date, reg_date) VALUES
(1, 1, 'Central Library', 'Rented', NOW(), NOW());

INSERT INTO commen_code (code, code_name) VALUES
('B002', 'Book Status');

INSERT INTO questionAndAnswer (user_id, question, answer, reg_date, answer_date) VALUES
(1, 'When will this book be available?', 'The book will be available next week.', NOW(), NOW());