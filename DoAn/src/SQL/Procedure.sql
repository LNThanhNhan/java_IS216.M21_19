/*********************************
1 - PROCEDURE: INSERT_HOTLINE
Procedure them nguoi dung hotline, khong tao tai khoan cho nguoi dung hotline
*********************************/
CREATE OR REPLACE PROCEDURE PROC_INSERT_HOTLINE (name_insert PERSON.name%type,
                                            gender_insert PERSON.gender%type,
                                            phone_insert PERSON.phone%type,
                                            province_insert PERSON.province%type,
                                            district_insert PERSON.district%type,
                                            town_insert PERSON.town%type,
                                            address_insert PERSON.address%type)
AS
BEGIN
    INSERT INTO PERSON(idper, name, gender, phone, province, district, town, address, status)
    VALUES (idper.nextval, name_insert,gender_insert, phone_insert, province_insert, district_insert, town_insert, address_insert,0);
    COMMIT;
END;
/
/*********************************
TABLE: PERSON
-2 - PROCEDURE: INSERT_PERSON
Procedure them nguoi gui yeu cau
*********************************/
CREATE OR REPLACE PROCEDURE PROC_INSERT_PERSON (username_insert ACCOUNT.username%TYPE,
                                            password_insert ACCOUNT.password%TYPE,                                            
                                            name_insert PERSON.name%TYPE,
                                            gender_insert PERSON.gender%TYPE,
                                            phone_insert PERSON.phone%TYPE,
                                            province_insert PERSON.province%TYPE,
                                            district_insert PERSON.district%TYPE,
                                            town_insert PERSON.town%TYPE,
                                            address_insert PERSON.address%TYPE)
AS
BEGIN
    INSERT INTO ACCOUNT(username, password, role)
    VALUES(username_insert, password_insert, 1);
    INSERT INTO PERSON(idper, username, name, gender, phone, province, district, town, address, status)
    VALUES(idper.nextval, username_insert, name_insert, gender_insert, phone_insert, province_insert, district_insert, town_insert, address_insert, 0);
    COMMIT;
END;
/


/*********************************
 TABLE: PERSON
3 - PROCEDURE: UPDATE_PERSON
Procedure chinh sua thong tin nguoi gui yeu cau
*********************************/
CREATE OR REPLACE PROCEDURE PROC_UPDATE_PERSON(idper_old PERSON.idper%TYPE,
                                            name_update PERSON.name%TYPE,
                                            gender_update PERSON.gender%TYPE,
                                            phone_update PERSON.phone%TYPE,
                                            province_update PERSON.province%TYPE,
                                            district_update PERSON.district%TYPE,
                                            town_update PERSON.town%TYPE,
                                            address_update PERSON.address%TYPE)
AS
BEGIN
    update PERSON
    set name = name_update, gender = gender_update , phone = phone_update, province = province_update, district = district_update, town = town_update, address = address_update
    where idper = idper_old;
    COMMIT;
     EXCEPTION WHEN NO_DATA_FOUND
        THEN RAISE_APPLICATION_ERROR(-20021, 'Khong tim thay id vua nhap!');
END;
/

--4 XOA NGUOI DUNG
CREATE OR REPLACE PROCEDURE PROC_DELETE_PERSON(ID_PERSON PERSON.IDPER%TYPE)
AS
    V_USERNAME PERSON.USERNAME%TYPE;
    WAITING_ADVISORY INT;
    WAITING_SUPPLY INT;
BEGIN 
--Kiem tra lan luot cac nguoi dung  trong he thong
--Neu nguoi dung nay dang co yeu cau nao dang o trang thai "Dang cho" thi khong the xoa nguoi dung nay
--Nguoc lai nguoi dung nay se bi xoa va xuat ra thong bao 'Xoa thanh cong'
    SELECT COUNT(*) INTO WAITING_SUPPLY  
    FROM SUPPLY 
    WHERE IDPER = ID_PERSON AND STATUS=3;
    SELECT COUNT(*) INTO WAITING_ADVISORY  
    FROM ADVISORY 
    WHERE IDPER = ID_PERSON AND STATUS=2;
    
    IF (WAITING_SUPPLY >0 ) 
    THEN
        RAISE_APPLICATION_ERROR(-20032,'Khong the xoa nguoi dung do con yeu cau tiep te chua hoan thanh');
    ELSIF(WAITING_ADVISORY >0)
    THEN
        RAISE_APPLICATION_ERROR(-20033,'Khong the xoa nguoi dung do con yeu cau tu van chua hoan thanh');
    ELSE 
        SELECT USERNAME INTO V_USERNAME
        FROM person
        WHERE IDPER = ID_PERSON;
        DELETE FROM SUPPLY WHERE IDPER = ID_PERSON ;
        DELETE FROM ADVISORY WHERE IDPER = ID_PERSON;
        DELETE FROM PERSON WHERE IDPER = ID_PERSON;
        DELETE FROM ACCOUNT  WHERE USERNAME = V_USERNAME;
        COMMIT;
    END IF;
