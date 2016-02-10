import java.io.*;
import java.sql.*; 
import oracle.jdbc.*;
import oracle.jdbc.pool.OracleDataSource;



public class Driver {
	public static void main(String[] args){
		
		OracleDataSource ds = null;
		Connection conn = null;

		//Establish database connection
		try{
			ds = new oracle.jdbc.pool.OracleDataSource();
			ds.setURL("jdbc:oracle:thin:@localhost:1521/XE");
			conn = ds.getConnection("system", "totalia");
			
		}catch (SQLException ex) { 
			System.out.println ("\n*** SQLException caught ***\n");
			System.exit(1);
		 }
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		DatabaseWrapper dbWrapper = new DatabaseWrapper(br,conn);
		
		//Get user input for action to take
		try{
			boolean continueIfTrue = true;
			while (continueIfTrue){
				displayOptions();
				System.out.print("Choice: ");
				String input = br.readLine();
				if (input.equals("1")){
					dbWrapper.displayTables();
				} else if (input.equals("2")){
					dbWrapper.insert();
				} else if (input.equals("3")){
					dbWrapper.displayMonthlySales();
				} else if (input.equals("4")){
					continueIfTrue = false;
				} else {
					System.out.println( input + " is not a valid choice.\n\n");
				}
			}
		} catch (IOException |SQLException e){
			e.printStackTrace();
		}
	}

	/*
	Method for printing menu of user choices
	*/
	public static void displayOptions(){
		System.out.println("Commands: Enter number of the command");
		System.out.println("1. Display tables");
		System.out.println("2. Insert into tables"); //purchase, product
		System.out.println("3. Display monthly sale information");
		System.out.println("4. Quit");
		
	}



}

