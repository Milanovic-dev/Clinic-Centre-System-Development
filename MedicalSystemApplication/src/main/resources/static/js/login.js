/**
 * 
 */


$(document).ready(function(){

	$('#submitLogin').click(function(e){
		e.preventDefault()
		
		
		let email = $('#inputEmail').val()
		let password = $('#inputPassword').val()
		
		flag = true
		
		if(email == "")
		{
			var emailInput = $('#inputEmail')
			
			emailInput.addClass('is-invalid')
			emailInput.removeClass('is-valid')
			flag = false
		}
		else
		{
			var emailInput = $('#inputEmail')
			
			emailInput.removeClass('is-invalid')
			emailInput.addClass('is-valid')
		}
		
		if(password == "")
		{
			var emailInput = $('#inputPassword')
			
			emailInput.addClass('is-invalid')
			emailInput.removeClass('is-valid')
			flag = false
		}
		else
		{
			var emailInput = $('#inputPassword')
			
			emailInput.removeClass('is-invalid')
			emailInput.addClass('is-valid')
		}
		
		if(flag == false) return
		
		showLoading('submitLogin')
		let json = JSON.stringify({"email":email,"password":password})
		
		$.ajax({
			type: 'POST',
			url:'/api/auth/login',
			data: json,
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			complete: function(data)
			{

				hideLoading('submitLogin')
				if(data.status == "200")
				{
					let user = data.responseJSON

					if(user.isFirstLog)	{
							window.location.href = "passwordChange.html"
					}
					else
					{
						let role = user.role

                        if(role == "CentreAdmin")
                        {
                        	window.location.href = "centreAdminPage.html"
                        } else {
                             window.location.href = "index.html"
                        }


						}
				}
				else if(data.status == "401")
				{
					$('#errorSpan').text("Ovaj nalog nije verifikovan! Link za verifikaciju je poslat na vaš email.")
				}
				else
				{
					var emailInput = $('#inputEmail')
					var passInput = $('#inputPassword')
					emailInput.addClass('is-invalid')
					emailInput.removeClass('is-valid')
					passInput.addClass('is-invalid')
					passInput.removeClass('is-valid')
					$('#errorSpan').text("Email ili sifra su pogresni.")
				}
			}
		})
		
	})
	
	
	
})