END;
/
/*********************************
 TABLE: EMPLOYEE
5 - PROCEDURE: INSERT_EMPLOYEE
Procedure them thong tin nhan vien
*********************************/
CREATE OR REPLACE PROCEDURE PROC_INSERT_EMPLOYEE(username_insert ACCOUNT.username%TYPE,
                                            password_insert ACCOUNT.password%TYPE,
                                            name_insert EMPLOYEE.name%TYPE,
                                            gender_insert EMPLOYEE.gender%TYPE,
                                            startdate_insert EMPLOYEE.startdate%TYPE,
                                            phone_insert EMPLOYEE.phone%TYPE,
                                            address_insert EMPLOYEE.address%TYPE)
AS
BEGIN
    INSERT INTO ACCOUNT(username, password, role)
    VALUES(username_insert, password_insert, 2);
    INSERT INTO EMPLOYEE(idem, username, name, gender, startdate, phone, address)
    VALUES(idemp.nextval, username_insert, name_insert,gender_insert, startdate_insert, phone_insert, address_insert );
    COMMIT;
END;
/

/*********************************
 TABLE: EMPLOYEE
6 - PROCEDURE: UPDATE_EMPLOYEE
Procedure cap nhat thong tin nhan vien
*********************************/    
CREATE OR REPLACE PROCEDURE PROC_UPDATE_EMPLOYEE(idem_old in EMPLOYEE.idem%TYPE,
                                            name_update EMPLOYEE.name%TYPE,
                                            gender_update EMPLOYEE.gender%TYPE,
                                            startdate_update EMPLOYEE.startdate%TYPE,
                                            phone_update EMPLOYEE.phone%TYPE,
                                            address_update EMPLOYEE.address%TYPE)
AS
BEGIN
    UPDATE EMPLOYEE
    SET name = name_update, gender = gender_update, startdate = startdate_update, phone = phone_update, address = address_update
    WHERE idem = idem_old;
    COMMIT;           
     EXCEPTION WHEN NO_DATA_FOUND
      THEN  RAISE_APPLICATION_ERROR(-20051, 'Khong tim thay id vua nhap!');
END;
/


/*********************************
 TABLE: EMPLOYEE
7 - PROCEDURE: DELETE_EMPLOYEE
Procedure xoa thong tin nhan vien
*********************************/    
CREATE OR REPLACE PROCEDURE PROC_DELETE_EMPLOYEE(idem_old EMPLOYEE.idem%TYPE)
AS
    find_username EMPLOYEE.username%TYPE;
BEGIN
    
     -- Tim username cua nhan vien vua nhap
    SELECT username  into find_username
    FROM EMPLOYEE
    WHERE idem = idem_old;    
    
    -- xoa nhan vien
    DELETE FROM EMPLOYEE
    WHERE idem = idem_old;

    -- Xoa tai khoan nhan vien
    DELETE FROM ACCOUNT
    WHERE username = find_username ; 
    COMMIT; 
    ---Kiem tra dieu kien
        EXCEPTION WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20061,'ID vua nhap co the bi sai hoac du lieu nguoi dung khong ton tai');
 
END;
/


/*********************************
 TABLE: CHARITY
8 - PROCEDURE: INSERT_CHARITY
Procedure them thong tin trung tam thien nguyen
*********************************/  
CREATE OR REPLACE PROCEDURE PROC_INSERT_CHARITY (username_insert account.username%type,
                                            password_insert account.password%type,
                                            name_insert charity.name%type,
                                            phone_insert charity.phone%type,
                                            province_insert charity.province%type,
                                            district_insert charity.district%type,
                                            town_insert charity.town%type,
                                            address_insert charity.address%type,
                                            hasfood_insert charity.hasfood%type,
                                            hasnecess_insert charity.hasnecess%type,
                                            hasequip_insert charity.hasequip%type)
AS
BEGIN
    INSERT INTO ACCOUNT(USERNAME, PASSWORD, ROLE)
    VALUES(username_insert,password_insert,3 );
    insert into charity(idchar, username, name, phone, province, district, town, address, hasfood, hasnecess, hasequip, point)
    values (idchar.nextval, username_insert, name_insert, phone_insert, province_insert, district_insert,town_insert, address_insert, hasfood_insert, hasnecess_insert, hasequip_insert,0);  
    COMMIT; 
END;
/

