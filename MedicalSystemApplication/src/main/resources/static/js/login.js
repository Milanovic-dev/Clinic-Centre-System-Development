/**
 * 
 */


$(document).ready(function(){
	
	$('#submitPrijava').click(function(e){
		e.preventDefault()
		
		
		let email = $('#inputEmail').val()
		let password = $('#inputPassword').val()
		
		$.ajax({
			type: 'POST',
			url:'/api/auth/login',
			data: JSON.stringify({"email":email,"password:":password}),
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			complete: function(data)
			{
				console.log(data.status)
				
				if(status == "200")
				{
					window.location.href = "index.html"
				}
			}
		})
		
	})
	
	
	
})

