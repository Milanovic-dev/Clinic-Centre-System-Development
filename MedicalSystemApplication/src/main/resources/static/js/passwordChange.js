

$(document).ready(function(){
	
	$('#submitPasswordChange').click(function(e){
		e.preventDefault()
		
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
			url:'api/users/update/password',
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
		
	})
	
	
		$('#exitPasswordChange').click(function(e){
			e.preventDefault()
			window.location.href = "userProfileNew.html"
		})
		
		
		
})