/*********************************
 TABLE: CHARITY
9 - PROCEDURE: UPDATE_CHARITY
Procedure cap nhat thong tin trung tam thien nguyen
*********************************/  
CREATE OR REPLACE PROCEDURE PROC_UPDATE_CHARITY(idchar_old in CHARITY.idchar%TYPE,
                                            name_update CHARITY.name%TYPE,
                                            phone_update CHARITY.phone%TYPE,
                                            province_update CHARITY.province%TYPE,
                                            district_update CHARITY.district%TYPE,
                                            town_update CHARITY.town%TYPE,
                                            address_update CHARITY.address%TYPE,
                                            hasfood_update CHARITY.hasfood%TYPE,
                                            hasnecess_update CHARITY.hasnecess%TYPE,
                                            hasequip_update CHARITY.hasequip%TYPE)                                           
AS
BEGIN
    update CHARITY
    set name = name_update, phone = phone_update, province = province_update, district = district_update, town = town_update, address = address_update, hasfood = hasfood_update, hasnecess = hasnecess_update, hasequip = hasequip_update
    where idchar = idchar_old;
    COMMIT; 
     EXCEPTION WHEN NO_DATA_FOUND
        THEN RAISE_APPLICATION_ERROR(-20081, 'Khong tim thay id vua nhap!');
END;
/

/*********************************
 TABLE: CHARITY
10 - PROCEDURE: DELETE_CHARITY
Procedure xoa thong tin trung tam thien nguyen
*********************************/  
CREATE OR REPLACE PROCEDURE PROC_DELETE_CHARITY(idchar_old CHARITY.idchar%TYPE)
AS
    WAITING_SUPPLY INT;
    CHAR_USERNAME CHARITY.USERNAME%TYPE;
BEGIN
    SELECT USERNAME INTO CHAR_USERNAME
    FROM charity
    WHERE IDCHAR=idchar_old;
    SELECT COUNT(*) INTO  WAITING_SUPPLY 
    FROM supply
    WHERE IDCHAR=idchar_old AND STATUS=3;
    IF(WAITING_SUPPLY > 0)
    THEN 
        RAISE_APPLICATION_ERROR(-20093, 'Khong the xoa do trung tam dang nhan yeu cau tiep te chua duoc hoan thanh');
    ELSE
    -- Xoa yeu cau tiep te ma trung tam nay nhan
        DELETE FROM SUPPLY
        WHERE idchar = idchar_old;
    -- Xoa trung tam va tai khoan cua trung tam
        DELETE FROM CHARITY
        WHERE idchar = idchar_old;
        DELETE FROM ACCOUNT
        WHERE username = CHAR_USERNAME;
        COMMIT;
    END IF;
END;
/

--procedure

---11 THEM BAC SI - CACH 2: KHONG CO ROLE

CREATE OR REPLACE PROCEDURE PROC_INSERT_DOCTOR(USERNAME_DOC DOCTOR.USERNAME%TYPE, PASS_WORD ACCOUNT.PASSWORD%TYPE,
    NAME_DOC DOCTOR.NAME%TYPE,GENDER_DOC DOCTOR.GENDER%TYPE, 
    PHONE_DOC DOCTOR.PHONE%TYPE, ACADEMICRANK_DOC DOCTOR.ACADEMICRANK%TYPE, SUBJECT_DOC DOCTOR.SUBJECT%TYPE,
    WORKUNITS_DOC DOCTOR.WORKUNITS%TYPE, PROVINCE_DOC DOCTOR.PROVINCE%TYPE)
AS
BEGIN
    INSERT INTO ACCOUNT(USERNAME, PASSWORD, ROLE) VALUES (USERNAME_DOC, PASS_WORD, 4);
    INSERT INTO DOCTOR (iddoc, USERNAME, NAME, GENDER, PHONE, ACADEMICRANK, SUBJECT, WORKUNITS, PROVINCE) 
    VALUES (iddoc.nextval, USERNAME_DOC, NAME_DOC, GENDER_DOC, PHONE_DOC, ACADEMICRANK_DOC, SUBJECT_DOC, WORKUNITS_DOC, PROVINCE_DOC);
    COMMIT; 
END;
/

--12 SUA BAC SI--
CREATE OR REPLACE PROCEDURE PROC_UPDATE_DOCTOR(ID_DOC DOCTOR.IDDOC%TYPE, NAME_DOC DOCTOR.NAME%TYPE, GENDER_DOC DOCTOR.GENDER%TYPE, 
    PHONE_DOC DOCTOR.PHONE%TYPE, ACADEMICRANK_DOC DOCTOR.ACADEMICRANK%TYPE, SUBJECT_DOC DOCTOR.SUBJECT%TYPE,
    WORKUNITS_DOC DOCTOR.WORKUNITS%TYPE, PROVINCE_DOC DOCTOR.PROVINCE%TYPE)
