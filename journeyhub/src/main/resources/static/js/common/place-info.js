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
   var selected = 0;
   var SetRatingStar = function() {
       $star_rating.each(function() {
       if (parseInt($star_rating.siblings('input.rating-value').val()) >= parseInt($(this).data('rating'))) {
         $(this).removeClass('fa-star-o').addClass('fa-star');
       } else {
         $(this).removeClass('fa-star').addClass('fa-star-o');
       }
     });
   };

   var updateRating = function() {
         $star_rating.each(function() {
           if (parseInt($star_rating.siblings('input.rating-value').val()) >= parseInt($(this).data('rating'))) {
             selected++;
             $(this).removeClass('fa-star-o').addClass('fa-star');
           } else {
             $(this).removeClass('fa-star').addClass('fa-star-o');
           }
         });

         var id = $('.star-rating').attr('id');
         $.ajax({
                  headers: {
                      'Content-Type': 'application/json'
                  },
                  type: 'POST',
                  url: 'http://localhost:8000/api/places/' + id + '/rating',
                  data: JSON.stringify({'rating':selected}),
                  success: function(response){
                      $('input.rating-value').val(parseFloat(response['rating']).toFixed(2));
                       SetRatingStar();
                    }
                  })
         selected = 0;
       };
   $star_rating.on('click', function() {
     $star_rating.siblings('input.rating-value').val($(this).data('rating'));
     return updateRating();
   });

   SetRatingStar();
   $(document).ready(function() {

   });
});

function initMap() {
  var basic = {lat: 43.00, lng: 25.00};
  var map = new google.maps.Map(document.getElementById('map'), {
    zoom: 4,
    center: basic
  });

  var geocoder = new google.maps.Geocoder();
  var location = $('#location').html()
  $.ajax({
      type: 'GET',
      url: 'http://localhost:8000/api/location/' + location,
      success: function (response) {
        if (response['longitude'] === null || response['latitude'] === null) {
            geocoder.geocode({
              'address': response['name']
            }, function (results, status) {
              if (status == google.maps.GeocoderStatus.OK) {
                map.setCenter(results[0].geometry.location);
                var marker = new google.maps.Marker({
                  map: map,
                  position: results[0].geometry.location
                });
              }
            })
        } else {
          basic['lat'] = response['latitude']
          basic['lng'] = response['longitude']
          map = new google.maps.Map(document.getElementById('map'), {
              zoom: 6,
              center: basic
            });
          var marker = new google.maps.Marker({
                                  position: basic,
                                  map: map
                                });
        }
      }
    })
}
