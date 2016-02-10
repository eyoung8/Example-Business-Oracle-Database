import java.sql.*; 
import oracle.jdbc.*;
import oracle.jdbc.pool.OracleDataSource;

/* 
* Class for displaying tables
*/
public class DisplayTables {
	private Connection connection;
	
	public DisplayTables(Connection conn){ 
		connection = conn;
	}

	/* 
	* Method to display the employees table 
	* Calls a pl/sql function stored in the database to aquire a ref cursor that is iterated through
	*/
	public void displayEmployees() throws SQLException{
		System.out.println("\nEmployees");
		CallableStatement cs = connection.prepareCall("begin ? := project2_package.get_employees(); end;");
	    cs.registerOutParameter(1, OracleTypes.CURSOR);

        // execute and retrieve the result set
        cs.execute();     
        ResultSet rs = (ResultSet)cs.getObject(1);
        System.out.println("EID       ENAME    Telephone");
        System.out.println("---------------------------------------------");
        while (rs.next()) {
            System.out.println(rs.getString(1) + "\t" +
              rs.getString(2) + "\t" + rs.getString(3));
        }
        System.out.println("");
        rs.close();
        cs.close();  
		
	}

	/* 
	* Method to display the customers table 
	* Calls a pl/sql function stored in the database to aquire a ref cursor that is iterated through
	*/
	public void displayCustomers() throws SQLException{
		System.out.println("\nCustomers");
		CallableStatement cs = connection.prepareCall("begin ? := project2_package.get_customers(); end;");
	    cs.registerOutParameter(1, OracleTypes.CURSOR);

        // execute and retrieve the result set
        cs.execute();     
        ResultSet rs = (ResultSet)cs.getObject(1);
        System.out.println("CID | CNAME | TELEPHONE# | VISITS_MADE | LAST_VISIT_MADE");
        System.out.println("---------------------------------------------------");
        while (rs.next()) {
            System.out.println(
              rs.getString(1) + "\t" +
              rs.getString(2) + "\t" + 
              rs.getString(3) + "\t" + 
              rs.getString(4) + "\t" + 
              rs.getString(5)
              
              );
        }
        System.out.println("");
        rs.close();
        cs.close();  
	}

	/* 
	* Method to display the products table 
	* Calls a pl/sql function stored in the database to aquire a ref cursor that is iterated through
	*/
	public void displayProducts() throws SQLException{
		System.out.println("\nProducts");
		CallableStatement cs = connection.prepareCall("begin ? := project2_package.get_products(); end;");
	    cs.registerOutParameter(1, OracleTypes.CURSOR);

        // execute and retrieve the result set
        cs.execute();     
        ResultSet rs = (ResultSet)cs.getObject(1);
        System.out.println("PID | PNAME | QOH | QOH_T | ORIGINAL_P| DISCNT_RATE");
        System.out.println("--------------------------------------------------------------");
        while (rs.next()) {
            System.out.println(
              rs.getString(1) + "\t" +
              rs.getString(2) + "\t" + 
              rs.getString(3) + "\t" + 
              rs.getString(4) + "\t" + 
              rs.getString(5) + "\t" + 
              rs.getString(6)
              
              
              );
        }
        System.out.println("");
        rs.close();
        cs.close();  
	}

	/* 
	* Method to display the suppliers table 
	* Calls a pl/sql function stored in the database to aquire a ref cursor that is iterated through
	*/
	public void displaySuppliers() throws SQLException{
		System.out.println("\nSUPPLIERS");
		CallableStatement cs = connection.prepareCall("begin ? := project2_package.get_suppliers(); end;");
	    cs.registerOutParameter(1, OracleTypes.CURSOR);

        // execute and retrieve the result set
        cs.execute();     
        ResultSet rs = (ResultSet)cs.getObject(1);
        System.out.println("SID | SNAME | CITY | TELEPHONE#");
        System.out.println("--------------------------------------");
        while (rs.next()) {
            System.out.println(
              rs.getString(1) + "\t" +
              rs.getString(2) + "\t" + 
              rs.getString(3) + "\t" + 
              rs.getString(4)
              
              
              );
        }
        System.out.println("");
        rs.close();
        cs.close();  
	}

