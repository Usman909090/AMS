
CREATE DATABASE AMS;

CREATE TABLE users(
id int AUTO_INCREMENT,
name VARCHAR(60),
cnic VARCHAR(18),
role VARCHAR(20),
email VARCHAR(30),
password VARCHAR(512),
primary key(id)
)