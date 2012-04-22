CREATE  TABLE `hrsys`.`DomainL1` (

  `id` INT NOT NULL AUTO_INCREMENT ,

  `name` TEXT NULL ,

  `leader` INT NULL ,

  PRIMARY KEY (`id`) ,

  UNIQUE INDEX `id_UNIQUE` (`id` ASC) );

