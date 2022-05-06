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

ALTER TABLE supply
    ADD CONSTRAINT fk_supply FOREIGN KEY ( idper )
        REFERENCES person ( idper );
        
ALTER TABLE supply
    ADD CONSTRAINT fk02_supply FOREIGN KEY ( idchar )
        REFERENCES charity ( idchar );

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
--trang thai ban dau la da mo
ALTER TABLE supply
    MODIFY status DEFAULT 1;
--ngay tao mac dinh la ngay hien tai
ALTER TABLE supply
    MODIFY created DEFAULT SYSDATE;
--Yeu cau phai co it nhat 1 loai yeu cau duoc chon
ALTER TABLE supply
    ADD CONSTRAINT fill_a_need_sup CHECK (needfood =1 or neednecess=1 or needequip=1);    
--BANG Supply
INSERT INTO Supply VALUES(1, 1, 1, TO_DATE('12/04/2022','dd/mm/yyyy'), 1, 1, 1, 4, 'Xin ho tro luong thuc gom gao va luong kho' );
INSERT INTO Supply VALUES(2, 2, NULL, TO_DATE('13/04/2022','dd/mm/yyyy'), 1, 1, 1, 2, 'Toi mong muon duoc ho tro day du, dac biet la luong thuc');
INSERT INTO Supply VALUES(3, 3, NULL, TO_DATE('15/04/2022','dd/mm/yyyy'), 1, 1, 1,1, 'Toi xin cac trung tam ho tro nhanh nhat co the');
INSERT INTO Supply VALUES(4, 4, NULL, TO_DATE('15/04/2022','dd/mm/yyyy'), 1, 1, 1,1, 'Gia dinh toi hien dang bi covid, rat can luong thuc và cac vat dung y te');
INSERT INTO Supply VALUES(5, 5, 2, TO_DATE('13/04/2022','dd/mm/yyyy'), 1, 1, 1, 3, 'Xin ho tro gao, sua, man, muoi va que test nhanh');
INSERT INTO Supply VALUES(6, 6, 5, TO_DATE('13/04/2022','dd/mm/yyyy'), 1, 1, 1, 3, 'Mong yeu cau se duoc ho tro nhanh');
INSERT INTO Supply VALUES(7, 7, 6, TO_DATE('13/04/2022','dd/mm/yyyy'), 1, 1, 1, 3, 'Toi can gao, trung, giay ve sinh, nhiet ke va que test nhanh');
INSERT INTO Supply VALUES(8, 8, 7, TO_DATE('13/04/2022','dd/mm/yyyy'), 1, 1, 1, 3, 'Toi hien dang mot minh va bi covid, hien tai khong con gi trong nha. Mong duoc ho tro nhanh nhat co the');
INSERT INTO Supply VALUES(9, 9, 8, TO_DATE('13/04/2022','dd/mm/yyyy'), 1, 1, 1, 3, 'Xin ho tro gao, sua, con, que test nhanh');
INSERT INTO Supply VALUES(10,10, 9, TO_DATE('13/04/2022','dd/mm/yyyy'), 1, 1, 1,3 , 'Xin ho tro gao, mi tom, thuoc ho, que test nhanh');