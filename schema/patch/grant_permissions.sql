SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';


USE `wanted` ;

update detail set detail.priority = 0 where detail.priority is null;
ALTER TABLE `wanted`.`detail` ALTER `priority` SET DEFAULT 0;

-- -----------------------------------------------------
-- procedure increaseDetailPriority
-- -----------------------------------------------------

USE `wanted`;
DROP procedure IF EXISTS `wanted`.`increaseDetailPriority`;

DELIMITER $$
USE `wanted`$$
CREATE PROCEDURE `increaseDetailPriority` (steam_id bigint, detail_id bigint, out updated_row_count bigint)
SQL SECURITY INVOKER
BEGIN
	update detail join item on item.wanted_id = detail.item_wanted_id set detail.priority = detail.priority +1 where item.steam_user_steam_id = steam_id and detail.detail_id = detail_id;
	select ROW_COUNT() into updated_row_count;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure decreaseDetailPriority
-- -----------------------------------------------------

USE `wanted`;
DROP procedure IF EXISTS `wanted`.`decreaseDetailPriority`;

DELIMITER $$
USE `wanted`$$
CREATE PROCEDURE `decreaseDetailPriority` (steam_id bigint, detail_id bigint, out updated_row_count bigint)
SQL SECURITY INVOKER
BEGIN
	update detail join item on item.wanted_id = detail.item_wanted_id set detail.priority = detail.priority -1 where item.steam_user_steam_id = steam_id and detail.detail_id = detail_id;
	select ROW_COUNT() into updated_row_count;
END$$

DELIMITER ;
SET SQL_MODE = '';

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
GRANT EXECUTE ON procedure `wanted`.`decreaseDetailPriority` TO 'pwanted'@'localhost';
GRANT EXECUTE ON procedure `wanted`.`increaseDetailPriority` TO 'pwanted'@'localhost';
