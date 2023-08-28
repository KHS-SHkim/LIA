SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS user_authority;
DROP TABLE IF EXISTS authority;
DROP TABLE IF EXISTS book_img;
DROP TABLE IF EXISTS Declaration;
DROP TABLE IF EXISTS note;
DROP TABLE IF EXISTS rental_info;

DROP TABLE IF EXISTS commen_code;
DROP TABLE IF EXISTS questionAndAnswer;
DROP TABLE IF EXISTS user;




/* Create Tables */

CREATE TABLE address
(
	id int NOT NULL AUTO_INCREMENT,
	user_id int NOT NULL,
	address varchar(200) NOT NULL,
	address_detail varchar(100) NOT NULL,
	post_num varchar(6) NOT NULL,
	stat varchar(5),
	reg_date datetime,
	PRIMARY KEY (id)
);


CREATE TABLE authority
(
	id int NOT NULL AUTO_INCREMENT,
	name varchar(40) NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE book
(
	id int NOT NULL AUTO_INCREMENT,
	name varchar(100) NOT NULL,
	cate varchar(10) NOT NULL,
	price int NOT NULL,
	rental_spot varchar(50) NOT NULL,
	book_detail varchar(1000) NOT NULL,
	rental_stat varchar(10) NOT NULL,
	rental_date int NOT NULL,
	reg_date datetime NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE book_img
(
	id int NOT NULL AUTO_INCREMENT,
	book_id int NOT NULL,
	img_src varchar(200),
	PRIMARY KEY (id)
);


CREATE TABLE commen_code
(
	code varchar(5) NOT NULL,
	code_name varchar(50) NOT NULL
);


CREATE TABLE Declaration
(
	id int NOT NULL AUTO_INCREMENT,
	user_id int NOT NULL,
	book_id int NOT NULL,
	reporter_id int NOT NULL,
	report_type varchar(50) NOT NULL,
	report_content varchar(500) NOT NULL,
	answer_content varchar(500) NOT NULL,
	reg_date datetime NOT NULL,
	answer_date datetime NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE note
(
	id int NOT NULL AUTO_INCREMENT,
	user_id int NOT NULL,
	book_id int NOT NULL,
	receiver_id int NOT NULL,
	contents longtext NOT NULL,
	reg_date datetime NOT NULL,
	reception_chk int NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE questionAndAnswer
(
	id int NOT NULL AUTO_INCREMENT,
	user_id int NOT NULL,
	question longtext NOT NULL,
	answer longtext,
	reg_date datetime NOT NULL,
	answer_date datetime NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE rental_info
(
	id int NOT NULL AUTO_INCREMENT,
	user_id int NOT NULL,
	book_id int NOT NULL,
	rental_spot varchar(50) NOT NULL,
	rental_stat varchar(10) NOT NULL,
	rental_date datetime NOT NULL,
	reg_date datetime NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE user
(
	id int NOT NULL AUTO_INCREMENT,
	username varchar(80) NOT NULL,
	password varchar(200) NOT NULL,
	name varchar(10) NOT NULL,
	email varchar(50) NOT NULL,
	phone varchar(20) NOT NULL,
	reg_date datetime,
	profile_img varchar(200),
	PRIMARY KEY (id),
	UNIQUE (username)
);


CREATE TABLE user_authority
(
	user_id int NOT NULL,
	authority_id int NOT NULL
);



/* Create Foreign Keys */

ALTER TABLE user_authority
	ADD FOREIGN KEY (authority_id)
	REFERENCES authority (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE book_img
	ADD FOREIGN KEY (book_id)
	REFERENCES book (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE Declaration
	ADD FOREIGN KEY (book_id)
	REFERENCES book (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE note
	ADD FOREIGN KEY (book_id)
	REFERENCES book (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE rental_info
	ADD FOREIGN KEY (book_id)
	REFERENCES book (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE address
	ADD FOREIGN KEY (user_id)
	REFERENCES user (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE Declaration
	ADD FOREIGN KEY (reporter_id)
	REFERENCES user (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE Declaration
	ADD FOREIGN KEY (user_id)
	REFERENCES user (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE note
	ADD FOREIGN KEY (receiver_id)
	REFERENCES user (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE note
	ADD FOREIGN KEY (user_id)
	REFERENCES user (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE questionAndAnswer
	ADD FOREIGN KEY (user_id)
	REFERENCES user (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE rental_info
	ADD FOREIGN KEY (user_id)
	REFERENCES user (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE user_authority
	ADD FOREIGN KEY (user_id)
	REFERENCES user (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



