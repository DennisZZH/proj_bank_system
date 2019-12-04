package cs174a;

public class OwnDao {

    private DBExecutor dbExecutor;

    public OwnDao(){
        dbExecutor = new DBExecutor();
    }

    public boolean addRelation(String customerid, String accountid, int isPrimary){
        String sql = "INSERT INTO Own VALUES(?,?,?)";
        Object[] objects = {customerid, accountid, isPrimary};
        return dbExecutor.runUpdate(sql, objects);
    }

    public boolean deleteRelationByAccountId(String accountId){
        String sql = "DELETE FROM Own WHERE account_id = " + accountId;
        return dbExecutor.runUpdate(sql);
    }

}
