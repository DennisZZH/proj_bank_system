package cs174a;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * This class is for getting a unique id for table accounts and transactions
 * There is a table called id_creator in database to store current id.
 * When get a new id, update the id by adding 1 to it.
 */
public class IDCreator {
    public static int getNextId(){
        // Get the result of table id_creator
        DBExecutor dbExecutor = new DBExecutor();
        String sql_getId = "SELECT * FROM IDs";
        List<Map<String, Object>> id = dbExecutor.query(sql_getId);
        // Get current id, the result is a BigDecimal instead of int
        BigDecimal currentId = (BigDecimal)id.get(0).get("VALUE");
        // Set next id
        int nextId = Integer.valueOf(currentId.toString()) + 1;
        // Update next id
        String sql_updateId = "UPDATE IDs SET value=" + nextId + " WHERE id=1";
        dbExecutor.runUpdate(sql_updateId);
        return Integer.valueOf(currentId.toString());
    }
}
