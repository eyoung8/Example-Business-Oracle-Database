set serveroutput on

CREATE OR REPLACE PACKAGE rbms AS
/*declaration section*/
	TYPE customerCursor IS REF CURSOR;
	
	/*show functions for question 2*/
	FUNCTION show_customers
	 RETURN customerCursor;
	/*need to implement the rest, but make sure they work with java program first*/

	/*monthly sale report for question 3*/
/*	PROCEDURE report_monthly_sale(prod_id IN products.pid%type);*/
	

END;
/
show errors

CREATE OR REPLACE PACKAGE BODY rbms AS
/*body section*/
	/*Function for returning the entire cutomers table using ref cursor */
	FUNCTION show_customers
		RETURN customerCursor AS
		cc customerCursor;
	BEGIN
		OPEN cc FOR
		select * from customers;
		RETURN cc;
	END;

	/*Procedure for reporting monthly sales*/
/*	PROCEDURE report_monthly_sale(prod_id IN products.pid%type) IS
		CURSOR all_sales IS
			SELECT * FROM purchases;
		
		BEGIN
		
		FOR purchase IN all_sales LOOP
			


		END;
*/	

CREATE OR REPLACE PROCEDURE show_products AS v_row products%rowtype;
begin
	select * into v_row from products;
	dbms_output.put_line('ROW: p1' || v_row.pname || ', p2' || v_row.pname);
end show_products;
/

END;
/
show errors
