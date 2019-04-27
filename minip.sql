-- CREATE TABLE admin_user (name VARCHAR2(30) NOT NULL,
--                             password VARCHAR(13) NOT NULL, 
--                             email VARCHAR2(30) PRIMARY KEY,
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


-- CREATE TABLE user_feedback (rollNo VARCHAR2(30),
--                             currentDate VARCHAR(30) NOT NULL, 
--                             timeSlot NUMBER NOT NULL CHECK (timeSlot > 0 and timeSlot < 4),
--                             rating NUMBER NOT NULL CHECK (rating > 0 and rating < 6),
--                             complaint VARCHAR(100),
--                             PRIMARY KEY (rollNo, currentDate, timeSlot));

-- insert into user_feedback values ('IIT2017100','26/11/11',3,5,'maal bhai');
-- insert into user_feedback values ('IIT2017100','26/11/11',2,3,'maal bhai');
-- insert into user_feedback values ('IIT2017100','26/11/11',1,2,'maal bhai');
-- insert into user_feedback values ('IIT2017099','26/11/11',3,5,'maal bhai');
-- insert into user_feedback values ('IIT2017099','26/11/11',2,3,'maal bhai');
-- insert into user_feedback values ('IIT2017099','26/11/11',1,2,'maal bhai');
-- insert into user_feedback values ('IIT2017096','26/11/11',3,5,'maal bhai');
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
-- INSERT into student_user values ('IIT2017095','test','iit96','testname5',0,'BH-4','testacc','testifsc','bank','branch','holder');


-- select * from student_user;

-- CREATE OR REPLACE PROCEDURE verify_user(
--     mRollNo student_user.rollNo%TYPE)
--     AS
-- BEGIN
--     UPDATE student_user SET verified = 1 WHERE rollNo =  mRollNo;
-- END;
-- /