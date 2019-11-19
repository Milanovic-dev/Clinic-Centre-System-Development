/**
 * 
 */

$(document).ready(function()
{
	
	$.ajax({
		type: 'get',
		url: 'api/auth/sessionUser',
		complete: function(data){
			
			return
			if(data == undefined)
			{
				$('#log_buttons').show()
			}
			else
			{
				$('#log_buttons').hide()
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
