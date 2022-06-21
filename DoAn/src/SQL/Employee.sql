CREATE TABLE employee (
    idem      NUMBER(10)    NOT NULL,
    username  VARCHAR2(32),
    name      VARCHAR2(50)  NOT NULL,
    gender    SMALLINT      NOT NULL,
    startdate DATE          NOT NULL,
    phone     VARCHAR2(10)  NOT NULL,
    address   VARCHAR2(200) NOT NULL,
    CONSTRAINT employee_pk PRIMARY KEY ( idem )
);
--RANG BUOC 
ALTER TABLE employee
    ADD CONSTRAINT fk_employee FOREIGN KEY ( username )
        REFERENCES account ( username );

ALTER TABLE employee
    ADD CONSTRAINT username_emp UNIQUE(username);
ALTER TABLE employee
    ADD CONSTRAINT gender_emp CHECK(gender in(0,1));
ALTER TABLE employee
    ADD CONSTRAINT phone_emp UNIQUE(phone);
    
--sequence cho ide employee
create sequence idemp
    start with 11
    increment by 1
    nocache
    nocycle;
    