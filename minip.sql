CREATE TABLE admin_user (name VARCHAR2(30) NOT NULL,
                            password VARCHAR(13) NOT NULL, 
                            email VARCHAR2(30) PRIMARY KEY,
                            mess VARCHAR2(5) NOT NULL);

CREATE TABLE student_users (rollNo VARCHAR2(30) PRIMARY KEY, 
                            password VARCHAR(13) NOT NULL, 
                            email VARCHAR2(30) NOT NULL,
                            name VARCHAR2(30) NOT NULL,
                            verified NUMBER(1,0) NOT NULL,
                            mess VARCHAR2(5) NOT NULL,
                            accountNo VARCHAR(15) NOT NULL,
                            IFSCCode VARCHAR(15) NOT NULL,
                            bankName VARCHAR(30) NOT NULL,
                            bankBranch VARCHAR(15) NOT NULL,
                            accountHolderName VARCHAR(30));

INSERT into student_users values ('IIT2017100','test','iit100','testname',0,'BH-5','testacc','testifsc','bank','branch','holder');
INSERT into student_users values ('IIT2017099','test','iit99','testname2',1,'BH-5','testacc','testifsc','bank','branch','holder');
INSERT into student_users values ('IIT2017098','test','iit98','testname3',0,'BH-5','testacc','testifsc','bank','branch','holder');
INSERT into student_users values ('IIT2017097','test','iit97','testname4',1,'BH-5','testacc','testifsc','bank','branch','holder');
INSERT into student_users values ('IIT2017096','test','iit96','testname5',0,'BH-5','testacc','testifsc','bank','branch','holder');
INSERT into student_users values ('IIT2017095','test','iit96','testname5',0,'BH-4','testacc','testifsc','bank','branch','holder');


CREATE TABLE attendence_record(rollNo VARCHAR2(30) REFERENCES student_users(rollNo),
                                currDate NUMBER(15, 0),
                                timeSlot NUMBER(1,0),
                                CONSTRAINT attendence PRIMARY KEY (rollNo, currDate, timeSlot));

insert into attendence_record values ('IIT2017100',86832,2);
insert into attendence_record values ('IIT2017100',86832,3);
insert into attendence_record values ('IIT2017100',86832,4);
insert into attendence_record values ('IIT2017100',86832,1);

CREATE TABLE feedback_record(rollNo VARCHAR2(30) REFERENCES student_users(rollNo),
                                currDate NUMBER(15, 0),
                                timeSlot NUMBER(1,0),
                                rating NUMBER(1, 0) NOT NULL,
                                complaint VARCHAR(100),
                                PRIMARY KEY (rollNo, currDate, timeSlot),
                                CONSTRAINT fk_attendence FOREIGN KEY (rollNo, currDate, timeSlot)
                                REFERENCES attendence_record(rollNo, currDate, timeSlot)); 


insert into feedback_record values ('IIT2017100',86832,2,4,'ek kaam kar yahi hagde');
insert into feedback_record values ('IIT2017100',86832,3,4,'ek kaam kar yahi hagde');
insert into feedback_record values ('IIT2017100',86832,4,4,'ek kaam kar yahi hagde');
insert into feedback_record values ('IIT2017100',86832,1,4,'ek kaam kar yahi hagde');




CREATE OR REPLACE PROCEDURE create_admin(
    
    name admin_user.name%TYPE,
    password admin_user.password%TYPE,
    email admin_user.email%TYPE,
    mess admin_user.mess%TYPE)
AS
BEGIN
    INSERT INTO admin_user VALUES (name, password, email, mess);
END;
/


select * from student_user;

CREATE OR REPLACE PROCEDURE verify_user(
    mRollNo student_users.rollNo%TYPE)
    AS
BEGIN
    UPDATE student_user SET verified = 1 WHERE rollNo =  mRollNo;
END;
/

CREATE TABLE rebate_application(rollNo VARCHAR2(30) REFERENCES student_users(rollNo), 
                                fromDate NUMBER(15, 0),
                                toDate NUMBER(15, 0),
                                accepted NUMBER(1,0),
                                PRIMARY KEY (rollNo, fromDate, toDate));

insert into rebate_application values ('IIT2017100',862832,26353,0);
insert into rebate_application values ('IIT2017097',861832,263633,0);
insert into rebate_application values ('IIT2017098',868322,26322,0);
insert into rebate_application values ('IIT2017099',868232,262253,0);

create or replace procedure accept_rebate(
    mRollNo student_users.rollNo%type,
    mFromDate rebate_application.fromDate%type,
    mToDate rebate_application.toDate%type)
    AS
BEGIN
    update rebate_application set accepted = 1 where rollNo = mRollNo and mFromDate = fromDate and mToDate = toDate;
END;
/

create table day_menu (mess VARCHAR2(30),
                        weekday VARCHAR(20),
                        timeSlot NUMBER (1,0),
                        menu VARCHAR2(200),
                        PRIMARY KEY (mess,weekday,timeSlot));

create table fixed_slot_menu (mess VARCHAR2(30),
                              timeSlot NUMBER(1,0),
                              menu VARCHAR2(200),
                              PRIMARY KEY (mess,timeSlot));

insert into day_menu values ('BH5',1,1,'Aalo Paratha, Dahi, Mithai');
insert into day_menu values ('BH5',1,2,'Rajma, Chana Dal');
insert into day_menu values ('BH5',1,3,'Chicken, Gulaab Jamun');
insert into day_menu values ('BH5',2,1,'Idhi, Sambhar, Nariyal');
insert into day_menu values ('BH5',3,1,'Karela ki bhajiya, Neem ki kadhi');

insert into fixed_slot_menu values ('BH5',1,'Coffee, Chai');
insert into fixed_slot_menu values ('BH5',2,'Aachar, Salad, Tatti');
insert into fixed_slot_menu values ('BH5',3,'Paneer, Roti');