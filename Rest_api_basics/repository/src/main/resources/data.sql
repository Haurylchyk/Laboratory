-- -----------------------------------------------------
-- Data for the table `gift_certificate`
-- -----------------------------------------------------
INSERT INTO gift_certificate (name, description, price, duration)
VALUES
('Spa services', 'Spa services and much more', 150, 15),
('Gym', 'Modern exercise machines and much more', 200, 20),
('Paintball', 'Many different locations and much more', 250, 25);

-- -----------------------------------------------------
-- Data for the table `tag`
-- -----------------------------------------------------
INSERT INTO tag (name)
VALUES
('Rest'),
('Spa'),
('Activity');

-- -----------------------------------------------------
-- Data for the table `certificate_tag`
-- -----------------------------------------------------
INSERT INTO certificate_tag (cert_id, tag_id)
VALUES
(1, 1),
(1, 2),
(2, 3),
(3, 1),
(3, 3);