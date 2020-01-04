/**
 * 
 */

//ULAZNA FUNKCIJA
function initPatient(user)
{
	let sideBar = $("#sideBar")
	sideBar.append("<li class='nav-item active'><a class='nav-link' href='userProfileNew.html'><i class='fas fa-fw fa-tachometer-alt'></i><span id='profileUser'>Profil</span></a></li>")	
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><span id='clinicList'>Lista klinika</span></a></li>")	
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><span id='medicalRecord'>Zdravstveni karton</span></a></li>")	
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><span id='appRequests'>Zahtevi za pregled</span></a></li>")	
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><span id='preApps'>Unapred definisani pregledi</span></a></li>")
	
	
	addView('showClinicContainer')
	addView('MedicalRecordContainer')
	addView('makeAppointmentContainer')
	addView('detailsAppointmentContainer')
	addView('showAppointmentRequestsPatient')
	addView('preAppointmentContainer')

	
	createBreadcrumb()
    createChooseDoctorTable()
    createAppointmentsTable()
	
	setUpPatientPage(user)
}

function createChooseDoctorTable()
{
	let headers = ['Email', 'Ime', 'Prezime', 'Prosecna ocena', 'Pocetak smene','Kraj smene','']
    let handle = createTable('chooseDoctorTable','Izaberite doktora',headers)
    insertTableInto('showDoctorsContainer',handle)
    insertElementIntoTable('chooseDoctorTable','<button type="button" class="btn btn-primary" id = "detailsAppointment_btn">Pregled</button>')
    getTableDiv('chooseDoctorTable').show()
}

function createAppointmentsTable()
{
	let headers = ['Datum','Termin','Sala','Doktor','Tip pregleda','Cena','']
	createDataTable('preAppTable',"preAppointmentContainer","Unapred definisani pregledi",headers,0)
	getTableDiv('preAppTable').show()
}

function createBreadcrumb()
{
	var bc1 = new BreadLevel()
	bc1.append('Lista klinika').append('Zakazivanje')
	var bc2 = new BreadLevel()
	bc2.append('Zdravstveni karton')
	var bc3 = new BreadLevel()
	bc3.append('Zahtevi za pregled')
	var bc4 = new BreadLevel()
	bc4.append('Unapred definisani pregledi')
	
	initBreadcrumb([bc1,bc2,bc3,bc4])
}

function sleep(ms) {
	  return new Promise(resolve => setTimeout(resolve, ms));
	}

function setUpPatientPage(user)
{
	$("#clinicList").click(function(e){
		e.preventDefault()
		
		
		showView('showClinicContainer')
		showBread('Lista klinika')
		
	})
	
	
	$.ajax({
		type:'GET',
		url:'api/priceList/getAll',
		complete: function(data)
		{
			
			let pricelists = data.responseJSON
						
			for(let p of pricelists)
			{
				$('#selectAppointmentType').append($('<option>',{
					value: p.typeOfExamination,
					text: p.typeOfExamination
				}))
				
			}
			
		}
	})
	
	
	
	
	var start = new Date(),
        prevDay,
        startHours = 9;

    // 09:00 AM
    start.setHours(9);
    start.setMinutes(0);

    // If today is Saturday or Sunday set 10:00 AM
    if ([6, 0].indexOf(start.getDay()) != -1) {
        start.setHours(10);
        startHours = 10
    }
    
    $('#clinicDatePick').datepicker({
    	dateFormat: "dd-mm-yyyy"   	
	})
	
	$('#preApps').click(function(e){
		e.preventDefault()
		
		showView('preAppointmentContainer')	
		showBread('Unapred definisani pregledi')
		
		$.ajax({		
			type: 'GET',
			url: "api/appointments/getAllPredefined",
			complete: function(data)
			{
				let apps = data.responseJSON
				index = 0;
				for(let app of apps)
				{
					list_preApps(app,index,user)
					index++
				}
				
			}
		})
	})
	
	
    $('#searchClinics').click(function(e){
    	e.preventDefault()
    	
    	let picker = $('#clinicDatePick').val()
    	getClinics(picker)
    })
	
	
	$('#medicalRecord').click(function(e){
		e.preventDefault()
		
		showView('MedicalRecordContainer')
		showBread('Zdravstveni karton')
		
		$.ajax({
			type:'GET',
			url:"api/users/patient/getMedicalRecord/"+user.email,
			complete: function(data)
			{
				let mr = data.responseJSON
				makeMedicalRecord(mr,user)
				
			}
		})
	})
	
	$('#appRequests').click(function(e){
		e.preventDefault()
		
		listAppRequests(user)

	})

}


