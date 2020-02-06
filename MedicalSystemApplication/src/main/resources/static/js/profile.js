

$(document).ready(function(){

	checkSession(function(exists){
		if(!exists) window.location.href = "index.html"
	})
	
	$.ajax({
		type: 'GET',
		url: 'api/auth/sessionUser',
		complete: function(data)
		{
			let sessionUser = data.responseJSON

			getProfileFromURL(function(profile, role){
				
				if(profile == undefined)
				{
					getRoleUser(sessionUser.email, sessionUser.role, function(roleUser){
						setPersonalInformations(roleUser, sessionUser.role)
					})
				}
				else
				{
					setPersonalInformations(profile, role, sessionUser)
				}
				
			})
			
			
		}
			
	})
	
})



function setPersonalInformations(user, role, foreign)
{
	
	
	if(user == undefined)
	{
		window.location.href = "index.html"
			return
	}
	
	if(role == "Doctor")
	{
		setDoctorProfile(user, foreign)
	}
	else if(role == "Patient")
	{
		setPatientProfile(user, foreign)
	}
	else if(role == "Nurse")
	{
		setNurseProfile(user, foreign)
	}
	else if(role == "ClinicAdmin")
	{
		setClinicAdminProfile(user, foreign)
	}
	
	
}

function setPatientProfile(patient, foreign)
{
	$('#medicalRecord_btn').prop('href',getPageURLWithUser('medicalRecord',patient.email))
	
	
	$("#pName").text(patient.name);
	$("#pSurname").text(patient.surname);
	$("#pEmail").text(patient.email);
	$("#pPhone").text(patient.phone);
	$("#pCity").text(patient.city);
	$("#pState").text(patient.state);
	$('#pAddress').empty()
	$("#pAddress").append(patient.address);
	$('#pInsurance').text(patient.insuranceId)
	
	$('#pRole').text("Status: Pacijent")
	$('#pRating').hide();
	
	$('#profileChange_btn').show()
	$('#profileChangePassword_btn').show()
	$('#medicalRecord_btn').hide()
	$('#startExamination_btn').hide()
	
	if(foreign == undefined) return
	
	$('#profileChange_btn').hide()
	$('#profileChangePassword_btn').hide()
	$('#medicalRecord_btn').show()
	
	
	if(foreign.role != "Doctor") return
	
	$('#startExamination_btn').show()
	
	let  headersAppointments = ["Email","Datum","Sala","Pregled/Operacija","Tip pregleda","Trajanje",""]
	createDataTable("listAppointmentsTable","appointmentsModalBody","Zakazani pregledi",headersAppointments,0)
	getTableDiv("listAppointmentsTable").show()
	
		$.ajax({
			type: 'GET',
			url: 'api/appointments/getAppointments/' + foreign.email + '/' + patient.email,
			complete: function(data)
			{
				let apps = data.responseJSON
				emptyTable("listAppointmentsTable")
						
				$.each(apps, function(i, a){
								
					if(a.done) return
					
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
				});											
			}				
		})
}

function setDoctorProfile(doctor, foreign)
{

	$("#pName").text(doctor.user.name);
	$("#pSurname").text(doctor.user.surname);
	$("#pEmail").text(doctor.user.email);
	$("#pPhone").text(doctor.user.phone);
	$("#pCity").text(doctor.user.city);
	$("#pState").text(doctor.user.state);
	$("#pAddress").text(doctor.user.address);
	$('#pInsurance').text(doctor.user.insuranceId)
	
	$("#sRating").text(doctor.avarageRating);
	$("#pRole").text("Status: Doktor");
	$('#pRating').show();
	
	$('#profileChange_btn').show()
	$('#profileChangePassword_btn').show()
	$('#medicalRecord_btn').hide()
	$('#startExamination_btn').hide()
	
	if(foreign == undefined) return
	
	$('#profileChange_btn').hide()
	$('#profileChangePassword_btn').hide()
	$('#medicalRecord_btn').hide()
	$('#startExamination_btn').hide()
					
}

function setNurseProfile(nurse, foreign)
{	
	$("#pName").text(nurse.name);
	$("#pSurname").text(nurse.surname);
	$("#pEmail").text(nurse.email);
	$("#pPhone").text(nurse.phone);
	$("#pCity").text(nurse.city);
	$("#pState").text(nurse.state);
	$("#pAddress").text(nurse.address);
	$('#pInsurance').text(nurse.insuranceId)
	
	$('#pRole').text("Status: Med. Sestra")
	$('#pRating').hide();
	
	$('#profileChange_btn').show()
	$('#profileChangePassword_btn').show()
	$('#medicalRecord_btn').hide()
	$('#startExamination_btn').hide()
	
	if(foreign == undefined) return
	
	$('#profileChange_btn').hide()
	$('#profileChangePassword_btn').hide()
	$('#medicalRecord_btn').hide()
	$('#startExamination_btn').hide()
}

function setClinicAdminProfile(admin, foreign)
{
	$("#pName").text(admin.name);
	$("#pSurname").text(admin.surname);
	$("#pEmail").text(admin.email);
	$("#pPhone").text(admin.phone);
	$("#pCity").text(admin.city);
	$("#pState").text(admin.state);
	$("#pAddress").text(admin.address);
	$('#pInsurance').text(admin.insuranceId)
	
	$('#pRole').text("Status: Admin Klinike")
	$('#pRating').hide();
	
	$('#profileChange_btn').show()
	$('#profileChangePassword_btn').show()
	$('#medicalRecord_btn').hide()
	$('#startExamination_btn').hide()
}
