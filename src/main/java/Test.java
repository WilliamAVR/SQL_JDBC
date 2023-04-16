import Database.DatabaseManager;
import Database.DatabaseUtil;
import access.company_access.CompanyDAO;
import access.company_access.CompanyDAOImp;
import access.coupon_access.CouponDAO;
import access.coupon_access.CouponDAOImp;
import access.customer_access.CustomerDAO;
import access.customer_access.CustomerDAOImp;
import access.cvc_access.CvCDAO;
import access.cvc_access.CvCDAOImp;
import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        CouponDAO couponDAO = new CouponDAOImp();
        couponDAO.delete(2);
        CvCDAO cvCDAO = new CvCDAOImp();
        cvCDAO.getCustomerCoupons(1).forEach(System.out::println);

    }
}
