var slideIndex = 1;
showDivs(slideIndex);

function plusDivs(n) {
  showDivs(slideIndex += n);
}

function showDivs(n) {
  var i;
  var x = document.getElementsByClassName("mySlides");
  if (n > x.length) {slideIndex = 1}
  if (n < 1) {slideIndex = x.length}
  for (i = 0; i < x.length; i++) {
     x[i].style.display = "none";
  }
  x[slideIndex-1].style.display = "block";
}

$(document).ready(function(){
   var $star_rating = $('.star-rating .fa');
   console.log($star_rating);
   var SetRatingStar = function() {
     return $star_rating.each(function() {
       if (parseInt($star_rating.siblings('input.rating-value').val()) >= parseInt($(this).data('rating'))) {
         return $(this).removeClass('fa-star-o').addClass('fa-star');
       } else {
         return $(this).removeClass('fa-star').addClass('fa-star-o');
       }
     });
   };

   $star_rating.on('click', function() {
     $star_rating.siblings('input.rating-value').val($(this).data('rating'));
     return SetRatingStar();
   });

   SetRatingStar();
   $(document).ready(function() {

   });
});