AS
BEGIN UPDATE DOCTOR SET 
    NAME = NAME_DOC,
    GENDER = GENDER_DOC,
    PHONE = PHONE_DOC,
    ACADEMICRANK = ACADEMICRANK_DOC,
    SUBJECT = SUBJECT_DOC,
    WORKUNITS = WORKUNITS_DOC,
    PROVINCE = PROVINCE_DOC
    WHERE IDDOC= ID_DOC;
    COMMIT; 
END;

/
--13 XOA BAC SI--
CREATE OR REPLACE PROCEDURE PROC_DELETE_DOCTOR(ID_DOC DOCTOR.IDDOC%TYPE)
AS
    WAITING_ADVISORY INT;
    DOC_USERNAME DOCTOR.USERNAME%TYPE;
BEGIN
    SELECT USERNAME INTO DOC_USERNAME
    FROM DOCTOR
    WHERE IDDOC=ID_DOC;
    SELECT COUNT(*) INTO  WAITING_ADVISORY
    FROM ADVISORY
    WHERE IDDOC=ID_DOC and STATUS=2;
    IF(WAITING_ADVISORY > 0)
    THEN 
        RAISE_APPLICATION_ERROR(-20122, 'Khong the xoa do bac si dang nhan yeu cau tu van chua duoc hoan thanh');
    ELSE
    -- Xoa yeu cau tu van ma bac si nay nhan
        DELETE FROM ADVISORY
        WHERE IDDOC=ID_DOC;
    -- Xoa bac si va tai khoan cua bac si
        DELETE FROM DOCTOR
        WHERE IDDOC=ID_DOC;
        DELETE FROM ACCOUNT
        WHERE username = DOC_USERNAME;
        COMMIT;
    END IF;
END;

/

--14 THEM TIEP TE--
CREATE OR REPLACE PROCEDURE PROC_INSERT_SUPPLY(ID_PER SUPPLY.IDPER%TYPE, NEED_FOOD SUPPLY.NEEDFOOD%TYPE, NEED_NECESS SUPPLY.NEEDNECESS%TYPE,
    NEED_EQUIP SUPPLY.NEEDEQUIP%TYPE, DETAIL_SUP SUPPLY.DETAIL%TYPE )
AS
BEGIN
    INSERT INTO SUPPLY (IDSUP, IDPER, NEEDFOOD, NEEDNECESS, NEEDEQUIP, DETAIL) VALUES (idsup.nextval, ID_PER, NEED_FOOD, NEED_NECESS, NEED_EQUIP, DETAIL_SUP);
END;
/


--15 SUA TIEP TE CHO NHAN VIEN--
CREATE OR REPLACE PROCEDURE PROC_UPDATE_SUPPLY(ID_SUP SUPPLY.IDSUP%TYPE, NEED_FOOD SUPPLY.NEEDFOOD%TYPE, NEED_NECESS SUPPLY.NEEDNECESS%TYPE,
    NEED_EQUIP SUPPLY.NEEDEQUIP%TYPE, DETAIL_SUP SUPPLY.DETAIL%TYPE)
AS
    v_STATUS SUPPLY.STATUS%TYPE;
BEGIN
--Lay thong tin ve trang thai hien tai cua yeu cau
--Dong thoi kiem tra xem yeu cau ton tai trong he thong khong
    SELECT STATUS INTO v_STATUS FROM SUPPLY WHERE IDSUP = ID_SUP;
    IF v_STATUS != 1 THEN
         RAISE_APPLICATION_ERROR(-20142, 'Trang thai don yeu cau khong hop le');
    ELSE
    UPDATE SUPPLY SET 
        NEEDFOOD = NEED_FOOD,
        NEEDNECESS = NEED_NECESS,
        NEEDEQUIP = NEED_EQUIP,
        DETAIL = DETAIL_SUP
    WHERE IDSUP = ID_SUP ;
    END IF;
    COMMIT; 
    EXCEPTION 
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20143,'Yeu cau khong ton tai');
END;
/

--16 XOA TIEP TE--
CREATE OR REPLACE PROCEDURE PROC_DELETE_SUPPLY(ID_SUP SUPPLY.IDSUP%TYPE)
AS
    v_STATUS SUPPLY.STATUS%TYPE;
BEGIN
--Lay thong tin ve trang thai hien tai cua yeu cau
--Dong thoi kiem tra xem yeu cau ton tai trong he thong khong
    SELECT STATUS INTO v_STATUS FROM SUPPLY WHERE IDSUP = ID_SUP;
    IF v_STATUS != 1 THEN
        RAISE_APPLICATION_ERROR(-20152,'Trang thai don yeu cau khong hop le');
    ELSE
    DELETE FROM SUPPLY WHERE IDSUP = ID_SUP;
    END IF;
    COMMIT; 
    EXCEPTION 
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20153,'Yeu cau khong ton tai');
END;

