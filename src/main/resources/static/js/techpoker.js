$(function () {
    "use strict";
    // your code goes here

    $('.card').click(
        function () {
            var cardValue = $(this).data('card');
            console.log("Selected Card Value: " + cardValue);
            $('.card').removeClass('selected');
            $(this).addClass('selected');
            $('input[name=vote]').val(cardValue);
            $('#voteForm').submit()
        }
    );

    $('#joinQr').each(function (idx, elem) {
        new QRCode(elem, $(elem).data('qr-url'));
    })
});

function reset() {
    $('.card').removeClass('selected');
}
