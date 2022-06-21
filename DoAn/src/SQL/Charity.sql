CREATE TABLE charity (
    idchar    NUMBER(10)    NOT NULL,
    username  VARCHAR2(30),
    name      VARCHAR2(50)  NOT NULL,
    phone     VARCHAR2(10)  NOT NULL,
    province  VARCHAR2(30)  NOT NULL,
    district  VARCHAR2(30)  NOT NULL,
    town      VARCHAR2(30)  NOT NULL,
    address   VARCHAR2(30)  NOT NULL,
    hasfood   SMALLINT      NOT NULL,
    hasnecess SMALLINT      NOT NULL,
    hasequip  SMALLINT      NOT NULL,
    point     NUMBER(10),
    CONSTRAINT charity_pk PRIMARY KEY ( idchar )
);
--Khoa ngoai la ten tai khoan cua trung tam
ALTER TABLE charity
    ADD CONSTRAINT fk_charity FOREIGN KEY ( username )
        REFERENCES account ( username );
--Ten tai khoan cua trung tam la duy nhat        
ALTER TABLE charity
    ADD CONSTRAINT username_char UNIQUE(username);
--SO dien thoai cua trung tam la duy nhat
ALTER TABLE charity
    ADD CONSTRAINT phone_char UNIQUE(phone);
ALTER TABLE charity
    ADD CONSTRAINT hasfood_char CHECK(hasfood in(0,1));
ALTER TABLE charity
    ADD CONSTRAINT hasnecess_char CHECK(hasnecess in(0,1));
ALTER TABLE charity
    ADD CONSTRAINT hasequip_char CHECK(hasequip in(0,1));
--Diem mac dinh cua trung tam la 0
ALTER TABLE charity
    MODIFY point DEFAULT 0;
--Diem trung tam khong duoc la so am
ALTER TABLE charity
    ADD CONSTRAINT point_char CHECK(point >=0);
--Trung tam phai cung cap duoc it nhat 1 loai yeu cau
ALTER TABLE charity
    ADD CONSTRAINT fill_a_need_char CHECK (hasfood =1 or hasnecess=1 or hasequip=1);   
    
--sequence cho id char
create sequence idchar
    start with 11
    increment by 1
    nocache
    nocycle;
