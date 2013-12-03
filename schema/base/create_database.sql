SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `wanted` ;
CREATE SCHEMA IF NOT EXISTS `wanted` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `wanted` ;

-- -----------------------------------------------------
-- Table `wanted`.`schema_history`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `wanted`.`schema_history` ;

CREATE TABLE IF NOT EXISTS `wanted`.`schema_history` (
  `schema_id` BIGINT NOT NULL AUTO_INCREMENT,
  `date_updated` TIMESTAMP NULL,
  PRIMARY KEY (`schema_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `wanted`.`steam_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `wanted`.`steam_user` ;

CREATE TABLE IF NOT EXISTS `wanted`.`steam_user` (
  `steam_id` BIGINT NOT NULL,
  `schema_id` BIGINT NOT NULL,
  PRIMARY KEY (`steam_id`),
  CONSTRAINT `fk_steam_user_schema_history1`
    FOREIGN KEY (`schema_id`)
    REFERENCES `wanted`.`schema_history` (`schema_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_steam_user_schema_history1_idx` ON `wanted`.`steam_user` (`schema_id` ASC);


-- -----------------------------------------------------
-- Table `wanted`.`item_schema`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `wanted`.`item_schema` ;

CREATE TABLE IF NOT EXISTS `wanted`.`item_schema` (
  `item_id` BIGINT NOT NULL,
  `item_name` VARCHAR(100) NOT NULL,
  `item_image` VARCHAR(150) NULL,
  PRIMARY KEY (`item_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `wanted`.`item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `wanted`.`item` ;

CREATE TABLE IF NOT EXISTS `wanted`.`item` (
  `wanted_id` BIGINT NOT NULL AUTO_INCREMENT,
  `state` TINYINT NULL COMMENT 'wanted, not wanted, unknown',
  `steam_user_steam_id` BIGINT NOT NULL,
  `item_id` BIGINT NOT NULL,
  PRIMARY KEY (`wanted_id`),
  CONSTRAINT `fk_item_steam_user1`
    FOREIGN KEY (`steam_user_steam_id`)
    REFERENCES `wanted`.`steam_user` (`steam_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_item_item_schema1`
    FOREIGN KEY (`item_id`)
    REFERENCES `wanted`.`item_schema` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `steamid_wantedId` ON `wanted`.`item` (`wanted_id` ASC);

CREATE INDEX `fk_item_steam_user1_idx` ON `wanted`.`item` (`steam_user_steam_id` ASC);

CREATE INDEX `fk_item_item_schema1_idx` ON `wanted`.`item` (`item_id` ASC);


-- -----------------------------------------------------
-- Table `wanted`.`detail`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `wanted`.`detail` ;

CREATE TABLE IF NOT EXISTS `wanted`.`detail` (
  `detail_id` BIGINT NOT NULL AUTO_INCREMENT,
  `quality` INT NOT NULL,
  `level_number` SMALLINT NOT NULL,
  `is_tradable` TINYINT NOT NULL,
  `is_craftable` TINYINT NOT NULL,
  `craft_number` BIGINT NOT NULL,
  `is_gift_wrapped` TINYINT NOT NULL,
  `is_obtained` TINYINT(1) NOT NULL,
  `priority` INT NULL,
  `price` VARCHAR(45) NULL,
  `item_wanted_id` BIGINT NOT NULL,
  PRIMARY KEY (`detail_id`),
  CONSTRAINT `fk_detail_item1`
    FOREIGN KEY (`item_wanted_id`)
    REFERENCES `wanted`.`item` (`wanted_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_detail_item1_idx` ON `wanted`.`detail` (`item_wanted_id` ASC);

USE `wanted` ;

-- -----------------------------------------------------
-- procedure addDetail
-- -----------------------------------------------------

USE `wanted`;
DROP procedure IF EXISTS `wanted`.`addDetail`;

DELIMITER $$
USE `wanted`$$
CREATE PROCEDURE `addDetail`(steam_id bigint, wanted_id bigint, quality int, level_number smallint, is_tradable tinyint, is_craftable tinyint, craft_number bigint, is_gift_wrapped tinyint, is_obtained tinyInt, priority int, price varchar(45), out detail_id bigint  )
SQL SECURITY INVOKER
BEGIN
INSERT INTO `wanted`.`detail` (
`item_wanted_id`,
`quality`,
`level_number`,
`is_tradable`,
`is_craftable`,
`craft_number`,
`is_gift_wrapped`,
`is_obtained`,
`priority`,
`price`) select wanted_id,quality,level_number,is_tradable,is_craftable,craft_number,is_gift_wrapped,is_obtained,priority,price from item as i where i.steam_user_steam_id = steam_id and i.wanted_id = wanted_id;
select LAST_INSERT_ID() into detail_id;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure updateUserItems
-- -----------------------------------------------------

USE `wanted`;
DROP procedure IF EXISTS `wanted`.`updateUserItems`;

DELIMITER $$
USE `wanted`$$
CREATE PROCEDURE `updateUserItems`(steam_id bigint, out affected_rows int)
SQL SECURITY INVOKER
BEGIN

insert into `wanted`.`item` (steam_user_steam_id, item_id, state)
select steam_id, s.item_id, 2
from item_schema s
where s.item_id not in (
    select item_id
    from item
    where item.steam_user_steam_id = steam_id);
SELECT ROW_COUNT() into affected_rows;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure createNewUser
-- -----------------------------------------------------

USE `wanted`;
DROP procedure IF EXISTS `wanted`.`createNewUser`;

DELIMITER $$
USE `wanted`$$
CREATE PROCEDURE `createNewUser` (steam_id bigint)
SQL SECURITY INVOKER
BEGIN
select @schema_id :=  max(schema_id)  from `wanted`.`schema_history`;
insert into `wanted`.`steam_user` (steam_id, schema_id) values (steam_id, @schema_id);
call `wanted`.`updateUserItems` (steam_id, @affected_rows);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure getWantedList
-- -----------------------------------------------------

USE `wanted`;
DROP procedure IF EXISTS `wanted`.`getWantedList`;

DELIMITER $$
USE `wanted`$$
CREATE PROCEDURE `getWantedList` (in steam_id bigint)
SQL SECURITY INVOKER
BEGIN
  select
    su.steam_id,
    su.schema_id,
    i.wanted_id,
    i.state,
    i.item_id,
  s.item_name,
  s.item_image,
    d.detail_id,
    d.quality,
    d.level_number,
    d.is_tradable,
    d.is_craftable,
    d.craft_number,
    d.is_gift_wrapped,
    d.is_obtained,
    d.priority,
    d.price
from
    steam_user as su
  left join item as i on su.steam_id = i.steam_user_steam_id
    left join detail as d on i.wanted_id = d.item_wanted_id
  left join item_schema as s on i.item_id = s.item_id
    where su.steam_id = steam_id;

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure addSchemaItem
-- -----------------------------------------------------

USE `wanted`;
DROP procedure IF EXISTS `wanted`.`addSchemaItem`;

DELIMITER $$
USE `wanted`$$
CREATE PROCEDURE `addSchemaItem` (item_id bigint, item_name varchar(100), item_image varchar(150))
SQL SECURITY INVOKER
BEGIN
  INSERT INTO `wanted`.`item_schema` (`item_id`, `item_name`, `item_image`) VALUES (item_id, item_name, item_image);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure addSchemaVersion
-- -----------------------------------------------------

USE `wanted`;
DROP procedure IF EXISTS `wanted`.`addSchemaVersion`;

DELIMITER $$
USE `wanted`$$
CREATE PROCEDURE `addSchemaVersion` (date_updated timestamp)
SQL SECURITY INVOKER
BEGIN
INSERT INTO `wanted`.`schema_history` (`date_updated`) VALUES (date_updated);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure getSchema
-- -----------------------------------------------------

USE `wanted`;
DROP procedure IF EXISTS `wanted`.`getSchema`;

DELIMITER $$
USE `wanted`$$
CREATE PROCEDURE `getSchema` ()
SQL SECURITY INVOKER
BEGIN
  select item_id, item_name from wanted.item_schema;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure getWelcomeStatus
-- -----------------------------------------------------

USE `wanted`;
DROP procedure IF EXISTS `wanted`.`getWelcomeStatus`;

DELIMITER $$
USE `wanted`$$
CREATE PROCEDURE `getWelcomeStatus` (steam_id bigint, out welcome_status int)
SQL SECURITY INVOKER
BEGIN
  declare userExists int;
  select count(1) into userExists from `wanted`.`steam_user` as su where su.steam_id = steam_id;
-- if user exists then
  if userExists > 0 then
    -- user exists, call update user
    begin

      call `wanted`.`updateUserItems`(steam_id, @rows_inserted);
      if @rows_inserted > 0 then
        -- new items

        select 1 into welcome_status;
      else
        -- no new items

        select 2 into welcome_status;
      end if;
    end;
  else
    begin
      -- new user

      call `wanted`.`createNewUser` (steam_id);
      select 0 into welcome_status;
    end;
  end if;

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure deleteDetail
-- -----------------------------------------------------

USE `wanted`;
DROP procedure IF EXISTS `wanted`.`deleteDetail`;

DELIMITER $$
USE `wanted`$$
CREATE PROCEDURE `deleteDetail` (steam_id bigint, detail_id bigint, out deleted_row_count bigint)
SQL SECURITY INVOKER
BEGIN
  delete detail from detail join item on item.wanted_id = detail.item_wanted_id where item.steam_user_steam_id = steam_id and detail.detail_id = detail_id;
  select ROW_COUNT() into deleted_row_count;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure editState
-- -----------------------------------------------------

USE `wanted`;
DROP procedure IF EXISTS `wanted`.`editState`;

DELIMITER $$
USE `wanted`$$
CREATE PROCEDURE `editState` (steam_id bigint, wanted_id bigint, state tinyint, out updated_row_count bigint)
SQL SECURITY INVOKER
BEGIN
  update item i set i.state = state where i.steam_user_steam_id = steam_id and i.wanted_id = wanted_id;
  select ROW_COUNT() into updated_row_count;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure markDetailAsObtained
-- -----------------------------------------------------

USE `wanted`;
DROP procedure IF EXISTS `wanted`.`markDetailAsObtained`;

DELIMITER $$
USE `wanted`$$
CREATE PROCEDURE `markDetailAsObtained` (detail_id bigint, out updated_row_count bigint)
SQL SECURITY INVOKER
BEGIN
  update detail set detail.is_obtained = true where detail.detail_id = detail_id;
  select ROW_COUNT() into updated_row_count;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure addDetailFromItemId
-- -----------------------------------------------------

USE `wanted`;
DROP procedure IF EXISTS `wanted`.`addDetailFromItemId`;

DELIMITER $$
USE `wanted`$$
CREATE PROCEDURE `addDetailFromItemId`(steam_id bigint, item_id bigint, quality int, level_number smallint, is_tradable tinyint, is_craftable tinyint, craft_number bigint, is_gift_wrapped tinyint, is_obtained tinyInt, out detail_id bigint  )
SQL SECURITY INVOKER
BEGIN
-- select @wanted_id from item join steam_user on item.steam_user_steam_id = steam_user.steam_id where item.item_id = item_id;
INSERT INTO `wanted`.`detail` (
`item_wanted_id`,
`quality`,
`level_number`,
`is_tradable`,
`is_craftable`,
`craft_number`,
`is_gift_wrapped`,
`is_obtained`,
`priority`,
`price`) select i.wanted_id,quality,level_number,is_tradable,is_craftable,craft_number,is_gift_wrapped,is_obtained, null, null from item as i where i.steam_user_steam_id = steam_id and i.item_id = item_id;
select LAST_INSERT_ID() into detail_id;
END$$

DELIMITER ;
SET SQL_MODE = '';
GRANT USAGE ON *.* TO pwanted@localhost;

GRANT EXECUTE ON procedure `wanted`.`addDetail` TO 'pwanted'@'localhost';
GRANT EXECUTE ON procedure `wanted`.`addSchemaItem` TO 'pwanted'@'localhost';
GRANT EXECUTE ON procedure `wanted`.`addSchemaVersion` TO 'pwanted'@'localhost';
GRANT EXECUTE ON procedure `wanted`.`createNewUser` TO 'pwanted'@'localhost';
GRANT EXECUTE ON procedure `wanted`.`deleteDetail` TO 'pwanted'@'localhost';
GRANT EXECUTE ON procedure `wanted`.`editState` TO 'pwanted'@'localhost';
GRANT EXECUTE ON procedure `wanted`.`getSchema` TO 'pwanted'@'localhost';
GRANT EXECUTE ON procedure `wanted`.`getWantedList` TO 'pwanted'@'localhost';
GRANT EXECUTE ON procedure `wanted`.`getWelcomeStatus` TO 'pwanted'@'localhost';
GRANT EXECUTE ON procedure `wanted`.`updateUserItems` TO 'pwanted'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE `wanted`.`detail` TO 'pwanted'@'localhost';
GRANT INSERT, SELECT, UPDATE, DELETE ON TABLE `wanted`.`item` TO 'pwanted'@'localhost';
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE `wanted`.`item_schema` TO 'pwanted'@'localhost';
GRANT INSERT, SELECT, UPDATE, DELETE ON TABLE `wanted`.`schema_history` TO 'pwanted'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE `wanted`.`steam_user` TO 'pwanted'@'localhost';
GRANT EXECUTE ON procedure `wanted`.`markDetailAsObtained` TO 'pwanted'@'localhost';
GRANT EXECUTE ON procedure `wanted`.`addDetailFromItemId` TO 'pwanted'@'localhost';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
