

function extractUser()
{	
	getProfileFromURL(function(profile)
	{
		if(profile == undefined)
		{
			$.ajax({
				type: 'GET',
				url: 'api/auth/sessionUser',
				complete: function(data)
				{
					setPersonalInformations(data.responseJSON,false)
				}
					
			})
		}
		else
		{
			setPersonalInformations(profile,true)
		}
	})
			
}

function setPersonalInformations(user, foreign)
{
	
	
	if(user == undefined)
	{
		window.location.href = "index.html"
			return
	}
	
	$('#medicalRecord_btn').prop('href',getPageURLWithUser('medicalRecord',user.email))
	
	$("#pName").text(user.name);
	$("#pSurname").text(user.surname);
	$("#pEmail").text(user.email);
	$("#pPhone").text(user.phone);
	$("#pCity").text(user.city);
	$("#pState").text(user.state);
	$("#pAddress").text(user.address);
	$('#pInsurance').text(user.insuranceId)
	
	
	if(user.role == "Doctor")
	{
		$("#sRating").text(user.avarageRating);
		$("#pRole").text("Doktor");
		$('#pRating').show();
	}
	else if(user.role == "Patient")
	{
		$('#pRole').text("Pacijent")
		$('#pRating').hide();
	}
	else if(user.role == "Nurse")
	{
		$('#pRole').text("Med. Sestra")
		$('#pRating').hide();
		
	}
	
	if(foreign)
	{
		$('#profileChange_btn').hide()
		$('#profileChangePassword_btn').hide()
		$('#medicalRecord_btn').show()
		if(user.role == "Doctor")
		{
			$('#startExamination_btn').show()
		}
		else
		{
			$('#startExamination_btn').hide()
		}
	}
	else
	{
		$('#profileChange_btn').show()
		$('#profileChangePassword_btn').show()
		$('#medicalRecord_btn').hide()
		$('#startExamination_btn').hide()
	}
	
	//ZAPOCNI PREGLED
	
	if(user.role != 'Doctor')
	{
		return;
	}
	
	let  headersAppointments = ["Email","Datum","Sala","Pregled/Operacija","Tip pregleda","Trajanje",""]
	createDataTable("listAppointmentsTable","appointmentsModalBody","Zakazani pregledi",headersAppointments,0)
	getTableDiv("listAppointmentsTable").show()
	
	$.ajax({
				type: 'GET',
				url: 'api/auth/sessionUser',
				complete: function(data)
				{
					let doctor = data.responseJSON
					$.ajax({
						type: 'GET',
						url: 'api/appointments/getAppointments/' + doctor.email + '/' + user.email,
						complete: function(data)
						{
							let apps = data.responseJSON
							emptyTable("listAppointmentsTable")
							let i=0
							for(a of apps)
							{
								let typeOfExamin = ''
									
								if(a.type == 'Surgery')
								{
									typeOfExamin = 'Operacija'
								}
								else
								{
									typeOfExamin = 'Pregled'
								}
								
								let values = [a.patientEmail , a.date, a.hallNumber, typeOfExamin,a.typeOfExamination,a.duration,'<button type="button" class="btn btn-primary" id = "startExaminationDoctor_btn'+i+'">Započni pregled</button>']
								insertTableData("listAppointmentsTable",values)
								
								$('#startExaminationDoctor_btn'+i).click(function(e){
									e.preventDefault()
									window.location.href = 'index.html?startExam=true&date='+ a.date + '&hall=' + a.hallNumber
									
								})
								
								i++
							}
						}
						
					})
				}
					
			})
			
	
			//KRAJ ZAPOCNI PREGLED
	
}

$(document).ready(function(){

	extractUser()
	
})
	
