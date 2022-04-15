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

ALTER TABLE charity
    ADD CONSTRAINT fk_charity FOREIGN KEY ( username )
        REFERENCES account ( username );
        
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
    
INSERT INTO charity VALUES (1, 'username11', 'Tinh  Thuong', '0373484186', 'Ho Chi Minh', 'THU DUC', 'LINH CHIEU', '72 DUONG 17',1, 1, 1, 1);
INSERT INTO charity VALUES (2, 'username12', 'Hoa Tu Bi', '0347694188', 'Ho Chi Minh', 'QUAN 1', 'BEN NGHE', '32 DUONG LE DUAN', 1, 1, 0, 0);
INSERT INTO Charity VALUES(3, 'username13', 'Hoa Hao', '0398437179', 'Ho Chi Minh', 'THU DUC',  'LINH TRUNG', '6/2D KHU PHO 2' ,1,1,1,1,0);
INSERT INTO Charity VALUES(4, 'username14', 'Mai Am', '0374892969', 'Ho Chi Minh', 'THU DUC', 'LINH TRUNG', '22 DUONG 1', 1,1,1,1,0);
INSERT INTO Charity VALUES(5, 'username15', 'Bep Com Van Tinh', '0358806568', 'Ho Chi Minh', 'THU DUC', 'LINH CHIEU', '4/14 DUONG 18', 1,1,1,1,0);
INSERT INTO Charity VALUES(6, 'username16', 'Huong Sen', '0343804268', 'Ho Chi Minh', 'THU DUC', 'LINH CHIEU', '108 DUONG HOANG DIEU 2', 1,1,1,1,0);
INSERT INTO Charity VALUES(7, 'username17', 'Tinh Nguyen Tre', '0335181173', 'Ho Chi Minh', 'THU DUC', 'LINH XUAN', '109 HEM 21 DUONG 11', 1,1,1,1,0);
INSERT INTO Charity VALUES(8, 'username18', 'Suc Song', '0334658575', 'Ho Chi Minh', 'THU DUC', 'LINH XUAN', '69 DUONG TRUONG TRE', 1,1,1,1,0);
INSERT INTO Charity VALUES(9, 'username19', 'Minh Tam', '0396171180', 'Ho Chi Minh', 'THU DUC', 'LINH XUAN', '83/1QUOC LO 1A', 1,1,1,1,0);
INSERT INTO Charity VALUES(10, 'username20', 'Mam Non', '0967596794', 'Ho Chi Minh', 'THU DUC', 'LINH XUAN', '31 DUONG 10', 1,1,1,1,0);