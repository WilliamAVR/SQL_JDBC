import Database.DatabaseManager;
import Database.DatabaseUtil;
import Facades.CustomerFacade;
import Factory.CompanyFactory;
import Factory.CouponFactory;
import Factory.CustomerFactory;
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
        DatabaseManager.deleteSchema();
        DatabaseManager.createSchema();
        DatabaseManager.createTables();
        DatabaseManager.prepareCategories();
        CompanyDAO companyDAO = new CompanyDAOImp();
        CompanyFactory.initCompanyList(1,5).forEach(companyDAO::add);
        CustomerDAO customerDAO = new CustomerDAOImp();
        CustomerFactory.initCustomerList(1,20).forEach(customerDAO::add);
        CouponDAO couponDAO = new CouponDAOImp();
        companyDAO.retrieveAll().forEach(company -> CouponFactory.initCouponList(1,9,company.getId()).forEach(couponDAO::add));
        CvCDAO cvCDAO = new CvCDAOImp();
        cvCDAO.buyCoupon(customerDAO.retrieve(15).getId(),couponDAO.retrieve(20));
        cvCDAO.buyCoupon(2,couponDAO.retrieve(20));
        CustomerFacade customerFacade = CustomerFacade.login("a","a");
        customerFacade.updatePassword("blahblah");
    }
}
