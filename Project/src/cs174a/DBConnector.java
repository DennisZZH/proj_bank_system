package cs174a;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * This class contains all config of database and used for connecting db
 */
public class DBConnector {

    private final static String DB_USER = "c##zihaozhang";

    private final static String DB_PASSWORD = "8862062";

    private final static String DB_URL = "jdbc:oracle:thin:@cs174a.cs.ucsb.edu:1521/orcl";

    /**
     * This class returns a connection
     *
     * @return
     */
    public static OracleConnection getConnection() throws SQLException {
        OracleConnection connection = null;
        // Initialize your system.  Probably setting up the DB connection.
        Properties info = new Properties();
        info.put(OracleConnection.CONNECTION_PROPERTY_USER_NAME, DB_USER);
        info.put(OracleConnection.CONNECTION_PROPERTY_PASSWORD, DB_PASSWORD);
        info.put(OracleConnection.CONNECTION_PROPERTY_DEFAULT_ROW_PREFETCH, "20");


            OracleDataSource ods = new OracleDataSource();
            ods.setURL(DB_URL);
            ods.setConnectionProperties(info);
            connection = (OracleConnection) ods.getConnection();

            // Get the JDBC driver name and version.
            DatabaseMetaData dbmd = connection.getMetaData();
            System.out.println("Driver Name: " + dbmd.getDriverName());
            System.out.println("Driver Version: " + dbmd.getDriverVersion());

            // Print some connection properties.
            System.out.println("Default Row Prefetch Value is: " + connection.getDefaultRowPrefetch());
            System.out.println("Database Username is: " + connection.getUserName());
            System.out.println();

        return connection;
    }

}
