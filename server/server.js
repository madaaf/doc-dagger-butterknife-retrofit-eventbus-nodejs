var path = require('path');
var express = require('express');
var http = require('http');
var app = express();

app.use(express.static(path.join(__dirname, 'public')));

require('./module/GameService.js').services(app);

var server = http.createServer(app);
server.listen(5001);
console.log("server listen on port 5001");
