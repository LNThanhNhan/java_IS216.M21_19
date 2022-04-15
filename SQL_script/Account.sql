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

INSERT INTO account VALUES ('heulwen', '123456789', 1);
INSERT INTO account VALUES ('eudora', 'qwerty', 1);
INSERT INTO account VALUES ('roderick', 'password123', 1);
INSERT INTO account VALUES ('orborne', '12345678', 1);
INSERT INTO account VALUES ('elfleda', '111111', 1);
INSERT INTO account VALUES ('griselda', '123123', 1);

INSERT INTO account VALUES ('phelan', 'qwerty123', 1);
INSERT INTO account VALUES ('boniface', '000000', 1);
INSERT INTO account VALUES ('augustus' , '1q2w345', 1);
INSERT INTO account VALUES ('mortimer' , 'aa12345678', 1);
INSERT INTO account VALUES ('glenda' , 'abc123', 1);
INSERT INTO account VALUES ('bellamy' , 'princess', 1);
INSERT INTO account VALUES ('jocelyn' , 'qwertyuiop', 1);
INSERT INTO account VALUES ('devlin' , 'abc123', 1);
INSERT INTO account VALUES ('jocasta' , 'abc123', 1);
INSERT INTO account VALUES ('grainne' , '123456', 1);

INSERT INTO account VALUES ('harding' , '25251325', 2);
INSERT INTO account VALUES ('jethro' , '123456', 2);
INSERT INTO account VALUES ('isolde' , '147852369', 2);
INSERT INTO account VALUES ('jezebel' , '987654321', 2);
INSERT INTO account VALUES ('amabel' , 'windowns', 2);
INSERT INTO account VALUES ('reginald' , 'linuxxx', 2);
INSERT INTO account VALUES ('gladys' , 'samsam', 2);
INSERT INTO account VALUES ('riselda' , 'dontknow', 2);
INSERT INTO account VALUES ('almira' , 'hieu123', 2);
INSERT INTO account VALUES ('selina' , 'viptop1', 2);

INSERT INTO account VALUES ('username01' , '123123', 4);
INSERT INTO account VALUES ('username02' , '123123', 4);
INSERT INTO account VALUES ('username03' , '123123', 4);
INSERT INTO account VALUES ('username04' , '123123', 4);
INSERT INTO account VALUES ('username05' , '123123', 4);
INSERT INTO account VALUES ('username06' , '123123', 4);
INSERT INTO account VALUES ('username07' , '123123', 4);
INSERT INTO account VALUES ('username08' , '123123', 4);
INSERT INTO account VALUES ('username09' , '123123', 4);
INSERT INTO account VALUES ('username10' , '123123', 4);

INSERT INTO account VALUES ('username11' , '123123', 3);
INSERT INTO account VALUES ('username12' , '123123', 3);
INSERT INTO account VALUES ('username13' , '123123', 3);
INSERT INTO account VALUES ('username14' , '123123', 3);
INSERT INTO account VALUES ('username15' , '123123', 3);
INSERT INTO account VALUES ('username16' , '123123', 3);
INSERT INTO account VALUES ('username17' , '123123', 3);
INSERT INTO account VALUES ('username18' , '123123', 3);
INSERT INTO account VALUES ('username19' , '123123', 3);
INSERT INTO account VALUES ('username20' , '123123', 3);
