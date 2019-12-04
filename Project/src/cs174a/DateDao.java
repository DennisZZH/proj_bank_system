package cs174a;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DateDao {
    public static int getCurrentAddTime(){
        // Get the result of table add_time
        DBExecutor dbExecutor = new DBExecutor();
        String sql_getTime = "SELECT * FROM Dates";
        List<Map<String, Object>> time = dbExecutor.query(sql_getTime);
        // Get current days
        Integer currentTime = (Integer)time.get(0).get("value");
        return Integer.valueOf(currentTime.toString());
    }

    public static Date getCurrentTime(){
        int addDays = getCurrentAddTime();
        long addMillis = (long)addDays * 24 * 3600 * 1000;
        return new Date(System.currentTimeMillis() + addMillis);
    }

    public static void addTime(int days){
        int currentAdd = DateDao.getCurrentAddTime();
        currentAdd += days;
        DBExecutor dbExecutor = new DBExecutor();
        String sql_updateId = "UPDATE Dates SET value = " + currentAdd + " WHERE id = 1";
        dbExecutor.runUpdate(sql_updateId);
    }
}
