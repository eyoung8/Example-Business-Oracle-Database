import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.OracleTypes;


/* 
* Class for making insertions into tables
*/
public class Insert {
	private Connection connection;
	public Insert(Connection conn){ 
		connection = conn;
	}

	/*
	* Method to insert a product into the products table
	* Accepts user input for the product information
	* Call a stored procedure for inserting products into the database
	*/
	public void insertProduct(BufferedReader br) throws SQLException, IOException{
		
		System.out.println("Please enter PID:");
		String pid = br.readLine();
		System.out.println("Please enter pname");
		String pname = br.readLine();
		System.out.println("Please enter QOH");
		int qoh = Integer.parseInt(br.readLine());
		System.out.println("Please enter QOH_threshold");
		int qoh_threshold = Integer.parseInt(br.readLine());
		System.out.println("Please enter original_price");
		double original_price = Double.parseDouble(br.readLine());
		System.out.println("Please enter discnt_rate");
		double discnt_rate = Double.parseDouble(br.readLine());
		
		System.out.println("Insert product\n");
		CallableStatement cs = connection.prepareCall("begin project2_package.insert_product(?,?,?,?,?,?); end;");
	    cs.setString(1,pid);
		cs.setString(2,pname);
	    cs.setInt(3,qoh);
	    cs.setInt(4,qoh_threshold);
	    cs.setDouble(5,original_price);
	    cs.setDouble(6,discnt_rate);
        // execute and retrieve the result set
        cs.execute();  
        }
	
    /*
	* Method to insert a purchase into the purchases table
	* Accepts user input for the purchase information
	* Call a stored procedure for inserting purchase into the database
	* Checks quantity before and after to determine if the purchase warranted a resupply and informs the user if it did 
    */
	public void insertPurchase(BufferedReader br) throws SQLException, IOException{
		System.out.println("Please enter EID:");
		String eid = br.readLine();
		System.out.println("Please enter PID");
		String pid = br.readLine();
		System.out.println("Please enter CID");
		String cid = br.readLine();
		System.out.println("Please enter Quantity");
		int quantity = Integer.parseInt(br.readLine());

		
		//Getting quantity on hand before purchase
		 PreparedStatement getQOH = connection.prepareStatement("SELECT QOH FROM PRODUCTS p WHERE p.PID = ?");
		 ResultSet rset;
     	 getQOH.setString(1,pid);
		 
	     rset = getQOH.executeQuery();
	     rset.next();
	     int qohBeforePurchase = rset.getInt(1);
	    
	    //Adding purchase to purchases table
		CallableStatement cs = connection.prepareCall("begin project2_package.insert_purchase(?,?,?,?); end;");
	    cs.setString(1,eid);
		cs.setString(2,pid);
	    cs.setString(3,cid);
	    cs.setInt(4,quantity);
        // execute and retrieve the result set
        cs.execute();  
       
        System.out.println("Insert purchase\n");
        
        //Getting quantity on hand after purchase and comparing to before
        rset = getQOH.executeQuery();
	    rset.next();
	    int qohAfterPurchase = rset.getInt(1);
	    int expectedQuantity = qohBeforePurchase - quantity;
	    
	    //Outputting new quantity on hand if there was a resupply
	    if(qohAfterPurchase != expectedQuantity){
	    	System.out.println("Quantity fell below threshold after purchase and was resupplied. New quantity is "+qohAfterPurchase+"\n");
	    	
	    }
           
	}
}