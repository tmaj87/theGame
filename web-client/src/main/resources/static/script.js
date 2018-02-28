var sessionId;
var userToColor = {};

var socket = new SockJS('/register');
var stompClient = Stomp.over(socket);
stompClient.connect({}, function () {
    sessionId = getUserFromUrl() || /\/([^\/]+)\/websocket/.exec(socket._transport.url)[1];
    updateName(sessionId);
    stompClient.subscribe('/feed/info', newFeed);
    postNewMessage('logon', '', 'Dołączyłeś do gry');
});

function getUserFromUrl() {
    let value = location.search.match(new RegExp('[\?\&]user=([^\&]*)(\&?)', 'i'));
    return value ? value[1] : value;
}

function newFeed(data) {
    let body = JSON.parse(data.body);
    let content = body.content;
    let type = body.type.toLowerCase();
    let user = body.user || "";
    getMessageByType(content, user, type);
}

function getMessageByType(content, user, type) {
    let message;
    switch (type) {
        case 'count' :
            $('#player_count').html(content);
            return;
        case 'won' :
            if (content === sessionId) {
                message = 'Wygrałeś!';
                type += ' alert-success';
            } else {
                message = 'Przegrałeś,';
                type += ' alert-danger';
            }
            message += ' <a href="?user=' + sessionId + '">zagraj jeszcze raz</a>';
            $('#player_count').html("0");
            stompClient.disconnect();
            $('#name_changer,#send').attr('disabled', '');
            break;
        default :
            type += ' alert-light';
            message = content;
            break;
    }
    if (userToColor[user] === undefined) {
        userToColor[user] = '#' + pickRandomColor();
    }
    postNewMessage(type, user, message);
}

function pickRandomColor() {
    var p1 = (Math.round(Math.random() * 180) + 50).toString(16),
        p2 = (Math.round(Math.random() * 180) + 50).toString(16),
        p3 = (Math.round(Math.random() * 180) + 50).toString(16);
    return p1 + p2 + p3;
}

function postNewMessage(type, user, message) {
    let element = $('<div style="display: none" class="border border-secondary alert ' + type + '"><span class="user" style="color: ' + userToColor[user] + ';">' + user + '</span>' + message + '</div>');
    $('#message_box').prepend(element);
    element.slideDown("slow");
}

$('#name_changer').click(function () {
    updateName($('#name').val())
});

function updateName(name) {
    sessionId = name;
    stompClient.send("/send/username", {}, sessionId);
    $('#hi').html(sessionId);
}

$('#form').submit(function (event) {
    event.preventDefault();
    submitForm();
});

$('#scoreboard').click(function (event) {
    window.open('//' + location.hostname + ':7979');
});

function submitForm() {
    let message = $('#message');
    let toServer = {
        content: message.val()
    };
    stompClient.send("/send/message", {}, JSON.stringify(toServer));
    message.val('');
    message.attr("placeholder", 'Wiadomość...');
}
