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
    
INSERT INTO employee VALUES (1, 'user21', 'Nguyen Thi Ly', 0, TO_DATE('14/06/2021','dd/mm/yyyy'), '0984626764', '56/11-CHUONG DUONG- LINH CHIEU- THU DUC');
INSERT INTO employee VALUES (2, 'user22', 'Nguyen Thi Xoa', 0, TO_DATE('20/10/2021','dd/mm/yyyy'), '0378574588', '29/1B DUONG 16- TO 2- KP1- LINH CHIEU- THU DUC');
INSERT INTO employee VALUES (3, 'user23', 'Tran Thi Uyen', 0, TO_DATE('26/09/2021','dd/mm/yyyy'), '0862121185', '1034/3 TO 8- KP1-  LINH CHIEU- THU DUC');
INSERT INTO employee VALUES (4, 'user24', 'Nguyen Bui Thuy Vy' , 0, TO_DATE('13/11/2021','dd/mm/yyyy'), '0332448466', '1104/4A KHA VAN CAN- LINH CHIEU- THU DUC');
INSERT INTO employee VALUES (5, 'user25', 'Than Trong Thien An', 1, TO_DATE('22/03/2021','dd/mm/yyyy'), '0865101714', '1046 KHA VAN CAN-  LINH CHIEU- THU DUC');
INSERT INTO employee VALUES (6, 'user26', 'Ngo Thi Thoa' , 0, TO_DATE('23/04/2021','dd/mm/yyyy'), '0348425066', '1/1096 KHA VAN CAN-  LINH CHIEU- THU DUC');
INSERT INTO employee VALUES (7, 'user27', 'Dang Thi Ngoc Yen', 0, TO_DATE('14/12/2021','dd/mm/yyyy'), '0354306079', '28/33 DUONG 16- LINH CHIEU- THU DUC');
INSERT INTO employee VALUES (8, 'user28', 'Truong Thi Mang', 0, TO_DATE('28/05/2021','dd/mm/yyyy'), '0368334441', '37A DUONG 17-  LINH CHIEU- THU DUC');
INSERT INTO employee VALUES (9, 'user29', 'Nguyen Thi Chi Mai', 0, TO_DATE('22/05/2021','dd/mm/yyyy'), '0374916079', '56 HOANG DIEU 2- LINH CHIEU- THU DUC');
INSERT INTO employee VALUES (10, 'user30', 'Nguyen Mai Quynh', 0, TO_DATE('12/05/2021','dd/mm/yyyy'), '0348606057', '34/9 DUONG 16- LINH CHIEU- THU DUC');
