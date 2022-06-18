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

ALTER TABLE advisory
    ADD CONSTRAINT fk01_advisory FOREIGN KEY ( idper )
        REFERENCES person ( idper );

ALTER TABLE advisory
    ADD CONSTRAINT fk02_advisory FOREIGN KEY ( iddoc )
        REFERENCES doctor ( iddoc );
        
--Gia tri tuong ung trong status
--Gom 3 trang thái: 
--1: Dã mo 
--2: Dang cho
--3: Hoàn thành
ALTER TABLE advisory
    ADD CONSTRAINT status_ad CHECK(status>=1 and status <=3);
--trang thai ban dau la da mo
ALTER TABLE advisory
    MODIFY status DEFAULT 1;
--ngay tao mac dinh la ngay hien tai
ALTER TABLE advisory
    MODIFY created DEFAULT SYSDATE;
--Nam sinh phai nho hon hoac bang nam tao yeu cau
ALTER TABLE advisory
    ADD CONSTRAINT yearbirth_created_ad CHECK(yearbirth >= EXTRACT(YEAR FROM created));
ALTER TABLE advisory
    ADD CONSTRAINT yearbirth_ad CHECK(yearbirth >0);
--Chieu cao khong la so am
ALTER TABLE advisory
    ADD CONSTRAINT height_ad CHECK(height >0);
--Can nang khong la so am
ALTER TABLE advisory
    ADD CONSTRAINT weight_ad CHECK(weight >0);

--sequence cho id advisory
create sequence idad
    start with 21
    increment by 1
    nocache
    nocycle;