package model;

public class Account {
    private int id;
    private float rate;
    private String branch_name;
    private AccountType type;
    private String primary_owner_id;
    private boolean closed;
    //private float balance;

    public Account(){

    }

    public int getID(){return id;}
    public float getRate(){return rate;}
    public String getBranchname(){return branch_name;}
    public AccountType getType() { return type; }
    public String getPrimaryOwner(){return primary_owner_id;}
    public boolean isClosed(){return closed;}
    //public float getBalance(){return balance;}

    public void setId(int id){this.id=id;}
    public void setRate(float rate){this.rate=rate;}
    public void setBranch_name(String name){branch_name=name;}
    public void setType(AccountType type){this.type=type;}
    public void setPrimaryOwner(String customer_Id){primary_owner_id=customer_Id;}
    public void setClosed(boolean closed){this.closed=closed;}
    //public void setBalance(float balance){this.balance=balance;}
}
