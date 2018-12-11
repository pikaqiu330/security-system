DROP TABLE if EXISTS person;

CREATE TABLE person
(personId INT PRIMARY KEY AUTO_INCREMENT
,name VARCHAR(50)
,age int (11)
,sex int (4)
,birthday date
);

INSERT INTO person(name, age, sex, birthday)
VALUES('中国移动',18,1,DATE'2018-04-18');