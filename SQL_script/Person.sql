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
ALTER TABLE person
    ADD CONSTRAINT phone_per UNIQUE(phone);
    
INSERT INTO person VALUES (1, 'heulwen', 'Mai Hoai An', 0, '0353975568', 'Ho Chi Minh', 'THU DUC', 'LINH CHIEU', '26 DUONG 15', 1);
INSERT INTO person VALUES (2, 'eudora', 'Pham Duy Anh', 0, '0962475039', 'Ho Chi Minh', 'THU DUC', 'LINH CHIEU', '46 DUONG 15', 1);
INSERT INTO person VALUES (3, 'roderick', 'Ha Minh Dai', 1, '0399019601', 'Ho Chi Minh', 'THU DUC', 'LINH CHIEU', '110/1 CHUONG DUONG', 1);
INSERT INTO person VALUES (4, 'orborne', 'Nguyen Van Ngoc Hieu', 1, '0346638188','Ho Chi Minh', 'THU DUC', 'LINH CHIEU', '1060/4 KHA VAN CAN', 1);
INSERT INTO person VALUES (5, 'elfleda', 'Nguyen Xuan Hoa', 0, '0971545751', 'Ho Chi Minh', 'THU DUC', 'LINH CHIEU', '76/36/2C DUONG 19', 1);
INSERT INTO person VALUES (6, 'griselda', 'Vu Gia Hoa', 1, '0394304299', 'Ho Chi Minh', 'THU DUC', 'LINH CHIEU', '51 DUONG 15', 1);
INSERT INTO person VALUES (7, null, 'Nguyen Tri Thanh', 1, '0336074664', 'Ho Chi Minh', 'THU DUC', 'LINH CHIEU', '1060/10 KHA VAN CAN', 1);
INSERT INTO person VALUES (8, null, 'Nguyen Duc Nghia', 1, '0868437363', 'Ho Chi Minh', 'THU DUC', 'LINH CHIEU', '45 DUONG 17', 1);
INSERT INTO person VALUES (9, null, 'Nguyen Minh Long', 1, '0329853968', 'Ho Chi Minh', 'THU DUC', 'LINH CHIEU', '1060/10 KHA VAN CAN', 1);
INSERT INTO person VALUES (10, null, 'Tran Huy Khanh', 1, '0375210196', 'Ho Chi Minh', 'THU DUC', 'LINH CHIEU', '46 DUONG 15', 1);