

function sessionCheck()
{
		$.ajax({
			type: 'GET',
			url: 'api/auth/sessionUser',
			complete: function(data){
				addPersonalInformations(data)
			}
				
		})
		
}

function addPersonalInformations(data)
{
	user = data.responseJSON
	if(user == undefined)
		{
			console.log("nema data.")
		}
	
	$("#pName").text(user.name);
	$("#pSurname").text(user.surname);
	$("#pEmail").text(user.email);
	$("#pPhone").text(user.phone);
	$("#pCity").text(user.city);
	$("#pState").text(user.state);
	$("#pAddress").text(user.address);
	
	
	
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
}

$(document).ready(function(){

	sessionCheck()
	
	
	
	$('#change_profile_edit').click(function(e){
		window.location.href = "profileChange.html"
	})
	
})
	
