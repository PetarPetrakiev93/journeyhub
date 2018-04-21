var addElements = function(){
    var elementsLiked = $('#likedPlaces option:selected').detach();
    var elementsOther = $('#otherPlaces option:selected').detach();
    elementsLiked.appendTo($('#selected'));
    elementsOther.appendTo($('#selected'));
}