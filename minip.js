const oracledb = require("oracledb");
const uniqueString = require("unique-string")
const express = require('express');
const app = express();
const bodyParser = require("body-parser")
const url = require("url")
const queryString = require("querystring")
const cors = require('cors');

//setup body-parser

app.use(bodyParser.urlencoded({extended : true}));
app.use(bodyParser.json());
//enables cors
app.use(cors());

app.use(function(req, res, next) {
   res.header("Access-Control-Allow-Origin", "*");
   res.header('Access-Control-Allow-Methods', 'DELETE, PUT, GET, POST');
   res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
   next();
});

oracledb.autoCommit = true;



const TABLE_ADMIN_USER = "admin_user";
const TABLE_STUDENT_USER = "student_users";
const TABLE_USER_FEEDBACKS = "feedback_record";
const TABLE_REBATE_APPLICATION = "rebate_application";


const COL_PASSWORD = "password";
const COL_EMAIL = "email";
const COL_NAME = "name";

const COL_MESS = "mess";
const COL_VERIFIED = "verified";
const COL_ROLLNO = "rollNo";
const COL_ACCOUNTNO = "accountNo";
const COL_IFSCCODE = "IFSCCode";
const COL_BANKNAME = "bankName";
const COL_BANKBRANCH = "bankBranch";
const COL_ACCOUNTHOLDERNAME = "accountHolderName";

const COL_CURRDATE = "currDate";
const COL_TIMESLOT = "timeSlot";
const COL_RATING = "rating";
const COL_COMPLAINT = "complaint";
const COL_FROMDATE = "fromDate";
const COL_TODATE = "toDate";


let connection;
// app.use(express.static('public'));

async function run() {
    
connection = await oracledb.getConnection(
  {
    user          : "shivansh",
    password      : "60996099",
    connectString : "(DESCRIPTION =(ADDRESS = (PROTOCOL = TCP)(HOST = localhost)(PORT = 1521))(CONNECT_DATA =(SID=XE)))"
  }, 
);
}

app.post("/admin", (req, res) => {
    console.log("Data Recieved : "+req.body.email+" "+req.body.name+" "+req.body.mess+" "+req.body.password);
    mEmail = req.body.email;
    mName = req.body.name;
    mMess = req.body.mess;
    mPassword = req.body.password;


    query = "BEGIN create_admin( :name, :password, :email, :mess); END;"

    connection.execute(query, {
        name: mName,
        email: mEmail,
        password: mPassword,    
        mess: mMess, 
    }, (err, result) => {
        
        if (err) { 
            console.error(err); 
            res.send({"status": false}); 
            return; 
        }
        res.send("true");
    })
})


app.get("/login/:email/:password", (req, res) => {
    
    parsedUrl = url.parse(req.url);
    parsedQrs = queryString.parse(parsedUrl.query);
    mEmail = req.params.email;
    mPassword = req.params.password;

    query = "SELECT * FROM " + TABLE_ADMIN_USER + " WHERE " + COL_EMAIL + " = :email AND " + COL_PASSWORD + " = :password";

    connection.execute(query, {
        email : mEmail, 
        password : mPassword
    }, (err, result) => {
        if (err) { 
            console.error(err); 
            res.send("Error"); 
            return; 
        }
        
        if(result.rows.length == 1) {
            console.log(result.rows[0])

            console.log("Length : "+result.rows.length);
                obj = {};
                keys = [COL_NAME,COL_PASSWORD,COL_EMAIL,COL_MESS];
                user = result.rows[0];
                for (j=0;j<keys.length;j++) {
                    obj[keys[j]] = user[j];
                }
                res.send(obj);
        } else {
            res.send({"status": false,"message": "Invalid"})
        }
        
    })
})


app.get("/admin/:name", (req, res)=> {

    parsedUrl = url.parse(req.url);
    parsedQrs = queryString.parse(parsedUrl.query);
    mName = req.params.name;
    
    //console.log(mName);

    query = "SELECT * FROM " + TABLE_ADMIN_USER + " WHERE " + COL_NAME + " = :name";
    //console.log(query)

    connection.execute(query, {name: mName}, (err, result) => {
        if(err) { 
            console.error(err); 
            res.send({"status": false});
            return; 
        } else if (result.rows.length != 1) {
            res.send({"status": false,"message": "not valid"})
            return;
        }
        var arrAdmin = [];
        obj = {};
        keys = [COL_NAME, COL_PASSWORD, COL_EMAIL,COL_MESS];

        var l = keys.length;
        user = result.rows[0]
        for(i = 0; i<l;i++){
            obj[keys[i]] = user[i];
        }
        arrAdmin.push(obj);

        res.send(obj);
    })
})

