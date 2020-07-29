CREATE DATABASE medical_institution;
USE medical_institution;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS room;
DROP TABLE IF EXISTS manipulation;
DROP TABLE IF EXISTS reservation;

CREATE TABLE users (
	user_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR (50) NOT NULL,
	second_name VARCHAR (50),
    phone_number VARCHAR(15) NOT NULL,
    gender ENUM ('MALE','FEMALE'),
    role VARCHAR(100) NOT NULL
);

CREATE TABLE room (
	room_id INT AUTO_INCREMENT PRIMARY KEY, 
    room_number INT NOT NULL,
    type VARCHAR(100) NOT NULL
);

CREATE TABLE manipulation (
	manipulation_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description text
);

CREATE TABLE reservation (
	reservation_id INT AUTO_INCREMENT PRIMARY KEY, 
    start_date timestamp  NOT NULL, 
    end_date timestamp  NOT NULL,
    active BOOLEAN NOT NULL,
    room_id INT,
    user_id INT, 
    manipulation_id INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (room_id) REFERENCES room(room_id),
    FOREIGN KEY (manipulation_id) REFERENCES manipulation(manipulation_id)
);

/*Users insert*/
INSERT INTO users (first_name, second_name, phone_number, gender, role) 
VALUES ('Alex', 'Gomolko', '+375447893819','MALE','surgeon');

INSERT INTO users (first_name, second_name, phone_number, gender, role) 
VALUES ('Anastasia', 'Vasilkova', '+375296743756','FEMALE','therapist');

INSERT INTO users (first_name, second_name, phone_number, gender, role) 
VALUES ('Vasilia', 'Andreev', '+375297895612','MALE','nurse');

INSERT INTO users (first_name, second_name, phone_number, gender, role) 
VALUES ('Kate', 'Malahova', '+375297895322','FEMALE','ophthalmologist');

/*Room insert*/
INSERT INTO room (room_number, type) 
VALUES (123, 'Neurological department');

INSERT INTO room (room_number, type) 
VALUES (124, 'Otorhinolaryngological department');

INSERT INTO room (room_number, type) 
VALUES (125, 'Admission department');

INSERT INTO room (room_number, type) 
VALUES (126, 'Clinical and diagnostic laboratory');

INSERT INTO room (room_number, type) 
VALUES (127, 'Office of ultrasonic diagnostics');

INSERT INTO room (room_number, type) 
VALUES (128, 'Neurological consulting room');

INSERT INTO room (room_number, type) 
VALUES (129, 'Outpatient gastroenterology office');

INSERT INTO room (room_number, type) 
VALUES (130, 'Dental');

/*Manipulation insert*/
INSERT INTO manipulation (name, description) 
VALUES ('Surgical intervention','Incision of tissues for the detection of the affected organ');

INSERT INTO manipulation (name, description) 
VALUES ('Dental office','The removal of wisdom teeth');

INSERT INTO manipulation (name, description) 
VALUES ('Information desk','Getting a statement');

INSERT INTO manipulation (name, description) 
VALUES ('Ophthalmologist office','Eye examination');

/*Reservation insert*/
INSERT INTO reservation (start_date, end_date, active, room_id,user_id,manipulation_id) 
VALUES ('2016-06-06 23:59','2016-06-08 23:59',false,1,1,1);



/*Query for normal view in all-reservations*/
SELECT rs.reservation_id, rs.start_date,rs.end_date,rs.active,r.room_number,r.type,rs.user_id,CONCAT_WS(' ',u.first_name,u.second_name) AS name,m.name
FROM reservation AS rs
JOIN room AS r ON rs.room_id = r.room_id
JOIN users AS u ON u.user_id = rs.user_id
JOIN manipulation AS m ON m.manipulation_id = rs.manipulation_id;

/*Checked for room reservation status on specified time*/
SELECT * FROM reservation r
WHERE (
('2016-06-07 00:00:00' BETWEEN r.start_date AND r.end_date
OR
'2016-06-10 23:59:59' BETWEEN r.start_date AND r.end_date)
AND r.room_id = 1
);

/*end automatically depending on the current time*/
UPDATE reservation
SET active = false WHERE reservation_id IN (
  SELECT reservation_id FROM (SELECT reservation_id FROM reservation WHERE active = true AND end_date < NOW()) AS tmp
);
/*Complete reservation ahead of time*/
UPDATE reservation SET active=? WHERE reservation_id=?