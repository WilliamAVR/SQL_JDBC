package access.coupon_access;

import Database.DatabaseUtil;
import beans.Category;
import beans.Company;
import beans.Coupon;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CouponDAOImp implements CouponDAO {
    private static final String ADD_COUPON = "INSERT INTO `coupon_system_database`.`coupons` (`COMPANY_ID`, `CATEGORY_ID`, `title`, `description`, `start_date`, `end_date`, `amount`, `price`, `image`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_COUPON = "UPDATE `coupon_system_database`.`coupons` SET (`COMPANY_ID` = ? , `CATEGORY_ID` = ?, `title` = ?, `description` = ?, `start_date` = ?, `end_date` = ?, `amount` = ?, `price` = ?, `image` = ?) WHERE (`id` = ?);";
    private static final String DELETE_COUPON = "DELETE FROM `coupon_system_database`.`coupons` WHERE (`id` = ?);";
    private static final String GET_ALL_COUPONS = "SELECT * FROM coupon_system_database.coupons;";
    private static final String GET_COUPON = "SELECT * FROM coupon_system_database.coupons WHERE (`id` = ?);";
    private static final String COUPON_EXISTS = "SELECT EXISTS ( SELECT * FROM coupon_system_database.coupons) AS `result`;";
    private static final String GET_ALL_COMPANY_COUPONS = "SELECT * FROM `coupon_system_database`.`coupons` where `COMPANY_ID` = ?;";
    private static final String GET_ALL_COUPONS_BY_DATE = "SELECT * FROM `coupon_system_database`.`coupons` where `COMPANY_ID` = ?;";

    public CouponDAOImp() {
    }

    @Override
    public void add(Coupon coupon) {
        Map<Integer, Object> map = setMapAdd(coupon);
        DatabaseUtil.runStatement(ADD_COUPON, map);
    }

    @Override
    public void update(Coupon coupon, int id) {
        Map<Integer, Object> map = setMapUpdate(coupon, id);
        DatabaseUtil.runStatement(UPDATE_COUPON, map);
    }

    @Override
    public void delete(int id) {
        Map<Integer, Object> map = setMap(id);
        DatabaseUtil.runStatement(DELETE_COUPON, map);
    }

    @Override
    public Coupon retrieve(int id) {
        try {
            Map<Integer, Object> map = setMap(id);
            List<?> list = DatabaseUtil.runQuery(GET_COUPON, map);
            return couponFromMap((Map<String, Object>) list.get(0));
        } catch (Exception e) {
            System.out.println("unable to retrieve object: ID not registered");
            return null;
        }
    }

    @Override
    public List<Coupon> retrieveAll() {
        List<Coupon> coupons = new ArrayList<>();
        List<?> list = DatabaseUtil.runQuery(GET_ALL_COUPONS);
        list.stream().map(obj -> couponFromMap((Map<String, Object>) obj)).forEach(coupons::add);
        return coupons;
    }

    @Override
    public List<Coupon> getCompanyCoupons(int companyID) {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> map = setMap(companyID);
        List<?> list = DatabaseUtil.runQuery(GET_ALL_COMPANY_COUPONS, map);
        list.stream().map(obj -> couponFromMap((Map<String, Object>) obj)).forEach(coupons::add);
        return coupons;
    }

    @Override
    public List<Coupon> findCouponsByDate(Date date) {
        return null;
    }


    @Override
    public boolean exists(int id) {
        List<?> list = DatabaseUtil.runQuery(COUPON_EXISTS, setMap(id));
        Map<String, Object> map = (Map<String, Object>) list.get(0);
        return ((Long) (map.get("result")) == 1);
    }

    @Override
    public Map<Integer, Object> setMapAdd(Coupon coupon) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, coupon.getCompanyID());
        map.put(2, coupon.getCategory().ordinal() + 1);
        map.put(3, coupon.getTitle());
        map.put(4, coupon.getDescription());
        map.put(5, coupon.getStartDate());
        map.put(6, coupon.getEndDate());
        map.put(7, coupon.getAmount());
        map.put(8, coupon.getPrice());
        map.put(9, coupon.getImage());
        return map;
    }

    @Override
    public Map<Integer, Object> setMapUpdate(Coupon coupon, int id) {
        Map<Integer, Object> map = setMapAdd(coupon);
        map.put(10, id);
        return map;
    }

    @Override
    public Map<Integer, Object> setMap(int id) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, id);
        return map;
    }

    private Coupon couponFromMap(Map<String, Object> map) {
        return Coupon.builder()
                .price((Double) map.get("price"))
                .companyID((Integer) map.get("COMPANY_ID"))
                .id((Integer) map.get("id"))
                .category(Category.categoryFromTable((Integer) map.get("CATEGORY_ID")))
                .title((String) map.get("title"))
                .startDate((Date) map.get("start_date"))
                .endDate((Date) map.get("end_date"))
                .amount((Integer) map.get("amount"))
                .description((String) map.get("description"))
                .image((String) map.get("image"))
                .price((Double) map.get("price"))
                .build();
    }


}
