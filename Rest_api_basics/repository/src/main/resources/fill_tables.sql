-- -----------------------------------------------------
-- Data for the table `gift_certificate`
-- -----------------------------------------------------
INSERT INTO gift_certificate (name, description, price, duration)
VALUES
('Spa services', 'Spa services and much more', 150, 15),
('Gym', 'Modern exercise machines and much more', 200, 20),
('Paintball', 'Many different locations and much more', 250, 25),
('Quest', 'Fun adventure', 180, 10);


-- -----------------------------------------------------
-- Data for the table `tag`
-- -----------------------------------------------------
INSERT INTO tag (name)
VALUES
('Rest'),
('Spa'),
('Activity'),
('Fun'),
('Relax');

-- -----------------------------------------------------
-- Data for the table `certificate_tag`
-- -----------------------------------------------------
INSERT INTO certificate_tag (cert_id, tag_id)
VALUES
(1, 1),
(1, 2),
(1, 5),
(2, 3),
(3, 1),
(3, 3),
(4, 1),
(4, 3),
(4, 4),
(4, 5);

-- -----------------------------------------------------
-- Data for the table `user`
-- -----------------------------------------------------
INSERT INTO user (name, login)
VALUES
('Ivan','athos'),
('Gena','porthos'),
('Maxim','aramis'),
('Alex','dartagnan');

-- -----------------------------------------------------
-- Data for the table `order`
-- -----------------------------------------------------
INSERT INTO `order` (user_id, cost)
VALUES
(1, 150),
(2, 350),
(3, 400),
(1, 180),
(4, 630),
(4, 780);

-- -----------------------------------------------------
-- Data for the table `order_certificate`
-- -----------------------------------------------------
INSERT INTO order_certificate (order_id, cert_id)
VALUES
(1, 1),
(2, 1),
(2, 2),
(3, 1),
(3, 3),
(4, 4),
(5, 2),
(5, 3),
(5, 4),
(6, 1),
(6, 2),
(6, 3),
(6, 4);