/

--17 THEM TU VAN-- 
CREATE OR REPLACE PROCEDURE PROC_INSERT_ADVISORY(ID_PER ADVISORY.IDPER%TYPE, YEAR_BIRTH ADVISORY.YEARBIRTH%TYPE,
    HEIGHT_AD ADVISORY.HEIGHT%TYPE, WEIGHT_AD ADVISORY.WEIGHT%TYPE,
    PASTMEDICALHISTORY_AD ADVISORY.PASTMEDICALHISTORY%TYPE, DETAIL_AD ADVISORY.DETAIL%TYPE)
AS
BEGIN
    INSERT INTO ADVISORY (IDAD, IDPER, YEARBIRTH, HEIGHT, WEIGHT, PASTMEDICALHISTORY, DETAIL) VALUES (idad.nextval, ID_PER, YEAR_BIRTH, HEIGHT_AD, WEIGHT_AD, PASTMEDICALHISTORY_AD, DETAIL_AD);
END;
/


--18 SUA TU VAN CHO NGUOI DUNG--
CREATE OR REPLACE PROCEDURE PROC_UPDATE_ADVISORY(ID_AD ADVISORY.IDAD%TYPE, YEAR_BIRTH ADVISORY.YEARBIRTH%TYPE,
    HEIGHT_AD ADVISORY.HEIGHT%TYPE, WEIGHT_AD ADVISORY.WEIGHT%TYPE,
    PASTMEDICALHISTORY_AD ADVISORY.PASTMEDICALHISTORY%TYPE, DETAIL_AD ADVISORY.DETAIL%TYPE)
AS
    v_STATUS ADVISORY.STATUS%TYPE;
BEGIN
--Lay thong tin ve trang thai hien tai cua yeu cau
--Dong thoi kiem tra xem yeu cau ton tai trong he thong khong
    SELECT STATUS INTO v_STATUS FROM ADVISORY WHERE IDAD = ID_AD;
    IF v_STATUS != 1 THEN
        RAISE_APPLICATION_ERROR(-20172,'Trang thai don yeu cau khong hop le');
    ELSE
    UPDATE ADVISORY SET 
        YEARBIRTH = YEAR_BIRTH,
        HEIGHT = HEIGHT_AD,
        WEIGHT = WEIGHT_AD,
        PASTMEDICALHISTORY = PASTMEDICALHISTORY_AD,
        DETAIL = DETAIL_AD
    WHERE IDAD = ID_AD ;
    COMMIT;
    END IF;    
    EXCEPTION 
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20173,'Yeu cau khong ton tai');
END;
/

--19 XOA TU VAN CHO NGUOI DUNG--
CREATE OR REPLACE PROCEDURE PROC_DELETE_ADVISORY(ID_AD ADVISORY.IDAD%TYPE)
AS
    v_STATUS ADVISORY.STATUS%TYPE;
BEGIN
--Lay thong tin ve trang thai hien tai cua yeu cau
--Dong thoi kiem tra xem yeu cau ton tai trong he thong khong
    SELECT STATUS INTO v_STATUS FROM ADVISORY WHERE IDAD = ID_AD;
    IF v_STATUS != 1 THEN
        RAISE_APPLICATION_ERROR(-20182,'Trang thai don yeu cau khong hop le');
    ELSE
    DELETE FROM ADVISORY WHERE IDAD = ID_AD;
    COMMIT;
    END IF;
     
    EXCEPTION 
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20183,'Yeu cau khong ton tai');
END;

/

--20 XAC THUC YEU CAU --
CREATE OR REPLACE PROCEDURE PROC_DENY_SUPPLY(ID_SUP SUPPLY.IDSUP%TYPE)
AS
    v_STATUS SUPPLY.STATUS%TYPE;
BEGIN
--Lay thong tin ve trang thai hien tai cua yeu cau
--Dong thoi kiem tra xem yeu cau ton tai trong he thong khong
    SELECT STATUS INTO v_STATUS FROM SUPPLY WHERE IDSUP = ID_SUP ;
    IF v_STATUS !=1 THEN 
        RAISE_APPLICATION_ERROR(-20192,'Trang thai don yeu cau khong hop le');
    ELSE
        UPDATE SUPPLY SET STATUS= 2 WHERE IDSUP = ID_SUP;
        COMMIT;
    END IF;
     
    EXCEPTION 
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20193,'Yeu cau khong ton tai');
END;

/

