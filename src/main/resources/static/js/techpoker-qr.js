function renderQr() {
    $('#joinQr').each(function (idx, elem) {
        var options = {text: $(elem).data('qr-url'), correctLevel: QRCode.CorrectLevel.L};
        if ($(elem).data('qr-size')) {
            options.width = options.height = $(elem).data('qr-size');
        }
        new QRCode(elem, options);
    });
    $('#joinQr').click(function () {
        $('#overlay').remove();
        $('body').prepend('<div style="position: absolute; z-index: 100; width: 70vmin; height: 70vmin; padding: 10vmin; background-color: white;" id="overlay"><div style="position: absolute;" id="overlay-qr"></div></div>');
        var overlay = $('#overlay');
        var height = $(window).height();
        var width = $(window).width();
        overlay.width(width);
        overlay.height(height);
        var size = Math.min(height, width) * 0.5;
        var overlayQR = $('#overlay-qr');
        overlayQR.css('top', (height - size) / 2);
        overlayQR.css('left', (width - size) / 2);
        new QRCode(overlayQR.get(0), {
            text: $(this).data('qr-url'),
            correctLevel: QRCode.CorrectLevel.L,
            width: size,
            height: size
        });
        overlay.click(function () {
            $(this).remove();
        });
    });
}
