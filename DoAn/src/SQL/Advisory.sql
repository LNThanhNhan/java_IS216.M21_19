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

insert into advisory values(1,1,3,default,1986,170,80,3,NULL,'Xin tu van hau covid 19');
insert into advisory values(2,2,10,default,1990,165,60,3,'Hen suyen','Em muon hoi cach dieu tri covid-19 tai nha');
insert into advisory values(3,3,NULL,default,2002,170,86,1,NULL,'Huong dan cach xai kit test nhanh');
insert into advisory values(4,4,NULL,default,1978,160,65,1,NULL,'Xin tu van hau covid 19');
insert into advisory values(5,5,5,default,1980,164,55,2,'Huyet ap cao','Khi bi mac covid thì can lam gi');
insert into advisory values(6,6,2,default,1965,158,57,2,'Tim mach','Tu van cach tu dieu tri covid tai nha');
insert into advisory values(7,7,NULL,default,2000,176,69,1,NULL,'Hoi cach cham sóc cho nguoi nha bi covid');
insert into advisory values(8,8,NULL,default,1997,180,70,1,NULL,'Em hien nay dang bi ho khan, mong bác si tu van');
insert into advisory values(9,9,9,default,1995,171,74,2,NULL,'Cho em hoi neu mà mac covid tho mình se có nhung trieu chung gi');
insert into advisory values(10,10,1,default,1989,167,71,3,NULL,'Mong bac si chan doan và kê don cho em thuoc dieu tri hau covid');