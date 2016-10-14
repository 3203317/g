var PORT = 3150;
var HOST = '127.0.0.1';

var dgram = require('dgram');
var message = new Buffer('haha');

var client = dgram.createSocket('udp4');
client.send(message, 0, message.length, PORT, HOST, function(err, bytes) {
    if (err) throw err;
    console.log('UDP message sent to ' + HOST +':'+ PORT);
    // client.close();
});

client.on('message',function(data){
    var data=JSON.parse(data.toString());
    if(!data.from){
        console.log(data.content);
    }else{
        if(!data.to){
            console.log("[%s]:%s",data.from.name,data.content);
        }else{
            console.log("[%s@%s]:%s",data.from.name,data.to.name,data.content);
        }
    }
});

client.on('error', function(err){
    console.log(`server error:\n${err.stack}`);
    server.close();
})