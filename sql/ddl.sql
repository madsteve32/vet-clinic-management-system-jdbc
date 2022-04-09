-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema vet_clinic_management_system
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema vet_clinic_management_system
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `vet_clinic_management_system` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `vet_clinic_management_system` ;

-- -----------------------------------------------------
-- Table `vet_clinic_management_system`.`administrators`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `vet_clinic_management_system`.`administrators` ;

CREATE TABLE IF NOT EXISTS `vet_clinic_management_system`.`administrators` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NULL DEFAULT NULL,
  `last_name` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(45) NOT NULL,
  `tel_number` VARCHAR(45) NULL DEFAULT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `gender` VARCHAR(45) NULL DEFAULT NULL,
  `role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `vet_clinic_management_system`.`clients`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `vet_clinic_management_system`.`clients` ;

CREATE TABLE IF NOT EXISTS `vet_clinic_management_system`.`clients` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NULL DEFAULT NULL,
  `last_name` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  `tel_number` VARCHAR(45) NULL DEFAULT NULL,
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `gender` VARCHAR(45) NULL DEFAULT NULL,
  `role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `vet_clinic_management_system`.`doctors`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `vet_clinic_management_system`.`doctors` ;

CREATE TABLE IF NOT EXISTS `vet_clinic_management_system`.`doctors` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NULL DEFAULT NULL,
  `last_name` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(60) NOT NULL,
  `tel_number` VARCHAR(45) NULL DEFAULT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `gender` VARCHAR(45) NOT NULL,
  `role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `vet_clinic_management_system`.`examinations`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `vet_clinic_management_system`.`examinations` ;

CREATE TABLE IF NOT EXISTS `vet_clinic_management_system`.`examinations` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `date` DATE NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vet_clinic_management_system`.`appointments`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `vet_clinic_management_system`.`appointments` ;

CREATE TABLE IF NOT EXISTS `vet_clinic_management_system`.`appointments` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `examination_type` VARCHAR(45) NOT NULL,
  `chosen_date_time` TIMESTAMP NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `doctor_id` INT NOT NULL,
  `client_id` INT NOT NULL,
  `examination_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `doctor_id`, `client_id`),
  INDEX `fk_appointments_doctors1_idx` (`doctor_id` ASC) VISIBLE,
  INDEX `fk_appointments_clients1_idx` (`client_id` ASC) VISIBLE,
  INDEX `fk_appointments_examinations1_idx` (`examination_id` ASC) VISIBLE,
  CONSTRAINT `fk_appointments_clients1`
    FOREIGN KEY (`client_id`)
    REFERENCES `vet_clinic_management_system`.`clients` (`id`)
	ON DELETE CASCADE,
  CONSTRAINT `fk_appointments_doctors1`
    FOREIGN KEY (`doctor_id`)
    REFERENCES `vet_clinic_management_system`.`doctors` (`id`)
	ON DELETE CASCADE,
  CONSTRAINT `fk_appointments_examinations1`
    FOREIGN KEY (`examination_id`)
    REFERENCES `vet_clinic_management_system`.`examinations` (`id`)
	ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `vet_clinic_management_system`.`pets`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `vet_clinic_management_system`.`pets` ;

CREATE TABLE IF NOT EXISTS `vet_clinic_management_system`.`pets` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `breed` VARCHAR(45) NOT NULL,
  `weight` INT NOT NULL,
  `client_id` INT NOT NULL,
  PRIMARY KEY (`id`, `client_id`),
  INDEX `fk_pets_clients1_idx` (`client_id` ASC) VISIBLE,
  CONSTRAINT `fk_pets_clients1`
    FOREIGN KEY (`client_id`)
    REFERENCES `vet_clinic_management_system`.`clients` (`id`)
	ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `vet_clinic_management_system`.`pet_passports`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `vet_clinic_management_system`.`pet_passports` ;

CREATE TABLE IF NOT EXISTS `vet_clinic_management_system`.`pet_passports` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `deworming_date` DATE NULL DEFAULT NULL,
  `vaccination_date` DATE NULL DEFAULT NULL,
  `examination_date` DATE NULL DEFAULT NULL,
  `pets_id` INT NOT NULL,
  `pets_client_id` INT NOT NULL,
  PRIMARY KEY (`id`, `pets_id`, `pets_client_id`),
  INDEX `fk_pet_passports_pets1_idx` (`pets_id` ASC, `pets_client_id` ASC) VISIBLE,
  CONSTRAINT `fk_pet_passports_pets1`
    FOREIGN KEY (`pets_id` , `pets_client_id`)
    REFERENCES `vet_clinic_management_system`.`pets` (`id` , `client_id`)
	ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
