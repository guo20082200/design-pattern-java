// 两种方式创建 Stomp客户端对象
// 直接通过 Stomp 获取 客户端对象
// const url = "ws://localhost:8080/portfolio";
// const client = Stomp.client(url);
// console.log("client: " + client)

// 构建SockJS对象，
const socketJS = new SockJS("http://localhost:8080/gs-guide-websocket");
// 通过socketJS对象获取 STOMP 子协议的客户端对象
const stompClient = Stomp.over(socketJS);
console.log("stompClient: " + stompClient)
// 开启调试，
stompClient.debug = function (str) {
    console.log("DEBUG---->" + str);
};

/**
 * 发生了以下的动作：
 * 1. DEBUG---->Opening Web Socket..
 * 2. DEBUG---->Web Socket Opened...
 * 3. DEBUG---->>>> CONNECT 带上账号和密码， 心跳参数
 * 4. DEBUG----><<< CONNECTED 已经连接
 * 5. DEBUG---->connected to server undefined
 * 6. 连接成功，执行回调函数 function connectCallback(frame)
 * 7. 连接失败，执行回调函数 errorCallback(error)
 */
// stompClient.connect('guest', 'guest', function connectCallback(frame) {
//     //stompClient.debug("connected to Stomp" + frame);
//     console.log("frame ： " + frame)
//     stompClient.subscribe('/topic/greetings', function (message) {
//         $("#messages").append("<p>" + message.body + "</p>\n");
//     }, function errorCallback(error) {
//         // 连接失败时（服务器响应 ERROR 帧）的回调方法
//         console.log("连接失败:" + error);
//     });
// });

//
// $(function () {
//     $("#connect").click(function () {
//         console.log("点击连接")
//         stompClient.connect('guest', 'guest', function connectCallback(frame) {
//             //stompClient.debug("connected to Stomp" + frame);
//             console.log("frame ： " + frame)
//             stompClient.subscribe('/topic/greetings', function (message) {
//                 $("#messages").append("<p>" + message.body + "</p>\n");
//             }, function errorCallback(error) {
//                 // 连接失败时（服务器响应 ERROR 帧）的回调方法
//                 console.log("连接失败:" + error);
//             });
//         });
//     });
//     $("#send").click(function () {
//         console.log("send")
//         stompClient.send({
//             destination: "/app/hello",
//             headers: {"username": "aaaa", "pwd": "bbb"},
//             body: JSON.stringify({'name': $("#name").val()})
//         });
//     });
// });


function connect() {
    stompClient.connect("aerfae", "aerfaer", function connectCallback(frame) {
            // 连接成功时（服务器响应 CONNECTED 帧）的回调方法
            console.log("连接成功 :" + frame);
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


function sendName() {
    stompClient.send({
        destination: "/app/hello",
        headers: {},
        body: JSON.stringify({'name': $("#name").val()})
    });
}


$(function () {
    //$("form").on('submit', (e) => e.preventDefault());
    $("#connect").click(() => connect());
    $("#disconnect").click(() => disconnect());
    $("#send").click(() => sendName());
});

