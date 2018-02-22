var emptyString = '';
var wrote = ': ';
var sendNewMessage = 'Wiadomość...';
var sessionId;

var socket = new SockJS('/register');
var stompClient = Stomp.over(socket);
stompClient.connect({}, function () {
    sessionId = /\/([^\/]+)\/websocket/.exec(socket._transport.url)[1];
    $('#hi').html(sessionId);
    stompClient.subscribe('/feed/info', newFeed);
    postNewMessage('logon', 'Dołączyłeś do gry');
});

function newFeed(data) {
    let body = JSON.parse(data.body);
    let content = body.content;
    let type = body.type.toLowerCase();
    getMessageByType(content, type);
}

function getMessageByType(content, type) {
    let message;
    switch (type) {
        case 'count' :
            $('#player_count').html(content);
            return;
        case 'won' :
            if (content === sessionId) {
                message = 'Wygrałeś!';
            } else {
                message = 'Przegrałeś,';
            }
            message += ' <a href="/">zagraj jeszcze raz</a>';
            stompClient.disconnect();
            break;
        default :
            message = content;
            break;
    }
    postNewMessage(type, message);
}

function postNewMessage(type, message) {
    $('#message_box').prepend('<li class="list-group-item ' + type + '">' + message + '</li>');
}

$('#form').submit(function (event) {
    event.preventDefault();
    submitForm();
});

$('#scoreboard').click(function (event) {
    window.open('//localhost:7979');
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
