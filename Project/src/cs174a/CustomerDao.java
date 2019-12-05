package cs174a;

import model.Customer;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public class CustomerDao {
    private DBExecutor dbExecutor;

    public CustomerDao(){
        dbExecutor = new DBExecutor();
    }

    private Customer[] doQueryCustomersSQL(String sql){
        List<Map<String, Object>> customerList = dbExecutor.query(sql);
        if(customerList == null || customerList.size() == 0)
            return null;
        Customer[] customers = new Customer[customerList.size()];
        for (int i = 0; i < customers.length; i++) {
            Customer customer = new Customer();
            customer.setId((String) customerList.get(i).get("TAX_ID"));
            customer.setName((String) customerList.get(i).get("NAME"));
            customer.setAddress((String) customerList.get(i).get("ADDRESS"));
            customers[i] = customer;
        }
        return customers;
    }

    public Customer queryCustomerByTaxId(String tax_id){
        String sql = "SELECT * FROM Customers WHERE tax_id = " + tax_id;
        List<Map<String, Object>> customerList = dbExecutor.query(sql);
        if(customerList == null || customerList.size() == 0)
            return null;
        Customer customer = new Customer();
        customer.setId((String) customerList.get(0).get("TAX_ID"));
        customer.setName((String) customerList.get(0).get("NAME"));
        customer.setAddress((String) customerList.get(0).get("ADDRESS"));
        return customer;
    }

    public boolean addCustomer(Customer customer){
        String sql = "INSERT INTO customers VALUES(?,?,?)";
        Object[] objects = {customer.getId(), customer.getName(), customer.getAddress()};
        // Set initial pin
        PinDao pinDao = new PinDao();
        pinDao.initialPin(customer.getId());
        return dbExecutor.runUpdate(sql, objects);
    }

    public Customer[] queryCustomerByAccountId(String accountId){
        String sql = "SELECT * FROM Customers WHERE tax_id in (SELECT Own.tax_id FROM Own WHERE account_id = " + accountId;
        return doQueryCustomersSQL(sql);
    }


}
