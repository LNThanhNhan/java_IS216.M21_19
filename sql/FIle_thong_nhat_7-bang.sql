--BANG TAI KHOAN
CREATE TABLE account (
    username VARCHAR2(30)   NOT NULL,
    password VARCHAR2(30)   NOT NULL,
    role     SMALLINT       NOT NULL,
    CONSTRAINT account_pk PRIMARY KEY ( username )
);
--BANG NGUOI DUNG
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
--BANG NHAN VIEN
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
--BANG TRUNG TAM THIEN NGUYEN
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
--BANG BAC SI
CREATE TABLE doctor (
    iddoc        NUMBER(10)     NOT NULL,
    username     VARCHAR2(30),
    name         VARCHAR2(50)   NOT NULL,
    gender       SMALLINT       NOT NULL,
    phone        VARCHAR2(10)   NOT NULL,
    academicrank SMALLINT       NOT NULL,
    subject      SMALLINT       NOT NULL,
    workunits    VARCHAR2(30)   NOT NULL,
    province     VARCHAR2(30)   NOT NULL,
    CONSTRAINT doctor_pk PRIMARY KEY ( iddoc ) 
);
--BANG YEU CAU TIEP TE
CREATE TABLE supply (
    idsup        NUMBER(10)     NOT NULL,
    idper        NUMBER(10)     NOT NULL,
    idchar       NUMBER(10),
    created      DATE, 
    needfood     SMALLINT       NOT NULL,
    neednecess   SMALLINT       NOT NULL,
    needequip    SMALLINT       NOT NULL,
    status       SMALLINT,
    detail       VARCHAR2(1000) NOT NULL,
    CONSTRAINT supply_pk PRIMARY KEY ( idsup ) 
);
--BANG YEU CAU TU VAN
CREATE TABLE advisory (
    idad               NUMBER(10)   NOT NULL,
    idper              NUMBER(10)   NOT NULL,
    iddoc              NUMBER(10),
    created            DATE, 
    yearbirth          NUMBER(4)    NOT NULL,   
    height             NUMBER(3)    NOT NULL,
    weight             NUMBER(3)    NOT NULL,
    status             SMALLINT, 
    pastmedicalhistory VARCHAR2(250),
    detail             VARCHAR2(1000) NOT NULL,
    CONSTRAINT advisory_pk PRIMARY KEY ( idad )
);
------------------------------------------------------------------
--RANG BUOC KHOA PHU
ALTER TABLE person
    ADD CONSTRAINT fk_account FOREIGN KEY ( username )
        REFERENCES account ( username );

ALTER TABLE employee
    ADD CONSTRAINT fk_employee FOREIGN KEY ( username )
        REFERENCES account ( username );

ALTER TABLE charity
    ADD CONSTRAINT fk_charity FOREIGN KEY ( username )
        REFERENCES account ( username );

ALTER TABLE doctor
    ADD CONSTRAINT fk_doctor FOREIGN KEY ( username )
        REFERENCES account ( username );

ALTER TABLE supply
    ADD CONSTRAINT fk_supply FOREIGN KEY ( idper )
        REFERENCES person ( idper );
        
ALTER TABLE supply
    ADD CONSTRAINT fk02_supply FOREIGN KEY ( idchar )
        REFERENCES charity ( idchar );

ALTER TABLE advisory
    ADD CONSTRAINT fk01_advisory FOREIGN KEY ( idper )
        REFERENCES person ( idper );

ALTER TABLE advisory
    ADD CONSTRAINT fk02_advisory FOREIGN KEY ( iddoc )
        REFERENCES doctor ( iddoc );
------------------------------------------------------------------
--RANG BUOC MIEN DU LIEU
--BANG PESON
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
ALTER TABLE person
    ADD CONSTRAINT phone_per UNIQUE(phone);
    
--BANG ACCOUNT
--Có 4 lo?i tài kho?n:
--1: Nguoi dùng
--2: Nhân viên
--3: Trung tâm
--4: Bác si
ALTER TABLE account
    ADD CONSTRAINT role_acc CHECK(role in (1,2,3,4));