function list_preApps(data,i,user)
{
	emptyTable('preAppTable')
	
	let dateSplit = data.date.split(' ')
	let date = dateSplit[0]
	let time = dateSplit[1]
	
	let d = [date,time,data.hallNumber,data.doctors[0],data.typeOfExamination,data.price,'<button class="btn btn-primary" id="submitPredefinedAppRequest'+i+'">Zakazi</button>']
	
	insertTableData('preAppTable',d)
	
	
	$('#submitPredefinedAppRequest'+i).click(function(e){
		e.preventDefault()
		
		let json = JSON.stringify({"date":data.date,"clinicName":data.clinicName,"hallNumber":data.hallNumber,"version":data.version})
		
		//TODO: Zakazi 
		$.ajax({
			type:'PUT',
			url:"api/appointments/reservePredefined/"+user.email,
			data: json,
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			complete:function(data)
			{
				console.log(data)
				if(data.status == "200")
				{
					console.success("Uspesno")
				}
				else if(data.status == "423")
				{
					console.error("Vec zakazno!")
				}
									
			}
		})
	})
	
}


async function getClinics(date)
{	
	$('#tableClinics tbody').empty()
	$('#searchClinics').attr('disabled',true)
	$('#searchBtnText').text("Trazi...")
	$('#clinicSpinner').show()
	await sleep(1000)
	
	let address = $('#clinicAdressPick').val()
	let rating = $('#clinicRatingPick').val()
	
	let type = $('#selectAppointmentType').val()
	let json = JSON.stringify({"name":"","address":address,"city":"","state":"","rating":rating})
	
	$.ajax({
		type: 'POST',
		url:"api/clinic/getAll/"+date+"/"+type,
		data: json,
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		complete: function(data)
		{
			let clinics = data.responseJSON
			let i = 0
			$('#clinicSpinner').hide()
			$('#searchClinics').removeAttr('disabled')
			$('#searchBtnText').text("Trazi")
			
			$('#tableClinics tbody').empty()
			for(let c of clinics)
			{
				p_listClinic(c,i,user)
				i++
			}
		}
		
	})
}

function makeMedicalRecord(data,user)
{
	$('#insuranceMR').text("Broj osiguranika: " + user.insuranceId)
	$('#height').text("Visina: " + data.height)
	$('#weight').text("Tezina: " + data.weight)
	$('#bloodType').text("Krvna grupa: " + data.bloodType)
	
	let alergies = data.alergies
	$('#alergies').empty()
	for(let al of alergies)
	{
		
		$('#alergies').append("<div class='col-4 themed-grid-col' >"+al+"</div>")	
	}
}


function listAppRequests()
{
	showView('showAppointmentRequestsPatient')
	showBread('Zahtevi za pregled')
	
	
	$.ajax({
		type:'GET',
		url:"api/appointments/patient/getAllRequests/"+user.email,
		complete: function(data)
		{
			
			let reqs = data.responseJSON
			let index = 0
			$('#tablePendingApps tbody').empty()
			for(let req of reqs)
			{
				p_listRequest(req,index)
				index++
			}
		}
	})
}


function p_listRequest(req,i)
{ 
	$.ajax({
		type:'GET',
		url:"api/clinic/"+req.clinicName,
		complete: function(data)
		{
			let clinic = data.responseJSON
			
			let tr = $('<tr></tr>')
			let tdDateAndTime=$('<td>'+ req.date +'</td>');	
			let tdDoctor=$('<td>'+ req.doctors[0] +'</td>');
			let tdType=$('<td>'+ req.typeOfExamination +'</td>');
			let tdClinic=$('<td>'+ req.clinicName +'</td>');	
			let tdAddress=$('<td>' + clinic.address + ', ' + clinic.city + ', ' + clinic.state + '</td>')
			let tdDelete = $('<td><button class="btn btn-danger">Otkazi</button></td>')
			
			tr.append(tdDateAndTime).append(tdDoctor).append(tdType).append(tdClinic).append(tdAddress).append(tdDelete)
			
			$('#tablePendingApps tbody').append(tr)
		}
	})
}


