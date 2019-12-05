package model;

public enum  AccountType {
    STUDENT_CHECKING,
    INTEREST_CHECKING,
    SAVINGS,
    POCKET;

    public static AccountType translate(String type){
        if(type.equals("STUDENT_CHECKING_ACCOUNT"))
            return STUDENT_CHECKING;
        if(type.equals("INTEREST_CHECKING_ACCOUNT"))
            return INTEREST_CHECKING;
        if(type.equals("SAVING_ACCOUNT"))
            return SAVINGS;
        if(type.equals("POCKET_ACCOUNT"))
            return POCKET;
        return null;
    }
}
