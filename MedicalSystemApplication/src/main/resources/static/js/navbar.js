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
			
			user = data.responseJSON
			
			if(user != undefined)
			{
				$('#log_buttons').hide()
				$('#drop_down_menu').show()
				
			if(user == undefined)
			{
				$('#log_buttons').show()
				$('#drop_down_menu').hide()
			}
			
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
	
	$('#dropdown-item_logout').click(function(e){
		e.preventDefault()
		//TODO: unistiti coockie
	})

})
