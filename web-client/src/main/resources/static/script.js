var emptyString = '';
var wrote = ' napisał ';
var welcome = 'Witaj ';
var socket = new SockJS('/register');
var stompClient = Stomp.over(socket);
var sessionId;

$(function () {
    stompClient.connect({}, function () {
        sessionId = /\/([^\/]+)\/websocket/.exec(socket._transport.url)[1];
        $('#hi').html(welcome + sessionId + "!");

        stompClient.subscribe('/feed/info', function (data) {
            let content = JSON.parse(data.body).content;
            $('#message_box').append('<div class="general">' + content + '</div>');
        });

        stompClient.subscribe('/feed/' + sessionId, function (data) {
            let content = JSON.parse(data.body).content;
            $('#message_box').append('<div class="private">' + content + '</div>');
        });
    });

    $('#form').submit(function (event) {
        event.preventDefault();
        let message = $('#message');
        let toServer = {
            content: sessionId + wrote + message.val()
        };
        stompClient.send("/send/message", {}, JSON.stringify(toServer));
        message.val(emptyString);
        message.attr("placeholder", emptyString);
    });
});
