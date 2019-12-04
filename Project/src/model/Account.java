package model;


public class Account {

    private String account_id;
    private AccountType account_type;
    private double balance;
    private String primary_owner_id;
    private double rate;
    private int isClosed;
    private  String linked_account_id;
    private String branch_name;

    public Account(){

    }

    public Account(String id, AccountType type, double balance, String primary_owner_id, double rate, int isClosed, String linked_account_id, String branch_name){
        this.account_id=id;
        this.account_type=type;
        this.balance=balance;
        this.primary_owner_id=primary_owner_id;
        this.rate=rate;
        this.isClosed=isClosed;
        this.linked_account_id = linked_account_id;
        this.branch_name=branch_name;
    }


    public String getId(){return this.account_id;}
    public AccountType getType() { return this.account_type; }
    public double getBalance(){return this.balance;}
    public String getPrimaryOwner(){return this.primary_owner_id;}
    public double getRate(){return this.rate;}
    public int isClosed(){return this.isClosed;}
    public String getLinkedID(){return this.linked_account_id;};
    public String getBranchName(){return this.branch_name;}


    public void setId(String id){this.account_id=id;}
    public void setType(AccountType type){this.account_type=type;}
    public void setBalance(double balance){this.balance=balance;}
    public void setPrimaryOwner(String customer_Id){primary_owner_id=customer_Id;}
    public void setRate(double rate){this.rate=rate;}
    public void setIsClosed(int isClosed){this.isClosed=isClosed;}
    public void setLinkedID(String linkedID){this.linked_account_id = linkedID;};
    public void setBranch_name(String name){branch_name=name;}


}
