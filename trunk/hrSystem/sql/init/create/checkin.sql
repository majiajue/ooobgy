CREATE  TABLE `hrsys`.`checkin` (

  `id` INT NOT NULL AUTO_INCREMENT ,

  `time` TIMESTAMP NULL ,

  `staff` INT NULL ,

  PRIMARY KEY (`id`) ,

  UNIQUE INDEX `id_UNIQUE` (`id` ASC) );