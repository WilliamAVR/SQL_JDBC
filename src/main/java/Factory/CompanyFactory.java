package Factory;

import beans.Company;

import java.util.ArrayList;
import java.util.List;

public class CompanyFactory {
    public static Company initCompany(int n){
        return Company.builder()
                .name("Company")
                .email(String.format("company%d@sample.com",n))
                .password("password")
                .build();
    }
    public static List<Company> initCompanyList(int startingPoint,int length){
        List<Company> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            list.add(initCompany(startingPoint+i));
        }
        return list;
    }
}
