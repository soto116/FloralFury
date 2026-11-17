DROP DATABASE IF EXISTS plantdatabase;
CREATE DATABASE plantdatabase;
USE plantdatabase;

-- Gardener
CREATE TABLE Gardener (
    userID INT PRIMARY KEY,
    password INT,
    gardenerName VARCHAR(100)
);

-- Taxonomy
CREATE TABLE Taxonomy (
    scientificName VARCHAR(100) PRIMARY KEY,
    family VARCHAR(100),
    `order` VARCHAR(100),
    class VARCHAR(100)
);

-- Plant
CREATE TABLE Plant (
    plantID INT AUTO_INCREMENT PRIMARY KEY,
    commonName VARCHAR(100),
    stemColor VARCHAR(50),
    scientificName VARCHAR(100),
    FOREIGN KEY (scientificName) REFERENCES Taxonomy(scientificName)
);

-- CaresFor relationship
CREATE TABLE CaresFor (
    userID INT,
    plantID INT,
    PRIMARY KEY (userID, plantID),
    FOREIGN KEY (userID) REFERENCES Gardener(userID),
    FOREIGN KEY (plantID) REFERENCES Plant(plantID)
);

-- NativeContinent
CREATE TABLE NativeContinent (
    continentName VARCHAR(50) PRIMARY KEY,
    EWhemisphere VARCHAR(50),
    NShemisphere VARCHAR(50)
);

-- GrowsOn relationship
CREATE TABLE GrowsOn (
    plantID INT,
    continentName VARCHAR(50),
    PRIMARY KEY (plantID, continentName),
    FOREIGN KEY (plantID) REFERENCES Plant(plantID),
    FOREIGN KEY (continentName) REFERENCES NativeContinent(continentName)
);

-- FloweringPlant
CREATE TABLE FloweringPlant (
    plantID INT PRIMARY KEY,
    petalNo INT,
    floweringSeason VARCHAR(50),
    flowerColor VARCHAR(50),
    FOREIGN KEY (plantID) REFERENCES Plant(plantID)
);

-- NonFloweringPlant
CREATE TABLE NonFloweringPlant (
    plantID INT PRIMARY KEY,
    reproductionMethod VARCHAR(100),
    foliageType VARCHAR(100),
    FOREIGN KEY (plantID) REFERENCES Plant(plantID)
);

-- Measurable Growing Requirements
CREATE TABLE MGrowReq (
    measureID INT PRIMARY KEY,
    unit VARCHAR(50),
    growType VARCHAR(100)
);

-- GrowsWith relationship
CREATE TABLE GrowsWith (
    plantID INT,
    measureID INT,
    amount INT,
    PRIMARY KEY (plantID, measureID),
    FOREIGN KEY (plantID) REFERENCES Plant(plantID),
    FOREIGN KEY (measureID) REFERENCES MGrowReq(measureID)
);

-- Non-Measurable Growing Requirements
CREATE TABLE NonMGrowReq (
    nonmeasureID INT PRIMARY KEY,
    reqType VARCHAR(100),
    description VARCHAR(255)
);

-- Needs relationship
CREATE TABLE Needs (
    plantID INT,
    nonmeasureID INT,
    PRIMARY KEY (plantID, nonmeasureID),
    FOREIGN KEY (plantID) REFERENCES Plant(plantID),
    FOREIGN KEY (nonmeasureID) REFERENCES NonMGrowReq(nonmeasureID)
);

-- Pest
CREATE TABLE Pest (
    pestName VARCHAR(100) PRIMARY KEY,
    pestType VARCHAR(100)
);

-- Infestation (weak entity)
CREATE TABLE Infestation (
    plantID INT,
    pestName VARCHAR(100),
    startedWhen DATE,
    treatmentPlan VARCHAR(255),
    activeStatus BOOLEAN,
    PRIMARY KEY (plantID, pestName),
    FOREIGN KEY (plantID) REFERENCES Plant(plantID),
    FOREIGN KEY (pestName) REFERENCES Pest(pestName)
);


USE plantdatabase;

INSERT INTO Taxonomy
(scientificName, family, `order`, class)
VALUES
('Helianthus annuus', 'Asteraceae', 'Asterales', 'Magnoliopsida'),
('Rosa chinensis', 'Rosaceae', 'Rosales', 'Magnoliopsida'),
('Ocimum basilicum', 'Lamiaceae', 'Lamiales', 'Magnoliopsida'),
('Nephrolepis exaltata', 'Nephrolepidaceae', 'Polypodiales', 'Polypodiopsida');



INSERT INTO Plant
(plantID, commonName, stemColor, scientificName)
VALUES
(1001, 'Sunflower', 'Green', 'Helianthus annuus'),
(1002, 'China Rose', 'Green', 'Rosa chinensis'),
(1003, 'Basil', 'Green', 'Ocimum basilicum'),
(1004, 'Boston Fern', 'Green', 'Nephrolepis exaltata');



INSERT INTO FloweringPlant
(plantID, petalNo, floweringSeason, flowerColor)
VALUES
(1001, NULL, 'Summer', 'Yellow'),
(1002, 5, 'Spring', 'Pink'),
(1003, 5, 'Summer', 'White');



INSERT INTO NonFloweringPlant
(plantID, reproductionMethod, foliageType)
VALUES
(1004, 'Spores', 'Evergreen fronds');



INSERT INTO NativeContinent
(continentName, EWhemisphere, NShemisphere)
VALUES
('North America', 'Western', 'Northern'),
('Asia', 'Eastern', 'Northern'),
('South America', 'Western', 'Southern');



INSERT INTO GrowsOn
(plantID, continentName)
VALUES
(1001, 'North America'),
(1002, 'Asia'),
(1003, 'Asia'),
(1004, 'North America'),
(1004, 'South America');




USE plantdatabase;

-- Measured requirements (parent records)
INSERT INTO MGrowReq (measureID, unit, growType)
VALUES
(1, 'test units', 'Test metric A'),
(2, 'test units', 'Test metric B');

-- Associate measured requirements with plants
INSERT INTO GrowsWith (plantID, measureID, amount)
VALUES
(1001, 1, 10),
(1001, 2, 20),
(1004, 1, 15);

-- Non-measured requirements (parent records)
INSERT INTO NonMGrowReq (nonmeasureID, reqType, description)
VALUES
(1, 'Test condition A', 'Illustrative test record only.'),
(2, 'Test condition B', 'Illustrative test record only.');

-- Associate non-measured requirements with plants
INSERT INTO Needs (plantID, nonmeasureID)
VALUES
(1001, 1),
(1004, 1),
(1004, 2);