	/* 
	* Method to display the supply table 
	* Calls a pl/sql function stored in the database to aquire a ref cursor that is iterated through
	*/
	public void displaySupply() throws SQLException{
		System.out.println("\nSUPPLY");
		CallableStatement cs = connection.prepareCall("begin ? := project2_package.get_supply(); end;");
	    cs.registerOutParameter(1, OracleTypes.CURSOR);

        // execute and retrieve the result set
        cs.execute();     
        ResultSet rs = (ResultSet)cs.getObject(1);
        System.out.println("SUP# | PID | SID | SDATE |quantity");
        System.out.println("--------------------------------------------------------------");
        while (rs.next()) {
            System.out.println(
              rs.getString(1) + "\t" +
              rs.getString(2) + "\t" + 
              rs.getString(3) + "\t" + 
              rs.getString(4) + "\t" + 
              rs.getString(5) 
              
              
              );
        }
        System.out.println("");
        rs.close();
        cs.close();  
	}

	/* 
	* Method to display the purchases table 
	* Calls a pl/sql function stored in the database to aquire a ref cursor that is iterated through
	*/
	public void displayPurchases() throws SQLException{
		System.out.println("\nPurchases");
		CallableStatement cs = connection.prepareCall("begin ? := project2_package.get_purchases(); end;");
	    cs.registerOutParameter(1, OracleTypes.CURSOR);

        // execute and retrieve the result set
        cs.execute();     
        ResultSet rs = (ResultSet)cs.getObject(1);
        System.out.println("PUR# | EID | PID | CID | PTIME | QTY |TOTAL_PRICE");
        System.out.println("-------------------------------------------------");
        while (rs.next()) {
            System.out.println(
              rs.getString(1) + "\t" +
              rs.getString(2) + "\t" + 
              rs.getString(3) + "\t" + 
              rs.getString(4) + "\t" + 
              rs.getString(5) + "\t" + 
              rs.getString(6) + "\t" + 
              rs.getString(7)
              
              
              );
        }
        System.out.println("");
        rs.close();
        cs.close();  
	}

	/* 
	* Method to display the logs table 
	* Calls a pl/sql function stored in the database to aquire a ref cursor that is iterated through
	*/
	public void displayLogs() throws SQLException{
		System.out.println("\nLogs");
		CallableStatement cs = connection.prepareCall("begin ? := project2_package.get_logs(); end;");
	    cs.registerOutParameter(1, OracleTypes.CURSOR);

        // execute and retrieve the result set
        cs.execute();     
        ResultSet rs = (ResultSet)cs.getObject(1);
        System.out.println("LOG# | WHO | OTIME | TABLE_NAME | OPERATION | KEY_VALUE ");
        System.out.println("-------------------------------------------------");
        while (rs.next()) {
            System.out.println(
              rs.getString(1) + "\t" +
              rs.getString(2) + "\t" + 
              rs.getString(3) + "\t" + 
              rs.getString(4) + "\t" + 
              rs.getString(5) + "\t" + 
              rs.getString(6)
              
              
              );
        }
	}

	/* 
	* Method to display the total monthly sales of a product
	* Accepts pid as parameter
	* Calls a pl/sql function stored in the database to aquire a ref cursor that is iterated through
	*/
	public void displayMonthlySales(String pid) throws SQLException{
		System.out.println("\nreport_monthly_sales");
		CallableStatement cs = connection.prepareCall("begin ? := project2_package.report_monthly_sale(?); end;");
	    cs.registerOutParameter(1, OracleTypes.CURSOR);
	    cs.setString(2, pid);
        // execute and retrieve the result set
        cs.execute();     
        ResultSet rs = (ResultSet)cs.getObject(1);
        System.out.println("Month    Year   TotalQ  TotalS  Average");
        System.out.println("---------------------------------------------");
        while (rs.next()) {
            System.out.println(rs.getString(1) + "\t" +
              rs.getString(2) + "\t" + rs.getInt(3) + "\t"
                +rs.getDouble(4) + 
                "\t" + rs.getDouble(5) + "\t");
        }
	}

}