app.get("/unverified/:mess", (req,res) =>{

    parsedUrl = url.parse(req.url);
    parsedQrs = queryString.parse(parsedUrl.query);

    mMess = req.params.mess;
    query = "SELECT * FROM " + TABLE_STUDENT_USER + " WHERE verified = 0 AND " + COL_MESS + " = :mess";
    //console.log(query);

    connection.execute(query,{mess:mMess},(err, result) => {
        if (err) {
            console.log(err);
            res.send({"status":false,"message":"Error"});
            return;
        }

        var arrUnverUser = [];

        console.log("Length : "+result.rows.length);
        for (i=0;i<result.rows.length;i++) {
            obj = {};
            keys = [COL_ROLLNO, COL_PASSWORD, COL_EMAIL, COL_NAME, COL_ACCOUNTNO,
                COL_IFSCCODE, COL_BANKNAME, COL_BANKBRANCH, COL_ACCOUNTHOLDERNAME];
            user = result.rows[i];
            for (j=0;j<keys.length;j++) {
                obj[keys[j]] = user[j];
            }
            arrUnverUser.push(obj);
        }
        res.send(arrUnverUser);
    })
})

app.post("/admin/verify", (req, res) => {
    mRollNo = req.body.rollNo;
    console.log(mRollNo);

    query = "BEGIN verify_user( :rollNo); END;";

    connection.execute(query, {
        rollNo: mRollNo
    }, (err, result) => {
        if (err) { 
            console.error(err); 
            res.send("flase"); 
            return; 
        }
        console.log(result);
        res.send("true");
    })
})

app.post("/rebate/verify", (req, res) => {
    mRollNo = req.body.rollNo;
    mFromDate = req.body.fromDate;
    mToDate = req.body.toDate;

    console.log(mRollNo);
    console.log(mFromDate);console.log(mToDate);

    query = "BEGIN accept_rebate( :rollNo, :fromDate, :toDate); END;";

    connection.execute(query, {
        rollNo: mRollNo,
        fromDate: mFromDate,
        toDate: mToDate
    }, (err, result) => {
        if (err) { 
            console.error(err); 
            res.send("flase"); 
            return; 
        }
        console.log(result);
        res.send("true");
    })
})

app.post("/rebate/verify", (req, res) => {
    mRollNo = req.body.rollNo;
    mFromDate = req.body.fromDate;
    mToDate = req.body.toDate;

    console.log(mRollNo);
    console.log(mFromDate);console.log(mToDate);

    query = "BEGIN accept_rebate( :rollNo, :fromDate, :toDate); END;";

    connection.execute(query, {
        rollNo: mRollNo,
        fromDate: mFromDate,
        toDate: mToDate
    }, (err, result) => {
        if (err) { 
            console.error(err); 
            res.send("flase"); 
            return; 
        }
        console.log(result);
        res.send("true");
    })
})

app.post("/poll/:mess", (req, res) => {
    mId = req.body.pollID
    mQues = req.body.pollquestion;
    mOptions = req.body.options;
    var array = mOptions.split(',')
    console.log(mId+" "+mQues+" "+mOptions);

    parsedUrl = url.parse(req.url);
    parsedQrs = queryString.parse(parsedUrl.query);

    mMess = req.params.mess;

    query = "INSERT INTO poll_question VALUES (:id, :ques, :mess,1)";
    console.log(query);

    connection.execute(query, {
        id: mId,
        ques: mQues,
        mess: mMess
    }, (err, result) => {
        if (err) { 
            console.error(err); 
            //res.send("flase"); 
            return; 
        }
        console.log(result);
    })

    for (i=0;i<array.length-1;i++) {

        console.log(i);
        query2 = "INSERT INTO poll_options VALUES (:id, :no, :optiontext)";

    connection.execute(query2, {
        id: mId,
        no: i+1,
        optiontext: array[i]
    }, (err, result) => {
        if (err) { 
            console.error(err); 
            //res.send("flase"); 
            return; 
        }
        console.log(result);
    
    })
    }

    res.send("true");
})



app.get("/feedbacks/:mess", (req, res) => {

    parsedUrl = url.parse(req.url);
    parsedQrs = queryString.parse(parsedUrl.query);

    mMess = req.params.mess;
    query = "SELECT feedback_record."+COL_ROLLNO+", "+COL_CURRDATE+", "+COL_TIMESLOT+", "+COL_RATING+", "
    +COL_COMPLAINT+ " from " + TABLE_USER_FEEDBACKS+", "+TABLE_STUDENT_USER
     + " where student_users.mess = :mess and student_users.rollNo = feedback_record.rollNo";
    console.log(query);
    connection.execute(query,{mess:mMess}, (err, result) => {
        if (err) { 
            console.error(err); 
            res.send({"status": false}); 
            return; 
        }

        var arrFeedbacks = [];

        console.log("Length : "+result.rows.length);

        for (i = 0;i < result.rows.length;i++) {
            obj = {};
            keys = [COL_ROLLNO,COL_CURRDATE, COL_TIMESLOT, COL_RATING, COL_COMPLAINT];
            user = result.rows[i];
            for (j=0;j<keys.length;j++) {
                obj[keys[j]] = user[j];
            }
            arrFeedbacks.push(obj);
        }
        res.send(arrFeedbacks);
    })
})

