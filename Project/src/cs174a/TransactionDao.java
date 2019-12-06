package cs174a;

import model.Account;
import model.Transaction;
import model.TransactionType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class TransactionDao {
    private DBExecutor dbExecutor;

    public TransactionDao(){
        dbExecutor = new DBExecutor();
    }

    public boolean addTransaction(Transaction transaction){
        // Need to translate the date in java to oracle
        java.util.Date sysDate = transaction.getTime();
        java.sql.Date date = new java.sql.Date(sysDate.getTime());
        String sql = "INSERT INTO transactions VALUES(?,?,?,?,?,?,?,?,?)";
        Object[] objects = {IDCreator.getNextId(), transaction.getType().toString(), date, transaction.getAmount(), transaction.getCustomer_id(), transaction.getFrom_id(), transaction.getTo_id(), transaction.getFee(), transaction.getCheck_number()};
        return dbExecutor.runUpdate(sql, objects);
    }

    private Transaction[] doQueryAccountsSQL(String sql){
        List<Map<String, Object>> transactionList = dbExecutor.query(sql);
        if(transactionList == null || transactionList.size() == 0)
            return null;
        Transaction[] transactions = new Transaction[transactionList.size()];
        for (int i = 0; i < transactions.length; i++) {
            Transaction transaction = new Transaction();
            transaction.setId((Integer) transactionList.get(i).get("TRANSACTION_ID"));
            transaction.setType(TransactionType.valueOf((String) transactionList.get(i).get("TRANSACTION_TYPE")));
            transaction.setTime((Date) transactionList.get(i).get("TIME"));
            transaction.setAmount((Double) transactionList.get(i).get("AMOUNT"));
            transaction.setCustomer_id((String) transactionList.get(i).get("CUSTOMER_ID"));
            transaction.setFrom_id((String) transactionList.get(i).get("FROM_ID"));
            transaction.setTo_id((String) transactionList.get(i).get("TO_ID"));
            transaction.setFee((Double)transactionList.get(i).get("FEE"));
            transaction.setCheck_number((String) transactionList.get(i).get("CHECK_NUMBER"));
            transactions[i] = transaction;
        }
        return transactions;
    }

    public Transaction[] queryTransactionsByCheckNumber(String checkNumber){
        String sql = "SELECT * FROM Transactions WHERE check_number = " + checkNumber;
        return doQueryAccountsSQL(sql);
    }

    public Transaction[] queryTransactionsByPrimaryOwner(String customer_id){
        String view = "(SELECT Own.account_id FROM Own WHERE tax_id = " + customer_id;
        String sql = "SELECT * FROM Transactions WHERE from_id in " + view + " OR to_id in " + view;
        return doQueryAccountsSQL(sql);
    }

    public Transaction[] queryTransactionsDuringCurrentMonth(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String date = sdf.format(DateDao.getCurrentDate());
        String sql = "SELECT * FROM Transactions WHERE TO_CHAR(time,'yyyy-MM') = " + date;
        return doQueryAccountsSQL(sql);
    }

    public Transaction[] queryTransactionsDuringCurrentMonthWithAccountId(String accountId){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String date = sdf.format(DateDao.getCurrentDate());
        String sql = "SELECT * FROM Transactions WHERE (from_id = " + accountId + " OR to_id = " + accountId + ") AND TO_CHAR(time,'yyyy-MM') = " + date;
        return doQueryAccountsSQL(sql);
    }

    public Transaction[] queryTransactionsByAccountId(String  accountId){
        String sql = "SELECT * FROM transactions WHERE from_id=" + accountId + " OR to_id=" + accountId;
        return doQueryAccountsSQL(sql);
    }


    public boolean deleteTransactionsByCustomerId(String customerId){
        String sql = "DELETE FROM Transactions WHERE customer_id = " + customerId;
        return dbExecutor.runUpdate(sql);
    }

    public boolean deleteAllTransactions(){
        String sql = "DELETE FROM Transactions";
        return dbExecutor.runUpdate(sql);
    }

    public Transaction[] queryAll(){
        String sql = "SELECT * FROM Transactions";
        return doQueryAccountsSQL(sql);
    }

    public static void main(String[] args) {

    }
}
