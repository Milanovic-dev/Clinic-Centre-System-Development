/**
 * 
 */


$(document).ready(function(){

	
	$.ajax({
		type: 'get',
		url: 'api/auth/sessionUser',
		complete: function(data)
		{
			user = data.responseJSON
			
			if(user != undefined)
			{
				$('#uu_index').attr('hidden')
				$('#wrapper').removeAttr('hidden')
			}
			else
			{
				$('#uu_index').removeAttr('hidden')
				$('#wrapper').attr('hidden')
			}
		}
		})
	
	
	
	$('#register_btn').click(function(e){
		e.preventDefault()
		window.location.href = "register.html"
	})
	
	
})

