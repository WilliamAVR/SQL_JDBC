package Facades;

import access.customer_access.CustomerDAO;
import access.customer_access.CustomerDAOImp;

public class CustomerFacade extends Client{
    private static final CustomerDAO customerDAO = new CustomerDAOImp();
    @Override
    public Client login(String username, String password) {

    }
}
