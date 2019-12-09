
$(document).ready(function(){
	
	
	$.ajax({
		type: 'GET',
		url: 'api/auth/sessionUser',
		complete: function(data)
		{
			user = data.responseJSON
			
			if(user != undefined)
			{
				$("#uu_index").attr('hidden')
				$("#wrapper").removeAttr('hidden')
				
				if(user.role == "Patient")
				{
					initPatient(user)
				}
				else if(user.role == "Doctor")
				{
					initDoctor(user)
				}
				else if(user.role == "Nurse")
				{
					initNurse(user)
				}
				else if(user.role == "ClinicAdmin")
				{
					initClinicAdmin(user)
				}
				
			}
			else
			{
				
				$("#uu_index").removeAttr('hidden')
				$("#wrapper").attr('hidden')				
			}
			
			$("#addHallContainer").hide()
   			$("#showHallContainer").hide()
        	$("#changeHallContainer").hide()
        	$("#zakazno").hide()
        	$('#showClinicContainer').hide()
        	$('#MedicalRecordContainer').hide()
        	$("#showUserContainer").hide()
						
		}
	
	})
	
	
	
	$('#register_btn').click(function(e){
		e.preventDefault()
		window.location.href = "register.html"
	})
	
	
})







