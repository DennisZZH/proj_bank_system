package model;

import java.util.Date;

public class Transaction {
    private String id;
    private TransactionType type;
    private double amount;
    private Date time;
    private Boolean isClosed;

    public Transaction(String id, TransactionType type, double amount, Date time, Boolean isClosed){
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.time = time;
        this.isClosed = isClosed;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setType(TransactionType type){
        this.type = type;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }

    public void setTime(Date time){
        this.time = time;
    }

    public void setIsClosed(Boolean isClosed){
        this.isClosed = isClosed;
    }

    public String getId(){
        return this.id;
    }

    public TransactionType getType(){
        return this.type;
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
