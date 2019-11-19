
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
	
	$("#inputEmailChange").val(data.email);
	$("#inputNameChange").val(data.name);
	$("#inputSurnameChange").val(data.surname);
	$("#selectStateChange").val(data.state);
	$("#inputCityChange").val(data.city);
	$("#inputAddressChange").val(data.address);
	$("#inputPhoneChange").val(data.phone);
	
}


$(document).ready(function(){

	sessionCheck()
	
	$("#exitChanges").click(function(e){
		e.preventDefault()
		window.location.href = "profile.html"
	})
	
	$("#submitChanges").click(function(e){
		e.preventDefault()
		
		
	let email = $("#inputEmailChange").val()
	let name = $("#inputNameChange").val()
	let surname = $("#inputSurnameChange").val()
	let state = $("#selectStateChange").val()
	let address = $("#inputAddressChange").val()
	let phone = $("#inputPhoneChange").val();
	let city = $("#inputCityChange").val();
	
	let changeReq = JSON.stringify({"username":"","password": "","email":email,"name":name,"surname":surname,"city":city,"address":address,"state":state,"phone":phone})	
	$.ajax({
			type: 'PUT',
			url: 'api/users/update/'+email,
			data: changeReq,
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			complete: function(data){
				console.log(data.status)
				
				if(data.status == "200")
				{
					window.location.href = "profile.html"
				}
			}
				
			}
		
		
	})
	
	
})