

$(document).ready(function(){
	
	
	$.ajax({
		type: 'GET',
		url: 'api/auth/sessionUser',
		complete: function(data){
			let user = data.responseJSON
			if(user.isFirstLog == true)
			{
				$('#inputPasswordChangeOld').hide()
				$('#exitPasswordChange').hide()
			}
			
			$('#submitPasswordChange').click(function(e){
				e.preventDefault()
				if(user.isFirstLog == false){
					passwordChange(data.responseJSON)		
				}
				else{
					firstLogPasswordChange(data.responseJSON)
				}
			})
		}
	
		
		
	})
	
	
		$('#exitPasswordChange').click(function(e){
			e.preventDefault()
			window.location.href = "userProfileNew.html"
		})
		
		
		
})

function firstLogPasswordChange(data)
{	
	let email = data.email
	let newPassword = $('#inputPasswordChangeNew').val()
	let repeatNewPassword = $('#inputPasswordChangeRepeat').val()
	flag = true
	
	if(newPassword == "")
	{
		var newPasswordInput = $('#inputPasswordChangeNew')
		newPasswordInput.addClass('is-invalid')
		newPasswordInput.removeClass('is-valid')
		flag = false
	}
	
	if(repeatNewPassword == "")
	{
		var repeatNewPasswordInput = $('#inputPasswordChangeRepeat')
		repeatNewPasswordInput.addClass('is-invalid')
		repeatNewPasswordInput.removeClass('is-valid')
		flag = false
	}
	
	
	if(newPassword != repeatNewPassword)
	{
		var newPasswordInput = $('#inputPasswordChangeNew')
		var repeatNewPasswordInput = $('#inputPasswordChangeRepeat')
		
		newPasswordInput.addClass('is-valid')
		repeatNewPasswordInput.addClass('is-invalid')
		flag = false
	}
	
	if(flag == false) return
	
	let json = JSON.stringify({"oldPassword": "","newPassword":newPassword})
	
	$.ajax({
		type: 'PUT',
		url:'api/users/update/firstPassword/'+email,
		data: json,
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		complete: function(data)
		{
			if(data.status == 200)
			{
			   let role = user.role

              if(role == "CentreAdmin")
              {
                   window.location.href = "centreAdminPage.html"
              } else {
                   window.location.href = "index.html"
              }

			}
			else
			{
				alert(data.status)
			}
		}
	})
	

}

function passwordChange(data){
	let email = data.email
	
	let oldPassword = $('#inputPasswordChangeOld').val()
	let newPassword = $('#inputPasswordChangeNew').val()
	let repeatNewPassword = $('#inputPasswordChangeRepeat').val()
	
	flag = true
	
	if(oldPassword == "")
	{
		var oldPasswordInput = $('#inputPasswordChangeOld')
		oldPasswordInput.addClass('is-invalid')
		oldPasswordInput.removeClass('is-valid')
		flag = false
	}
	
	if(newPassword == "")
	{
		var newPasswordInput = $('#inputPasswordChangeNew')
		newPasswordInput.addClass('is-invalid')
		newPasswordInput.removeClass('is-valid')
		flag = false
	}
	
	if(repeatNewPassword == "")
	{
		var repeatNewPasswordInput = $('#inputPasswordChangeRepeat')
		repeatNewPasswordInput.addClass('is-invalid')
		repeatNewPasswordInput.removeClass('is-valid')
		flag = false
	}
	
	
	if(newPassword != repeatNewPassword)
	{
		var newPasswordInput = $('#inputPasswordChangeNew')
		var repeatNewPasswordInput = $('#inputPasswordChangeRepeat')
		
		newPasswordInput.addClass('is-valid')
		repeatNewPasswordInput.addClass('is-invalid')
		flag = false
	}
	
	if(flag == false) return
	
	
	let json = JSON.stringify({"oldPassword":oldPassword,"newPassword":newPassword})
	
	$.ajax({
		type: 'PUT',
		url:'api/users/update/password/'+email,
		data: json,
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		complete: function(data)
		{
			if(data.status == 200)
			{
				window.location.href = "userProfileNew.html"
			}
			else
			{
				alert(data.status)
			}
		}
	})
	
	
}