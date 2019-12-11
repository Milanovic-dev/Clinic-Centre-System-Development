/**
 * 
 */

$(document).ready(function()
{
	
	$.ajax({
		type: 'get',
		url: 'api/auth/sessionUser',
		complete: function(data){
			
			user = data.responseJSON
			
			if(user != undefined)
			{
				$('#drop_down_menu').removeAttr('hidden')
				$('#log_buttons').attr('hidden')
				$('#welcome-text').text("Dobrodosao, " + user.name)
				
				
				let role = user.role
				
				if(role == "CentreAdmin")
				{
					$('#admin_btn').show()
					
					$('#admin_btn').click(function(e){
						e.preventDefault()
						
						window.location.href = "centreAdminPage.html"
					})
				}
				else
				{
					$('#admin_btn').hide()
				}
				
			}	
			if(user == undefined)
			{
				$('#drop_down_menu').attr('hidden')
				$('#log_buttons').removeAttr('hidden')
				$('#welcome-text').text("")
				
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
	
	$('#dropdown-item_logout').on("click",function(e){
		e.preventDefault()

		$.ajax({
			type : 'POST',
			url: 'api/auth/logout',
			complete: function(data)
			{
				window.location.href = "index.html"
			}
		})
	})

})
