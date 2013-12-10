SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

USE `wanted` ;

DROP procedure IF EXISTS `wanted`.`increasePriority`;
DROP procedure IF EXISTS `wanted`.`decreasePriority`;
-- -----------------------------------------------------
-- procedure setDetailPriority
-- -----------------------------------------------------

USE `wanted`;

DROP procedure IF EXISTS `wanted`.`setDetailPriority`;

DELIMITER $$
USE `wanted`$$
CREATE PROCEDURE `setDetailPriority` (steam_id bigint, detail_id bigint, priority bigint, out updated_row_count bigint)
SQL SECURITY INVOKER
BEGIN
  update detail as d join item on item.wanted_id = d.item_wanted_id set d.priority = priority where item.steam_user_steam_id = steam_id and d.detail_id = detail_id;
  select ROW_COUNT() into updated_row_count;
END$$

DELIMITER ;
SET SQL_MODE = '';

GRANT EXECUTE ON procedure `wanted`.`setDetailPriority` TO 'pwanted'@'localhost';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
