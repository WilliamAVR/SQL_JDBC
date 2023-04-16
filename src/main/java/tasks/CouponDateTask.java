package tasks;

import access.coupon_access.CouponDAO;
import access.coupon_access.CouponDAOImp;
import access.cvc_access.CvCDAO;
import access.cvc_access.CvCDAOImp;

public class CouponDateTask implements Runnable{
    @Override
    public void run() {
        CouponDAO couponDAO = new CouponDAOImp();
        while(true){

        }
    }
}
