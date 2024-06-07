create database ams
use ams








CREATE TABLE users(
	id int AUTO_INCREMENT,
	name VARCHAR(60),
	cnic VARCHAR(18),
	role VARCHAR(20),
	email VARCHAR(30),
	password VARCHAR(512),
	balance double,
	primary key(id)
);

-- insertion of AUCTIONEER
insert into users(id, name,cnic,role,email,password,balance)
values (2, "Ali", "3660209449993", "AUCTIONEER", "ali@gmail.com", "9090", 0)


CREATE TABLE UserRoles (
    role ENUM('BUYER', 'SELLER', 'AUCTIONEER') NOT NULL,
    PRIMARY KEY (role)
);

CREATE TABLE Buyers (
    user_id INT PRIMARY KEY,
    bidsWon INT NOT NULL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES Users(id)
);

CREATE TABLE Sellers (
    user_id INT PRIMARY KEY,
    itemsSold INT NOT NULL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES Users(id)
);

insert into sellers(user_id) values (1)
insert into Auctioneers(user_id) values (2)

-- Auctioneers table
CREATE TABLE Auctioneers (
    user_id INT PRIMARY KEY,
    number_of_resolved_disptues INT NOT NULL DEFAULT 0,
    number_of_auctions INT NOT NULL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES Users(id)
);

CREATE TABLE items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    starting_price DECIMAL(10, 2) NOT NULL,
    seller_id INT NOT NULL,
    FOREIGN KEY (seller_id) REFERENCES users(id)
);

CREATE TABLE ItemDescriptor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);




CREATE TABLE Auctions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    item_id INT NOT NULL,
    seller_id INT NOT NULL,
    start_price DECIMAL(10, 2) NOT NULL,
    current_bid DECIMAL(10, 2) DEFAULT NULL,
    auctioneer_id INT NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    FOREIGN KEY (item_id) REFERENCES Items(id),
    FOREIGN KEY (seller_id) REFERENCES Sellers(user_id),
    FOREIGN KEY (auctioneer_id) REFERENCES Auctioneers(user_id)
);

select * from auctions
CREATE TABLE AuctionDescriptor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    item_id INT NOT NULL,
    seller_id INT NOT NULL,
    start_price DECIMAL(10, 2) NOT NULL,
    final_bid DECIMAL(10, 2) DEFAULT NULL,
    auctioneer_id INT NOT NULL,
    FOREIGN KEY (item_id) REFERENCES Items(id),
    FOREIGN KEY (seller_id) REFERENCES Sellers(user_id),
    FOREIGN KEY (auctioneer_id) REFERENCES Auctioneers(user_id)
);




















INSERT INTO items (name, description, starting_price, seller_id) 
VALUES ('Vintage Watch', 'A classic vintage watch in perfect condition.', 150.00, 3);

INSERT INTO items (name, description, starting_price, seller_id) 
VALUES ('Antique Vase', 'A beautiful antique vase from the 19th century.', 200.00, 3);


