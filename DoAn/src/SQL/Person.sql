CREATE TABLE person 
(
    idper    NUMBER(10)     NOT NULL,
    username VARCHAR2(30),
    name     VARCHAR2(50)   NOT NULL,
    gender   SMALLINT       NOT NULL,
    phone    VARCHAR2(10)   NOT NULL,
    province VARCHAR2(30)   NOT NULL,
    district VARCHAR2(30)   NOT NULL,
    town     VARCHAR2(30)   NOT NULL,
    address  VARCHAR2(30)   NOT NULL,
    status   SMALLINT, 
    CONSTRAINT person_pk PRIMARY KEY ( idper )
);

ALTER TABLE person
    ADD CONSTRAINT fk_account FOREIGN KEY ( username )
        REFERENCES account ( username );
        
ALTER TABLE person
    ADD CONSTRAINT username_per UNIQUE(username);
ALTER TABLE person
    ADD CONSTRAINT gender_per CHECK(gender in(0,1));
ALTER TABLE person
    MODIFY status DEFAULT 0;
--Gia tri tuong ung trong status
--Tình trang cua tài khoan có 2 loai: 
--0: dang su dung,
--1: dã bi khóa
ALTER TABLE person
    ADD CONSTRAINT status_per CHECK(status in(0,1));
--So dien thoai cua nguoi dung la duy nhat
ALTER TABLE person
    ADD CONSTRAINT phone_per UNIQUE(phone);
    
--sequence cho id person
create sequence idper
    start with 31
    increment by 1
    nocache
    nocycle;