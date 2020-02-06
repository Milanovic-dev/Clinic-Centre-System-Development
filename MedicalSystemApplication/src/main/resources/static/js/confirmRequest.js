/**
 * 
 */

$(document).ready(function(){
	
	$('#header').text("Salje se zahtev...")
	
	let reg = getParameterByName("reg")
	
	if(reg != undefined)
	{
		$.ajax({
			type: 'PUT',
			url: "api/auth/verifyAccount/"+reg,
			complete: function(data)
			{
				if(data.status == 200)
				{
					$('#header').text("Uspesno!")
					$('#spinner').hide()
				}
				else
				{
					alert("Server nije uspeo da odgovori (500)")
				}
			}
		})
	}
	
	let clinic = getParameterByName("clinic")
	let hall = getParameterByName("hall")
	let date = getParameterByName("date")
	let confirm = getParameterByName("confirmed")
	
	if(clinic != undefined && hall != undefined && date != undefined && confirm != undefined)
	{
		let json = JSON.stringify({"clinicName":clinic,"hallNumber":hall,"date":date})
		
		if(confirm)
		{
			$.ajax({
				type:'PUT',
				url:"api/appointments/confirmAppointment",
				data: json,
				dataType : "json",
				contentType : "application/json; charset=utf-8",
				complete: function(data)
				{
					if(data.status == 200)
					{
						$('#header').text("Uspesno!")
						$('#spinner').hide()
						$('#message').text("Pregled je uspesno potvrdjen!")
					}
					else
					{
						alert("Server nije uspeo da odgovori (500)")
					}
				}
			})			
		}
		else
		{
			$('#header').text("Uspesno!")
			$('#spinner').hide()
			$('#message').text("Pregled je uspesno odbijen.")
		}
		
	}
	
})