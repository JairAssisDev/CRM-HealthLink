-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`User` (
  `idUser` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(80) NOT NULL,
  `birthDate` DATE NOT NULL,
  `cpf` VARCHAR(14) NOT NULL,
  `acessLevel` ENUM('Patient', 'Doctor', 'Employee') NOT NULL DEFAULT 'Patient',
  `login` VARCHAR(256) NOT NULL,
  `password` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`idUser`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC),
  UNIQUE INDEX `cpf_UNIQUE` (`cpf` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Patient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Patient` (
  `idPatient` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(256) NULL,
  `User_idUser` INT NOT NULL,
  PRIMARY KEY (`idPatient`, `User_idUser`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  INDEX `fk_Patient_User_idx` (`User_idUser` ASC),
  CONSTRAINT `fk_Patient_User`
    FOREIGN KEY (`User_idUser`)
    REFERENCES `mydb`.`User` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Doctor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Doctor` (
  `idDoctor` INT NOT NULL AUTO_INCREMENT,
  `crm` VARCHAR(10) NOT NULL,
  `specialty` VARCHAR(50) NOT NULL,
  `User_idUser` INT NOT NULL,
  PRIMARY KEY (`idDoctor`, `User_idUser`),
  INDEX `fk_Doctor_User1_idx` (`User_idUser` ASC),
  CONSTRAINT `fk_Doctor_User1`
    FOREIGN KEY (`User_idUser`)
    REFERENCES `mydb`.`User` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Employee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Employee` (
  `idEmployee` INT NOT NULL AUTO_INCREMENT,
  `office` ENUM('Receptionist', 'Manager') NOT NULL,
  `User_idUser` INT NOT NULL,
  PRIMARY KEY (`idEmployee`, `User_idUser`),
  INDEX `fk_Employee_User1_idx` (`User_idUser` ASC),
  CONSTRAINT `fk_Employee_User1`
    FOREIGN KEY (`User_idUser`)
    REFERENCES `mydb`.`User` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Exam`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Exam` (
  `idExam` INT NOT NULL AUTO_INCREMENT,
  `date` DATE NOT NULL,
  `descriptionExamn` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`idExam`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Appointment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Appointment` (
  `idAppointment` INT NOT NULL AUTO_INCREMENT,
  `date` DATE NOT NULL,
  `descriptionAppointment` VARCHAR(256) NOT NULL,
  `Patient_idPatient` INT NOT NULL,
  `Doctor_idDoctor` INT NOT NULL,
  `Employee_idEmployee` INT NOT NULL,
  `Exam_idExam` INT NOT NULL,
  PRIMARY KEY (`idAppointment`, `Exam_idExam`),
  INDEX `fk_Appointment_Patient1_idx` (`Patient_idPatient` ASC),
  INDEX `fk_Appointment_Doctor1_idx` (`Doctor_idDoctor` ASC),
  INDEX `fk_Appointment_Employee1_idx` (`Employee_idEmployee` ASC),
  INDEX `fk_Appointment_Exam1_idx` (`Exam_idExam` ASC),
  CONSTRAINT `fk_Appointment_Patient1`
    FOREIGN KEY (`Patient_idPatient`)
    REFERENCES `mydb`.`Patient` (`idPatient`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Appointment_Doctor1`
    FOREIGN KEY (`Doctor_idDoctor`)
    REFERENCES `mydb`.`Doctor` (`idDoctor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Appointment_Employee1`
    FOREIGN KEY (`Employee_idEmployee`)
    REFERENCES `mydb`.`Employee` (`idEmployee`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Appointment_Exam1`
    FOREIGN KEY (`Exam_idExam`)
    REFERENCES `mydb`.`Exam` (`idExam`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
