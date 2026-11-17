
CREATE DATABASE plant_database;

USE plant_database;

CREATE TABLE Plants (
    plant_id INT AUTO_INCREMENT PRIMARY KEY,
    common_name VARCHAR(100),
    scientific_name VARCHAR(150)
);

INSERT INTO Plant(common_name, scientific_name)
VALUES
('Sunflower', 'Helianthus annuus'),
('Rose', 'Rosa rubiginosa'),
('Basil', 'Ocimum basilicum');

SELECT * FROM Plants;


