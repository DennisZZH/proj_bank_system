package model;

import java.util.Date;

public class Transaction {
    private int transaction_id;
    private TransactionType transaction_type;
    private double amount;
    private Date time;
    private Boolean isClosed;

    public Transaction(int id, TransactionType type, double amount, Date time, Boolean isClosed){
        this.transaction_id = id;
        this.transaction_type = type;
        this.amount = amount;
        this.time = time;
        this.isClosed = isClosed;
    }

    public void setId(int id){
        this.transaction_id = id;
    }

    public void setType(TransactionType type){
        this.transaction_type = type;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }

    public void setTime(Date time){ this.time = time; }

    public void setIsClosed(Boolean isClosed){ this.isClosed = isClosed; }

    public int getId(){return this.transaction_id; }

    public TransactionType getType(){
        return this.transaction_type;
    }

    public double getAmount(){
        return this.amount;
    }

    public Date getTime(){
        return this.time;
    }

    public Boolean getIsClosed(){
        return this.isClosed;
    }

}
