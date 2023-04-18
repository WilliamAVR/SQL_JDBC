package Factory;

import beans.Company;

import java.util.ArrayList;
import java.util.List;

public class CompanyFactory {
    public static Company initCompany(){
        return Company.builder()
                .name("Company")
                .email("company@sample.com")
                .password("password")
                .build();
    }
    public static List<Company> initCompanyList(int length){
        List<Company> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            list.add(initCompany());
        }
        return list;
    }
}