app.get("/rebate/:mess", (req, res) => {

    parsedUrl = url.parse(req.url);
    parsedQrs = queryString.parse(parsedUrl.query);
    mMess = req.params.mess;

    query = "SELECT rebate_application."+COL_ROLLNO+", "
    +COL_FROMDATE+", "+COL_TODATE + " from " + TABLE_REBATE_APPLICATION+", "+TABLE_STUDENT_USER
     + " where student_users.mess = :mess and student_users.rollNo = rebate_application.rollNo and accepted = 0";
    console.log(query);
    connection.execute(query,{mess:mMess}, (err, result) => {
        if (err) { 
            console.error(err); 
            res.send({"status": false}); 
            return; 
        }

        var arrRebates = [];

        console.log("Length : "+result.rows.length);

        for (i = 0;i < result.rows.length;i++) {
            obj = {};
            keys = [COL_ROLLNO,COL_FROMDATE, COL_TODATE];
            user = result.rows[i];
            for (j=0;j<keys.length;j++) {
                obj[keys[j]] = user[j];
            }
            arrRebates.push(obj);
        }
        res.send(arrRebates);
    })
})

app.get("/menu/:mess/", (req, res) => {

    parsedUrl = url.parse(req.url);
    parsedQrs = queryString.parse(parsedUrl.query);
    mMess = req.params.mess;
    
    query = "SELECT day_menu.timeSlot, weekday, day_menu.menu, fixed_slot_menu.menuDaily from fixed_slot_menu, day_menu where fixed_slot_menu.mess = :mess and fixed_slot_menu.timeSLot = day_menu.timeSlot ORDER BY weekday, timeSlot"
    console.log(query);
   
   
    connection.execute(query,{mess:mMess}, (err, result) => {
        if (err) { 
            console.error(err); 
            res.send({"status": false}); 
            return; 
        }

        console.log("Length : "+result.rows);

        var final = [];
        for (i = 0;i < 3 ; i++) {
            //console.log("I : "+i);
            keys = ["timeSlot", "fixedMenu","mondayMenu","tuesdayMenu","wednesdayMenu","thursdayMenu","fridayMenu","saturdayMenu","sundayMenu"];
            obj = {};

            user1 = result.rows[i];
            obj[keys[0]] = user1[0];
            obj[keys[1]] = user1[3];
            count = 2;

            for (j = i;j < result.rows.length;j = j+3) {
                //console.log("J : "+j);
                
                user = result.rows[j];
                obj[keys[count]] = user[2];
                count++;
            }
            final.push(obj);
        }
        res.send(final);
    })
})


app.post("/add/menu/:mess", (req, res) => {

    parsedUrl = url.parse(req.url);
    parsedQrs = queryString.parse(parsedUrl.query);
    mMess = req.params.mess;
    

    mTimeSlot = req.body.timeSlot;
    mfixedMenu = req.body.fixedMenu;
    mondayMenu = req.body.mondayMenu;
    tuesdayMenu = req.body.tuesdayMenu;
    wednesdayMenu = req.body.wednesdayMenu;
    thursdayMenu = req.body.thursdayMenu;
    fridayMenu = req.body.fridayMenu;
    saturdayMenu = req.body.saturdayMenu;
    sundayMenu = req.body.sundayMenu;




    query = "BEGIN add_menu( :mess, :fixedmenu, :mondayMenu, :tuesMenu, :wedMenu, :thurMenu, :friMenu, :satMenu, :sunMenu, :timeSlot); END;";

    connection.execute(query, {
        mess: mMess,
        fixedMenu: mfixedMenu,
        mondayMenu : mondayMenu,
        tuesMenu: tuesdayMenu,
        wedMenu : wednesdayMenu,
        thurMenu : thursdayMenu,
        friMenu : fridayMenu,
        satMenu : saturdayMenu,
        sunMenu : sundayMenu,
        timeSlot : mTimeSlot
    }, (err, result) => {
        if (err) { 
            console.error(err); 
            res.send("flase"); 
            return; 
        }
        console.log(result);
        res.send("true");
    })
})


run();


app.listen(4000, function(){
  console.log("server started........");
})