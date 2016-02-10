create or replace trigger SUP#_insert_trigger
before insert on SUPPLY
for each row
begin 

:new.SUP# := SUP#_seq.nextval;

end;
/

create or replace trigger logs_insert_trigger
before insert on LOGS
for each row
begin 

:new.LOG# := LOGS_seq.nextval;

end;
/


create or replace trigger purchase_insert_trigger
before insert on PURCHASES
for each row
begin 

:new.PUR# := purchases_seq.nextval;

end;
/


create or replace trigger purchase_insert_trigger_log
after insert on PURCHASES
for each row
begin 

insert into LOGS(WHO,OTIME,TABLE_NAME,OPERATION,KEY_VALUE) values (user,sysdate,'PURCHASES','INSERT',:new.PUR#);

end;
/


create or replace trigger products_update_trigger_log
after update of QOH on PRODUCTS
for each row
begin 

insert into LOGS(WHO,OTIME,TABLE_NAME,OPERATION,KEY_VALUE) values (user,sysdate,'PRODUCTS','UPDATE',:new.PID);

end;
/


create or replace trigger customers_update_trigger_log
after update of visits_made on CUSTOMERS
for each row
begin 

insert into LOGS(WHO,OTIME,TABLE_NAME,OPERATION,KEY_VALUE) values (user,sysdate,'CUSTOMERS','UPDATE',:new.CID);

end;
/


create or replace trigger supply_insert_trigger_log
after insert on SUPPLY
for each row
begin 

insert into LOGS(WHO,OTIME,TABLE_NAME,OPERATION,KEY_VALUE) values (user,sysdate,'SUPPLY','INSERT',:new.SUP#);

end;
/

create or replace trigger qoh_sufficient
before insert on purchases
for each row

declare 
qoh_after NUMBER(5,0);
current_qoh NUMBER(5,0);

begin
  select qoh into current_qoh from products where pid = :new.pid;
  qoh_after := current_qoh - :new.qty;
  IF (qoh_after < 0) THEN 
        raise_application_error( -20001, 'Insufficient quality in stock.');
    END IF; 
end;
/


create or replace trigger update_QOH_after_purchase
after insert on PURCHASES
for each row

declare
QOH_V number(5,0);
QOH_THRESHOLD_V number(4,0);
suplier char(2);
mins number(5,0);
quantity_ordered number(5,0);
NEW_QOH number(5,0);

begin

update PRODUCTS set QOH = (QOH - :new.QTY) WHERE PRODUCTS.PID = :new.PID;

select QOH into QOH_V from PRODUCTS p where p.PID = :new.PID;
select QOH_THRESHOLD into QOH_THRESHOLD_V from PRODUCTS p where p.PID = :new.PID;

if (QOH_V < QOH_THRESHOLD_V) then

  SYS.DBMS_OUTPUT.PUT_LINE('The QOH of '||:new.PID||' has fallen below the threshold and new inventory is required.');

  select MIN(SUPPLY.SID) into suplier from SUPPLY where SUPPLY.PID = :new.PID;
  mins := (QOH_THRESHOLD_V - QOH_V)+1;
  quantity_ordered := 10+mins+QOH_V;
  insert into SUPPLY(PID,SID,SDATE,QUANTITY) values (:new.PID,suplier,sysdate,quantity_ordered);
    
  update PRODUCTS set QOH = QOH + quantity_ordered WHERE PRODUCTS.PID = :new.PID;

  select distinct QOH into NEW_QOH from PRODUCTS p where p.PID = :new.PID;
  
end if;

end;
/


create or replace trigger QOH_INCREASE_TRIGGER_PRINT
after update of QOH on PRODUCTS
for each row
begin

if :new.QOH > :old.QOH then

 SYS.DBMS_OUTPUT.PUT_LINE('The new Quantity is '||:new.QOH||' for '||:new.PID);

end if;

end;
/


create or replace trigger update_last_visit
before insert on purchases
for each row

declare
l_visit char(10);
v_made NUMBER(4,0);

begin
  select to_char(last_visit_date,'MM-YYYY-DD') into l_visit from customers where customers.cid = :new.cid;
  
  IF (to_char(:new.ptime, 'MM-YYYY-DD') != l_visit) THEN
    update customers
    set last_visit_date = :new.ptime
    where customers.cid = :new.cid;
   
    select visits_made into v_made from customers where customers.cid = :new.cid;
    
    update customers
    set visits_made = v_made + 1
    where customers.cid = :new.cid;
    END IF;
end;
