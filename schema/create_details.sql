CREATE TABLE `mydb`.`details` (
  `steamId` BIGINT NOT NULL COMMENT 'Uniquely identifies a user',
  `detailId` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Uniquely identifies the details of a wanted item',
  `itemId` BIGINT NOT NULL COMMENT 'Identifies the item, values are as per TF2 Item schema.',
  `quality` INT NOT NULL COMMENT 'Quality of the item (vintage etc)',
  `level` INT NOT NULL COMMENT 'Level of the item (1-200 typically)',
  `isTradable` TINYINT NOT NULL COMMENT 'Tradability of the item can be 0,1,2 for no, yes, don\'t care',
  `isCraftable` TINYINT NOT NULL COMMENT 'Craftability of the item can be 0,1,2 for no, yes, don\'t care',
  `craftNumber` BIGINT NOT NULL COMMENT 'Craft number of the item can be 0,1,2 for No craft number vs the craft number required',
  `isGiftWrapped` TINYINT NOT NULL COMMENT 'Giftwrap state of the item can be 0,1,2 for no, yes, don\'t care',
  `isObtained` BIT NOT NULL COMMENT 'Obtained state of the item can be 0 or 1 for not obtained or obtained',
  `priority` INT NULL COMMENT 'priority given to the item (for consideration when sorting)',
  `price` VARCHAR(45) NULL COMMENT 'price of the item',
  PRIMARY KEY (`detailId`),
  INDEX `DETAILID_STEAMID` (`detailId` ASC, `steamId` ASC),
  INDEX `STEAMID` (`steamId` ASC))
COMMENT = 'Links users to items they want';
