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

    best(aWinners);
    bestWrapper();
    latestWrapper();

    function bestWrapper() {
        $.ajax('/best').done(function (data) {
            scoreboard.html('');
            best(data);
            setTimeout(bestWrapper, 4000);
        });
    }

    function best(data) {
        let id = 1;
        for (let player in data) {
            let row = $('<tr class="id_' + id + '"><th>' + id + '</th><td>' + player + '</td><td>' + data[player] + '</td></tr>');
            id++;
            scoreboard.append(row);
        }
    }

    function latestWrapper() {
        $.ajax('/latest').done(function (data) {
            latest.html(data);
            setTimeout(latestWrapper, 3000);
        });
    }
});
