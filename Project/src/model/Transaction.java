package model;

import java.util.Date;

public class Transaction {
    private int transaction_id;
    private TransactionType transaction_type;
    private double amount;
    private Date time;
    private Boolean isClosed;

    private int from;
    private int to;
    private double fee;
    private int check_number;

    public Transaction(int id, TransactionType type, double amount, Date time, Boolean isClosed,int from,int to,double fee, int check_number){
        this.transaction_id = id;
        this.transaction_type = type;
        this.amount = amount;
        this.time = time;
        this.isClosed = isClosed;
        this.from=from;
        this.to=to;
        this.fee=fee;
        this.check_number= check_number;
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

    public void setFrom(int from){this.from=from;}

    public void setTo(int to){this.to=to;}

    public void setFee(double fee){this.fee=fee;}

    public void setCheck_number(int check_number){this.check_number=check_number;}




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

    public int getFrom(){return from;}

    public int getTo(){return to;}

    public double getFee(){return fee;}

    public int getCheck_number(){return check_number;}

}