--BANG EMPLOYEE
ALTER TABLE employee
    ADD CONSTRAINT username_emp UNIQUE(username);
ALTER TABLE employee
    ADD CONSTRAINT gender_emp CHECK(gender in(0,1));
ALTER TABLE employee
    ADD CONSTRAINT phone_emp UNIQUE(phone);

--BANG CHARITY
ALTER TABLE charity
    ADD CONSTRAINT username_char UNIQUE(username);
ALTER TABLE charity
    ADD CONSTRAINT phone_char UNIQUE(phone);
ALTER TABLE charity
    ADD CONSTRAINT hasfood_char CHECK(hasfood in(0,1));
ALTER TABLE charity
    ADD CONSTRAINT hasnecess_char CHECK(hasnecess in(0,1));
ALTER TABLE charity
    ADD CONSTRAINT hasequip_char CHECK(hasequip in(0,1));
ALTER TABLE charity
    MODIFY point DEFAULT 0;
ALTER TABLE charity
    ADD CONSTRAINT point_char CHECK(point >=0);

--BANG SUPPLY
ALTER TABLE supply
    ADD CONSTRAINT needfood_sup CHECK(needfood in(0,1));
ALTER TABLE supply
    ADD CONSTRAINT neednecess_sup CHECK(neednecess in(0,1));
ALTER TABLE supply
    ADD CONSTRAINT needequip_sup CHECK(needequip in(0,1));
--Gia tri tuong ung trong status
--Gom 5 trang thái: 
--1: Dã mo
--2: Dã xác thuc
--3: Dang cho
--4: Hoàn thành
--5: Dã huy
ALTER TABLE supply
    ADD CONSTRAINT status_sup CHECK(status>=1 and status <=5);
ALTER TABLE supply
    MODIFY status DEFAULT 1;
ALTER TABLE supply
    MODIFY created DEFAULT SYSDATE;
    
--BANG Advisory
--Gia tri tuong ung trong status
--Gom 3 trang thái: 
--1: Dã mo 
--2: Dang cho
--3: Hoàn thành
ALTER TABLE advisory
    ADD CONSTRAINT status_ad CHECK(status>=1 and status <=3);
ALTER TABLE advisory
    MODIFY status DEFAULT 1;
ALTER TABLE advisory
    MODIFY created DEFAULT SYSDATE;
ALTER TABLE advisory
    ADD CONSTRAINT yearbirth_created_ad CHECK(yearbirth <= EXTRACT(YEAR FROM created));
ALTER TABLE advisory
    ADD CONSTRAINT yearbirth_ad CHECK(yearbirth >0);
ALTER TABLE advisory
    ADD CONSTRAINT height_ad CHECK(height >0);
ALTER TABLE advisory
    ADD CONSTRAINT weight_ad CHECK(weight >0);

--BANG DOCTOR
ALTER TABLE doctor
    ADD CONSTRAINT username_doc UNIQUE(username);
ALTER TABLE doctor
    ADD CONSTRAINT gender_doc CHECK(gender in(0,1));
ALTER TABLE doctor
    ADD CONSTRAINT phone_doc UNIQUE(phone);
--Gia tri tuong ung trong academicrank
--1: Cu nhân
--2: Bác si
--3: Thac si
--4: Tien si
--5: Giáo su/Phó giáo  su
--6: Chuyên khoa 1
--7: Chuyên khoa 2
--8: Sinh viên nam cuoi/ke cuoi Y Duoc
--9: Ky thuat viên
ALTER TABLE doctor
    ADD CONSTRAINT academicrank_doc CHECK(academicrank >=1 and academicrank<=9);
--Gia tri tuong ung trong subject 
--1: Bác si chuyên khoa
--2: Chuyên viên tâm lı
--3: Duoc si
--4: Dieu duong, ho sinh
--5: Sinh viên nam cuoi/ke cuoi Y Duoc
ALTER TABLE doctor
    ADD CONSTRAINT subject_doc CHECK(subject>=1 and subject <=5);
    
--============================================
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

