var stompClient = null;
var cards = [];
var voteTemplate = null;
var playerName = "";
var sessionId = "";
var playerId = "";

function playerJoined(session, player) {
    sessionId = session;
    playerId = player;
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe("/topic/session/" + sessionId + "/reset", function () {
            console.log("Reset Received");
            reset();
        });
        render();
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
    $('#sessionId').val(window.location.hash.substring(1));
    var voteSource = $("#vote-template").html();
    voteTemplate = Handlebars.compile(voteSource);
}

function render() {
    var context = {cards: cards, playerName: playerName};
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
}

function vote(value) {
    console.log("vote: "+value);
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
            playerJoined(data.sessionId, data.playerId)
        }).fail(function (err) {
            console.log(err);
        });
    });
});
