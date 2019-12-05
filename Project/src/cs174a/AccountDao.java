package cs174a;

import model.Account;
import model.AccountType;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public class AccountDao {

    private DBExecutor dbExecutor;

    public AccountDao(){
        dbExecutor = new DBExecutor();
    }

    public boolean addAcount(Account account){
        String sql = "INSERT INTO Accounts VALUES(?,?,?,?,?,?,?,?)";

        Object[] objects = {account.getId(), account.getType().toString(), Double.toString(account.getBalance()), account.getPrimaryOwner(), Double.toString(account.getRate()),
                                                Integer.toString(account.isClosed()), account.getLinkedID(), account.getBranchName()};
        return dbExecutor.runUpdate(sql, objects);
    }

    private Account[] doQueryAccountsSQL(String sql){
        List<Map<String, Object>> accountList = dbExecutor.query(sql);
        if(accountList == null || accountList.size() == 0)
            return null;
        Account[] accounts = new Account[accountList.size()];
        for (int i = 0; i < accounts.length; i++) {
            Account account = new Account();
            account.setId((String) accountList.get(i).get("ACCOUNT_ID"));
            account.setType(AccountType.translate((String) accountList.get(i).get("ACCOUNT_TYPE")));
            account.setBalance( ( (BigDecimal) accountList.get(0).get("BALANCE") ).doubleValue());
            account.setPrimaryOwner((String) accountList.get(0).get("PRIMARY_OWNER_ID"));
            account.setRate(( (BigDecimal) accountList.get(0).get("RATE") ).doubleValue());
            account.setIsClosed(( (BigDecimal) accountList.get(0).get("ISCLOSED") ).intValue());
            account.setLinkedID((String) accountList.get(i).get("LINKED_ACCOUNT_ID"));
            account.setBranch_name((String) accountList.get(i).get("BRANCH_NAME"));

            accounts[i] = account;
        }
        return accounts;
    }

    public Account queryAccountById(String id){
        String sql = "SELECT * FROM Accounts WHERE account_id = " + id;
        List<Map<String, Object>> accountList = dbExecutor.query(sql);
        if(accountList == null || accountList.size() == 0)
            return null;
        Account account = new Account();
        account.setId(id);
        account.setType(AccountType.translate((String) accountList.get(0).get("ACCOUNT_TYPE")));
        account.setBalance( ( (BigDecimal) accountList.get(0).get("BALANCE") ).doubleValue());
        account.setPrimaryOwner((String) accountList.get(0).get("PRIMARY_OWNER_ID"));
        account.setRate(( (BigDecimal) accountList.get(0).get("RATE") ).doubleValue());
        account.setIsClosed(( (BigDecimal) accountList.get(0).get("ISCLOSED") ).intValue());
        account.setLinkedID((String) accountList.get(0).get("LINKED_ACCOUNT_ID"));
        account.setBranch_name((String) accountList.get(0).get("BRANCH_NAME"));
        return account;
    }

    public Account[] queryAccountByCustomerId(String customerId){
        String sql = "SELECT * FROM Accounts WHERE account_id in (SELECT Own.account_id FROM Own WHERE tax_id = " + customerId + ")";
        return doQueryAccountsSQL(sql);
    }

    public Account[] queryPrimaryAccountByCustomerId(String customerId){
        String sql = "SELECT * FROM Accounts WHERE primary_owner_id = " + customerId;
        return doQueryAccountsSQL(sql);
    }

    public Account[] queryClosedAccount(){
        String sql = "SELECT * FROM Accounts WHERE isClosed = 1";
        return doQueryAccountsSQL(sql);
    }

    public Account[] queryInterestAccount(){
        String sql = "SELECT * FROM Accounts WHERE (account_type = 'INTEREST_CHECKING_ACCOUNT' OR account_type = 'SAVING_ACCOUNT') AND isClosed = 0";
        return doQueryAccountsSQL(sql);
    }

    public boolean deleteAllClosedAccount(){
        String sql = "DELETE FROM Accounts WHERE isClosed = 1";
        return dbExecutor.runUpdate(sql);
    }

    public boolean updateBalance(String accountId, Double newBalance){
        String sql = "UPDATE Accounts SET balance = '" + Double.toString(newBalance) + "' WHERE account_id = '" + accountId + "'";
        return dbExecutor.runUpdate(sql);
    }

    public boolean updateBranchName(String branch_name, String accountId){
        String sql = "UPDATE Accounts SET branch_name = " + "'" + branch_name +"'" + " WHERE account_id = " + "'" + accountId + "'";
        return dbExecutor.runUpdate(sql);
    }

    public boolean updateState(String accountId, int isClosed){
        String sql = "UPDATE Accounts SET isClosed = '" + isClosed + "' WHERE account_id = '" + accountId + "'";
        return dbExecutor.runUpdate(sql);
    }

    public Account[] queryAll(){
        String sql = "SELECT * FROM accounts";
        return doQueryAccountsSQL(sql);
    }

    public static void main(String[] args) {

    }

}
