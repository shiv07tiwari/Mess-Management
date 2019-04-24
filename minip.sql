-- CREATE TABLE admin_user (name VARCHAR2(30) NOT NULL,
--                             password VARCHAR(13) NOT NULL, 
--                             email VARCHAR2(30) NOT NULL,
--                             mess VARCHAR2(5) NOT NULL);

-- CREATE TABLE student_user (rollNo VARCHAR2(30) PRIMARY KEY, 
--                             password VARCHAR(13) NOT NULL, 
--                             email VARCHAR2(30) NOT NULL,
--                             name VARCHAR2(30) NOT NULL,
--                             verified NUMBER(1,0) NOT NULL,
--                             mess VARCHAR2(5) NOT NULL,
--                             accountNo VARCHAR(15) NOT NULL,
--                             IFSCCode VARCHAR(15) NOT NULL,
--                             bankName VARCHAR(30) NOT NULL,
--                             bankBranch VARCHAR(15) NOT NULL,
--                             accountHolderName VARCHAR(30));


-- CREATE TABLE user_feedback (rollNo VARCHAR2(30) PRIMARY KEY,
--                             mess VARCHAR(5) NOT NULL,
--                             currentDate VARCHAR(30) NOT NULL, 
--                             timeSlot VARCHAR2(30) NOT NULL,
--                             rating NUMBER NOT NULL,
--                             complaint VARCHAR(100));
insert into user_feedback values ('IIT2017100','BH-5','aaj','abhi',4,'maal bhai');
insert into user_feedback values ('IIT2017101','BH-4','aaj','abhi',2,'baal bhai');
insert into user_feedback values ('IIT2017102','BH-5','aaj','abhi',3,'daal bhai');
insert into user_feedback values ('IIT2017105','BH-5','aaj','abhi',1,'ek kaam kar yahi hagde');

-- CREATE OR REPLACE PROCEDURE create_admin(
    
--     name admin_user.name%TYPE,
--     password admin_user.password%TYPE,
--     email admin_user.email%TYPE,
--     mess admin_user.mess%TYPE)
-- AS
-- BEGIN
--     INSERT INTO admin_user VALUES (name, password, email, mess);
-- END;
-- /

-- INSERT into student_user values ('IIT2017100','test','iit100','testname',0,'BH-5','testacc','testifsc','bank','branch','holder');
-- INSERT into student_user values ('IIT2017099','test','iit99','testname2',1,'BH-5','testacc','testifsc','bank','branch','holder');
-- INSERT into student_user values ('IIT2017098','test','iit98','testname3',0,'BH-5','testacc','testifsc','bank','branch','holder');
-- INSERT into student_user values ('IIT2017097','test','iit97','testname4',1,'BH-5','testacc','testifsc','bank','branch','holder');
-- INSERT into student_user values ('IIT2017096','test','iit96','testname5',0,'BH-5','testacc','testifsc','bank','branch','holder');

-- select * from student_user;

-- CREATE OR REPLACE PROCEDURE verify_user(
--     mRollNo student_user.rollNo%TYPE)
--     AS
-- BEGIN
--     UPDATE student_user SET verified = 1 WHERE rollNo =  mRollNo;
-- END;
-- /