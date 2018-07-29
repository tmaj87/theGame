var aWinners = {
    Karolina: 12,
    Jacek: 9,
    Tomek: 8,
    Anna: 6,
    Marek: 5
};

$(function () {
    let scoreboard = $('#scoreboard');
    let latest = $('#latest');
    print(aWinners);
    reprint();
    // $.ajax('/latest').done(function (data) {
    //     latest.html(data);
    // });

    function print(data) {
        let id = 1;
        for (let player in data) {
            let row = $('<tr class="id_' + id + '"><th>' + id + '</th><td>' + player + '</td><td>' + data[player] + '</td></tr>');
            id++;
            scoreboard.append(row);
        }
    }

    function reprint() {
        $.ajax('/best').done(function (data) {
            scoreboard.html('');
            print(data);
            setTimeout(reprint, 6000);
        });
    }
});
