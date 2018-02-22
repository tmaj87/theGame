var emptyString = '';
var wrote = ': ';
var sendNewMessage = 'Wiadomość...';
var sessionId;

var socket = new SockJS('/register');
var stompClient = Stomp.over(socket);
stompClient.connect({}, function () {
    sessionId = /\/([^\/]+)\/websocket/.exec(socket._transport.url)[1];
    $('#hi').html(sessionId);
    // postMessage('join', 'Dołączyłeś do gry');
    stompClient.subscribe('/feed/info', newFeed);
});

function newFeed(data) {
    let body = JSON.parse(data.body);
    let content = body.content;
    let type = body.type.toLowerCase();
    let message = getMessageByType(content, type);
    postMessage(type, message);
}

function getMessageByType(content, type) {
    let message;
    switch (type) {
        case 'won' :
            if (content === sessionId) {
                message = 'Wygrałeś!';
            } else {
                message = 'Przegrałeś,';
            }
            message += ' <a href="/">zagraj jeszcze raz</a>';
            delayedDisconnect();
            break;
        default :
            message = content;
            break;
    }
    return message;
}

function delayedDisconnect() {
    setTimeout(function () {
        stompClient.disconnect();
    }, 1000);
}

function postMessage(type, message) {
    $('#message_box').append('<div class="' + type + '">' + message + '</div>');
}

$('#form').submit(function (event) {
    event.preventDefault();
    submitForm();
});

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
