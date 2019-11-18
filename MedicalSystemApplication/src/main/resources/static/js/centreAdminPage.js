$("#slideshow > div:gt(0)").hide();

setInterval(function () {
    $('#slideshow > div:first')
        .fadeOut(1000)
        .next()
        .fadeIn(1000)
        .end()
        .appendTo('#slideshow');
}, 3000);



$('#openNav').on('click', function() {
  $('#myNav').css("width", "100%");
});

$('#closeNav').on('click', function() {
  $('#myNav').css("width", "0%");
});


