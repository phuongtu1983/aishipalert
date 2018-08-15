var map;
var markers = [];
var ajaxInterval = null;
var imagevs = "images/vs.png";
var imagesv = "images/sv.png";
$(document).ready(function () {
    success();
});

function success()
{
    markers = [];
    enableGetAISAjax();
    var googleLatLng = new google.maps.LatLng(10.663402010340304, 106.79608941078186);
    var mapOtn = {
        zoom: 16,
        center: googleLatLng,
        mapTypeId: google.maps.MapTypeId.SATELLITE
    }

    var Pmap = document.getElementById("map");

    map = new google.maps.Map(Pmap, mapOtn);

    var triangleCoords = [
        {lat: 10.668079801211517, lng: 106.79417967796326},
        {lat: 10.662055945753107, lng: 106.80123388767242},
        {lat: 10.65901234329248, lng: 106.79836392402649},
        {lat: 10.664987054794873, lng: 106.7909449338913},
        {lat: 10.668079801211517, lng: 106.79417967796326}
    ];
    drawPolygon(triangleCoords, 'yellow');

    triangleCoords = [
        {lat: 10.666939568202169, lng: 106.79552481736926},
        {lat: 10.663209, lng: 106.799879},
        {lat: 10.660239, lng: 106.796837},
        {lat: 10.663851, lng: 106.792359},
        {lat: 10.666939568202169, lng: 106.79552481736926}
    ];
    drawPolygon(triangleCoords, 'red');

    triangleCoords = [
        {lat: 10.666268527714273, lng: 106.79630532860756},
        {lat: 10.663871198480797, lng: 106.79908946156502},
        {lat: 10.660950622337852, lng: 106.7959512770176},
        {lat: 10.663276805517713, lng: 106.79308265447617},
        {lat: 10.666268527714273, lng: 106.79630532860756}
    ];
    drawPolygon(triangleCoords, 'red');
    /*    
     // yellow
     drawCircle(10.66260333459467, 106.79513587939925, 520);
     drawCircle(10.662226400982233, 106.79475031443121, 520);
     drawCircle(10.663322933796216, 106.79588287214222, 520);
     drawCircle(10.663760491420474, 106.79634085958105, 520);
     drawCircle(10.664184868961435, 106.79679543725299, 520);
     
     //red
     drawCircle(10.66260333459467, 106.79513587939925, 314);
     drawCircle(10.662226400982233, 106.79475031443121, 314);
     drawCircle(10.663322933796216, 106.79588287214222, 314);
     drawCircle(10.662978949781976, 106.79552010416523, 314);
     drawCircle(10.663760491420474, 106.79634085958105, 314);
     drawCircle(10.663546984470017, 106.79615588901048, 314);
     drawCircle(10.663977952040657, 106.7965698537846, 314);
     drawCircle(10.664393792645135, 106.79700437380211, 314);
     drawCircle(10.664600020294783, 106.79722331065, 314);
     drawCircle(10.664184868961435, 106.79679543725299, 314);
     */
    drawCircle(10.663402010340304, 106.79608941078186, 1000);
}

function getAISAjax() {
    $.ajax({
        url: "AISMapJsonServlet",
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',

        success: function (json) {
            var currentdate = new Date();
            var data = json.aisList;
            $("#alertSpan").css("background-color", json.alert);
            $("#aisSpan").text(json.aisStatus);
            $.each(data, function (index, boat) {
                var location = new google.maps.LatLng(parseFloat(boat.latitude), parseFloat(boat.longtitude));
                var title = "Name: " + boat.name + "; Report Age: " + boat.reportAge + "; Distance: " + boat.distance + " (m)";
                addMarker(location, title, boat.id, currentdate, boat.navigationImage);
            });
            clearOldMarker(currentdate);
        },
        error: function (data, status, er) {
            console.log("error: ", er);
        }
    });
}

function addMarker(googleLatLng, title, id, updatedId, navigationImage) {
    var length = markers.length;
    var obj;
    for (var i = 0; i < length; i++) {
        obj = markers[i];
        if (obj.id == id) {
            obj.setPosition(googleLatLng);
            obj.updatedId = updatedId;
            obj.setTitle(title);
            if (obj.navigationImage == 0 && navigationImage != 0) {
                obj.navigationImage = 1;
                if (navigationImage > 0)
                    obj.setIcon(imagevs);
                else
                    obj.setIcon(imagesv);
            }
            return;
        }
    }
    var markerOptn;
    var image;
    if (navigationImage == 0)
        markerOptn = {
            position: googleLatLng,
            map: map,
            title: title,
            id: id,
            updatedId: updatedId,
            navigationImage: navigationImage
        };
    else
    if (navigationImage > 0)
        image = imagevs;
    else
        image = imagesv;
    markerOptn = {
        position: googleLatLng,
        map: map,
        title: title,
        icon: image,
        id: id,
        updatedId: updatedId,
        navigationImage: 1
    };

    var marker = new google.maps.Marker(markerOptn);
    markers.push(marker);
}

function clearOldMarker(updatedId) {
    var length = markers.length;
    var obj;
    for (var i = length - 1; i >= 0; i--) {
        obj = markers[i];
        if (obj.updatedId == updatedId) {
            continue;
        }
        obj.setMap(null);
        markers.splice(i, 1);
    }
}

function clearMarkers() {
    setMapOnAll(null);
}

function setMapOnAll(map) {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(map);
    }
}

function drawPolygon(triangleCoords, color) {
    new google.maps.Polygon({
        paths: triangleCoords,
        map: map,
        strokeColor: color,
        strokeOpacity: 0.8,
        strokeWeight: 2,
        fillOpacity: 0.1
    });
}

function drawCircle(lat, lon, radius) {
    new google.maps.Circle({
        strokeColor: 'blue',
        strokeOpacity: 0.8,
        strokeWeight: 2,
        fillOpacity: 0.1,
        map: map,
        center: {lat: lat, lng: lon},
        radius: radius
    });

}

function enableGetAISAjax() {
    if (ajaxInterval == null)
        ajaxInterval = window.setInterval(getAISAjax, 1000);
}