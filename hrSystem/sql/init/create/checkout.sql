CREATE  TABLE `hrsys`.`checkout` (

  `id` INT NOT NULL AUTO_INCREMENT ,

  `time` TIMESTAMP NULL ,

  `staff` INT NULL ,

  PRIMARY KEY (`id`) ,

  UNIQUE INDEX `id_UNIQUE` (`id` ASC) );