--insert person
INSERT INTO person VALUES (idper.nextval, 'heulwen', 'Mai Hoai An', 0, '0353975568', 'Ho Chi Minh', 'THU DUC', 'LINH CHIEU', '26 DUONG 15', 0);
INSERT INTO person VALUES (idper.nextval, 'eudora', 'Pham Duy Anh', 0, '0962475039', 'Ho Chi Minh', 'THU DUC', 'LINH CHIEU', '46 DUONG 15', 0);
INSERT INTO person VALUES (idper.nextval, 'roderick', 'Ha Minh Dai', 1, '0399019601', 'Ho Chi Minh', 'THU DUC', 'LINH CHIEU', '110/1 CHUONG DUONG', 0);
INSERT INTO person VALUES (idper.nextval, 'orborne', 'Nguyen Van Ngoc Hieu', 1, '0346638188','Ho Chi Minh', 'THU DUC', 'LINH CHIEU', '1060/4 KHA VAN CAN', 0);
INSERT INTO person VALUES (idper.nextval, 'elfleda', 'Nguyen Xuan Hoa', 0, '0971545751', 'Ho Chi Minh', 'THU DUC', 'LINH CHIEU', '76/36/2C DUONG 19', 0);
INSERT INTO person VALUES (idper.nextval, 'griselda', 'Vu Gia Hoa', 1, '0394304299', 'Ho Chi Minh', 'THU DUC', 'LINH CHIEU', '51 DUONG 15', 0);
INSERT INTO person VALUES (idper.nextval, null, 'Nguyen Tri Thanh', 1, '0336074664', 'Ho Chi Minh', 'THU DUC', 'LINH CHIEU', '1060/10 KHA VAN CAN', 0);
INSERT INTO person VALUES (idper.nextval, null, 'Nguyen Duc Nghia', 1, '0868437363', 'Ho Chi Minh', 'THU DUC', 'LINH CHIEU', '45 DUONG 17', 0);
INSERT INTO person VALUES (idper.nextval, null, 'Nguyen Minh Long', 1, '0329853968', 'Ho Chi Minh', 'THU DUC', 'LINH CHIEU', '1060/10 KHA VAN CAN', 0);
INSERT INTO person VALUES (idper.nextval, null, 'Tran Huy Khanh', 1, '0375210196', 'Ho Chi Minh', 'THU DUC', 'LINH CHIEU', '46 DUONG 15', 0);

--insert employee
INSERT INTO employee VALUES (idper.nextval, 'harding', 'Nguyen Thi Ly', 0, SYSDATE, '0984626764', '56/11-CHUONG DUONG- LINH CHIEU- THU DUC');
INSERT INTO employee VALUES (idper.nextval, 'jethro', 'Nguyen Thi Xoa', 0, SYSDATE, '0378574588', '29/1B DUONG 16- TO 2- KP1- LINH CHIEU- THU DUC');
INSERT INTO employee VALUES (idper.nextval, 'isolde', 'Tran Thi Uyen', 0, SYSDATE, '0862121185', '1034/3 TO 8- KP1-  LINH CHIEU- THU DUC');
INSERT INTO employee VALUES (idper.nextval, 'jezebel', 'Nguyen Bui Thuy Vy' , 0, SYSDATE, '0332448466', '1104/4A KHA VAN CAN- LINH CHIEU- THU DUC');
INSERT INTO employee VALUES (idper.nextval, 'amabel', 'Than Trong Thien An', 1, SYSDATE, '0865101714', '1046 KHA VAN CAN-  LINH CHIEU- THU DUC');
INSERT INTO employee VALUES (idper.nextval, 'reginald', 'Ngo Thi Thoa' , 0, SYSDATE, '0348425066', '1/1096 KHA VAN CAN-  LINH CHIEU- THU DUC');
INSERT INTO employee VALUES (idper.nextval, 'gladys', 'Dang Thi Ngoc Yen', 0, SYSDATE, '0354306079', '28/33 DUONG 16- LINH CHIEU- THU DUC');
INSERT INTO employee VALUES (idper.nextval, 'griselda', 'Truong Thi Mang', 0, SYSDATE, '0368334441', '37A DUONG 17-  LINH CHIEU- THU DUC');
INSERT INTO employee VALUES (idper.nextval, 'almira', 'Nguyen Thi Chi Mai', 0, SYSDATE, '0374916079', '56 HOANG DIEU 2- LINH CHIEU- THU DUC');
INSERT INTO employee VALUES (idper.nextval, 'selina', 'Nguyen Mai Quynh', 0, SYSDATE, '0348606057', '34/9 DUONG 16- LINH CHIEU- THU DUC');

