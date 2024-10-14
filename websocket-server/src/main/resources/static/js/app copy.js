var socket = new SockJS("http://localhost:8080/gs-guide-websocket");

// 获取 STOMP 子协议的客户端对象
var stompClient = Stomp.over(socket);
stompClient.debug = function (str) {
    console.log("DEBUG---->" + str);
};
/*const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-guide-websocket'
});*/

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connectedeeeeeeeeeeeeeeeeeee: ' + frame);
    stompClient.subscribe('/topic/greetings', (greeting) => {
        showGreeting(JSON.parse(greeting.body).content);
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

const username = "zs";
const subscribe = "zs";

function connect() {
    stompClient.connect({name: username, token: username}, function connectCallback(frame) {
            // 连接成功时（服务器响应 CONNECTED 帧）的回调方法
            console.log("连接成功");
            console.log("---订阅：" + subscribe);
            stompClient.subscribe(subscribe, function (res) {

                console.log("----res:" + res);
                let result = JSON.parse(res.body);
                console.log(result);
            });
        },
        function errorCallBack(error) {
            // 连接失败时（服务器响应 ERROR 帧）的回调方法
            console.log("连接失败");
        });
}

function disconnect() {
    stompClient.disconnect()
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.publish({
        destination: "/app/hello",
        body: JSON.stringify({'name': $("#name").val()})
    });
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#connect").click(() => connect());
    $("#disconnect").click(() => disconnect());
    $("#send").click(() => sendName());
});