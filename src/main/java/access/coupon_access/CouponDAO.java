package access.coupon_access;

import access.ObjectAccess;
import beans.Coupon;

import java.sql.Date;
import java.util.List;

public interface CouponDAO extends ObjectAccess<Coupon> {
    public List<Coupon> getCompanyCoupons(int companyID);
    public List<Coupon> findCouponsByDate(Date date);
}