--21 HUY YEU CAU TIEP TE CHO NHAN VIEN--
CREATE OR REPLACE PROCEDURE PROC_DENY_SUPPLY(ID_SUP SUPPLY.IDSUP%TYPE)
AS
    v_STATUS SUPPLY.STATUS%TYPE;
BEGIN
--Lay thong tin ve trang thai hien tai cua yeu cau
--Dong thoi kiem tra xem yeu cau ton tai trong he thong khong
    SELECT STATUS INTO v_STATUS FROM SUPPLY WHERE IDSUP = ID_SUP ;
    IF v_STATUS !=1 THEN 
        RAISE_APPLICATION_ERROR(-20202,'Trang thai don yeu cau khong hop le');
    ELSE
        UPDATE SUPPLY SET STATUS= 5 WHERE IDSUP = ID_SUP;
        COMMIT;
    END IF;
     
    EXCEPTION 
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20203,'Yeu cau khong ton tai');
END;

/

--22 CHAP NHAN YEU CAU TIEP TE--
CREATE OR REPLACE PROCEDURE PROC_ACCEPT_SUPPLY(ID_CHAR CHARITY.IDCHAR%TYPE, ID_SUP SUPPLY.IDSUP%TYPE)
AS
v_STATUS SUPPLY.STATUS%TYPE;
--Bat loi vi pham khoa ngoai
--De biet trung tam co ton tai khong
CONSTRAINT_FOREIGN_VIOLATED EXCEPTION;
PRAGMA EXCEPTION_INIT(CONSTRAINT_FOREIGN_VIOLATED, -20212);
BEGIN
--Lay thong tin ve trang thai hien tai cua yeu cau
--Dong thoi kiem tra xem yeu cau ton tai trong he thong khong
    SELECT STATUS INTO v_STATUS FROM SUPPLY WHERE IDSUP = ID_SUP ;
    IF v_STATUS !=2 THEN 
        RAISE_APPLICATION_ERROR(-20213,'Trang thai don yeu cau khong hop le');
    ELSE
        UPDATE SUPPLY SET STATUS= 3, IDCHAR=ID_CHAR WHERE IDSUP = ID_SUP;
        COMMIT;
    END IF;
     
    EXCEPTION 
    WHEN CONSTRAINT_FOREIGN_VIOLATED THEN 
        RAISE_APPLICATION_ERROR(-20214,'Trung tam khong ton tai');

    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20215,'Yeu cau khong ton tai');
END;
/


--23 HUY YEU CAU TIEP TE CHO TRUNG TAM--
CREATE OR REPLACE PROCEDURE PROC_CANCEL_SUPPLY(ID_CHAR CHARITY.IDCHAR%TYPE, ID_SUP SUPPLY.IDSUP%TYPE)
AS
v_STATUS SUPPLY.STATUS%TYPE;
--Bat loi vi pham khoa ngoai
--De biet trung tam co ton tai khong
CONSTRAINT_FOREIGN_VIOLATED EXCEPTION;
PRAGMA EXCEPTION_INIT(CONSTRAINT_FOREIGN_VIOLATED, -20222);
BEGIN
--Lay thong tin ve trang thai hien tai cua yeu cau
--Dong thoi kiem tra xem yeu cauton tai trong he thong khong
    SELECT STATUS INTO v_STATUS FROM SUPPLY WHERE IDSUP = ID_SUP ;
    IF v_STATUS !=3 THEN 
        RAISE_APPLICATION_ERROR(-20223,'Trang thai don yeu cau khong hop le');
    ELSE
        UPDATE SUPPLY SET STATUS= 5, IDCHAR=ID_CHAR WHERE IDSUP = ID_SUP;
        COMMIT;
    END IF;    
    EXCEPTION 
    WHEN CONSTRAINT_FOREIGN_VIOLATED THEN 
       RAISE_APPLICATION_ERROR(-20224,'Trung tam khong ton tai');

    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20225,'Yeu cau khong ton tai');
END;
/



--25
create or replace PROCEDURE PROC_ACCEPT_ADVISORY(ID_DOC DOCTOR.IDDOC%TYPE, ID_AD ADVISORY.IDAD%TYPE)
AS
v_STATUSAD ADVISORY.STATUS%TYPE;

BEGIN
--Lay thong tin ve trang thai hien tai cua yeu cau
--Dong thoi kiem tra xem yeu cauton tai trong he thong khong
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
    SELECT STATUS INTO v_STATUSAD 
    FROM ADVISORY 
    WHERE IDAD = ID_AD
    FOR UPDATE;
    IF v_STATUSAD =1 THEN 
        UPDATE ADVISORY SET STATUS= 2, IDDOC=ID_DOC WHERE IDAD = ID_AD;
    ELSE
        RAISE_APPLICATION_ERROR(-20241, 'Trang thai don yeu cau khong hop le');
    END IF;
    EXCEPTION 
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20242, 'Yeu cau khong ton tai');
END;

