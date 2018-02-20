function obtainNewList() {
    $.ajax('/list')
        .done(function (data) {
                let scoreboard = $('.scoreboard');
                scoreboard.html("");
                for (let index in data) {
                    scoreboard.append('<li>' + data[index] + '</li>')
                }
                setTimeout(obtainNewList, 3000);
            }
        );
}

$(function () {
    obtainNewList();
});
