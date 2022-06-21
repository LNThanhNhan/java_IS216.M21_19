CREATE TABLE doctor (
    iddoc        NUMBER(10)     NOT NULL,
    username     VARCHAR2(30),
    name         VARCHAR2(50)   NOT NULL,
    gender       SMALLINT       NOT NULL,
    phone        VARCHAR2(10)   NOT NULL,
    academicrank SMALLINT       NOT NULL,
    subject      SMALLINT       NOT NULL,
    workunits    VARCHAR2(50)   NOT NULL,
    province     VARCHAR2(30)   NOT NULL,
    CONSTRAINT doctor_pk PRIMARY KEY ( iddoc ) 
);

ALTER TABLE doctor
    ADD CONSTRAINT fk_doctor FOREIGN KEY ( username )
        REFERENCES account ( username );
        
ALTER TABLE doctor
    ADD CONSTRAINT username_doc UNIQUE(username);
ALTER TABLE doctor
    ADD CONSTRAINT gender_doc CHECK(gender in(0,1));
ALTER TABLE doctor
    ADD CONSTRAINT phone_doc UNIQUE(phone);
--Gia tri tuong ung trong academicrank
--1: Cu nh�n, 2: B�c si, 3: Thac si, 4: Tien si 
--5: Gi�o su/Ph� gi�o su, 6: Chuy�n khoa 1 
--7: Chuy�n khoa 2, 8: Sinh vi�n nam cuoi/ke cuoi Y Duoc
--9: Ky thuat vi�n
ALTER TABLE doctor
    ADD CONSTRAINT academicrank_doc CHECK(academicrank >=1 and academicrank<=9);
--Gia tri tuong ung trong subject 
--1: B�c si chuy�n khoa
--2: Chuy�n vi�n t�m l�
--3: Duoc si
--4: Dieu duong, ho sinh
--5: Sinh vi�n nam cuoi/ke cuoi Y Duoc
ALTER TABLE doctor
    ADD CONSTRAINT subject_doc CHECK(subject>=1 and subject <=5);
    
--sequence cho id doctor
create sequence iddoc
    start with 11
    increment by 1
    nocache
    nocycle;
