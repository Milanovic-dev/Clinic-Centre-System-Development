

function sessionCheck()
{
	let urlData = getUrlVars()
	let name = urlData.name
	
		$.ajax({
			type: 'GET',
			url: 'api/clinic/'+ name,
			complete: function(data){
				addClinicInformations(data)
			}
				
		})
		
}

function addClinicInformations(data)
{
	clinic = data.responseJSON
	if(clinic == undefined)
		{
			console.log("no clinic data.")
		}
	
	$("#pNameClinic").text(clinic.name);
	$("#pAdressClinic").text(clinic.address);
	$("#pDescriptionClinic").text(clinic.description);
	$("#pCityClinic").text(clinic.city);
	$("#pStateClinic").text(clinic.state);
	$("#sClinicRating").text(clinic.);
	

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