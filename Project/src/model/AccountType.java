package model;

public enum  AccountType {
    STUDENT_CHECKING,
    INTEREST_CHECKING,
    SAVINGS,
    POCKET;

    public static AccountType translate(String type){
        if(type.equals("STUDENT_CHECKING"))
            return STUDENT_CHECKING;
        if(type.equals("INTEREST_CHECKING"))
            return INTEREST_CHECKING;
        if(type.equals("SAVING"))
            return SAVINGS;
        if(type.equals("POCKET"))
            return POCKET;
        return null;
    }
}
