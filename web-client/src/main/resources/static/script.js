var socket = new SockJS('/register');
var stompClient = Stomp.over(socket);
stompClient.connect({}, function (frame) {
    stompClient.subscribe('/feed/info', function (data) {
        $('.message_box').append('<div>' + data.body + '</div>');
    });
});

function sendMessage() {
    stompClient.send("/client/message", {}, JSON.stringify({'content': 'Joshua'}));
}
