package access.customer_access;

import Database.DatabaseUtil;
import beans.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDAOImp implements CustomerDAO {
    private static final String ADD_CUSTOMER = "INSERT INTO `coupon_system_database`.`customers` (`first_name`, `last_name`, `email`, `password`) VALUES (?, ?, ?, ?);";
    private static final String UPDATE_CUSTOMER = "UPDATE `coupon_system_database`.`customers` SET `first_name` = ?, `last_name` = ?, `email` = ?, `password` = ? WHERE (`id` = ?);";
    private static final String DELETE_CUSTOMER = "DELETE FROM `coupon_system_database`.`customers` WHERE (`id` = ?);";
    private static final String GET_CUSTOMER = "SELECT * FROM `coupon_system_database`.`customers` WHERE (`id` = ?);";
    private static final String GET_ALL_CUSTOMERS = "SELECT * FROM `coupon_system_database`.`customers`;";
    private static final String CUSTOMER_EXISTS = "SELECT EXISTS (SELECT * FROM `coupon_system_database`.`customers` WHERE (`id` = ?)) as `result`;";
    private static final String CUSTOMER_VERIFY = "SELECT EXISTS (SElECT * FROM coupon_system_database.customers WHERE `email` = ? AND `password` = ?) as `result`;";
    private static final String CUSTOMER_LOGIN = "SELECT * FROM coupon_system_database.customers WHERE `email` = ? AND `password` = ?;";

    public CustomerDAOImp() {
    }

    @Override
    public void add(Customer customer) {
        Map<Integer, Object> map = setMapAdd(customer);
        DatabaseUtil.runStatement(ADD_CUSTOMER, map);
    }

    @Override
    public void update(Customer customer, int id) {
        Map<Integer, Object> map = setMapUpdate(customer, id);
        DatabaseUtil.runStatement(UPDATE_CUSTOMER, map);
    }

    @Override
    public void delete(int id) {
        Map<Integer, Object> map = setMap(id);
        DatabaseUtil.runStatement(DELETE_CUSTOMER, map);
    }

    @Override
    public Customer retrieve(int id) {
        try {
            Map<Integer, Object> map = setMap(id);
            List<?> list = DatabaseUtil.runQuery(GET_CUSTOMER, map);
            return customerFromMap((Map<String, Object>) list.get(0));
        }catch (Exception e){
            System.out.println("unable to retrieve object: ID not registered");
            return null;
        }
    }

    @Override
    public List<Customer> retrieveAll() {
        List<Customer> customers = new ArrayList<>();
        List<?> list = DatabaseUtil.runQuery(GET_ALL_CUSTOMERS);
        list.stream().map(obj -> customerFromMap((Map<String, Object>) obj)).forEach(customers::add);
        return customers;
    }

    @Override
    public boolean exists(int id) {
        List<?> list = DatabaseUtil.runQuery(CUSTOMER_EXISTS, setMap(id));
        Map<String, Object> map = (Map<String, Object>) list.get(0);
        return ((Long) (map.get("result")) == 1);
    }
    @Override
    public Customer loginCustomer(String email, String password) throws Exception {
        List<?> list = DatabaseUtil.runQuery(CUSTOMER_LOGIN, setMapLogin(email,password));
        if(list.isEmpty()){
            throw new Exception("incorrect email or password");
        }
        Map<String, Object> map = (Map<String, Object>) list.get(0);
        return customerFromMap(map);
    }
    public Map<Integer,Object> setMapLogin(String email,String password){
        Map<Integer,Object> map = new HashMap<>();
        map.put(1,email);
        map.put(2,password);
        return map;
    }
    @Override
    public Map<Integer, Object> setMapAdd(Customer customer) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customer.getFirstName());
        map.put(2, customer.getLastName());
        map.put(3, customer.getEmail());
        map.put(4, customer.getPassword());
        return map;
    }

    @Override
    public Map<Integer, Object> setMapUpdate(Customer customer, int id) {
        Map<Integer, Object> map = setMapAdd(customer);
        map.put(5, id);
        return map;
    }

    @Override
    public Map<Integer, Object> setMap(int id) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, id);
        return map;
    }

    private Customer customerFromMap(Map<String, Object> map) {
        return Customer.builder()
                .firstName((String) map.get("first_name"))
                .lastName((String) map.get("last_name"))
                .password((String) map.get("password"))
                .email((String) map.get("email"))
                .id((Integer) map.get("id"))
                .build();
    }

}
