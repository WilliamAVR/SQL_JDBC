package access.customer_access;

import access.ObjectAccess;
import beans.Customer;

public interface CustomerDAO extends ObjectAccess<Customer> {
    public Customer loginCustomer(String email,String password) throws Exception;
}
