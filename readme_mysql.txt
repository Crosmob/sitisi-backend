#In order to create the database, use the command:
CREATE DATABASE  IF NOT EXISTS menus DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

#In order to create a new user and give access a database, use the command:
GRANT ALL ON `menus`.* TO `user1`@localhost IDENTIFIED BY '1234';
FLUSH PRIVILEGES;

USE menus;

#In order to create the table, use the command:
CREATE TABLE IF NOT EXISTS menus (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  institution VARCHAR(255) NOT NULL,
  meal_day DATE NOT NULL,
  meal_type VARCHAR(255) NOT NULL,
  main_dishes VARCHAR(511) NOT NULL,
  side_dishes VARCHAR(511) NOT NULL,
  PRIMARY KEY ( id ),
  CONSTRAINT menus UNIQUE (institution, meal_day, meal_type)
) ENGINE = InnoDB;

In order to create an index on meal_type, use:
CREATE INDEX menus_meal_type ON menus(meal_type);

#If we want to drop the index:
#DROP INDEX menus_meal_type ON menus;

#For openshift, the database is already created, so we only need to create the table and the index.