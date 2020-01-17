
$(document).ready(function(){

	sessionCheck()
	
})

function sessionCheck()
{
	let n = getParameterByName("clinic")
	console.log(n)
		$.ajax({
			type: 'GET',
			url: 'api/clinic/' + n,
			complete: function(data){
				addClinicInformations(data)
			}
				
		})
		
}

function addClinicInformations(data)
{
	if(clinic == undefined)
	{
		console.log("no clinic data.")
		return;
	}
	
	$("#pNameClinic").text(clinic.name);
	$("#pAdressClinic").text(clinic.address);
	$("#pDescriptionClinic").text(clinic.description);
	$("#pCityClinic").text(clinic.city);
	$("#pStateClinic").text(clinic.state);
	$("#sClinicRating").text(parseFloat(clinic.rating).toPrecision(4));
	

}

function getUrlVars()
{
    var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
        hash = hashes[i].split('=');
        vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }
    return vars;
}