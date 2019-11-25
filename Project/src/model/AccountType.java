package model;

public enum  AccountType {
    STUDENT_CHECKING_ACCOUNT, INTEREST_CHECKING_ACCOUNT, SAVING_ACCOUNT, POCKET_ACCOUNT;

    public static AccountType translate(String type){
        if(type.equals("STUDENT_CHECKING_ACCOUNT"))
            return STUDENT_CHECKING_ACCOUNT;
        if(type.equals("INTEREST_CHECKING_ACCOUNT"))
            return INTEREST_CHECKING_ACCOUNT;
        if(type.equals("SAVING_ACCOUNT"))
            return SAVING_ACCOUNT;
        if(type.equals("POCKET_ACCOUNT"))
            return POCKET_ACCOUNT;
        return null;
    }
}
