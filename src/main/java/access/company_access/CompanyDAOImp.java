package access.company_access;

import Database.DatabaseUtil;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompanyDAOImp implements CompanyDAO {
    private static final String ADD_COMPANY = "INSERT INTO `coupon_system_database`.`companies` (`name`, `email`, `password`) VALUES (?, ?, ?);";
    private static final String UPDATE_COMPANY = "UPDATE `coupon_system_database`.`companies` SET `name` = ?, `email` = ?, `password` = ? WHERE (`id` = ?);";
    private static final String DELETE_COMPANY = "DELETE FROM `coupon_system_database`.`companies` WHERE (`id` = ?);";
    private static final String GET_COMPANY = "SELECT * FROM `coupon_system_database`.`companies` WHERE (`id` = ?);";
    private static final String GET_ALL_COMPANIES = "SELECT * FROM `coupon_system_database`.`companies`;";
    private static final String COMPANY_EXISTS = "SELECT EXISTS (SELECT * FROM `coupon_system_database`.`companies` WHERE (`id` = ?)) as `result`;";


    public CompanyDAOImp() {
    }

    @Override
    public void add(Company company) {
        Map<Integer, Object> map = setMapAdd(company);
        DatabaseUtil.runStatement(ADD_COMPANY, map);
    }

    @Override
    public void update(Company company, int id) {
        Map<Integer, Object> map = setMapUpdate(company, id);
        DatabaseUtil.runStatement(UPDATE_COMPANY, map);
    }

    @Override
    public void delete(int id) {
        Map<Integer, Object> map = setMap(id);
        DatabaseUtil.runStatement(DELETE_COMPANY, map);
    }

    @Override
    public Company retrieve(int id) {
        try {
            Map<Integer, Object> map = setMap(id);
            List<?> list = DatabaseUtil.runQuery(GET_COMPANY, map);
            return companyFromMap((Map<String, Object>) list.get(0));
        }catch (Exception e){
            System.out.println("unable to retrieve object: ID not registered");
            return null;
        }

    }

    @Override
    public List<Company> retrieveAll() {
        List<Company> companies = new ArrayList<>();
        List<?> list = DatabaseUtil.runQuery(GET_ALL_COMPANIES);
        list.stream().map(obj -> companyFromMap((Map<String, Object>) obj)).forEach(companies::add);
        return companies;
    }

    @Override
    public boolean exists(int id) {
        Map<Integer, Object> map = setMap(id);
        List<?> list = DatabaseUtil.runQuery(COMPANY_EXISTS, map);
        return (long)(((Map<String, Object>) list.get(0)).get("result")) == 1;
    }

    @Override
    public Map<Integer, Object> setMapAdd(Company company) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, company.getName());
        map.put(2, company.getEmail());
        map.put(3, company.getPassword());
        return map;
    }

    @Override
    public Map<Integer, Object> setMapUpdate(Company company, int id) {
        Map<Integer, Object> map = setMapAdd(company);
        map.put(4, id);
        return map;
    }

    @Override
    public Map<Integer, Object> setMap(int id) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, id);
        return map;
    }

    private Company companyFromMap(Map<String, Object> map) {
        return Company.builder()
                .name((String) map.get("name"))
                .password((String) map.get("password"))
                .email((String) map.get("email"))
                .id((Integer) map.get("id"))
                .build();
    }
}
