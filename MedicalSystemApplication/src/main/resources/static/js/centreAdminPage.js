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



	if(user.email == "klinickicentartest@gmail.com")
	{

       	document.getElementById("centreAdminReg").style.visibility = "visible";

	}


}

$(document).ready(function(){

	sessionCheck()





})




