function reprint() {
    let scoreboard = $('#scoreboard');
    $.ajax('/best')
        .done(function (data) {
                scoreboard.html('');
                let id = 1;
                for (let player in data) {
                    let row = $('<tr class="id_' + id + '"><th>' + id + '</th><td>' + player + '</td><td>' + data[player] + '</td></tr>');
                    id++;
                    scoreboard.append(row);
                }
                setTimeout(reprint, 6000);
            }
        );
}

$(function () {
    reprint();
});
