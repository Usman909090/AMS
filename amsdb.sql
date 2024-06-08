create database ams;
use ams;

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
    FOREIGN KEY (item_id) REFERENCES Items(id),
    FOREIGN KEY (seller_id) REFERENCES Sellers(user_id),
    FOREIGN KEY (auctioneer_id) REFERENCES Auctioneers(user_id)
);

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

CREATE TABLE Disputes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    reporter_id INT NOT NULL,
    description TEXT,
    status VARCHAR(20) DEFAULT 'PENDING',
    FOREIGN KEY (reporter_id) REFERENCES Users(id)
);

CREATE TABLE DisputeAffectedUsers (
    dispute_id INT,
    user_id INT,
    PRIMARY KEY (dispute_id, user_id),
    FOREIGN KEY (dispute_id) REFERENCES Disputes(id),
    FOREIGN KEY (user_id) REFERENCES Users(id)
);

INSERT INTO users (name, cnic, role, email, password, balance)
VALUES 
    ('John Doe', '1234567890123456', 'BUYER', 'john@example.com', 'password123', 1000.00),
    ('Jane Doe', '1234567890123457', 'SELLER', 'jane@example.com', 'password456', 1500.00),
    ('Alice Smith', '1234567890123458', 'AUCTIONEER', 'alice@example.com', 'password789', 2000.00);

INSERT INTO disputes (reporter_id, description, status)
VALUES 
    (1, 'Sample dispute 1', 'Pending'),
    (2, 'Sample dispute 2', 'Resolved');
    
INSERT INTO disputeAffectedUsers (dispute_id, user_id) VALUES (4, 5);

SELECT * FROM Users;
SELECT * FROM Disputes;
SELECT * FROM DisputeAffectedUsers;

SELECT d.id, d.reporter_id, d.description, d.status, GROUP_CONCAT(u.name SEPARATOR ', ') AS affected_users
			FROM disputes d
			INNER JOIN DisputeAffectedUsers du ON d.id = du.dispute_id
			INNER JOIN Users u ON du.user_id = u.id
			GROUP BY d.id;
            