/
--26

create or replace PROCEDURE PROC_FINISH_ADVISORY(ID_AD ADVISORY.IDAD%TYPE)
AS
v_STATUSAD ADVISORY.STATUS%TYPE;

BEGIN
--Lay thong tin ve trang thai hien tai cua yeu cau
--Dong thoi kiem tra xem yeu cau ton tai trong he thong khong
    SELECT STATUS INTO v_STATUSAD FROM ADVISORY WHERE IDAD = ID_AD ;
    IF v_STATUSAD =2 THEN 
        UPDATE ADVISORY SET STATUS= 3 WHERE IDAD = ID_AD;
        COMMIT;
    ELSE
        RAISE_APPLICATION_ERROR(-20241, 'Trang thai yeu cau khong hop le');
    END IF;
--Xuat thong bao khi khong co yeu cau tu van
    EXCEPTION 
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20242, 'Yeu cau khong ton tai');
END;
/
--27
create or replace PROCEDURE PROC_CHANGE_PASSWORD (ACC_USERNAME ACCOUNT.USERNAME%TYPE, 
                                             ACC_OLD_PASS ACCOUNT.PASSWORD%TYPE, 
                                             ACC_NEW_PASS ACCOUNT.PASSWORD%TYPE)
AS
    V_USERNAME ACCOUNT.USERNAME%TYPE;
    V_PASS ACCOUNT.PASSWORD%TYPE;
BEGIN
    -- Tim trong databse ten tai khoan trung voi ten tai khoan duoc nhap, sau do gan gia tri vao bien V_USERNAME 
        SELECT USERNAME INTO V_USERNAME 
        FROM ACCOUNT 
        WHERE USERNAME = ACC_USERNAME;
    -- Tim trong databse mat khau trung voi mat khau cua tai khoan duoc nhap, sau do gan gia tri vao bien V_PASS
        SELECT PASSWORD INTO V_PASS 
        FROM ACCOUNT 
        WHERE USERNAME = ACC_USERNAME;     

    --Kiem tra mat khau nhap vao co dung mat khau hien tai khong
    IF (V_PASS != ACC_OLD_PASS)
        THEN RAISE_APPLICATION_ERROR(-20103, 'Sai mat khau hien tai');
    -- Kiem tra mat khau vua nhap co trung voi mat khau xac nhan khong
    -- Kiem tra mat khau vua nhap trung voi mat khau cu khong
    ELSIF ( V_PASS = ACC_NEW_PASS)
        THEN RAISE_APPLICATION_ERROR(-20104, 'Mat khau moi da trung voi mat khau cu');
    -- Neu khong trung thi cap nhat mat khau moi cho nguoi dung 
    ELSE    
        UPDATE ACCOUNT 
        SET PASSWORD = ACC_NEW_PASS
        WHERE USERNAME = ACC_USERNAME;
        COMMIT;
    END IF;
    --Truong hop ten tai khoan, mat khau duoc nhap chua ton tai trong he thong
    EXCEPTION WHEN NO_DATA_FOUND
        THEN RAISE_APPLICATION_ERROR(-20106,'Khong co nguoi dung vua nhap hoac mat khau da bi nhap sai!');
END;
/
--28 Procedure nay dung de xoa cac yeu cau bi qua han thoi gian cho
CREATE OR REPLACE PROCEDURE PROC_DELETE_OVERDUE 
AS
    v_SUPPLY SUPPLY%ROWTYPE;
    v_ADVISORY ADVISORY%ROWTYPE;  
BEGIN   
    FOR v_SUPPLY IN ( SELECT CREATED, STATUS, IDSUP 
                    FROM SUPPLY  )
    LOOP
    IF (v_SUPPLY.STATUS=2 AND ((sysdate) - to_date(v_SUPPLY.Created, 'DD-MON-YY')>3))
    THEN
        DELETE FROM SUPPLY WHERE IDSUP = v_SUPPLY.IDSUP;
        DBMS_OUTPUT.PUT_LINE('DON TIEP TE ' || v_SUPPLY.IDSUP || ' DA XOA');
        COMMIT;
    END IF;
    END LOOP;

    FOR v_ADVISORY IN ( SELECT CREATED, STATUS, IDAD
                    FROM ADVISORY )
    LOOP
    IF (v_ADVISORY.STATUS != 2 AND ((sysdate) - to_date(v_ADVISORY.Created, 'DD-MON-YY')>3))
    THEN
        DELETE FROM ADVISORY WHERE IDAD = v_ADVISORY.IDAD;
        DBMS_OUTPUT.PUT_LINE('DON TU VAN ' || v_ADVISORY.IDAD || ' DA XOA');
        COMMIT;
    END IF;
    END LOOP;
