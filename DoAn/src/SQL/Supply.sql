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

--sequence cho id supply
create sequence idsup
    start with 31
    increment by 1
    nocache
    nocycle;