--insert charity
INSERT INTO charity VALUES (idchar.nextval, 'username11', 'Tinh  Thuong', '0373484186', 'Ho Chi Minh', 'THU DUC', 'LINH CHIEU', '72 DUONG 17',1, 1, 1,0);
INSERT INTO charity VALUES (idchar.nextval, 'username12', 'Hoa Tu Bi', '0347694188', 'Ho Chi Minh', 'QUAN 1', 'BEN NGHE', '32 DUONG LE DUAN', 1, 1, 0, 0);
INSERT INTO Charity VALUES(idchar.nextval, 'username13', 'Hoa Hao', '0398437179', 'Ho Chi Minh', 'THU DUC',  'LINH TRUNG', '6/2D KHU PHO 2' ,1,1,1,0);
INSERT INTO Charity VALUES(idchar.nextval, 'username14', 'Mai Am', '0374892969', 'Ho Chi Minh', 'THU DUC', 'LINH TRUNG', '22 DUONG 1', 1,1,1,0);
INSERT INTO Charity VALUES(idchar.nextval, 'username15', 'Bep Com Van Tinh', '0358806568', 'Ho Chi Minh', 'THU DUC', 'LINH CHIEU', '4/14 DUONG 18', 1,1,1,0);
INSERT INTO Charity VALUES(idchar.nextval, 'username16', 'Huong Sen', '0343804268', 'Ho Chi Minh', 'THU DUC', 'LINH CHIEU', '108 DUONG HOANG DIEU 2', 1,1,1,0);
INSERT INTO Charity VALUES(idchar.nextval, 'username17', 'Tinh Nguyen Tre', '0335181173', 'Ho Chi Minh', 'THU DUC', 'LINH XUAN', '109 HEM 21 DUONG 11', 1,1,1,0);
INSERT INTO Charity VALUES(idchar.nextval, 'username18', 'Suc Song', '0334658575', 'Ho Chi Minh', 'THU DUC', 'LINH XUAN', '69 DUONG TRUONG TRE', 1,1,1,0);
INSERT INTO Charity VALUES(idchar.nextval, 'username19', 'Minh Tam', '0396171180', 'Ho Chi Minh', 'THU DUC', 'LINH XUAN', '83/1QUOC LO 1A', 1,1,1,0);
INSERT INTO Charity VALUES(idchar.nextval, 'username20', 'Mam Non', '0967596794', 'Ho Chi Minh', 'THU DUC', 'LINH XUAN', '31 DUONG 10', 1,1,1,0);

--insert doctor
insert into doctor values(iddoc.nextval,'username01','Ho Thi Ha',0,'0935716315',1,1,'Benh vien nhi dong 1','Ho Chi Minh');
insert into doctor values(iddoc.nextval,'username02','Nguyen Phuong',0,'0764518719',2,1,'Benh vien Ung buou','Ho Chi Minh');
insert into doctor values(iddoc.nextval,'username03','Pham Nhat Linh',1,'0930750831',6,1,'Benh vien Da Khoa Thu Duc','Ho Chi Minh');
insert into doctor values(iddoc.nextval,'username04','Nguyen Minh Cong',1,'0944508031',2,1,'Benh vien Quan Bình Thanh','Ho Chi Minh');
insert into doctor values(iddoc.nextval,'username05','Nguyen Thanh Quan',1,'0768502537',4,1,'Benh vien Quan 1','Ho Chi Minh');
insert into doctor values(iddoc.nextval,'username06','Nguyen Quynh Phuong Anh',0,'0984476843',9,4,'Benh vien Da Khoa Hoàng Long','Ho Chi Minh');
insert into doctor values(iddoc.nextval,'username07','Nguyen Thi Hai Lien',0,'0957270143',8,5,'Benh vien Dai hoc Y duoc','Ho Chi Minh');
insert into doctor values(iddoc.nextval,'username08','Nguyen Van Chanh',1,'0959016831',6,1,'Benh vien Cho Ray','Ho Chi Minh');
insert into doctor values(iddoc.nextval,'username09','Le Tuan Thanh',1,'0946097020',7,1,'Benh vien Thong Nhat','Ho Chi Minh');
insert into doctor values(iddoc.nextval,'username10','Tran Quynh Kim',0,'0937169708',3,2,'Benh vien Thong Nhat','Ho Chi Minh');

