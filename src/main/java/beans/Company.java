package beans;

import access.coupon_access.CouponDAO;
import access.coupon_access.CouponDAOImp;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Company {
    private int id;
    private String name;
    private String email;
    private String password;
    public List<Coupon> getCoupons(){
        CouponDAO couponDAO = new CouponDAOImp();
        return couponDAO.getCompanyCoupons(id);
    }
}
