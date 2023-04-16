package access.cvc_access;

import Database.DatabaseUtil;
import access.coupon_access.CouponDAO;
import access.coupon_access.CouponDAOImp;
import beans.Coupon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CvCDAOImp implements CvCDAO {
    private static final String GET_CUSTOMER_COUPONS = "SELECT `coupon_id` FROM coupon_system_database.customers_vs_coupons where `customer_id` = ?;";
    private static final String USE_COUPON = "DELETE FROM `coupon_system_database`.`customers_vs_coupons` WHERE (`customer_id` = ?) and (`coupon_id` = ?);";
    private static final String REGISTER_COUPON = "INSERT INTO `coupon_system_database`.`customers_vs_coupons` (`customer_id`, `coupon_id`) VALUES (?, ?);";
    public CvCDAOImp() {
    }

    @Override
    public List<Coupon> getCustomerCoupons(int customerID) {
        List<Integer> couponIDs = new ArrayList<>();
        Map<Integer, Object> map = setMap(customerID);
        List<?> list = DatabaseUtil.runQuery(GET_CUSTOMER_COUPONS, map);
        list.stream().map(obj ->getCouponID((Map<String,Object>)obj)).forEach(couponIDs::add);
        CouponDAO couponDAO = new CouponDAOImp();
        List<Coupon> couponList = new ArrayList<>();
        couponIDs.stream().map(id -> couponDAO.retrieve(id)).forEach(couponList::add);
        return couponList;
    }

    @Override
    public void useCustomerCoupon(int customerID, int couponID) {
        Map<Integer,Object> map = setMapUse(customerID,couponID);
        DatabaseUtil.runStatement(USE_COUPON,map);
    }

    @Override
    public void buyCoupon(int customerID, Coupon coupon) {
        if(coupon.getAmount()>0) {
            Map<Integer, Object> map = setMapUse(customerID, coupon.getId());
            DatabaseUtil.runStatement(REGISTER_COUPON, map);
            CouponDAO couponDAO = new CouponDAOImp();
            coupon.setAmount(coupon.getAmount() - 1);
            couponDAO.update(coupon, coupon.getId());
            return;
        }
        System.out.println("there are no coupons left");

    }
    public Map<Integer, Object> setMap(int id) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, id);
        return map;
    }
    public Map<Integer, Object> setMapUse(int customerID,int couponID) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerID);
        map.put(2, couponID);
        return map;
    }
    private int getCouponID(Map<String,Object> map){
        return (Integer)map.get("coupon_id");
    }
}
