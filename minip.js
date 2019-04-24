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
const TABLE_STUDENT_USER = "student_user";
const TABLE_USER_FEEDBACKS = "user_feedback";


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
            keys = [COL_ROLLNO, COL_PASSWORD, COL_EMAIL, COL_NAME, COL_VERIFIED, COL_MESS, COL_ACCOUNTNO,
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
            res.send({"status": false}); 
            return; 
        }
        console.log(result);
        res.send({"status" : true});
    })
})


app.get("/feedbacks/:mess", (req, res) => {

    parsedUrl = url.parse(req.url);
    parsedQrs = queryString.parse(parsedUrl.query);

    mMess = req.params.mess;
    query = "SELECT * from " + TABLE_USER_FEEDBACKS + " where mess = :mess";
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
            keys = [COL_ROLLNO, COL_PASSWORD, COL_CURRDATE, COL_TIMESLOT, COL_RATING, COL_COMPLAINT];
            user = result.rows[i];
            for (j=0;j<keys.length;j++) {
                obj[keys[j]] = user[j];
            }
            arrFeedbacks.push(obj);
        }
        res.send(arrFeedbacks);
    })
})

app.post("/createpoll", (req, res) => {

    //console.log(Object.keys(req.body).length);
    
    arrKeys = Object.keys(req.body);

    arrKeys.forEach(element => {
        console.log(req.body[element].options);
    });

    res.send({"status":"true"});
})

run();


app.listen(3000, function(){
  console.log("server started........");
})