package model;

public class Account {
    private int account_id;
    private float rate;
    private String branch_name;
    private AccountType account_type;
    private String primary_owner_id;
    private boolean closed;
    private double balance;

    public Account(){

    }

    public Account(int id, float rate, String branch_name, AccountType type, String primary_owner_id, boolean closed, double balance){
        this.account_id=id;
        this.rate=rate;
        this.branch_name=branch_name;
        this.account_type=type;
        this.primary_owner_id=primary_owner_id;
        this.closed=closed;
        this.balance=balance;
    }

    public int getID(){return account_id;}
    public float getRate(){return rate;}
    public String getBranchname(){return branch_name;}
    public AccountType getType() { return account_type; }
    public String getPrimaryOwner(){return primary_owner_id;}
    public boolean isClosed(){return closed;}
    public double getBalance(){return balance;}

    public void setId(int id){this.account_id=id;}
    public void setRate(float rate){this.rate=rate;}
    public void setBranch_name(String name){branch_name=name;}
    public void setType(AccountType type){this.account_type=type;}
    public void setPrimaryOwner(String customer_Id){primary_owner_id=customer_Id;}
    public void setClosed(boolean closed){this.closed=closed;}
    public void setBalance(double balance){this.balance=balance;}
}
