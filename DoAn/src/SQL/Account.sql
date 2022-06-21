--BANG TAI KHOAN
CREATE TABLE account (
    username VARCHAR2(30)   NOT NULL,
    password VARCHAR2(30)   NOT NULL,
    role     SMALLINT       NOT NULL,
    CONSTRAINT account_pk PRIMARY KEY ( username )
);

--C� 4 loai t�i khoan:
--1: Nguoi d�ng
--2: Nh�n vi�n
--3: Trung t�m
--4: B�c si
ALTER TABLE account
    ADD CONSTRAINT role_acc CHECK(role in (1,2,3,4));
