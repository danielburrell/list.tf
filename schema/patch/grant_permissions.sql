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
