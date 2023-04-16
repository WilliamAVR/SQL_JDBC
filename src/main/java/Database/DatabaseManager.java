package Database;

import beans.Category;

import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {
    private static final String CREATE_SCHEMA = "CREATE SCHEMA `coupon_system_database` ;";
    private static final String DELETE_SCHEMA = "DROP SCHEMA `coupon_system_database` ;";
    private static final String CREATE_TABLE_COMPANIES = "CREATE TABLE `coupon_system_database`.`companies` (`id` INT NOT NULL AUTO_INCREMENT,`name` VARCHAR(255) NOT NULL,`email` VARCHAR(255) NOT NULL,`password` VARCHAR(255) NOT NULL, PRIMARY KEY (`id`));";
    private static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE `coupon_system_database`.`customers` (`id` INT NOT NULL AUTO_INCREMENT,`first_name` VARCHAR(255) NOT NULL,`last_name` VARCHAR(255) NOT NULL,`email` VARCHAR(255) NOT NULL,`password` VARCHAR(255) NOT NULL,PRIMARY KEY (`id`));";
    private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE `coupon_system_database`.`categories` (`id` INT NOT NULL AUTO_INCREMENT,`name` VARCHAR(255) NOT NULL,PRIMARY KEY (`id`));";
    private static final String INSERT_INTO_TABLE_CATEGORIES = "INSERT INTO  `coupon_system_database`.`categories` (`name`) VALUES(?)";
    private static final String CREATE_TABLE_COUPONS = "CREATE TABLE `coupon_system_database`.`coupons` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `COMPANY_ID` INT NOT NULL,\n" +
            "  `CATEGORY_ID` INT NOT NULL,\n" +
            "  `title` VARCHAR(255) NOT NULL,\n" +
            "  `description` VARCHAR(255) NOT NULL,\n" +
            "  `start_date` DATE NOT NULL,\n" +
            "  `end_date` DATE NOT NULL,\n" +
            "  `amount` INT NOT NULL,\n" +
            "  `price` DOUBLE NOT NULL,\n" +
            "  `image` VARCHAR(255) NOT NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  INDEX `COMPANY_ID_idx` (`COMPANY_ID` ASC) VISIBLE,\n" +
            "  INDEX `CATEGORY_ID_idx` (`CATEGORY_ID` ASC) VISIBLE,\n" +
            "  CONSTRAINT `COMPANY_ID`\n" +
            "    FOREIGN KEY (`COMPANY_ID`)\n" +
            "    REFERENCES `coupon_system_database`.`companies` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `CATEGORY_ID`\n" +
            "    FOREIGN KEY (`CATEGORY_ID`)\n" +
            "    REFERENCES `coupon_system_database`.`categories` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);";
    private static final String CREATE_TABLE_CUSTOMERS_VS_COUPONS = "CREATE TABLE `coupon_system_database`.`customers_vs_coupons` (\n" +
            "  `customer_id` INT NOT NULL,\n" +
            "  `coupon_id` INT NOT NULL,\n" +
            "  PRIMARY KEY (`customer_id`, `coupon_id`),\n" +
            "  INDEX `coupon_id_idx` (`coupon_id` ASC) VISIBLE,\n" +
            "  CONSTRAINT `customer_id`\n" +
            "    FOREIGN KEY (`customer_id`)\n" +
            "    REFERENCES `coupon_system_database`.`customers` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `coupon_id`\n" +
            "    FOREIGN KEY (`coupon_id`)\n" +
            "    REFERENCES `coupon_system_database`.`coupons` (`id`)\n" +
            "    ON DELETE CASCADE\n" +
            "    ON UPDATE NO ACTION);";
    public static void createSchema(){
        DatabaseUtil.runStatement(CREATE_SCHEMA);
    }
    public static void deleteSchema(){
        DatabaseUtil.runStatement(DELETE_SCHEMA);
    }
    public static void createTables(){
        DatabaseUtil.runStatement(CREATE_TABLE_CATEGORIES);
        DatabaseUtil.runStatement(CREATE_TABLE_CUSTOMERS);
        DatabaseUtil.runStatement(CREATE_TABLE_COMPANIES);
        DatabaseUtil.runStatement(CREATE_TABLE_COUPONS);
        DatabaseUtil.runStatement(CREATE_TABLE_CUSTOMERS_VS_COUPONS);
    }
    public static void prepareCategories(){
        for (Category category:Category.values()) {
            Map<Integer,Object> map = new HashMap<>();
            map.put(1,category.name().toLowerCase());
            DatabaseUtil.runStatement(INSERT_INTO_TABLE_CATEGORIES,map);
        }
    }


}
