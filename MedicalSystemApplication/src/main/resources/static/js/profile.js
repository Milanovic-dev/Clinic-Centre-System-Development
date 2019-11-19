

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
	data = {"name":"Nikola","surname":"Milanovic","email":"nikola@gmail.com","phone":"0532543","city":"Novi Sad","state":"Srbija","address":"Karadjordjeva 8","role":"Doktor","avarageRating":"8.56"}
	
	
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

$(document).ready(function(){
	
	sessionCheck()
	
	
	$('#change_profile_edit').click(function(e){
		//TODO: Otvori stranicu za editovanje
		window.location.href = "profileChange.html
	})
	
})
	
