package cs174a;                                             // THE BASE PACKAGE FOR YOUR APP MUST BE THIS ONE.  But you may add subpackages.

// You may have as many imports as you need.
import java.sql.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.SplittableRandom;

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
		final String DROP_TABLE_Customers = "DROP TABLE Customers CASCADE CONSTRAINTS";
		final String DROP_TABLE_Accounts = "DROP TABLE Accounts CASCADE CONSTRAINTS";
		final String DROP_TABLE_Transactions = "DROP TABLE Transactions CASCADE CONSTRAINTS";
		final String DROP_TABLE_Own = "DROP TABLE Own CASCADE CONSTRAINTS";
		final String DROP_TABLE_PINs = "DROP TABLE PINS CASCADE CONSTRAINTS";
		final String DROP_TABLE_Dates = "DROP TABLE Dates CASCADE CONSTRAINTS";
		try {
			stmt = _connection.createStatement();
			stmt.executeUpdate(DROP_TABLE_Customers);
			stmt.executeUpdate(DROP_TABLE_Accounts);
			stmt.executeUpdate(DROP_TABLE_Transactions);
			stmt.executeUpdate(DROP_TABLE_Own);
			stmt.executeUpdate(DROP_TABLE_PINs);
			stmt.executeUpdate(DROP_TABLE_Dates);
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
				+ "tax_id VARCHAR(20) PRIMARY KEY,"
				+ "name VARCHAR(20) NOT NULL,"
				+ "address VARCHAR(50) NOT NULL)";

		final String CREATE_TABLE_Accounts = "CREATE TABLE Accounts ("
				+ "account_id INTEGER,"
				+ "account_type VARCHAR(20) NOT NULL,"
				+ "balance REAL NOT NULL,"
				+ "primary_owner_id VARCHAR(20) NOT NULL,"
				+ "rate REAL NOT NULL,"
				+ "isClosed INTEGER DEFAULT 0,"
				+ "linked_account_id INTEGER DEFAULT NULL,"
				+ "branch_name VARCHAR(20) DEFAULT NULL,"
				+ "PRIMARY KEY (account_id),"
				+ "FOREIGN KEY (primary_owner_id) REFERENCES Customers)";

		final String CREATE_TABLE_Transactions = "CREATE TABLE Transactions ("
				+ "transaction_id INTEGER,"
				+ "transaction_type VARCHAR(20) NOT NULL,"
				+ "time DATE NOT NULL,"
				+ "amount REAL NOT NULL,"
				+ "customer_id VARCHAR(20),"
				+ "from_id INTEGER,"
				+ "to_id INTEGER,"
				+ "fee REAL,"
				+ "check_number VARCHAR2(20),"
				+ "FOREIGN KEY (customer_id) REFERENCES Customers,"
				+ "FOREIGN KEY (from_id) REFERENCES Accounts,"
				+ "FOREIGN KEY (to_id) REFERENCES Accounts,"
				+ "PRIMARY KEY (transaction_id))";

		final String CREATE_TABLE_Own = "CREATE TABLE Own ("
				+ "tax_id VARCHAR(20),"
				+ "account_id INTEGER,"
				+ "isprimary INTEGER NOT NULL,"
				+ "PRIMARY KEY (tax_id,account_id),"
				+ "FOREIGN KEY(tax_id) REFERENCES Customers,"
				+ "FOREIGN KEY(account_id) REFERENCES Accounts)";

		final String CREATE_TABLE_PINs = "CREATE TABLE PINs("
				+ "customer_id VARCHAR(20) PRIMARY KEY NOT NULL,"
				+ "pin CHAR(4))";

		final String CREATE_TABLE_Dates = "CREATE TABLE Dates ("
				+ "system_date DATE PRIMARY KEY NOT NULL)";

		try {
			stmt = _connection.createStatement();
			stmt.executeUpdate(CREATE_TABLE_Customers);
			stmt.executeUpdate(CREATE_TABLE_Accounts);
			stmt.executeUpdate(CREATE_TABLE_Transactions);
			stmt.executeUpdate(CREATE_TABLE_Own);
			stmt.executeUpdate(CREATE_TABLE_PINs);
			stmt.executeUpdate(CREATE_TABLE_Dates);
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

		final String INSERT_INTO_System_Date = "INSERT INTO Dates "
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
				"VALUES (" + id + "," + AccountType.POCKET.toString() + "," + Double.toString(initialTopUp) + "," + tin + "," + "0.0" + "," + "0" + linkedId +")";

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
		String r = "0 ";
		double old_balance = 0.00, new_balance = 0.00;
		Statement stmt = null;

		final String query = "SELECT * FROM Accounts WHERE account_id = " + accountId;
		try{
			stmt = _connection.createStatement();
			ResultSet result = stmt.executeQuery(query);
			old_balance = result.getDouble("balance");
			new_balance = old_balance + amount;
			final String update = "UPDATE Accounts SET balance = " + Double.toString(new_balance) + " WHERE account_id = " + accountId;
			stmt.executeUpdate(update);
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

		DecimalFormat df2 = new DecimalFormat("#.##");
		String add_on = df2.format(old_balance) + " " + df2.format(new_balance);

		return r + add_on;
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
		String r = "0 ";
		double balance = 0.00;
		Statement stmt = null;

		final String query = "SELECT * FROM Accounts WHERE account_id = " + accountId;
		try{
			stmt = _connection.createStatement();
			ResultSet result = stmt.executeQuery(query);
			balance = result.getDouble("balance");
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

		String add_on = String.format("%.2f", balance);

		return r + add_on;
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
		String r = "0 ";
		Statement stmt = null;
		double old_linked_balance = 0.00, new_linked_balance = 0.00;
		double old_pocket_balance = 0.00, new_pocket_balance = 0.00;
		int linkedId = 0;
		String add_on = String.format("%.2f", old_linked_balance) + " " + String.format("%.2f", old_pocket_balance);

		final String query_pocket = "SELECT * FROM Accounts WHERE account_id = " + accountId;
		try{
			stmt = _connection.createStatement();
			ResultSet pocket_result = stmt.executeQuery(query_pocket);
			old_pocket_balance = pocket_result.getDouble("balance");
			linkedId = pocket_result.getInt("linked_account_id");

			final String query_linked = "SELECT * FROM Accounts WHERE account_id = " + Integer.toString(linkedId);
			ResultSet lined_result = stmt.executeQuery(query_linked);
			old_linked_balance = lined_result.getDouble("balance");

			if(old_linked_balance - 0.01 <= amount){
				// Transaction failed due to low balance
				r = "1 ";
			}else{
				// Transaction success
				new_linked_balance = old_linked_balance - amount;
				new_pocket_balance = old_pocket_balance + amount;
				final String update_linked = "UPDATE Accounts SET balance = " + Double.toString(new_linked_balance) + " WHERE account_id = " + Integer.toString(linkedId);
				final String update_pocket = "UPDATE Accounts SET balance = " + Double.toString(new_pocket_balance) + " WHERE account_id = " + accountId;
				stmt.executeUpdate(update_linked);
				stmt.executeUpdate(update_pocket);
				add_on = String.format("%.2f", new_linked_balance) + " " + String.format("%.2f", new_pocket_balance);
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
		String r = "0 ";
		Statement stmt = null;
		double old_from_balance = 0.00, new_from_balance = 0.00;
		double old_to_balance = 0.00, new_to_balance = 0.00;

		final String query_from = "SELECT * FROM Accounts WHERE account_id = " + from;
		final String query_to = "SELECT * FROM Accounts WHERE account_id = " + to;
		try {
			stmt = _connection.createStatement();
			ResultSet from_result = stmt.executeQuery(query_from);
			old_from_balance = from_result.getDouble("balance");
			ResultSet to_result = stmt.executeQuery(query_to);
			old_to_balance = to_result.getDouble("balance");

			if(amount > old_from_balance){
				// Transaction failed due to inefficient balance
				r = "1 ";
			}else{
				// Transaction proceed
				new_from_balance = old_from_balance - amount;
				new_to_balance = new_to_balance + amount;
				final String update_from = "UPDATE Accounts SET balance = " + Double.toString(new_from_balance) + " WHERE account_id = " + from;
				final String update_to = "UPDATE Accounts SET balance = " + Double.toString(new_to_balance) + " WHERE account_id = " + to;
				stmt.executeUpdate(update_from);
				stmt.executeUpdate(update_to);

				if(new_from_balance <= 0.01){
					// Source account needs to be closed due to a low balance
					final String close_from = "UPDATE Accounts SET isClosed = " + "1" + " WHERE account_id = " + from;
					stmt.executeUpdate(close_from);
				}

			}

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

		return r + Double.toString(new_to_balance) + " " + Double.toString(new_to_balance);
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
