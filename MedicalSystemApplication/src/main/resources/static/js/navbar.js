/**
 * 
 */

$(document).ready(function()
{
	
	$.ajax({
		type: 'get',
		url: 'api/auth/sessionUser',
		complete: function(data){
			
			if(data.status == "200")
			{
				console.log(data.responseJSON)
			}
			
		}		
	})
	
	
	$('#navbar_prijava_btn').click(function(e){
		e.preventDefault()
		window.location.href = "login.html"
	})
	
	$('#navbar_registracija_btn').click(function(e){
		e.preventDefault()
		window.location.href = "register.html"
	})

})
