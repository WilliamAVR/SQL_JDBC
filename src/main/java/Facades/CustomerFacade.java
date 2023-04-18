package Facades;

import access.customer_access.CustomerDAO;
import access.customer_access.CustomerDAOImp;
import beans.Customer;

public class CustomerFacade extends Client{
    private static final CustomerDAO customerDAO = new CustomerDAOImp();
    private Customer customer = null;
    private CustomerFacade(Customer customer){
        this.customer = customer;
    }
    public static CustomerFacade login(String email,String password) {
        Customer login = null;
        try {
            login = customerDAO.loginCustomer(email,password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return new CustomerFacade(login);
    }
    public void updatePassword(String password){
        customer.setPassword(password);
        customerDAO.update(customer,customer.getId());
    }

}
