create or replace PACKAGE project2_package as
function get_logs return sys_refcursor;
function get_purchases return sys_refcursor;
function get_customers return sys_refcursor;
function get_employees return sys_refcursor;
function get_products return sys_refcursor;
function get_suppliers return sys_refcursor;
function get_supply return sys_refcursor;
function report_monthly_sale(prod_id in purchases.PID%TYPE) return sys_refcursor;
PROCEDURE insert_product(
pid_in IN products.pid%TYPE,
pname_in IN products.pname%TYPE, 
qoh_in IN products.qoh%TYPE, 
qoh_threshold_in IN products.qoh_threshold%TYPE,
original_price_in IN products.original_price%TYPE,
discnt_rate_in IN products.discnt_rate%TYPE);
    
PROCEDURE insert_purchase(
       eid_in IN purchases.EID%TYPE,
     pid_in IN purchases.PID%TYPE,
     cid_in IN purchases.CID%TYPE,
     qty_in IN purchases.QTY%TYPE);
    
end;
/
create or replace package body project2_package as


      function get_purchases return sys_refcursor
      is
      
      rVal sys_refcursor;
      
      begin
        open rVal for 'select * from purchases';
        
        return rVal; 
      end;
      
      function get_logs return sys_refcursor
      is
      
      rVal sys_refcursor;
      
      begin
        open rVal for 'select * from logs';
        
        return rVal; 
      end;
      
      function get_customers return sys_refcursor
      is
      
      rVal sys_refcursor;
      
      begin
        open rVal for 'select * from customers';
        
        return rVal; 
      end;
      
      
      function get_employees return sys_refcursor
      is
      
      rVal sys_refcursor;
      
      begin
        open rVal for 'select * from employees';
        
        return rVal; 
      end;
      
      
      function get_products return sys_refcursor
      is
      
      rVal sys_refcursor;
      
      begin
        open rVal for 'select * from products';
        
        return rVal; 
      end;
      
      function get_suppliers return sys_refcursor
      is
      
      rVal sys_refcursor;
      
      begin
        open rVal for 'select * from suppliers';
        
        return rVal; 
      end;
      
      function get_supply return sys_refcursor
      is
      
      rVal sys_refcursor;
      
      begin
        open rVal for 'select * from supply';
        
        return rVal; 
      end;
      
      function report_monthly_sale(
      prod_id in purchases.PID%TYPE) return sys_refcursor
      is 
      rVal sys_refcursor;
      
      begin
        
        open rVal for 'select to_char(ptime,''MON'') as Month ,to_char(ptime,''YYYY'') as YEAR ,SUM(QTY) as TOTAL_QUANTITY,SUM(total_price) as TOTAL_PRICE,
        SUM(total_price)/SUM(QTY) as Average
        from purchases p
        where p.PID = :prod_id
        group by to_char(ptime,''MON''),to_char(PTIME,''YYYY'')' using prod_id;
        
        return rVal;
        
      end;
      
      
      PROCEDURE insert_product(
             pid_in IN products.PID%TYPE,
             pname_in IN products.pname%TYPE,
             qoh_in IN products.qoh%TYPE,
             qoh_threshold_in IN products.qoh_threshold%TYPE,
           original_price_in IN products.original_price%TYPE,
             discnt_rate_in IN products.discnt_rate%TYPE)
      
      AS
      BEGIN
      
        INSERT INTO products (pid, pname, qoh, qoh_threshold, original_price, discnt_rate)   
        VALUES (pid_in, pname_in, qoh_in, qoh_threshold_in, original_price_in, discnt_rate_in);
      
        COMMIT;
      
      END;
      
      
      
      
      PROCEDURE insert_purchase(
             eid_in IN purchases.EID%TYPE,
           pid_in IN purchases.PID%TYPE,
           cid_in IN purchases.CID%TYPE,
           qty_in IN purchases.QTY%TYPE)
      AS
      og_price NUMBER(6,2);
      d_rate NUMBER(3,2);
      total_price_in NUMBER(7,2);
      BEGIN
      
        SELECT original_price INTO og_price from products where products.pid = pid_in;
        SELECT discnt_rate INTO d_rate from products where products.pid = pid_in;
        total_price_in := (og_price - (og_price * d_rate)) * qty_in;
        INSERT INTO purchases (pur#, eid, pid, cid, qty, ptime, total_price)   
        VALUES (NULL, eid_in, pid_in, cid_in, qty_in, SYSDATE, total_price_in);
      
        COMMIT;
      
      END;

end;