package cs174a;                                             // THE BASE PACKAGE FOR YOUR APP MUST BE THIS ONE.  But you may add subpackages.

// You may have as many imports as you need.
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import oracle.jdbc.pool.OracleDataSource;
import oracle.jdbc.OracleConnection;

/**
 * The most important class for your application.
 * DO NOT CHANGE ITS SIGNATURE.
 */
public class App implements Testable
{
	private OracleConnection _connection;                   // Example connection object to your DB.

	/**
	 * Default constructor.
	 * DO NOT REMOVE.
	 */
	App()
	{
		// TODO: Any actions you need.
	}

	/**
	 * This is an example access operation to the DB.
	 */
	void exampleAccessToDB()
	{
		// Statement and ResultSet are AutoCloseable and closed automatically.
		try( Statement statement = _connection.createStatement() )
		{
			try( ResultSet resultSet = statement.executeQuery( "select owner, table_name from all_tables" ) )
			{
				while( resultSet.next() )
					System.out.println( resultSet.getString( 1 ) + " " + resultSet.getString( 2 ) + " " );
			}
		}
		catch( SQLException e )
		{
			System.err.println( e.getMessage() );
		}
	}

	////////////////////////////// Implement all of the methods given in the interface /////////////////////////////////
	// Check the Testable.java interface for the function signatures and descriptions.

	@Override
	public String initializeSystem()
	{
		// Some constants to connect to your DB.
		final String DB_URL = "jdbc:oracle:thin:@cs174a.cs.ucsb.edu:1521/orcl";
		final String DB_USER = "c##zihaozhang";
		final String DB_PASSWORD = "8862062";

		// Initialize your system.  Probably setting up the DB connection.
		Properties info = new Properties();
		info.put( OracleConnection.CONNECTION_PROPERTY_USER_NAME, DB_USER );
		info.put( OracleConnection.CONNECTION_PROPERTY_PASSWORD, DB_PASSWORD );
		info.put( OracleConnection.CONNECTION_PROPERTY_DEFAULT_ROW_PREFETCH, "20" );

		try
		{
			OracleDataSource ods = new OracleDataSource();
			ods.setURL( DB_URL );
			ods.setConnectionProperties( info );
			_connection = (OracleConnection) ods.getConnection();

			// Get the JDBC driver name and version.
			DatabaseMetaData dbmd = _connection.getMetaData();
			System.out.println( "Driver Name: " + dbmd.getDriverName() );
			System.out.println( "Driver Version: " + dbmd.getDriverVersion() );

			// Print some connection properties.
			System.out.println( "Default Row Prefetch Value is: " + _connection.getDefaultRowPrefetch() );
			System.out.println( "Database Username is: " + _connection.getUserName() );
			System.out.println();

			return "0";
		}
		catch( SQLException e )
		{
			System.err.println( e.getMessage() );
			return "1";
		}
	}

	/**
	 * Destroy all of the tables in your DB.
	 * @return a string "r", where r = 0 for success, 1 for error.
	 */
	@Override
	public String dropTables(){
		return "STUB";
	}

	/**
	 * Create all of your tables in your DB.
	 * @return a string "r", where r = 0 for success, 1 for error.
	 */
	@Override
	public String createTables(){
		String r = "0";
		Statement stmt = null;

		final String CREATE_TABLE_Customers="CREATE TABLE Customers ("
				+ "tax_id INTEGER,"
				+ "name CHAR(20) NOT NULL,"
				+ "address CHAR(40) NOT NULL,"
				+ "pin CHAR(20) NOT NULL,"
				+ "PRIMARY KEY (tax_id))";

		final String CREATE_TABLE_Accounts="CREATE TABLE Accounts ("
				+ "account_id INTEGER,"
				+ "branch_name CHAR(20) NOT NULL,"
				+ "account_type CHAR(20) NOT NULL,"
				+ "rate REAL,"
				+ "PRIMARY KEY (account_id))";

		final String CREATE_TABLE_Transactions="CREATE TABLE Transactions ("
				+ "transaction_id INTEGER,"
				+ "transaction_type CHAR(20) NOT NULL,"
				+ "time DATE NOT NULL,"
				+ "amount REAL NOT NULL,"
				+ "PRIMARY KEY (transaction_id))";

		final String CREATE_TABLE_Generate="CREATE TABLE Generate ("
				+ "tax_id INTEGER,"
				+ "transaction_id INTEGER,"
				+ "account_id_one INTEGER NOT NULL,"
				+ "account_id_two INTEGER,"
				+ "PRIMARY KEY (tax_id,transaction_id,account_id_one),"
				+ "FOREIGN KEY(tax_id) REFERENCES Customers,"
				+ "FOREIGN KEY(transaction_id) REFERENCES Transactions,"
				+ "FOREIGN KEY(account_id_one) REFERENCES Accounts)";

		final String CREATE_TABLE_Own="CREATE TABLE Own ("
				+ "tax_id INTEGER,"
				+ "account_id INTEGER,"
				+ "isprimary INTEGER,"
				+ "PRIMARY KEY (tax_id,account_id),"
				+ "FOREIGN KEY(tax_id) REFERENCES Customers,"
				+ "FOREIGN KEY(account_id_one) REFERENCES Accounts)";

		try{
			stmt = _connection.createStatement();
			stmt.executeUpdate(CREATE_TABLE_Customers);
			stmt.executeUpdate(CREATE_TABLE_Accounts);
			stmt.executeUpdate(CREATE_TABLE_Transactions);
			stmt.executeUpdate(CREATE_TABLE_Generate);
			stmt.executeUpdate(CREATE_TABLE_Own);
			System.out.println("Tables created");
		}catch (SQLException e){
			e.printStackTrace();
			r = "1";
		}

		return r;
	}

	/**
	 * Set system's date.
	 * @param year Valid 4-digit year, e.g. 2019.
	 * @param month Valid month, where 1: January, ..., 12: December.
	 * @param day Valid day, from 1 to 31, depending on the month (and if it's a leap year).
	 * @return a string "r yyyy-mm-dd", where r = 0 for success, 1 for error; and yyyy-mm-dd is the new system's date, e.g. 2012-09-16.
	 */
	@Override
	public String setDate( int year, int month, int day ){
		return "STUB";
	}
















	/**
	 * Example of one of the testable functions.
	 */
	@Override
	public String listClosedAccounts()
	{
		return "0 it works!";
	}

	/**
	 * Another example.
	 */
	@Override
	public String createCheckingSavingsAccount( AccountType accountType, String id, double initialBalance, String tin, String name, String address )
	{
		return "0 " + id + " " + accountType + " " + initialBalance + " " + tin;
	}
}
