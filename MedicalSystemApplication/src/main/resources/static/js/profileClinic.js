
$(document).ready(function(){

	sessionCheck()
	const tabs = document.querySelectorAll('[role="tab"]');
	const tabList = document.querySelector('[role="tablist"]');

	// Add a click event handler to each tab
	tabs.forEach(tab => {
	  tab.addEventListener("click", changeTabs);
	 });

	
})

function changeTabs(e) {
  const target = e.target;
  const parent = target.parentNode;
  const grandparent = parent.parentNode;

  // Remove all current selected tabs
  parent
    .querySelectorAll('[aria-selected="true"]')
    .forEach(t => t.setAttribute("aria-selected", false));

  // Set this tab as selected
  target.setAttribute("aria-selected", true);

  // Hide all tab panels
  grandparent
    .querySelectorAll('[role="tabpanel"]')
    .forEach(p => p.setAttribute("hidden", true));

  // Show the selected panel
  grandparent.parentNode
    .querySelector(`#${target.getAttribute("aria-controls")}`)
    .removeAttribute("hidden");
}

function sessionCheck()
{
	let n = getParameterByName("clinic")

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
	clinic = data.responseJSON
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
	
	if(clinic.rating <= -1)
	{
		$("#sClinicRating").text("Klinika jos uvek nije ocenjena.")
	}
	else
	{
		$("#sClinicRating").text(parseFloat(clinic.rating).toPrecision(3))
	}
	
	setDoctorRatings(clinic)
	

}

function setDoctorRatings(clinic)
{
	let headersTypes = ["Ime ","Prezime ","Email","Prosečna ocena"]
	createDataTable("tableDoctorRatings","showDoctorRatingContainer","Lista lekara i njihovih prosečnih ocena",headersTypes,0)
	
	$.ajax({
			type: 'GET',
			url: 'api/clinic/getDoctors/' + clinic.name,
			complete: function(data)
			{
				doctors = data.responseJSON
				emptyTable("tableDoctorRatings")
				for(d of doctors)
				{
			
					let values = [d.user.name,d.user.surname,d.user.email,d.avarageRating]
					insertTableData("tableDoctorRatings",values)
				}
			}
				
		})

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