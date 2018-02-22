function obtainNewList() {
    $.ajax('/list')
        .done(function (data) {
                let scoreboard = $('#scoreboard');
                scoreboard.html("");
                for (let index in data) {
                    let place = parseInt(index) + 1;
                    scoreboard.append('<tr class="id_' + place + '"><th scope="row">' + place + '</th><td>' + data[index] + '</td><td>1</td></tr>');
                }
                setTimeout(obtainNewList, 3000);
            }
        );
}

$(function () {
    obtainNewList();
});