function p_listClinic(data,i,user)
{
	let tr=$('<tr></tr>');
	let tdName=$('<td>'+ data.name +'</td>');
	let tdAdress = $('<td>'+ data.address +'</td>');
	let tdCity = $('<td>'+ data.city +'</td>');
	let tdState = $('<td>'+ data.state +'</td>');
	let tdDesc = $('<td>'+ data.description +'</td>');
	let tdRating = $('<td>'+ data.rating +'</td>');
	let tdAppointment = $('<td><button type="button" class="btn btn-primary" id = "makeAppointment_btn'+i+'">Zakazi pregled</button></td>')
		
	tr.append(tdName).append(tdAdress).append(tdCity).append(tdState).append(tdDesc).append(tdRating).append(tdAppointment);
	$('#tableClinics tbody').append(tr);
		
	$('#makeAppointment_btn'+i).off('click')
	$('#makeAppointment_btn'+i).click(function(e){
		e.preventDefault()
		showView('makeAppointmentContainer')
		
		$('#inputClinicName').val(data.name)
		$('#inputClinicAddress').val(data.address+", "+data.city+", "+data.state)
		$('#inputDate').val($('#clinicDatePick').val())
		$('#inputAppointmentType').val($('#selectAppointmentType').val() + " (Pregled)")	
		
		showBread('Zakazivanje')
		
		$.ajax({
			type:'GET',
			url:"api/clinic/getDoctors/"+data.name,
			complete: function(data)
			{
				let doctors = data.responseJSON
				
				let index = 0
				$('#tableDoctorsActive tbody').empty()
				for(let d of doctors)
				{
					console.log(d)
					p_listDoctorActive(d,index,doctors.length);
					index++;
				}
				
				/*
				$('#inputAppointmentType').change(function(e){
					
					for(let j = 0 ; j < doctors.length ; j++)
					{
						$("#checkDoctor"+j).prop('checked',false)
					}
					
				})
				*/
				
				$('#detailsAppointment_btn').click(function(e){
					e.preventDefault()
					
					
					doctorsSelected = []
					
					for(let j = 0 ; j < doctors.length ; j++)
					{
						if($("#checkDoctor"+j).is(":checked"))
						{
							doctorsSelected.push(doctors[j])
							$('#inputStartTime').prop('min',doctors[j].shiftStart)
							$('#inputStartTime').prop('max',doctors[j].shiftEnd)
						}
						
					}
					
					if(doctorsSelected.length == 0)
					{
						displayError('detailsAppointment_btn',"Morate izabrati doktora")
						return
					}
					
					showView('detailsAppointmentContainer')
					emptyTable('chooseDoctorTable')
					for(let d of doctorsSelected)
					{
						p_listDoctorDisabled(d,index,doctors.length);
						index++;
					}
					
				})
				
				$('#submitAppointmentRequest').off('click')
				$('#submitAppointmentRequest').click(function(e){
					e.preventDefault()
					
					let clinicName = $('#inputClinicName').val()
					let date =  $('#inputDate').val()
					let patientEmail = user.email
					let typeOfExamination = $('#selectAppointmentType').val()
					let time = $('#inputStartTime').val()

					let doctorArray = []
					
					for(let d of doctorsSelected)
					{
						doctorArray.push(d.user.email)
					}
					
					let json = JSON.stringify({"date":date+" "+time,"clinicName":clinicName,"patientEmail":patientEmail,"doctors":doctorArray,"typeOfExamination":typeOfExamination,"type":"Examination"})
					console.log(json)
					$('#submitAppSpinner').show()
					//SEND REQUEST
					
					$.ajax({
						type:'POST',
						url:'api/appointments/sendRequest',
						data: json,
						dataType : "json",
						contentType : "application/json; charset=utf-8",
						complete: function(data)
						{
							$('#submitAppSpinner').hide()
							if(data.status == "201")
							{
								listAppRequests(user)
							}
														
						}
					})
				})
			
			
			}		
				
		})
		
		
		
	})
	
}

function p_listDoctorActive(data,i,doctorCount)
{
	
	let d = [data.user.email,data.user.name,data.user.surname,data.avarageRating,data.shiftStart,data.shiftEnd,"<input type='checkbox' id='checkDoctor"+i+"'><label for='checkDoctor"+i+"'></label>"]
	insertTableData('chooseDoctorTable',d)
	
	$('#checkDoctor'+i).off('click')
	$('#checkDoctor'+i).click(function(e){
		
		for(let j = 0 ; j < doctorCount ; j++)
		{
			if(j == i)
			{
				$("#checkDoctor"+j).prop('checked',true)
			}
			else
			{
				$("#checkDoctor"+j).prop('checked',false)
			}
		}
		
	})
	
}

function p_listDoctorDisabled(data,i,doctorCount)
{
	let tr=$('<tr></tr>');
	let tdEmail = $('<td>' + data.user.email + '</td>')
	let tdName=$('<td>'+ data.user.name +'</td>');
	let tdSurname=$('<td>'+ data.user.surname +'</td>');
	let tdRating=$('<td>'+ data.avarageRating +'</td>');
	let tdTime=$('<td>'+ data.shiftStart + ' : ' + data.shiftEnd + '</td>')
	
	tr.append(tdEmail).append(tdName).append(tdSurname).append(tdRating).append(tdTime)
	$('#tableDoctorsDisabled tbody').append(tr)
}