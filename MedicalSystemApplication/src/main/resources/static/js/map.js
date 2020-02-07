/**
 * 
 */

$(document).ready(function(){

	checkSession(function(exists){
		if(!exists) window.location.href = "index.html"
	})

	
	let clinicName = getParameterByName("clinic")
	
	if(clinicName != undefined)
	{
		$.ajax({
			type:'GET',
			url: "api/clinic/"+ clinicName,
			complete:function(data)
			{
				let clinic = data.responseJSON
				console.log(clinic.address)
				if(clinic != undefined)
				{
					$.ajax({
						type:'GET',
						url:"api/getGeoCoding/"+clinic.city + ","+clinic.address,
						complete: function(data)
						{
							let obj = data.responseJSON
							let results = obj.results
							console.log(results)
							if(results != undefined)
							{
								let lat = results[0].geometry.location.lng
								let lng = results[0].geometry.location.lat
								
								let location = [lat, lng]
								console.log(location)
								var baseMapLayer = new ol.layer.Tile({
									  source: new ol.source.OSM()
									});
									var map = new ol.Map({
									  target: 'map',
									  layers: [ baseMapLayer],
									  view: new ol.View({
									          center: ol.proj.fromLonLat(location), 
									          zoom: 14 //Initial Zoom Level
									        })
									});
									//Adding a marker on the map
									var marker = new ol.Feature({
									  geometry: new ol.geom.Point(
									    ol.proj.fromLonLat(location)
									  ),  // Cordinates of New York's Town Hall
									});
									var vectorSource = new ol.source.Vector({
									  features: [marker]
									});
									var markerVectorLayer = new ol.layer.Vector({
									  source: vectorSource,
									});
									map.addLayer(markerVectorLayer);
							}
							else
							{
								//Map
								 var map = new ol.Map({
									    target: 'map',
									    layers: [
									      new ol.layer.Tile({
									        source: new ol.source.OSM()
									      })
									    ],
									    view: new ol.View({
									      center: ol.proj.fromLonLat([37.41, 8.82]),
									      zoom: 4
									    })
									  });
							}
						}
					})
				}
			}
		})
	}

})
