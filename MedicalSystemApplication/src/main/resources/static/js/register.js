/**
 * 
 */

$(document).ready(function(){
	
	$('#submitRegister').click(function(e){
		e.preventDefault()
		
		let email = $('#inputEmail').val()
		let password = $('#inputPassword').val()
		let name = $('#inputFirstname').val()
		let surname = $('#inputSurname').val()
		let state = $('#selectState').val()
		let city = $('#inputCity').val()
		let address = $('#inputAddress').val()
		let phone = $('#inputPhone').val()

		let regReq = JSON.stringify({"username":"","password":password,"email":email,"name":name,"surname":surname,"city":city,"address":address,"state":state,"phone":phone})
		console.log(regReq)
		$.ajax({
			type: 'POST',
			url:'/api/auth/registerRequest',
			data: regReq,
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			complete: function(data)
			{
				console.log(data.status)
				
				if(data.status == "200")
				{
					window.location.href = "registrationComplete.html"
				}
			}
		})
		
	})
	
	
	
})