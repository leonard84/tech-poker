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
        new QRCode(elem, {text: $(elem).data('qr-url'), correctLevel : QRCode.CorrectLevel.L});
    });
    $('#joinQr').click(function () {
        $('#overlay').remove();
        $('body').prepend('<div style="position: absolute; z-index: 100; width: 70vmin; height: 70vmin; top: 20vh; left: 20vw; padding: 10vmin; background-color: white;" id="overlay"></div>');
        var overlay = $('#overlay');
        var size = Math.min($( window ).height(), $( window ).width()) *0.5;
        new QRCode(overlay.get(0), {text: $(this).data('qr-url'), correctLevel : QRCode.CorrectLevel.L, width: size, height: size});
        overlay.click(function () {
           $(this).remove();
        });
    });
});

function reset() {
    $('.card').removeClass('selected');
}
