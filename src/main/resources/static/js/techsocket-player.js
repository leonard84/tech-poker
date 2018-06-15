var stompClient = null;
var cards = [];
var voteTemplate = null;
var playerName = "";
var sessionId = "";
var playerId = "";
var errorCounter = 0;

function playerJoined(session, player) {
    sessionId = session;
    playerId = player;
    connectToServer();
}

function connectToServer() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe("/topic/session/" + sessionId + "/reset", function () {
            console.log("Reset Received");
            reset();
        });
        render();
    }, function (error) {
        console.log("WebSocket Connection Error.", error);
        errorCounter++;
        if (errorCounter < 10) {
            var delay = (500 * errorCounter) + 1;
            console.log("Trying to reconnect in " + delay + "ms");
            setTimeout(connectToServer, delay);
        } else {
            console.log("Giving up reconnecting. Try reloading the page");
            window.location.reload(true);
        }
    });
}

function loadCards() {
    $.ajax({
        url: '/rest/cards',
        type: 'GET',
        dataType: 'json'
    }).done(function (data) {
        console.log(data);
        cards = data.cards;
    }).fail(function (err) {
        console.log(err);
    });
}

function init() {
    var query = window.location.hash.substring(1);
    var tmp = query.split(',');
    sessionId = tmp[0];
    playerId = tmp[1];
    $('#sessionId').val(sessionId);

    var voteSource = $("#vote-template").html();
    voteTemplate = Handlebars.compile(voteSource);

    if (typeof (playerId) !== "undefined") {
        setTimeout(function () {
            fetchStats(sessionId, playerId);
            playerJoined(sessionId, playerId);
        }, 10);
    }
}

function fetchStats(session, player) {
    $.ajax({
        url: '/rest/stats/' + session + '/' + player,
        type: 'GET',
        dataType: 'json'
    }).done(function (data) {
        console.log(data);
        playerName = data.playerName;
        setTimeout(function () {
            $('.card').removeClass('selected');
            $('.card[data-card="' + data.vote + '"]').addClass('selected');
        }, 500);
    }).fail(function (err) {
        console.log(err);
    });
}

function render() {
    var context = {cards: cards, playerName: playerName, sessionId: sessionId, playerId: playerId};
    $('#container').html(voteTemplate(context));
    $('.card').click(
        function () {
            var cardValue = $(this).data('card');
            console.log("Selected Card Value: " + cardValue);
            $('.card').removeClass('selected');
            $(this).addClass('selected');
            vote(cardValue);
        }
    );
    $('#reconnect').click(function () {
        window.location.reload(true);
    });
}

function vote(value) {
    console.log("vote: " + value);
    stompClient.send('/app/session/vote', {}, JSON.stringify({sessionId: sessionId, playerId: playerId, vote: value}));
}

function reset() {
    $('.card').removeClass('selected');
}

$(function () {
    init();
    loadCards();
    $('#join').click(function () {
        playerName = $('#playerName').val();
        $.ajax({
            url: '/rest/join',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({sessionId: $('#sessionId').val(), playerName: playerName}),
            dataType: 'json'
        }).done(function (data) {
            console.log(data);
            window.location.hash = data.sessionId + "," + data.playerId;
            playerJoined(data.sessionId, data.playerId);
        }).fail(function (err) {
            console.log(err);
        });
    });
});
