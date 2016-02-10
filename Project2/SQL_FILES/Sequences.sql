drop sequence SUP#_seq;
drop sequence LOGS_seq;
drop sequence purchases_seq;

/*
Creating sequence for supply numbers
*/
create sequence SUP#_seq
increment by 1
start with 1000
maxvalue 9999
minvalue 1000
order;

/*
Creating sequence for log numbers
*/
create sequence LOGS_seq
increment by 1
start with 10000
maxvalue 99999
minvalue 10000
order;

/*
Creating sequence for purchase numbers
*/
create sequence purchases_seq
increment by 1
start with 100000
maxvalue 999999
minvalue 100000
order;