--insert advisory
insert into advisory values(idad.nextval,1,NULL,default,1986,170,80,1,NULL,'Xin tu van hau covid 19');
insert into advisory values(idad.nextval,2,NULL,default,1990,165,60,1,'Hen suyen','Em muon hoi cach dieu tri covid-19 tai nha');
insert into advisory values(idad.nextval,3,NULL,default,2002,170,86,1,NULL,'Huong dan cach xai kit test nhanh');
insert into advisory values(idad.nextval,4,NULL,default,1978,160,65,1,NULL,'Xin tu van hau covid 19');
insert into advisory values(idad.nextval,5,NULL,default,1980,164,55,1,'Huyet ap cao','Khi bi mac covid thì can lam gi');
insert into advisory values(idad.nextval,6,NULL,default,1965,158,57,1,'Tim mach','Tu van cach tu dieu tri covid tai nha');
insert into advisory values(idad.nextval,7,NULL,default,2000,176,69,1,NULL,'Hoi cach cham sóc cho nguoi nha bi covid');
insert into advisory values(idad.nextval,8,NULL,default,1997,180,70,1,NULL,'Em hien nay dang bi ho khan, mong bác si tu van');
insert into advisory values(idad.nextval,9,NULL,default,1995,171,74,1,NULL,'Cho em hoi neu mà mac covid tho mình se có nhung trieu chung gi');
insert into advisory values(idad.nextval,10,NULL,default,1989,167,71,1,NULL,'Mong bac si chan doan và kê don cho em thuoc dieu tri hau covid');


--insert supply
INSERT INTO Supply VALUES(idsup.nextval , 1, NULL, sysdate, 1, 1, 1, 1, 'Xin ho tro luong thuc gom gao va luong kho' );
INSERT INTO Supply VALUES(idsup.nextval, 2, NULL, sysdate, 1, 1, 1, 1, 'Toi mong muon duoc ho tro day du, dac biet la luong thuc');
INSERT INTO Supply VALUES(idsup.nextval, 3, NULL, sysdate, 1, 1, 1,1, 'Toi xin cac trung tam ho tro nhanh nhat co the');
INSERT INTO Supply VALUES(idsup.nextval, 4, NULL, sysdate, 1, 1, 1,1, 'Gia dinh toi hien dang bi covid, rat can luong thuc và cac vat dung y te');
INSERT INTO Supply VALUES(idsup.nextval, 5, NULL, sysdate, 1, 1, 1, 1, 'Xin ho tro gao, sua, man, muoi va que test nhanh');
INSERT INTO Supply VALUES(idsup.nextval, 6, NULL, sysdate, 1, 1, 1, 1, 'Mong yeu cau se duoc ho tro nhanh');
INSERT INTO Supply VALUES(idsup.nextval, 7, NULL, sysdate, 1, 1, 1, 1, 'Toi can gao, trung, giay ve sinh, nhiet ke va que test nhanh');
INSERT INTO Supply VALUES(idsup.nextval, 8, NULL, sysdate, 1, 1, 1, 1, 'Toi hien dang mot minh va bi covid, hien tai khong con gi trong nha. Mong duoc ho tro nhanh nhat co the');
INSERT INTO Supply VALUES(idsup.nextval, 9, NULL, sysdate, 1, 1, 1, 1, 'Xin ho tro gao, sua, con, que test nhanh');
INSERT INTO Supply VALUES(idsup.nextval,10, NULL, sysdate, 1, 1, 1,1 , 'Xin ho tro gao, mi tom, thuoc ho, que test nhanh');