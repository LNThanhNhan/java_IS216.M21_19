
--R1: “Thuc hien khóa tài khoan co cac yeu cau bi huy tu 3 lan tro lên”

-- BANG PERSON
-- nguoi dung khong co huy yeu cau ma trung tam voi nhan vien thuc hien huy
-- =>chinh thanh "tai khoan da bi khoa do cac yeu cau da bi huy...." 
-- BANG SUPPLY
create or replace  TRIGGER TRG_SUP_BLOCK_PERSON
AFTER UPDATE  
ON supply
FOR EACH ROW  
DECLARE 
    SO_LAN_HUY INT;
--Thuc hien viec truy van con
PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN	
    --Dem so lan yeu cau bi huy cua nguoi dung
	SELECT COUNT(IDSUP) INTO SO_LAN_HUY 
	FROM  SUPPLY S
	WHERE :old.idper = S.idper 
	AND S.status = 5;
    IF (SO_LAN_HUY >= 3)
    THEN
        --Thuc hien khoa tai khoan so lan huy >=3
        UPDATE PERSON SET status = 1
        WHERE IDPER=:OLD.IDPER;
        COMMIT;
    END IF;
END TRG_SUP_BLOCK_PERSON;
/

-- R2: “Mot nguoi không the tao 1 yêu cau tu van neu nhu yêu cau tu van truoc còn ton tai hay dang trong trang thái dang cho”
-- INSERT
create or replace TRIGGER TRG_ADVISORY_CREATE_INSERT
BEFORE INSERT
ON advisory
FOR EACH ROW
DECLARE
    CURRENT_Ad INT;
BEGIN
    --Kiem tra con yeu cau dang mo hay dang cho dang ton tai hay khong
    SELECT COUNT(*) INTO CURRENT_Ad
    FROM advisory
    WHERE idper = :NEW.idper AND STATUS!=3;
    --Neu nhu ton tai thi bao loi 
    IF (CURRENT_Ad >0)
    THEN
        RAISE_APPLICATION_ERROR(-20003,'Khong the tao yeu cau tu van do ton tai yeu cau chua duoc giai quyet!');
    END IF;
END;
/
--R3: “Yeu cau tiep theo chi duoc tao voi noi dung bao gom vat pham có trong 
--yêu cau truoc dó khi yêu cau truoc dó o trang thái hoàn thành”.
CREATE OR REPLACE TRIGGER TRG_SUPPLY_CREATE
BEFORE INSERT 
ON supply
FOR EACH ROW
DECLARE
    V_NEEDFOOD INT;
    V_NEEDNECESS INT;
    V_NEEDEQUIP INT;
PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN
    --Kiem tra nguoi dung con yeu cau luong thuc nao chua hoan thanh hay khong
    IF(:NEW.NEEDFOOD=1)THEN
        SELECT COUNT(*) INTO V_NEEDFOOD
        FROM SUPPLY
        WHERE IDPER=:NEW.IDPER 
        AND NEEDFOOD=1
        AND STATUS!=4 AND STATUS!=5;
        IF(V_NEEDFOOD > 0) THEN
            RAISE_APPLICATION_ERROR(-20500,'Con yeu cau ve luong thuc chua hoan thanh, khong the tao yeu cau');
        END IF;
    END IF;
    --Kiem tra nguoi dung con yeu cau nhu yeu pham nao chua hoan thanh hay khong
    IF(:NEW.NEEDNECESS=1) THEN
        SELECT COUNT(*) INTO V_NEEDNECESS
        FROM SUPPLY
        WHERE IDPER=:NEW.IDPER 
        AND NEEDNECESS=1
        AND STATUS!=4 AND STATUS!=5;
        IF (V_NEEDNECESS > 0) THEN
            RAISE_APPLICATION_ERROR(-20501,'Con yeu cau ve nhu yeu pham chua hoan thanh, khong the tao yeu cau');
        END IF;
    END IF;
    --Kiem tra nguoi dung con yeu cau vat trang y te nao chua hoan thanh hay khong
    IF(:NEW.NEEDEQUIP=1) THEN
        SELECT COUNT(*) INTO V_NEEDEQUIP
        FROM SUPPLY
        WHERE IDPER=:NEW.IDPER 
        AND NEEDEQUIP=1
        AND STATUS!=4 AND STATUS!=5;   
        IF (V_NEEDEQUIP > 0) THEN
            RAISE_APPLICATION_ERROR(-20502,'Con yeu cau ve vat trang y te chua hoan thanh, khong the tao yeu cau');
        END IF;
    END IF;
END;
/

--R4 trigger tai khoan khoa khong the tao yeu cau tiep te
CREATE OR REPLACE TRIGGER TRG_LOCK_ACCOUNT_CANNOT_CREATE_SUPPLY
BEFORE INSERT ON SUPPLY
FOR EACH ROW
DECLARE
   v_idper person.idper%type;
   v_status person.status%type;
BEGIN
    --Lay trang trang thai hien tai cua nguoi dung
    select idper,status into v_idper, v_status
    from person p
    where p.idper=:new.idper;
    --Kiem tra co phai tai khoan khoa hay khong
    IF(v_status=1)
    THEN
        RAISE_APPLICATION_ERROR(-20010,'Tai khoan da bi khoa, khong the tao yeu cau');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Them thanh cong');
    END IF;
END;
/
--R5 trigger tai khoan khoa khong the tao yeu cau tu van
CREATE OR REPLACE TRIGGER TRG_LOCK_ACCOUNT_CANNOT_CREATE_ADVISORY
BEFORE INSERT ON ADVISORY
FOR EACH ROW
DECLARE
   v_idper person.idper%type;
   v_status person.status%type;
BEGIN
    --Lay trang trang thai hien tai cua nguoi dung
    select idper,status into v_idper, v_status
    from person p
    where p.idper=:new.idper;
    --Kiem tra co phai tai khoan khoa hay khong
    IF(v_status=1)
    THEN
        RAISE_APPLICATION_ERROR(-20010,'Tai khoan da bi khoa, khong the tao yeu cau');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Them thanh cong');
    END IF;
END;
/