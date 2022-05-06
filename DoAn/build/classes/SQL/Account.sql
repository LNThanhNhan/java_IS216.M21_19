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

INSERT INTO account VALUES ('user01' , '123123', 4);
INSERT INTO account VALUES ('user02' , '123123', 4);
INSERT INTO account VALUES ('user03' , '123123', 4);
INSERT INTO account VALUES ('user04' , '123123', 4);
INSERT INTO account VALUES ('user05' , '123123', 4);
INSERT INTO account VALUES ('user06' , '123123', 4);
INSERT INTO account VALUES ('user07' , '123123', 4);
INSERT INTO account VALUES ('user08' , '123123', 4);
INSERT INTO account VALUES ('user09' , '123123', 4);
INSERT INTO account VALUES ('user10' , '123123', 4);

INSERT INTO account VALUES ('user11' , '123123', 3);
INSERT INTO account VALUES ('user12' , '123123', 3);
INSERT INTO account VALUES ('user13' , '123123', 3);
INSERT INTO account VALUES ('user14' , '123123', 3);
INSERT INTO account VALUES ('user15' , '123123', 3);
INSERT INTO account VALUES ('user16' , '123123', 3);
INSERT INTO account VALUES ('user17' , '123123', 3);
INSERT INTO account VALUES ('user18' , '123123', 3);
INSERT INTO account VALUES ('user19' , '123123', 3);
INSERT INTO account VALUES ('user20' , '123123', 3);

INSERT INTO account VALUES ('user21' , '25251325', 2);
INSERT INTO account VALUES ('user22' , '123456', 2);
INSERT INTO account VALUES ('user23' , '147852369', 2);
INSERT INTO account VALUES ('user24' , '987654321', 2);
INSERT INTO account VALUES ('user25' , 'windowns', 2);
INSERT INTO account VALUES ('user26' , 'linuxxx', 2);
INSERT INTO account VALUES ('user27' , 'samsam', 2);
INSERT INTO account VALUES ('user28' , 'dontknow', 2);
INSERT INTO account VALUES ('user29' , 'hieu123', 2);
INSERT INTO account VALUES ('user30' , 'viptop1', 2);

INSERT INTO account VALUES ('user31', '123456789', 1);
INSERT INTO account VALUES ('user32', 'qwerty', 1);
INSERT INTO account VALUES ('user33', 'password123', 1);
INSERT INTO account VALUES ('user34', '12345678', 1);
INSERT INTO account VALUES ('user35', '111111', 1);
INSERT INTO account VALUES ('user36', '123123', 1);