var socket = new SockJS('/register');
var stompClient = Stomp.over(socket);
stompClient.connect({}, function () {
    stompClient.subscribe('/feed/info', function (data) {
        let content = JSON.parse(data.body).content;
        $('.message_box').append('<div>' + content + '</div>');
    });
});

function sendMessage() {
    stompClient.send("/send/message", {}, JSON.stringify($('#message').val()));
}
