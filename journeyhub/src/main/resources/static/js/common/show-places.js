var app = app || {};

app.loadImages = (function loadImages(){
    var items = document.getElementsByClassName("grid-item");
    var places = [];
    for(var i in items){
        if(items[i].id === undefined){
            continue;
        }
        //console.log(items[i].id);
        places.push({"id":items[i].id})
    }
    function getImages() {
    $.ajax({
        headers: {
            'Content-Type': 'application/json'
        },
        type: 'POST',
        url: 'http://localhost:8000/api/images/places',
        data: JSON.stringify(places),
        success: function(response){
            for(var placeImage in response['placeImages']){
                document.getElementById(
                response['placeImages'][placeImage]['placeId']).childNodes[1]
                    .src = response['placeImages'][placeImage]['url']
            }
        }
        })
    }
    getImages()
})();