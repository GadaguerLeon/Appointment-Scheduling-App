--
-- 1. Delete prior tables
-- 2. Create new tables and populate with sample data
--

--
-- Replace U99999 below with the specific database name for the assignment
--

USE U99999;
--
-- Script to drop prior tables 
--

SET FOREIGN_KEY_CHECKS = 0;
SET @tarray = NULL;
SELECT GROUP_CONCAT('`', table_name, '`') INTO @tarray
  FROM information_schema.tables WHERE table_schema = (SELECT DATABASE());
SELECT IFNULL(@tarray,'dummy') INTO @tarray;
SET @tarray = CONCAT('DROP TABLE IF EXISTS ', @tarray);
PREPARE stmt FROM @tarray;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
SET FOREIGN_KEY_CHECKS = 1;


-- -----------------------------------------------------
-- Table `APTtype`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `APTtype` ;

CREATE TABLE IF NOT EXISTS `APTtype` (
  `APTtype_id` INT(11) NOT NULL,
  `description` VARCHAR(255) NOT NULL COMMENT 'First Appt, Weekly Visit, Emergency Call etc.',
  `created_at` DATETIME NULL DEFAULT NULL COMMENT 'When created',
  `created_by` VARCHAR(255) NULL DEFAULT NULL COMMENT 'Who created',
  `updated_at` DATETIME NULL DEFAULT NULL COMMENT 'When updated',
  `updated_by` VARCHAR(255) NULL DEFAULT NULL COMMENT 'Who updated',
  PRIMARY KEY (`APTtype_id`));

-- -----------------------------------------------------
-- Table `counselor`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `counselor` ;

CREATE TABLE IF NOT EXISTS `counselor` (
  `c_id` INT(11) NOT NULL,
  `c_name` VARCHAR(255) NOT NULL,
  `c_password` VARCHAR(255) NOT NULL,
  `c_pin` INT(11) NOT NULL,
  `created_at` DATETIME NULL DEFAULT NULL COMMENT 'When created',
  `created_by` VARCHAR(255) NULL DEFAULT NULL COMMENT 'Who created',
  `updated_at` DATETIME NULL DEFAULT NULL COMMENT 'When updated',
  `updated_by` VARCHAR(255) NULL DEFAULT NULL COMMENT 'Who updated',
  PRIMARY KEY (`c_id`));



-- -----------------------------------------------------
-- Table `address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `address` ;

CREATE TABLE IF NOT EXISTS `address` (
  `address_id` INT(11) NOT NULL,
  `addressline_1` VARCHAR(255) NOT NULL,
  `addressline_2` VARCHAR(255) NULL DEFAULT NULL,
  `city` VARCHAR(255) NOT NULL,
  `state` VARCHAR(255) NOT NULL,
  `postal_code` VARCHAR(255) NOT NULL,
  `phone` VARCHAR(255) NOT NULL,
  `created_at` DATETIME NULL DEFAULT NULL COMMENT 'When created',
  `created_by` VARCHAR(255) NULL DEFAULT NULL COMMENT 'Who created',
  `updated_at` DATETIME NULL DEFAULT NULL COMMENT 'When updated',
  `updated_by` VARCHAR(255) NULL DEFAULT NULL COMMENT 'Who updated',
  PRIMARY KEY (`address_id`));


-- -----------------------------------------------------
-- Table `appointment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `appointment` ;

CREATE TABLE IF NOT EXISTS `appointment` (
  `apt_id` INT(11) NOT NULL,
  `pt_id` INT(11) NOT NULL,
  `cr_id` INT(11) NOT NULL,
  `apt_type_id` INT(11) NOT NULL,
  `notes` VARCHAR(255) NOT NULL,
  `start_datetime` DATETIME NOT NULL,
  `created_at` DATETIME NULL DEFAULT NULL COMMENT 'When created',
  `created_by` VARCHAR(255) NULL DEFAULT NULL COMMENT 'Who created',
  `updated_at` DATETIME NULL DEFAULT NULL COMMENT 'When updated',
  `updated_by` VARCHAR(255) NULL DEFAULT NULL COMMENT 'Who updated',
  `patient_pt_id` INT(11) NOT NULL,
  `counselor_c_id` INT(11) NOT NULL,
  `APTtype_APTtype_id` INT(11) NOT NULL,
  PRIMARY KEY (`apt_id`),

  CONSTRAINT `fk_appointment_patient1`
    FOREIGN KEY (`patient_pt_id`)
    REFERENCES `patient` (`pt_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_appointment_counselor1`
    FOREIGN KEY (`counselor_c_id`)
    REFERENCES `counselor` (`c_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_appointment_APTtype1`
    FOREIGN KEY (`APTtype_APTtype_id`)
    REFERENCES `APTtype` (`APTtype_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);



-- -----------------------------------------------------
-- Table patient `
-- -----------------------------------------------------
DROP TABLE IF EXISTS `patient` ;

CREATE TABLE IF NOT EXISTS `patient` (
  `pt_id` INT(11) NOT NULL,
  `pt_name` VARCHAR(255) NOT NULL,
  `address_id` INT(11) NOT NULL,
  `INS_PR` VARCHAR(255) NOT NULL,
  `created_at` DATETIME NULL DEFAULT NULL COMMENT 'When created',
  `created_by` VARCHAR(255) NULL DEFAULT NULL COMMENT 'Who created',
  `updated_at` DATETIME NULL DEFAULT NULL COMMENT 'When updated',
  `updated_by` VARCHAR(255) NULL DEFAULT NULL COMMENT 'Who updated',
  `address_address_id` INT(11) NOT NULL,
  PRIMARY KEY (`pt_id`),
  CONSTRAINT `fk_patient _address`
    FOREIGN KEY (`address_address_id`)
    REFERENCES `address` (`address_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);



