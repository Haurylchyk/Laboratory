DROP DATABASE IF EXISTS `gift_certificate_system`;

CREATE DATABASE gift_certificate_system DEFAULT CHARACTER SET utf8;

USE gift_certificate_system;

-- -----------------------------------------------------
-- Table `gift_certificate`
-- -----------------------------------------------------
CREATE TABLE gift_certificate (
    id               INT          NOT NULL AUTO_INCREMENT,
    name             VARCHAR(100) NOT NULL,
    description      VARCHAR(255) NOT NULL,
    price            INT          NOT NULL,
    duration         INT          NOT NULL,
    create_date      TIMESTAMP    NOT NULL DEFAULT NOW(),
    last_update_date TIMESTAMP    NOT NULL DEFAULT NOW(),
	 PRIMARY KEY (id));

-- -----------------------------------------------------
-- Table `tag`
-- -----------------------------------------------------
CREATE TABLE tag (
    id   INT          NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
     PRIMARY KEY (id));

-- -----------------------------------------------------
-- Table `certificate_tag`
-- -----------------------------------------------------
CREATE TABLE certificate_tag (
    cert_id INT NOT NULL,
    tag_id  INT NOT NULL,
    PRIMARY KEY (cert_id, tag_id),
    CONSTRAINT fk_cert_1
        FOREIGN KEY (cert_id)
            REFERENCES gift_certificate (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_tag
        FOREIGN KEY (tag_id)
            REFERENCES tag (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
CREATE TABLE user (
    id   INT          NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    login VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(45) NOT NULL,
    PRIMARY KEY (id));

-- -----------------------------------------------------
-- Table `gift_order`
-- -----------------------------------------------------
CREATE TABLE gift_order (
    id               INT          NOT NULL AUTO_INCREMENT,
    user_id          INT          NOT NULL,
    cost             INT          NOT NULL,
    date             TIMESTAMP    NOT NULL DEFAULT NOW(),
	 PRIMARY KEY (id),
	     CONSTRAINT fk_user
        FOREIGN KEY (user_id)
            REFERENCES user (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table `order_certificate`
-- -----------------------------------------------------
CREATE TABLE order_certificate (
    order_id INT NOT NULL,
    cert_id  INT NOT NULL,
    CONSTRAINT fk_order
        FOREIGN KEY (order_id)
            REFERENCES gift_order (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_cert_2
        FOREIGN KEY (cert_id)
            REFERENCES gift_certificate (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE);
