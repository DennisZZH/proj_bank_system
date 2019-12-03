package cs174a;                         // THE BASE PACKAGE FOR YOUR APP MUST BE THIS ONE.  But you may add subpackages.

// DO NOT REMOVE THIS IMPORT.
import cs174a.Testable.*;

import java.sql.SQLOutput;


/**
 * This is the class that launches your application.
 * DO NOT CHANGE ITS NAME.
 * DO NOT MOVE TO ANY OTHER (SUB)PACKAGE.
 * There's only one "main" method, it should be defined within this Main class, and its signature should not be changed.
 */
public class Main
{
	/**
	 * Program entry point.
	 * DO NOT CHANGE ITS NAME.
	 * DON'T CHANGE THE //!### TAGS EITHER.  If you delete them your program won't run our tests.
	 * No other function should be enclosed by the //!### tags.
	 */
	//!### COMENZAMOS
	public static void main( String[] args )
	{
		App app = new App();                        // We need the default constructor of your App implementation.  Make sure such
		// constructor exists.
		String r = app.initializeSystem();          // We'll always call this function before testing your system.
		if( r.equals( "0" ) )
		{
//			app.exampleAccessToDB();                // Example on how to connect to the DB.

//			//test dropTables						// PASS
//			r = app.dropTables();
//			System.out.println(r);

//			//test createTables						// PASS
//			r = app.createTables();
//			System.out.println(r);


//			//test setDate							// PASS
//			r = app.setDate(2011,3,1);
//			System.out.println(r);



//			// test createCheckingSavingsAccount	// PASS
//			r = app.createCheckingSavingsAccount( AccountType.INTEREST_CHECKING, "41725", 1234.56, "201674933", "George Brush", "5346 Foothill Av" );
//			System.out.println( r );
//
//			r = app.createCheckingSavingsAccount( AccountType.STUDENT_CHECKING, "17431", 1000.0, "344151573", "Joe Pepsi", "3210 State St" );
//			System.out.println( r );
//
//			r = app.createCheckingSavingsAccount( AccountType.STUDENT_CHECKING, "12121", 1500.0, "207843218", "David Copperfill", "1357 State St" );
//			System.out.println( r );
//
//			r = app.createCheckingSavingsAccount( AccountType.SAVINGS, "43942", 4000.00, "361721022", "Alfred Hitchcock", "6667 El Colegio #40" );
//			System.out.println( r );
//
//			r = app.createCheckingSavingsAccount( AccountType.INTEREST_CHECKING, "93156", 2000.00, "209378521", "Kelvin Costner", "Known" );
//			System.out.println( r );
//
//			r = app.createCheckingSavingsAccount( AccountType.SAVINGS, "29107", 3000.00, "209378521", "known", "known" );
//			System.out.println( r );



//			//test createCustomer					// PASS
//			r = app.createCustomer("29107","12116070","Li Kung","2 People's Rd Beijing");
//			System.out.println(r);
//
//			r = app.createCustomer("17431","412231856","Cindy Laugher","7000 Hollister");
//			System.out.println(r);


//			//test createPocketAccount				// PASS
//			r = app.createPocketAccount("53027","12121",500.00,"207843218");
//			System.out.println(r);

//			r = app.createPocketAccount("43947","29107",500.00,"12116070");
//			System.out.println(r);

//			//test deposit							// PASS
//			r = app.showBalance("41725");
//			System.out.println(r);
//			r = app.deposit("41725",234.56);
//			System.out.println(r);
//			r = app.showBalance("41725");
//			System.out.println(r);
//
//			r = app.showBalance("43942");
//			System.out.println(r);
//			r = app.deposit("43942",400.00);
//			System.out.println(r);
//			r = app.showBalance("43942");
//			System.out.println(r);

//			//TEST TOPUP							// PASS
//			r = app.topUp("53027",200.00);
//			System.out.println(r);
//			r = app.topUp("43947", 50.00);
//			System.out.println(r);

//			//test payfriend						// PASS
//			r = app.payFriend("53027","43947",100.00);
//			System.out.println(r);
//
//			r = app.payFriend("43947","53027",77.00);
//			System.out.println(r);


			// test listColsed Accounts				// PASS
//			r=app.UpdateStatus("12121",1);
//			System.out.println(r);
//			r = app.listClosedAccounts();
//			System.out.println( r );


		}
	}
	//!### FINALIZAMOS
}
