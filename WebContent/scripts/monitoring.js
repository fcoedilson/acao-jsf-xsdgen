var markerlist = [];
var markerRoute = [];
var rotaArray = [];

function updateMap() {
	updateVehicles();
	if (currentVehicle && currentVehicle.id) {
		currentVehicle.showAndCenter();
		updateCurrentVehicle(currentVehicle);
	}
	calculateBounds();
}

function showAndSelect(id) {
	var oldVehicle = currentVehicle;
	currentVehicle = retrieveVehicle(id);
	currentVehicle.showAndCenter();
	updateCurrentVehicle(currentVehicle);
	checkRemoveCurrentVehicle(oldVehicle);
	var row = document.getElementById('m:'+ id + ':current');
	removeRoutePoints();
	if(row){
		if(row.checked == true){
			showRouteVehicleOnMap(id, true);
			calculateBoundsRoute();
		}
	}
	calculateBoundsRoute();
}

function showOnMap(id, checked) {
	if (checked) {
		retrieveVehicle(id).show();
	} else {
		retrieveVehicle(id).hide();
	}
	calculateBoundsRoute();
}

function showRouteVehicleOnMap(id, checked) {

	if (checked) {
		var dataRoute = new Array();
		var str = document.getElementById("r:"+ id + "");
		dataRoute = str.value.split('|');
		var j = 0;
		rotaArray = new Array();
		for ( var i = 0; i < dataRoute.length; i+=6) {
			rotaArray[j] = new GLatLng(dataRoute[i], dataRoute[i+1]);
			var lat = dataRoute[i];
			var lng = dataRoute[i+1];
			var point = dataRoute[i+2];
			var veic = dataRoute[i+3];
			var ord = dataRoute[i+4];
			var dat = dataRoute[i+5];
			var string = "Cliente:<b> " + point + "</b><br/>";
			string += "Veiculo:<b> " + veic + "</b><br/>";
			string += "Ordem:<b> " + ord + "</b><br/>";
			string += "Data: " + dat + "<br/>";
			var markerIcon = createIcon2(ord);
			var markerOptions = {icon:markerIcon, draggable:false, bouncy:false, zIndexProcess:function(marker,b) {return 1;}};
			var marker = new GMarker(new GLatLng(lat,lng), markerOptions);
			markerRoute.push(createMarkerRoute(marker, string));
			j++;
		}

		var pontosRota = new Array();
		var pontos = new Array();
		ROUTES = new Array();
		POLYLINES = new Array();

		for ( var k = 0; k < markerRoute.length; k++) {
			map.addOverlay(markerRoute[k]);
			if( k > 0){
				pontosRota.push(markerRoute[k-1].getPoint());
				pontosRota.push(markerRoute[k].getPoint());
				setRoutesListDirections(pontosRota);
			}
			pontos.push(markerRoute[k]);
			pontosRota = [];
		}
		setTimeout(distancia, 2000);
	} else {
		removeRoutePoints();
	}
}


function removeRoutePoints(){

	if(markerRoute.length > 0){
		for ( var i = 0; i < markerRoute.length; i++) {
			map.removeOverlay(markerRoute[i]);
		}
	}
	document.getElementById("distanciaTempo").innerHTML = "";
	markerRoute = new Array();
	clearCurrenteRoute2();
}

function showAllOnMap(checked) {
	if (checked) {
		showAllVehicles();
	} else {
		hideAllVehicles();
	}
	calculateBounds();
}

function createIcon2(type) {

	var icon = new GIcon();
	icon.image = "../image?id=" + type;
	icon.iconSize = new GSize(32, 32);
	icon.iconAnchor = new GPoint(16, 16);
	icon.infoWindowAnchor = new GPoint(16, 16);
	return icon;
}

function createMarkerRoute(marker, descricao) {
	GEvent.addListener(marker, "click", function() {
		marker.openInfoWindowHtml(descricao);
	});
	return marker;
}

function createMarker2(marker, nome) {
	GEvent.addListener(marker, "click", function() {
		marker.openInfoWindowHtml("Ponto <b>" + nome + "</b>");
	});
	return marker;
}

function showClientPointsOnMap(checked){

	var list = new Array();
	var string = document.getElementById('pointsToView');
	list = string.value.split('|');
	if(checked){

		for ( var i = 2; i < list.length; i+=3) {
			var nome = list[i-2];
			var lat = list[i-1];
			var lng = list[i];
			markerlist.push(createMarker2(new GMarker(new GLatLng(lat, lng)), nome));
		}
		for ( var i = 0; i < markerlist.length; i++) {
			map.addOverlay(markerlist[i]);
		}

	} else {
		for ( var i = 0; i < markerlist.length; i++) {
			map.removeOverlay(markerlist[i]);
		}
	}
}

function calculateBounds() {

	if (vehicles.length > 0 && (!currentVehicle)) {
		var mbr = new GLatLngBounds();
		for(var i = 0; i < vehicles.length; i++) {
			if (vehicles[i].isVisible()) {
				mbr.extend(vehicles[i].center);
			}
		}
		map.setCenter(mbr.getCenter(), map.getBoundsZoomLevel(mbr));
	}
}

function checkRemoveCurrentVehicle(oldVehicle) {

	if (currentVehicle && oldVehicle && (currentVehicle.id == oldVehicle.id)) {
		currentVehicle = null;
		removeRastro();
		document.getElementById("currentVehicle").value = "";
		document.getElementById("m:" + oldVehicle.id + ":current").checked = false;
	}
}

function updateCurrentVehicle(currentVehicle) {

	document.getElementById("currentVehicle").value = currentVehicle.id;
	document.getElementById("m:" + currentVehicle.id + ":current").checked = true;
}
