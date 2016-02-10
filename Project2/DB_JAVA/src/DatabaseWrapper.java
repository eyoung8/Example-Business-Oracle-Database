import java.io.*;
import java.sql.*; 
import oracle.jdbc.*;
import oracle.jdbc.pool.OracleDataSource;


/* 
* Class that provides menus for the user to navigate through
* Menus will call methods in DisplayTables or Insert classes
*/
public class DatabaseWrapper {
	private BufferedReader br;
	private Connection connection;
	public DatabaseWrapper(BufferedReader brIn,Connection conn){ 
		br = brIn;
		connection = conn;
	}
	/* 
	* Method that allows the user choose which table to display or quit
	* Based on user input calls a method of the class DisplayTables
	*/
	public void displayTables(){ 
		DisplayTables display = new DisplayTables(connection); 
		try {
			boolean continueIfTrue = true;
			while (continueIfTrue){
				displayTablesMenu();
				String input = br.readLine();

				if (input.equals("1")){
					display.displayEmployees();
					continueIfTrue = false;
				} else if (input.equals("2")){
					display.displayCustomers();
					continueIfTrue = false;
				} else if (input.equals("3")){
					display.displayProducts();
					continueIfTrue = false;
				} else if (input.equals("4")){
					display.displaySuppliers();
					continueIfTrue = false;
				} else if (input.equals("5")){
					display.displaySupply();
					continueIfTrue = false;
				} else if (input.equals("6")){
					display.displayPurchases();
					continueIfTrue = false;
				} else if (input.equals("7")){
					display.displayLogs();
					continueIfTrue = false;
				} else if (input.equals("8")){
					continueIfTrue = false;
				} else {
					System.out.println(input + " is not a valid input.");
				}
			}
		} catch (IOException |SQLException e){
			e.printStackTrace();
		}

	}

	/* 
	* Method that prints the menu of tables to display
	*/
	private void displayTablesMenu(){
		System.out.println("Which table would you like to display?");
		System.out.println("1. Employees");
		System.out.println("2. Customers");
		System.out.println("3. Products");
		System.out.println("4. Suppliers");
		System.out.println("5. Supply");
		System.out.println("6. Purchases");
		System.out.println("7. Logs");
		System.out.println("8. Quit");
		System.out.print("Choice: ");

	}

	/* 
	* Method that allows the user choose which table to insert into or quit
	* Based on user input calls a method of the class Insert
	* Handles exceptions using error codes provided by the oracle database
	*/
	public void insert(){
		try {
			Insert insert = new Insert(connection); 
			boolean continueIfTrue = true;
			while (continueIfTrue){
				displayInsertMenu();
				String input = br.readLine();
				if (input.equals("1")){
					insert.insertPurchase(br);
					continueIfTrue = false;
				} else if (input.equals("2")){
					insert.insertProduct(br);
					continueIfTrue = false;
				} else if (input.equals("3")){
					continueIfTrue = false;
				} else {
					System.out.println(input + " is not a valid input.");
				}
			}
		} catch (SQLException e){

			if (e.getErrorCode() == 6502){
				System.out.println("Insufficient quantity on hand for purchase");
			} else if (e.getErrorCode() == 1) {
				System.out.println("Product with that PID already exists");
			} else {
				System.out.println("Invalid data: Either out of bounds or fails as foreign key");
			}
		} catch (IOException e){
			System.out.println("Could not read line");
		}

	}

	/* 
	* Method that prints the menu of tables to insert into
	*/
	private void displayInsertMenu(){
		System.out.println("Which table would you like to insert into");
		System.out.println("1. Purchases");
		System.out.println("2. Products");
		System.out.println("3. Quit");
		System.out.print("Choice: ");

	}

	/*
	* Method for entering pid to display monthly sales of corresponding product or quit
	*/
	public void displayMonthlySales() throws SQLException{
		DisplayTables display = new DisplayTables(connection); 
		try {
			boolean continueIfTrue = true;
			while (continueIfTrue){
				displayMonthlySaleMenu();
				String input = br.readLine();
				if (input.toUpperCase().equals("QUIT")){
					continueIfTrue = false;
				} else {
					display.displayMonthlySales(input);
					continueIfTrue = false;
				}
			}


		} catch (IOException e){
			e.printStackTrace();
		}

	}

	/*
	* Method for printing menu of for displaying monthly sales
	*/
	private void displayMonthlySaleMenu(){
		System.out.println("Which PID would you like to display the monthly sales of?");
		System.out.println("Enter 'QUIT' to quit");
		System.out.print("PID: ");

	}

	
}