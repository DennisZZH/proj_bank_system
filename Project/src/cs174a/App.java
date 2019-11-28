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
		String r = "0";
		Statement stmt = null;
		final String DROP_TABLE_Customers = "DROP TABLE Customers";
		final String DROP_TABLE_Accounts = "DROP TABLE Accounts";
		final String DROP_TABLE_Transactions = "DROP TABLE Transactions";
		final String DROP_TABLE_Generate = "DROP TABLE Generate";
		final String DROP_TABLE_Own = "DROP TABLE Own";
		try{
			stmt = _connection.createStatement();
			stmt.executeUpdate(DROP_TABLE_Customers);
			stmt.executeUpdate(DROP_TABLE_Accounts);
			stmt.executeUpdate(DROP_TABLE_Transactions);
			stmt.executeUpdate(DROP_TABLE_Generate);
			stmt.executeUpdate(DROP_TABLE_Own);
		}catch(SQLException e){
			e.printStackTrace();
			r = "1";
		}finally {
			try{
				if(stmt != null)
					stmt.close();
			}catch (SQLException e){
				e.printStackTrace();
			}
		}
		return r;
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
				+ "account_id CHAR(20),"
				+ "branch_name CHAR(20) NOT NULL,"
				+ "account_type CHAR(20) NOT NULL,"
				+ "rate REAL,"
				+ "isClosed BOOLEAN,"
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
		}finally {
			try{
				if(stmt != null)
					stmt.close();
			}catch (Exception e){
				e.printStackTrace();
			}
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
		String r = "0 ";

		return r;
	}




	/**
	 * Example of one of the testable functions.
	 */
	@Override
	public String listClosedAccounts()
	{
		Statement stmt=null;
		String r = "0 ";
		String result="";
		String sql = "SELECT * FROM Accounts WHERE isClosed= false ";

		try {
			stmt = _connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String aid = rs.getString("account_id");
				result=aid+result;
			}
			    rs.close();
		}catch(SQLException e){
			e.printStackTrace();
			r = "1";

		}
		finally {
			try{
				if(stmt!= null)
					stmt.close();
			}catch (SQLException e){
				e.printStackTrace();
			}
		}

		return r +" " + result;
	}

	/**
	 * Another example.
	 */
	@Override
	public String createCheckingSavingsAccount( AccountType accountType, String id, double initialBalance, String tin, String name, String address )
	{
		//先intialaccount再用relation将customer和account连起来
		//String sql="INSERT INTO Accounts VALUES(?,?,?,?,?)";
		//Account account = new Account(accountType,id,initialBalance);


        //return "0/1,aid,accpoun_type,initial_balance,primary_owner_taxid"
		return "0 " + id + " " + accountType + " " + initialBalance + " " + tin;
	}


	public String UpdateStatus(String aid,boolean isClosed){
		String r="0";
		Statement stmt=null;
		String sql = "UPDATE Accounts SET isClosed=" + isClosed + " WHERE id=" + aid;
		try {
			stmt = _connection.createStatement();
			stmt.executeUpdate(sql);
		}catch (SQLException e){
			e.printStackTrace();
			r = "1";
		}finally {
			try{
				if(stmt != null)
					stmt.close();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return r;
	}




}
