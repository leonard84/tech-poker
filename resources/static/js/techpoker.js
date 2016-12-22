$(function () {
    "use strict";
    // your code goes here

    $('.card').click(
        function () {
            console.log("Selected Card Value: " + $(this).data('card'));
            $('.card').removeClass('selected');
            $(this).addClass('selected');
        }
    );
});
