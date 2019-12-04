package model;

import java.util.Date;

public class Transaction {
    private int transaction_id;
    private TransactionType transaction_type;
    private Date time;
    private double amount;
    private String customer_id;
    private String from_id;
    private String to_id;
    private double fee;
    private String check_number;

    public Transaction(){

    }

    public Transaction(int id, TransactionType type, Date time, double amount, String customer_id, String  from_id, String  to_id, double fee, String check_number){
        this.transaction_id = id;
        this.transaction_type = type;
        this.time = time;
        this.amount = amount;
        this.customer_id = customer_id;
        this.from_id = from_id;
        this.to_id = to_id;
        this.fee = fee;
        this.check_number = check_number;
    }

    public void setId(int id){
        this.transaction_id = id;
    }

    public void setType(TransactionType type){
        this.transaction_type = type;
    }

    public void setTime(Date time){ this.time = time; }

    public void setAmount(double amount){
        this.amount = amount;
    }

    public void setCustomer_id(String customer_id){this.customer_id = customer_id;};

    public void setFrom_id(String  from_id){this.from_id = from_id;}

    public void setTo_id(String to_id){this.to_id = to_id;}

    public void setFee(double fee){this.fee=fee;}

    public void setCheck_number(String check_number){this.check_number=check_number;}




    public int getId(){return this.transaction_id; }

    public TransactionType getType(){
        return this.transaction_type;
    }

    public Date getTime(){
        return this.time;
    }

    public double getAmount(){
        return this.amount;
    }

    public String getCustomer_id(){ return this.customer_id;};

    public String  getFrom_id(){return from_id;}

    public String  getTo_id(){return to_id;}

    public double getFee(){return fee;}

    public String  getCheck_number(){return check_number;}

}
