

function extractUser()
{	
	getProfileFromURL(function(profile)
	{
		if(profile == undefined)
		{
			$.ajax({
				type: 'GET',
				url: 'api/auth/sessionUser',
				complete: function(data)
				{
					setPersonalInformations(data.responseJSON,false)
				}
					
			})
		}
		else
		{
			setPersonalInformations(profile,true)
		}
	})
			
}

function setPersonalInformations(user, foreign)
{

	if(user == undefined)
	{
		window.location.href = "index.html"
			return
	}
	
	$("#pName").text(user.name);
	$("#pSurname").text(user.surname);
	$("#pEmail").text(user.email);
	$("#pPhone").text(user.phone);
	$("#pCity").text(user.city);
	$("#pState").text(user.state);
	$("#pAddress").text(user.address);
	$('#pInsurance').text(user.insuranceId)
	
	
	if(user.role == "Doctor")
	{
		$("#sRating").text(user.avarageRating);
		$("#pRole").text("Doktor");
		$('#pRating').show();
	}
	else if(user.role == "Patient")
	{
		$('#pRole').text("Pacijent")
		$('#pRating').hide();
	}
	else if(user.role == "Nurse")
	{
		$('#pRole').text("Med. Sestra")
		$('#pRating').hide();
	}
	
	if(foreign)
	{
		$('#profileChange_btn').hide()
		$('#profileChangePassword_btn').hide()
	}
	else
	{
		$('#profileChange_btn').show()
		$('#profileChangePassword_btn').show()
	}
	
}

$(document).ready(function(){

	extractUser()
	
})
	