END;
/


--29 HAM TINH DIEM HOAT DONG CHO TRUNG TAM
CREATE OR REPLACE FUNCTION FUNC_CALCULATE_POINT(ID_CHAR CHARITY.IDCHAR%TYPE)
RETURN int
AS
    total_point int;
    v_idsup SUPPLY.IDSUP%TYPE;
    --Tao con tro lay tat ca yeu cau tiep te ma trung tam 
    --hoan thanh trong nam, cac yeu cau khong thuoc 
    --tai khoan khoa tao
    CURSOR ALL_VALID_SUPPLY IS 
    SELECT  IDSUP
    FROM SUPPLY S
    JOIN PERSON P
    ON S.IDPER=P.IDPER
    WHERE P.STATUS=0 
    AND S.STATUS=4
    AND IDCHAR=ID_CHAR
    AND EXTRACT(YEAR FROM SYSDATE)= EXTRACT(YEAR FROM CREATED);
BEGIN
    --tao bien tong diem de tinh diem
    total_point:=0;
    open ALL_VALID_SUPPLY;
    LOOP
        --duyet qua tung yeu cau de tinh diem
        FETCH ALL_VALID_SUPPLY INTO v_idsup;
        EXIT WHEN ALL_VALID_SUPPLY%NOTFOUND;
        DECLARE 
            V_NEEDFOOD SUPPLY.NEEDFOOD%TYPE;
            V_NEEDNECESS SUPPLY.NEEDNECESS%TYPE;
            V_NEEDEQUIP SUPPLY.NEEDNECESS%TYPE;
        BEGIN
        SELECT NEEDFOOD,NEEDNECESS,NEEDEQUIP INTO V_NEEDFOOD,V_NEEDNECESS,V_NEEDEQUIP
        FROM SUPPLY
        WHERE IDSUP=v_idsup;
        --Tinh diem tuong ung doi voi tung loai yeu cau tiep te
        --ma  trung tam da dap ung 
        total_point:=total_point+10*(V_NEEDFOOD+V_NEEDNECESS+V_NEEDEQUIP);
        END;
    END LOOP;
    CLOSE ALL_VALID_SUPPLY;
    --tra ve tong diem hien tai cua trung tam trong nam
    RETURN total_point;
END;
/

--30 Ham SLEEP de gia lap tre tren ung dung
CREATE OR REPLACE PROCEDURE sleep (in_time number)
AS
 v_now date;
BEGIN
 SELECT SYSDATE
 INTO v_now
 FROM DUAL;

 LOOP
 EXIT WHEN v_now + (in_time * (1/86400)) <= SYSDATE;
 END LOOP;
end;
/
--24 HOAN THANH YEU CAU TIEP TE--
CREATE OR REPLACE PROCEDURE PROC_FINISH_SUPPLY(ID_SUP SUPPLY.IDSUP%TYPE)
AS
    v_STATUS SUPPLY.STATUS%TYPE;
    v_IDCHAR SUPPLY.IDCHAR%TYPE;
    V_NEEDFOOD SUPPLY.NEEDFOOD%TYPE;
    V_NEEDNECESS SUPPLY.NEEDNECESS%TYPE;
    V_NEEDEQUIP SUPPLY.NEEDNECESS%TYPE;
BEGIN
--Lay thong tin ve trang thai hien tai cua yeu cau
--Dong thoi kiem tra xem yeu cau ton tai trong he thong khong
    SELECT STATUS,NEEDFOOD,NEEDNECESS,NEEDEQUIP
    INTO v_STATUS,V_NEEDFOOD,V_NEEDNECESS,V_NEEDEQUIP
    FROM SUPPLY WHERE IDSUP = ID_SUP ;
    SELECT IDCHAR INTO v_IDCHAR FROM SUPPLY WHERE IDSUP = ID_SUP ;
    IF v_STATUS !=3 THEN 
        RAISE_APPLICATION_ERROR(-20232,'Trang thai don yeu cau khong hop le');
    ELSE
        UPDATE CHARITY 
        SET POINT = FUNC_CALCULATE_POINT(v_IDCHAR)+10*(V_NEEDFOOD+V_NEEDNECESS+V_NEEDEQUIP) 
        WHERE IDCHAR = v_IDCHAR;
        UPDATE SUPPLY SET STATUS= 4 WHERE IDSUP = ID_SUP;
        COMMIT;
    END IF;    
    EXCEPTION 
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20234,'Yeu cau khong ton tai');
END;

/
