package beans;

import access.cvc_access.CvCDAO;
import access.cvc_access.CvCDAOImp;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Customer {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;


    public List<Coupon> getCouponList() {
        CvCDAO cvCDAO = new CvCDAOImp();
        return cvCDAO.getCustomerCoupons(id);
    }
}
