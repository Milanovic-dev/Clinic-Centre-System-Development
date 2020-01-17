/**
 * 
 */

$(document).ready(function(){
	$('#alreadyExists').hide()

	
	$('#submitRegister').click(function(e){
		
		let email = $('#inputEmail').val()
		let password = $('#inputPassword').val()
		let confirmPassword = $('#confirmPassword').val()
		let name = $('#inputName').val()
		let surname = $('#inputSurname').val()
		let state = $('#selectState').val()
		let city = $('#inputCity').val()
		let address = $('#inputAddress').val()
		let phone = $('#inputPhone').val()
		let insurance = $('#inputInsurance').val()
		
		flag = true
		
			
		if(!validation('inputEmail', email.indexOf("@gmail.com") == -1, "Email mora biti u dobrom formatu"))
		{
			flag = false
		}
		
		if(!validation('inputName', /^[a-zA-Z]+$/.test(name) == false || name == "", "Ime moze sadrzati samo slova."))
		{
			flag = false
		}
		
		
		if(!validation('inputSurname',/^[a-zA-Z]+$/.test(surname) == false || surname == "", "Prezime moze sadrzati samo slova"))
		{
			flag = false
		}
		
		if(!validation('inputPassword', password == "" || password.length < 3, "Lozinka mora biti duza od 3 karaktera."))
		{
			flag = false
		}
							
						
		if(password != confirmPassword)
		{
			var passInput = $('#inputPassword')
			var passConfirm = $('#confirmPassword')
			
			passInput.addClass('is-valid')
			passConfirm.addClass('is-invalid')
			flag = false
		}
		
		if(!validation('inputCity', city == "", "Morate uneti grad."))
		{
			flag = false
		}
		
		if(!validation('inputAddress',address == "", "Morate uneti adresu."))
		{
			flag = false
		}
		
		if(!validation('inputPhone',phone == "", "Morate uneti telefon."))
		{
			flag = false
		}

		if(!validation('inputInsurance',insurance == "", "Morate uneti broj osiguranika."))
		{
			flag = false
		}
		
		if(!validation('selectState',$('#selectState').find(':selected').prop('disabled') , "Morate izabrati drzavu."))
		{
			flag = false
		}
		
		
		if(flag == false) return
		
		
		let regReq = JSON.stringify({"password":password,"email":email,"name":name,"surname":surname,"city":city,"address":address,"state":state,"phone":phone,"insuranceId":insurance})
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
				
				if(data.status == "208")
				{
					$('#alreadyExists').show()
					$('#alreadyExists').text("Korisnik sa tim emailom vec postoji.")
				}
				
				if(data.status == "200")
				{			
					$('#alreadyExists').hide()
					window.location.href = "registrationComplete.html"
				}
			}
		})
		
	})
	
	
	
})