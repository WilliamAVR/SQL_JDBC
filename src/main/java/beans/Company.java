package beans;

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
    private List<Coupon> couponList;
}
