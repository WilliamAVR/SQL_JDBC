package Factory;

import beans.Category;
import beans.Coupon;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CouponFactory {
    public static final String DEFAULT_PLACEHOLDER = "placeholder";
    public static final Category DEFAULT_CATEGORY = Category.FOOD;
    public static final int DEFAULT_AMOUNT = 500;
    public static  final int DEFAULT_PRICE = 0;
    public static final Date DEFAULT_START_DATE = Date.valueOf(LocalDate.now());
    public static final Date DEFAULT_END_DATE = Date.valueOf(LocalDate.now().plusDays(14));

    public static Coupon initCoupon(int num, int companyID){
        return Coupon.builder()
                .title(String.format("Coupon %d",num))
                .description(DEFAULT_PLACEHOLDER)
                .image(DEFAULT_PLACEHOLDER)
                .category(DEFAULT_CATEGORY)
                .companyID(companyID)
                .amount(DEFAULT_AMOUNT)
                .price(DEFAULT_PRICE)
                .startDate(DEFAULT_START_DATE)
                .endDate(DEFAULT_END_DATE)
                .build();
    }
    public static List<Coupon> initCouponList(int startingPoint, int length, int companyID){
        List<Coupon> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            list.add(initCoupon((startingPoint+i),companyID));
        }
        return list;
    }
}
