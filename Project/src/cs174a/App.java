package cs174a;                                             // THE BASE PACKAGE FOR YOUR APP MUST BE THIS ONE.  But you may add subpackages.

// You may have as many imports as you need.
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import oracle.jdbc.pool.OracleDataSource;
import oracle.jdbc.OracleConnection;

/**
 * The most important class for your application.
 * DO NOT CHANGE ITS SIGNATURE.
 */
public class App implements Testable {
	private OracleConnection _connection;                   // Example connection object to your DB.

	/**
	 * Default constructor.
	 * DO NOT REMOVE.
	 */
	App() {
		// TODO: Any actions you need.
	}

	/**
	 * This is an example access operation to the DB.
	 */
	void exampleAccessToDB() {
		// Statement and ResultSet are AutoCloseable and closed automatically.
		try (Statement statement = _connection.createStatement()) {
			try (ResultSet resultSet = statement.executeQuery("select owner, table_name from all_tables")) {
				while (resultSet.next())
					System.out.println(resultSet.getString(1) + " " + resultSet.getString(2) + " ");
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	////////////////////////////// Implement all of the methods given in the interface /////////////////////////////////
	// Check the Testable.java interface for the function signatures and descriptions.

	@Override
	public String initializeSystem() {
		// Some constants to connect to your DB.
		final String DB_URL = "jdbc:oracle:thin:@cs174a.cs.ucsb.edu:1521/orcl";
		final String DB_USER = "c##zihaozhang";
		final String DB_PASSWORD = "8862062";

		// Initialize your system.  Probably setting up the DB connection.
		Properties info = new Properties();
		info.put(OracleConnection.CONNECTION_PROPERTY_USER_NAME, DB_USER);
		info.put(OracleConnection.CONNECTION_PROPERTY_PASSWORD, DB_PASSWORD);
		info.put(OracleConnection.CONNECTION_PROPERTY_DEFAULT_ROW_PREFETCH, "20");

		try {
			OracleDataSource ods = new OracleDataSource();
			ods.setURL(DB_URL);
			ods.setConnectionProperties(info);
			_connection = (OracleConnection) ods.getConnection();

			// Get the JDBC driver name and version.
			DatabaseMetaData dbmd = _connection.getMetaData();
			System.out.println("Driver Name: " + dbmd.getDriverName());
			System.out.println("Driver Version: " + dbmd.getDriverVersion());

			// Print some connection properties.
			System.out.println("Default Row Prefetch Value is: " + _connection.getDefaultRowPrefetch());
			System.out.println("Database Username is: " + _connection.getUserName());
			System.out.println();

			return "0";
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return "1";
		}
	}

	/**
	 * Destroy all of the tables in your DB.
	 *
	 * @return a string "r", where r = 0 for success, 1 for error.
	 */
	@Override
	public String dropTables() {
		String r = "0";
		Statement stmt = null;
		final String DROP_TABLE_Customers = "DROP TABLE Customers";
		final String DROP_TABLE_Accounts = "DROP TABLE Accounts";
		final String DROP_TABLE_Transactions = "DROP TABLE Transactions";
		final String DROP_TABLE_Generate = "DROP TABLE Generate";
		final String DROP_TABLE_Own = "DROP TABLE Own";
		final String DROP_TABLE_System_Date = "DROP TABLE System_Date";
		try {
			stmt = _connection.createStatement();
			stmt.executeUpdate(DROP_TABLE_Customers);
			stmt.executeUpdate(DROP_TABLE_Accounts);
			stmt.executeUpdate(DROP_TABLE_Transactions);
			stmt.executeUpdate(DROP_TABLE_Generate);
			stmt.executeUpdate(DROP_TABLE_Own);
			stmt.executeUpdate(DROP_TABLE_System_Date);
			System.out.println("Tables dropped");
		} catch (SQLException e) {
			e.printStackTrace();
			r = "1";
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return r;
	}

	/**
	 * Create all of your tables in your DB.
	 *
	 * @return a string "r", where r = 0 for success, 1 for error.
	 */
	@Override
	public String createTables() {
		String r = "0";
		Statement stmt = null;

		final String CREATE_TABLE_Customers = "CREATE TABLE Customers ("
				+ "tax_id INTEGER,"
				+ "name CHAR(20) NOT NULL,"
				+ "address CHAR(40) NOT NULL,"
				+ "pin INTEGER DEFAULT 1717,"
				+ "PRIMARY KEY (tax_id))";

		final String CREATE_TABLE_Accounts = "CREATE TABLE Accounts ("
				+ "account_id INTEGER,"
				+ "account_type CHAR(20) NOT NULL,"
				+ "balance REAL NOT NULL,"
				+ "primary_owner_id INTEGER NOT NULL,"
				+ "rate REAL,"
				+ "isClosed INTEGER DEFAULT 0,"				// 1 for true, 0 for false
				+ "branch_name CHAR(20) DEFAULT NULL,"
				+ "PRIMARY KEY (account_id)"
				+ "FOREIGN KEY (primary_owner_id) REFERENCES Customers)";

		final String CREATE_TABLE_Transactions = "CREATE TABLE Transactions ("
				+ "transaction_id INTEGER,"
				+ "transaction_type CHAR(20) NOT NULL,"
				+ "time DATE NOT NULL,"
				+ "amount REAL NOT NULL,"
				+ "PRIMARY KEY (transaction_id))";

		final String CREATE_TABLE_Generate = "CREATE TABLE Generate ("
				+ "tax_id INTEGER,"
				+ "transaction_id INTEGER,"
				+ "account_id_one INTEGER NOT NULL,"
				+ "account_id_two INTEGER,"
				+ "PRIMARY KEY (tax_id,transaction_id,account_id_one),"
				+ "FOREIGN KEY(tax_id) REFERENCES Customers,"
				+ "FOREIGN KEY(transaction_id) REFERENCES Transactions,"
				+ "FOREIGN KEY(account_id_one) REFERENCES Accounts)";

		final String CREATE_TABLE_Own = "CREATE TABLE Own ("
				+ "tax_id INTEGER,"
				+ "account_id INTEGER,"
				+ "isprimary INTEGER NOT NULL,"
				+ "PRIMARY KEY (tax_id,account_id),"
				+ "FOREIGN KEY(tax_id) REFERENCES Customers,"
				+ "FOREIGN KEY(account_id) REFERENCES Accounts)";

		final String CREATE_TABLE_System_Date = "CREATE TABLE System_Date ("
				+ "date DATE,"
				+ "PRIMARY KEY (date))";

		try {
			stmt = _connection.createStatement();
			stmt.executeUpdate(CREATE_TABLE_Customers);
			stmt.executeUpdate(CREATE_TABLE_Accounts);
			stmt.executeUpdate(CREATE_TABLE_Transactions);
			stmt.executeUpdate(CREATE_TABLE_Generate);
			stmt.executeUpdate(CREATE_TABLE_Own);
			stmt.executeUpdate(CREATE_TABLE_System_Date);
			System.out.println("Tables created");
		} catch (SQLException e) {
			e.printStackTrace();
			r = "1";
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return r;
	}

	/**
	 * Set system's date.
	 *
	 * @param year  Valid 4-digit year, e.g. 2019.
	 * @param month Valid month, where 1: January, ..., 12: December.
	 * @param day   Valid day, from 1 to 31, depending on the month (and if it's a leap year).
	 * @return a string "r yyyy-mm-dd", where r = 0 for success, 1 for error; and yyyy-mm-dd is the new system's date, e.g. 2012-09-16.
	 */

	@Override
	public String setDate(int year, int month, int day) {
		String r = "0 ";
		PreparedStatement ps = null;
		String date_str = Integer.toString(year) + '-' + Integer.toString(month) + '-' + Integer.toString(day);
		Date date = parseDate(date_str);

		final String INSERT_INTO_System_Date = "INSERT INTO System_Date"
				+ "VALUES (?)";

		try{
			ps = _connection.prepareStatement(INSERT_INTO_System_Date);
			ps.setDate(1, new java.sql.Date(date.getTime()));
			ps.executeUpdate();
			System.out.println("Date inserted: " + date_str);
		}catch(SQLException e){
			e.printStackTrace();
			r = "1 ";
		}finally {
			try{
				if(ps != null)
					ps.close();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return r + date_str;
	}

	public static Date parseDate(String date) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			return null;
		}
	}


	/**
	 * Create a new checking or savings account.
	 * If customer is new, then their name and address should be provided.
	 * @param accountType New account's checking or savings type.
	 * @param id New account's ID.
	 * @param initialBalance Initial account balance.
	 * @param tin Account's owner Tax ID number - it may belong to an existing or new customer.
	 * @param name [Optional] If customer is new, this is the customer's name. //or is "know"
	 * @param address [Optional] If customer is new, this is the custfomer's address.//or is "known"
	 * @return a string "r aid type balance tin", where
	 *         r = 0 for success, 1 for error;
	 *         aid is the new account id;
	 *         type is the new account's type (see the enum codes above, e.g. INTEREST_CHECKING);
	 *         balance is the account's initial balance with 2 decimal places (e.g. 1000.34, as with %.2f); and
	 *         tin is the Tax ID of account's primary owner.
	 */
	@Override
	public String createCheckingSavingsAccount( AccountType accountType, String id, double initialBalance, String tin, String name, String address )
	{
		String r = "0 ";
		Statement stmt = null;
		String add_on = id + " " + accountType + " " + Double.toString(initialBalance) + " " + tin;

		double rate = 0.0;
		if(accountType == AccountType.INTEREST_CHECKING){
			rate = 0.03;
		}else if(accountType == AccountType.STUDENT_CHECKING){
			rate = 0.0;
		}else if(accountType == AccountType.SAVINGS){
			rate = 0.048;
		}

		final String INSERT_INTO_Customers = "INSERT INTO Customers " +
											"VALUES (" + tin + "," + name + "," + address + ")";

		final String INSERT_INTO_Accounts = "INSERT INTO Accounts " +
											"VALUES (" + id + "," + accountType.toString() + "," + Double.toString(initialBalance) + "," + tin + "," + Double.toString(rate) + ")";

		final String INSERT_INTO_Own = "INSERT INTO Own " +
										"VALUES (" + tin + "," + id + "," + "1" + ")";

		try{
			stmt = _connection.createStatement();
			if(name == "known" && address == "known"){
				// existed customer
			}else{
				// new customer
				stmt.executeUpdate(INSERT_INTO_Customers);
			}
			stmt.executeUpdate(INSERT_INTO_Accounts);
			stmt.executeUpdate(INSERT_INTO_Own);
		}catch(SQLException e){
			e.printStackTrace();
			r = "1 ";
		}finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
				r = "1 ";
			}
		}

		return r + add_on;
	}


	/**
	 * Create a new pocket account.
	 *
	 * @param id           New account's ID.
	 * @param linkedId     Linked savings or checking account ID.
	 * @param initialTopUp Initial balance to be deducted from linked account and deposited into new pocket account.
	 * @param tin          Existing customer's Tax ID number.  He/She will become the new pocket account's owner.
	 * @return a string "r aid type balance tin", where
	 * r = 0 for success, 1 for error;
	 * aid is the new account id;
	 * type is the new account's type (see the enum codes above);
	 * balance is the account's initial balance with up to 2 decimal places (e.g. 1000.12, as with %.2f); and
	 * tin is the Tax ID of account's primary owner.
	 */
	@Override
	public String createPocketAccount(String id, String linkedId, double initialTopUp, String tin) {
		String r = "0 ";
		String add_on = id + " " + AccountType.POCKET.toString() + " " + Double.toString(initialTopUp) + " " + tin;
		Statement stmt = null;

		final String query = "SELECT * FROM Accounts WHERE account_id = " + linkedId;
		final String update = "UPDATE Accounts SET balance = balance - " + Double.toString(initialTopUp)
				+ " WHERE account_id = " + linkedId;
		final String create_pocket = "INSERT INTO Accounts " +
				"VALUES (" + id + "," + AccountType.POCKET.toString() + "," + Double.toString(initialTopUp) + "," + tin + "," + "0.0" + ")";

		try{
			stmt = _connection.createStatement();
			ResultSet result = stmt.executeQuery(query);
			double balance = result.getDouble("balance");
			if(balance - 0.01 <= initialTopUp){
				// Transaction failed due to low balance
				r = "1 ";
			}else{
				stmt.executeUpdate(update);
				stmt.executeUpdate(create_pocket);
			}
		}catch (SQLException e){
			e.printStackTrace();
			r = "1 ";
		}finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
				r = "1 ";
			}
		}

		return r + add_on;
	}


	/**
	 * Create a new customer and link them to an existing checking or saving account.
	 * @param accountId Existing checking or saving account.
	 * @param tin New customer's Tax ID number.
	 * @param name New customer's name.
	 * @param address New customer's address.
	 * @return a string "r", where r = 0 for success, 1 for error.
	 */
	@Override
	public String createCustomer( String accountId, String tin, String name, String address ){
		String r = "0";
		Statement stmt = null;

		final String create_customer = "INSERT INTO Customers " +
										"VALUES (" + tin + "," + name + "," + address + ")";

		final String create_own = "INSERT INTO Own " +
									"VALUES (" + tin + "," + accountId + "," + "0" + ")";

		try{
			stmt = _connection.createStatement();
			stmt.executeUpdate(create_customer);
			stmt.executeUpdate(create_own);
		}catch (SQLException e){
			e.printStackTrace();
			r = "1";
		}finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
				r = "1 ";
			}
		}

