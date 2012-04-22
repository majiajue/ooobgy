CREATE  TABLE `hrsys`.`Staff` (

  `id` INT NOT NULL AUTO_INCREMENT ,

  `name` TEXT NULL ,

  `login` VARCHAR(45) NULL ,

  `psw` VARCHAR(45) NULL ,

  `domainl1` INT NULL ,

  `domainl2` INT NULL ,

  `domainl3` INT NULL ,

  `email` TEXT NULL ,

  `role` INT NULL ,

  `level` INT NULL ,

  `salary` DECIMAL NULL ,

  `location` TEXT NULL ,

  `phone` VARCHAR(45) NULL ,

  `gender` TINYINT(1) NULL ,

  `age` INT NULL ,

  `checkin` DATETIME NULL ,

  PRIMARY KEY (`id`) ,

  UNIQUE INDEX `id_UNIQUE` (`id` ASC) );

