
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
	
	
	$("#inputEmailChange").val(user.email);
	$("#inputNameChange").val(user.name);
	$("#inputSurnameChange").val(user.surname);
	$("#selectStateChange").val(user.state);
	$("#inputCityChange").val(user.city);
	$("#inputAddressChange").val(user.address);
	$("#inputPhoneChange").val(user.phone);
	
}


$(document).ready(function(){

	sessionCheck()
	
	$('#exitChanges').click(function(e){
		e.preventDefault()
		window.location.href = "userProfileNew.html"
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
		
		})
	})
		
})