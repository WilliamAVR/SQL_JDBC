package access.cvc_access;

import beans.Coupon;

import java.util.List;

public interface CvCDAO {
    List<Coupon> getCustomerCoupons(int customerID);
    void useCustomerCoupon(int customerID,int couponID);
    void buyCoupon(int customerCoupon,Coupon coupon);
}
