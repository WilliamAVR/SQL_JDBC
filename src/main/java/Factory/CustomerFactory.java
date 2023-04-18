package Factory;

import beans.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerFactory {
    public static Customer initCustomer(int n){
        return Customer.builder()
                .firstName("first")
                .lastName("last")
                .email(String.format("customer%d@sample.com",n))
                .password("password")
                .build();
    }
    public static List<Customer> initCustomerList(int startingPoint,int length){
        List<Customer> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            list.add(initCustomer(startingPoint+i));
        }
        return list;
    }
}
