package cs174a;

import oracle.jdbc.OracleConnection;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DateDao {
    public static Date getCurrentDate(){
        DBExecutor dbExecutor = new DBExecutor();
        String sql_getTime = "SELECT * FROM Dates";
        List<Map<String, Object>> time = dbExecutor.query(sql_getTime);
        // Get current days
        Date curr_date = (Date)time.get(0).get("VALUE");
        return curr_date;
    }

}
