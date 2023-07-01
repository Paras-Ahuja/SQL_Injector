var express = require('express');
var mysql = require('mysql');

var con = mysql.createConnection({
    host:"localhost",
    user:"root",
    password:"password",
    database:"mysql"
});

con.connect((err)=>{
    if (err){
console.log("not connected");
throw err;
    } else{
        console.log("Connected");
        // con.query("Insert INTO employee VALUES ('abc','def')",function(err,result){
        //     if(err) throw err;
        //     console.log("Database Created");
        // });
        // var str;
        // con.query("SELECT * FROM employee", function (err, result, fields) {
        //     if (err) throw err;
        //     // console.log(result);
        //     // console.log(fields);
        //     // str= result;
        //     console.log(result.length);
        //   });
          
    }
    
});

var app=express()

// con.connect((err)=>{
    
//     console.log("Connected");
// })

app.get("/login",(req,res)=>{
    console.log(req.query);
    console.log("Username= ",req.query.username);
    console.log("Password= ",req.query.password);
    // res.send(`
    // <div align='center'>
    // <br><br><h2>Login Sucessful</div>`);
    //,'${req.query.password}'
    con.query(`Select * from employee where username='${req.query.username}'`, function (err, result, fields){
        if (err||result.length==0) {
            // console.log(result);
            res.status(400);
            res.send(`
            <div align='center'><h1>Unable to Login<h1></div>
            `);
            // throw err;
        }else{
            // console.log(res)
            res.send(`
            <div align='center'><h1>Logged in Successfully</h1></div>
            `);
        }
    });
    
    
})

app.listen('3000',()=>{
    console.log("server started");
})