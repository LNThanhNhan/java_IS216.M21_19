--BANG TAI KHOAN
CREATE TABLE account (
    username VARCHAR2(30)   NOT NULL,
    password VARCHAR2(30)   NOT NULL,
    role     SMALLINT       NOT NULL,
    CONSTRAINT account_pk PRIMARY KEY ( username )
);

--Có 4 loai tài khoan:
--1: Nguoi dùng
--2: Nhân viên
--3: Trung tâm
--4: Bác si
ALTER TABLE account
    ADD CONSTRAINT role_acc CHECK(role in (1,2,3,4));
