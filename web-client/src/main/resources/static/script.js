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
            let body = JSON.parse(data.body);
            let content = body.content;
            let message;
            switch (body.type.toLowerCase()) {
                case 'join' :
                    message = content + ' dołączył do gry';
                    break;
                case 'left' :
                    message = content + ' opuścił grę';
                    break;
                case 'won' :
                    stompClient.disconnect();
                    if (content == sessionId) {
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
            $('#message_box').append('<div class="general">' + message + '</div>');
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
