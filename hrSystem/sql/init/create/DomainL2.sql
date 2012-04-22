CREATE  TABLE `hrsys`.`DomainL2` (

  `id` INT NOT NULL AUTO_INCREMENT ,
  
  `pid` INT NOT NULL,

  `name` TEXT NULL ,

  `leader` INT NULL ,

  PRIMARY KEY (`id`) ,

  UNIQUE INDEX `id_UNIQUE` (`id` ASC) );

