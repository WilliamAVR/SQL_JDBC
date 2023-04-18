package Factory;

import beans.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerFactory {
    public static Customer initCustomer(){
        return Customer.builder()
                .firstName("first")
                .lastName("last")
                .email("customer@sample.com")
                .password("password")
                .build();
    }
    public static List<Customer> initCustomerList(int length){
        List<Customer> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            list.add(initCustomer());
        }
        return list;
    }
}