		return r;
	}


	/**
	 * Deposit a given amount of dollars to an existing checking or savings account.
	 * @param accountId Account ID.
	 * @param amount Non-negative amount to deposit.
	 * @return a string "r old new" where
	 *         r = 0 for success, 1 for error;
	 *         old is the old account balance, with up to 2 decimal places (e.g. 1000.12, as with %.2f); and
	 *         new is the new account balance, with up to 2 decimal places.
	 */
	@Override
	public String deposit( String accountId, double amount ){
		return "STUB";
	}


	/**
	 * Show an account balance (regardless of type of account).
	 * @param accountId Account ID.
	 * @return a string "r balance", where
	 *         r = 0 for success, 1 for error; and
	 *         balance is the account balance, with up to 2 decimal places (e.g. with %.2f).
	 */
	@Override
	public String showBalance( String accountId ){
		return "STUB";
	}


	/**
	 * Move a specified amount of money from the linked checking/savings account to the pocket account.
	 * @param accountId Pocket account ID.
	 * @param amount Non-negative amount to top up.
	 * @return a string "r linkedNewBalance pocketNewBalance", where
	 *         r = 0 for success, 1 for error;
	 *         linkedNewBalance is the new balance of linked account, with up to 2 decimal places (e.g. with %.2f); and
	 *         pocketNewBalance is the new balance of the pocket account.
	 */
	@Override
	public String topUp( String accountId, double amount ){
		return "STUB";
	}


	/**
	 * Move a specified amount of money from one pocket account to another pocket account.
	 * @param from Source pocket account ID.
	 * @param to Destination pocket account ID.
	 * @param amount Non-negative amount to pay.
	 * @return a string "r fromNewBalance toNewBalance", where
	 *         r = 0 for success, 1 for error.
	 *         fromNewBalance is the new balance of the source pocket account, with up to 2 decimal places (e.g. with %.2f); and
	 *         toNewBalance is the new balance of destination pocket account, with up to 2 decimal places.
	 */
	public String payFriend( String from, String to, double amount ){
		return "STUB";

	}


	/**
	 * Generate list of closed accounts.
	 * @return a string "r id1 id2 ... idn", where
	 *         r = 0 for success, 1 for error; and
	 *         id1 id2 ... idn is a list of space-separated closed account IDs.
	 */
	@Override
	public String listClosedAccounts() {
		Statement stmt = null;
		String r = "0 ";
		String result = "";
		String sql = "SELECT * FROM Accounts WHERE isClosed= false ";

		try {
			stmt = _connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String aid = rs.getString("account_id");
				result = aid + result;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			r = "1";

		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return r + " " + result;
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
