$(function () {
    $('#joinws').click(function () {
        var joinform = $("#joinform");
        if(joinform[0].checkValidity()) {
            playerName = $('#playerName').val();
            $.ajax({
                url: '/rest/join',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({sessionId: $('#joinws').data('session-id'), playerName: playerName}),
                dataType: 'json'
            }).done(function (data) {
                console.log(data);
                window.location = "wp#" + data.sessionId + "," + data.playerId;
            }).fail(function (err) {
                console.log(err);
            });
        } else {
            // only way to trigger browser built-in ui-validation elements is to actually submit the form
            // its ok here since we already established that it is invalid and so it won't submit
            joinform.find("input[type='submit']").click()
        }
    });
});
