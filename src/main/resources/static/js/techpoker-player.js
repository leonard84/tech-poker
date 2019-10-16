$(function () {
    "use strict";
    // your code goes here

    $('.poker-card').click(
        function () {
            var cardValue = $(this).data('card');
            if (cardValue) { // qr-code also has the .poker-card class, so check if it has a valid vote value
                console.log("Selected Card Value: " + cardValue);
                $('.poker-card').removeClass('selected');
                $(this).addClass('selected');
                $('input[name=vote]').val(cardValue);
                $('#voteForm').submit();
            }
        }
    );

    renderQr();
});

function reset() {
    $('.poker-card').removeClass('selected');
}
