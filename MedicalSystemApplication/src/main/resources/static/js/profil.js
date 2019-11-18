

function sessionCheck()
{
		$.ajax({
			type: 'get',
			url: 'api/auth/sessionUser',
			complete: 
				addPersonalInformations(data)
			
		})
		
}

function addPersonalInformations(data)
{
	$("#pName").text(data.name);
	$("#pSurname").text(data.surname);
	$("#pEmail").text(data.email);
	$("#pPhone").text(data.phone);
	$("#pCity").text(data.city);
	$("#pState").text(data.state);
	$("#pAddress").text(data.address);
	
	
	$("#pRole").text(data.role);
	$("#sRating").text(data.avarageRating);
}

$(document).ready(){
	
	sessionCheck();
}