/**
 * 
 */

$(document).ready(function()
{
	
	$('#navbar_prijava_btn').click(function(e){
		e.preventDefault()
		window.location.href = "login.html"
	})
	
	$('#navbar_registracija_btn').click(function(e){
		e.preventDefault()
		window.location.href = "register.html"
	})

})
