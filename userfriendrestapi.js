const express = require('express');
const router = express.Router();

const app = express();
const port = process.env.PORT || 5000;

app.listen(port, () => console.log(`Listening on port ${port}`));

var bodyParser = require('body-parser');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true })); 


var mysql = require('mysql')
var connection = mysql.createConnection({
  host: 'localhost',
 //Private sorry :)
});

connection.connect((err)=> {
    if(err) throw err;
    console.log("Connected to database!");
});

//--------------------------------------------------------------GET ALL hackusers-------------------------------------------
//get all hackusers
app.get('/employees', function (req, res) {
    console.log("GET REQUEST RECIEVED!");
    console.log(req);
    res.write("{\"hackusers\": ");
    connection.query('select * from hackusers', function (error, results, fields) {
      if (error) throw error;
      console.log(JSON.stringify(results));
      res.write(JSON.stringify(results));
      res.end(", \"numbers\": 2, \"message\": \"success\"}");
    });
  });
  

//--------------------------------------------------------------GET USER ID FROM USERNAME------------------------------------
//get user id 
app.get('/getUsername/:username', function (req, res) {
  console.log(req);
  connection.query(`select * from hackusers where user_name = ?`, [req.params.username], function (error, results, fields) {
    if (error) throw error;
    res.end("{\"gotUsername\":" + JSON.stringify(results) + "}");
  });
});


//--------------------------------------------------------------MAKE NEW USER/PASS------------------------------------
//actually plswork3
//add user -- post req
app.get('/addUser/:username/:password', function (req, res) {
  console.log(req);
  var userExists = false;
  //Check if database already contains this username
  connection.query("SELECT * FROM hackusers WHERE user_name = ?",
  [req.params.username], function (error, results, fields) {
    if (error) throw error;
    if(results.length!=0){
      res.end("Error! This username already exists.");
      userExists = true;
    }
 });
 //Add user
 if(!userExists){
    connection.query("INSERT INTO hackusers (`user_name`, `password`) VALUES (?, ?);", 
    [req.params.username, req.params.password], function (error, results, fields) {
    if (error) throw error;
    res.end("{\"addUser\": [{\"message\":\"Success\"}]}");
  });
}
});

//--------------------------------------------------------LOGIN: AUTHENTICATE A USER/PASS------------------------------------
app.get('/login/:username/:password', function (req, res) {
  console.log(req);
  connection.query("SELECT * FROM hackusers WHERE user_name = ? AND password = ?", [req.params.username, req.params.password], function (error, results, fields) {
  if (error) throw error;
  if(results.length == 0){
    res.end("{\"login\": [{\"message\":\"Failure\"}]}");
  }
  else {res.end("{\"login\": [{\"message\":\"Success\"}]}");}
 });
});


//--------------------------------------------------------------DELETE A USER----------------------------------------------
//delete a user
app.get('/deleteUser/:user_name', function (req, res) {
  console.log(req);
  connection.query(`DELETE FROM hackusers WHERE user_name = ?`, [req.params.user_name], function (error, results, fields) {
    if (error) throw error;
    res.end(JSON.stringify(results));
  });
});



//get
//-------------------------------------------------------ADD A FRIEND------------------------------------
//get device id 
app.get('/addFriend/:username/:friend_name/:friend_phone', function (req, res) {
    console.log(req);
   //Add user
      connection.query("INSERT INTO hackfriends (`user_name`, `friend_name`, `friend_phone`) VALUES (?, ?, ?);", 
      [req.params.username, req.params.friend_name, req.params.friend_phone], function (error, results, fields) {
      if (error) throw error;
      res.end("{\"addFriend\": [{\"message\":\"Success\"}]}");
    });
  });

//post
//-------------------------------------------------------GET A FRIEND'S PHONE #-----------------------------------------
app.get('/getfriend/:username', function (req, res) {
  console.log(req);
  connection.query("SELECT * FROM hackfriends WHERE user_name = ?",
  [req.params.username], function (error, results, fields) {
    if (error) throw error;
    if(results.length!=0){
      res.write("{\"hackfriends\": ");
      res.write(JSON.stringify(results));
      res.end(", \"numbers\": 2, \"message\": \"success\"}");
    }
    else{
        res.end("Error! This username has no friends :(((.");
    }
 });
});
