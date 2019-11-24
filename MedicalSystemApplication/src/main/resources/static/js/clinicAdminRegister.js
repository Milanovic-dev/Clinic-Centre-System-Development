$(document).ready(function(){
	
	
	$.ajax({
		type: 'GET',
		url:'api/clinic/getAll',
		complete: function(data)
		{
			let select = $('#selectClinic').val()
			
			$.each(data.responseJSON, function (i, item) {
			    $('#selectClinic').append($('<option>', { 
			        value: item.name,
			        text : item.name 
			    }));
			});

			
			
		}
	})
	

	$('#submitRegister').click(function(e){
		e.preventDefault()

        let email = $('#inputEmail').val()
		let name = $('#inputName').val()
		let surname = $('#inputSurname').val()
		let state = $('#selectState').val()
		let city = $('#inputCity').val()
		let address = $('#inputAddress').val()
		let phone = $('#inputPhone').val()
		let clinic = $('#selectClinic').val()

		flag = true


		if(email.indexOf("@gmail.com") == -1)
		{
			var emailInput = $('#inputEmail')

			emailInput.addClass('is-invalid')
			emailInput.removeClass('is-valid')
			flag = false
		}
		else
		{
			var emailInput = $('#inputEmail')

			emailInput.addClass('is-valid')
			emailInput.removeClass('is-invalid')
		}

		if(/^[a-zA-Z]+$/.test(name) == false || name == "")
		{
			var nameInput = $('#inputName')

			nameInput.addClass('is-invalid')
			nameInput.removeClass('is-valid')
			flag = false
		}
		else
		{
			var nameInput = $('#inputName')

			nameInput.addClass('is-valid')
			nameInput.removeClass('is-invalid')
		}


		if(/^[a-zA-Z]+$/.test(surname) == false || surname == "")
		{
			var nameInput = $('#inputSurname')

			nameInput.addClass('is-invalid')
			nameInput.removeClass('is-valid')
			flag = false
		}
		else
		{
			var nameInput = $('#inputSurname')

			nameInput.addClass('is-valid')
			nameInput.removeClass('is-invalid')
		}

		if(city == "")
		{
			var input = $('#inputCity')

			input.addClass('is-invalid')
			input.removeClass('is-valid')
		}
		else
		{
			var input = $('#inputCity')

			input.removeClass('is-invalid')
			input.addClass('is-valid')
		}

		if(address == "")
		{
			var input = $('#inputAddress')

			input.addClass('is-invalid')
			input.removeClass('is-valid')
		}
		else
		{
			var input = $('#inputAddress')

			input.removeClass('is-invalid')
			input.addClass('is-valid')
		}

		if(phone == "")
		{
			var input = $('#inputPhone')

			input.addClass('is-invalid')
			input.removeClass('is-valid')
		}
		else
		{
			var input = $('#inputPhone')

			input.removeClass('is-invalid')
			input.addClass('is-valid')
		}

			if($('#state').find(':selected').prop('disabled')){
                		    var input = $('#state')
                            input.addClass('is-invalid')
                            input.removeClass('is-valid')
                		} else {
                		    var input = $('#state')
                            input.removeClass('is-invalid')
                            input.addClass('is-valid')
                		}


		if(flag == false) return


		let data = JSON.stringify({"username":"","password":"","email":email,"name":name,"surname":surname,"city":city,"address":address,"state":state,"phone":phone})
		console.log(data)
		$.ajax({
			type: 'POST',
			url:'/api/admins/center/registerClinicAdmin/'+clinic,
			data: data,
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			success: function(){

			}

		})

	})



})