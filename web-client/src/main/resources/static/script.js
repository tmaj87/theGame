var emptyString = '';
var wrote = ': ';
var sendNewMessage = 'Wiadomość...';
var socket = new SockJS('/register');
var stompClient = Stomp.over(socket);
var sessionId;

$(function () {
    stompClient.connect({}, function () {
        sessionId = /\/([^\/]+)\/websocket/.exec(socket._transport.url)[1];
        $('#hi').html(sessionId);
        stompClient.subscribe('/feed/info', newFeed);
    });

    $('#form').submit(function (event) {
        event.preventDefault();
        submitForm();
    });
});

function newFeed(data) {
    let body = JSON.parse(data.body);
    let content = body.content;
    let type = body.type.toLowerCase();
    let message = getMessageByType(content, type);
    $('#message_box').append('<div class="' + type + '">' + message + '</div>');
}

function getMessageByType(content, type) {
    let message;
    switch (type) {
        case 'won' :
            stompClient.disconnect();
            if (content === sessionId) {
                message = 'Wygrałeś!';
            } else {
                message = 'Przegrałeś,';
            }
            message += ' <a href="/">zagraj jeszcze raz</a>';
            break;
        default :
            message = content;
            break;
    }
    return message;
}

function submitForm() {
    let message = $('#message');
    let toServer = {
        content: sessionId + wrote + message.val(),
        user: sessionId
    };
    stompClient.send("/send/message", {}, JSON.stringify(toServer));
    message.val(emptyString);
    message.attr("placeholder", sendNewMessage);
}
