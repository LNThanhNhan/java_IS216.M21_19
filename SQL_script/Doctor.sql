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

ALTER TABLE doctor
    ADD CONSTRAINT fk_doctor FOREIGN KEY ( username )
        REFERENCES account ( username );
        
ALTER TABLE doctor
    ADD CONSTRAINT username_doc UNIQUE(username);
ALTER TABLE doctor
    ADD CONSTRAINT gender_doc CHECK(gender in(0,1));
ALTER TABLE doctor
    ADD CONSTRAINT phone_doc UNIQUE(phone);
--Gia tri tuong ung trong academicrank
--1: Cu nhân, 2: Bác si, 3: Thac si, 4: Tien si 
--5: Giáo su/Phó giáo su, 6: Chuyên khoa 1 
--7: Chuyên khoa 2, 8: Sinh viên nam cuoi/ke cuoi Y Duoc
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
    
insert into doctor values(1,'username01','Ho Thi Ha',0,'0935716315',1,1,'Benh vien nhi dong 1','Ho Chi Minh');
insert into doctor values(2,'username02','Nguyen Phuong',0,'0764518719',2,1,'Benh vien Ung buou','Ho Chi Minh');
insert into doctor values(3,'username03','Pham Nhat Linh',1,'0930750831',6,1,'Benh vien Da Khoa Thu Duc','Ho Chi Minh');
insert into doctor values(4,'username04','Nguyen Minh Cong',1,'0944508031',2,1,'Benh vien Quan Binh Thanh','Ho Chi Minh');
insert into doctor values(5,'username05','Nguyen Thanh Quan',1,'0768502537',4,1,'Benh vien Quan 1','Ho Chi Minh');
insert into doctor values(6,'username06','Nguyen Quynh Phuong Anh',0,'0984476843',9,4,'Benh vien Da Khoa Hoang Long','Ho Chi Minh');
insert into doctor values(7,'username07','Nguyen Thi Hai Lien',0,'0957270143',8,5,'Benh vien Dai hoc Y duoc','Ho Chi Minh');
insert into doctor values(8,'username08','Nguyen Van Chanh',1,'0959016831',6,1,'Benh vien Cho Ray','Ho Chi Minh');
insert into doctor values(9,'username09','Le Tuan Thanh',1,'0946097020',7,1,'Benh vien Thong Nhat','Ho Chi Minh');
insert into doctor values(10,'username10','Tran Quynh Kim',0,'0937169708',3,2,'Benh vien Thong Nhat','Ho Chi Minh');