package cs174a;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class run sql statement and return a result
 * There is 2 major methods:
 * 1. run query(select) with a sql statement -- return a ResultSet
 * 2. run update(insert, delete, update) with a sql statement -- return a flag which means if success
 */
public class DBExecutor {
    /**
     * Run a select statement
     */
    public List<Map<String, Object>> query(String sql){
        // Create a Connection to database
        Connection conn = null;
        // Create a PreparedStatement to execute sql statement
        PreparedStatement ps = null;
        // Create a ResultSet to return query result
        ResultSet rs = null;
        try {
            // Connect to database
            conn = DBConnector.getConnection();
            // Set sql statement
            ps = conn.prepareStatement(sql);
            // Execute and Get result
            rs = ps.executeQuery();
            // Set a list to store all result
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            // Traversal ResultSet and get all rows
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnNumber = rsmd.getColumnCount();
            while(rs.next()){
                Map<String, Object> map = new HashMap<>();
                for (int i = 1; i <= columnNumber; i++) {
                    map.put(rsmd.getColumnName(i), rs.getObject(i));
                }
                list.add(map);
            }
            return list;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                // Finally close all resource
                rs.close();
                ps.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Run a update statement
     */
    public boolean runUpdate(String sql){
        // Create a Connection to database
        Connection conn = null;
        // Create a PreparedStatement to execute sql statement
        PreparedStatement ps = null;
        try {
            // Connect to database
            conn = DBConnector.getConnection();
            // Set sql statement
            ps = conn.prepareStatement(sql);
            // Execute sql statement
            ps.executeUpdate();
            // If success, this return will be called, return true
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            // If catch exception, return false;
            return false;
        } finally {
            try {
                // Finally close all resource
                ps.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Run a update statement with parameters
     */
    public boolean runUpdate(String sql, Object[] objects){
        // Create a Connection to database
        Connection conn = null;
        // Create a PreparedStatement to execute sql statement
        PreparedStatement ps = null;
        try {
            // Connect to database
            conn = DBConnector.getConnection();
            // Set sql statement
            ps = conn.prepareStatement(sql);
            // Add parameters
            for (int i = 0; i < objects.length; i++) {
                ps.setObject(i + 1, objects[i]);
            }
            // Execute sql statement
            ps.executeUpdate();
            // If success, this return will be called, return true
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            // If catch exception, return false;
            return false;
        } finally {
            try {
                // Finally close all resource
                ps.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

