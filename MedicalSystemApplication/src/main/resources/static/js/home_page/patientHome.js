/**
 * 
 */

//ULAZNA FUNKCIJA

var thisUser = ""

function initPatient(user)
{
	thisUser = user
	let sideBar = $("#sideBar")
	sideBar.append("<li class='nav-item active'><a class='nav-link' href='userProfileNew.html'><span id='profileUser'>Profil</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><span id='medicalRecord'>Zdravstveni karton</span></a></li>")	
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><span id='clinicList'>Lista klinika</span></a></li>")	
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><span id='preApps'>Unapred definisani pregledi</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><span id='Apps'>Pregledi</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><span id='appRequests'>Pregledi na cekanju</span></a></li>")	
	
	addView('showClinicContainer')
	addView('MedicalRecordContainer')
	addView('makeAppointmentContainer')
	addView('detailsAppointmentContainer')
	addView('showAppointmentRequestsPatient')
	addView('preAppointmentContainer')
	addView('showAppointmentsPatient')
	
	createClinicTable()
	createBreadcrumb()
    createChooseDoctorTable()
    createPreAppointmentsTable()
    createAppointmentsTable()
    createHistoryTable()
	setUpPatientPage(user)
}


function createClinicTable()
{
	let headers = ["Ime Klinike","Adresa","Grad","Drzava","Opis","Rejting",""]
	createDataTable("clinicTable","showClinicContainer","Lista klinika",headers,0)
		
	let search = new TableSearch()
	search.input('<input class="form-control datepicker-here" data-language="en" placeholder="Izaberite datum" id="clinicDatePick" readonly="true">')
	search.input('<select class="form-control" id="selectAppointmentType"><option selected disabled>Tip pregleda</option></select>')
	search.input('<input class="form-control" type="text" placeholder="Unesite adresu" id="clinicAdressPick">')
	search.input('<input class="form-control" type="number" placeholder="Unesite ocenu" id="clinicRatingPick">')
	
	insertSearchIntoTable("clinicTable",search,function(){
		
		let picker = $('#clinicDatePick').val()
    	getClinics(picker)
    	
	})
	
	 getTableDiv('clinicTable').show()
}


function createChooseDoctorTable()
{
	let headers = ['Email', 'Ime', 'Prezime', 'Prosecna ocena', 'Pocetak smene','Kraj smene','']
    let handle = createTable('chooseDoctorTable','Izaberite doktora',headers)
    insertTableInto('showDoctorsContainer',handle)
    insertElementIntoTable('chooseDoctorTable','<button type="button" class="btn btn-primary" id = "detailsAppointment_btn">Pregled</button>')
    getTableDiv('chooseDoctorTable').show()
}

function createPreAppointmentsTable()
{
	let headers = ['Datum','Termin','Sala','Doktor','Tip pregleda','Cena','']
	createDataTable('preAppTable',"preAppointmentContainer","Unapred definisani pregledi",headers,0)
	
	let ts = new TableSearch()
	ts.input('<input class="form-control" type="text" placeholder="Unesite adresu" style="width:20%" >')
	ts.input('<input class="form-control" type="text" placeholder="Unesite adresu" style="width:20%" >')
	ts.input('<input class="form-control" type="text" placeholder="Unesite adresu" style="width:20%">')
	insertSearchIntoTable("preAppTable",ts)
	//insertTableInto("preAppointmentContainer",handle)
	getTableDiv('preAppTable').show()
}

function createAppointmentsTable()
{
	let headers = ['Datum','Termin','Sala','Doktor','Tip pregleda','Cena']
	let handle = createTable('patientAppsTable',"Zakazani pregledi",headers)
	insertTableInto('showAppointmentsPatient',handle)
	getTableDiv('patientAppsTable').show()
}


function createHistoryTable()
{
	let headers = ['Datum','Klinika','Doktor/i','Pacijent','Razlog pregleda','Dijagnoza','Recepti']
	createDataTable("historyTable","history","Istorija pregleda/operacija",headers,0)
	getTableDiv("historyTable").show()
}

function createBreadcrumb()
{
	var bc1 = new BreadLevel()
	bc1.append('Lista klinika').append('Zakazivanje')
	var bc2 = new BreadLevel()
	bc2.append('Zdravstveni karton')
	var bc3 = new BreadLevel()
	bc3.append('Pregledi na cekanju')
	var bc4 = new BreadLevel()
	bc4.append('Unapred definisani pregledi')
	var bc5 = new BreadLevel()
	bc5.append('Pregledi')
	
	initBreadcrumb([bc1,bc2,bc3,bc4,bc5])
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
	
	$('#Apps').click(function(e){
		e.preventDefault()
		console.log(getViews())
		showView('showAppointmentsPatient')
		showBread('Pregledi')
		setAppointmentsTable()
	})
	
	$('#preApps').click(function(e){
		e.preventDefault()
		
		showView('preAppointmentContainer')	
		showBread('Unapred definisani pregledi')
		
		setPreAppointmentsTable()
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

function setAppointmentsTable()
{
	$.ajax({		
		type: 'GET',
		url: "api/appointments/patient/getAll/"+thisUser.email,
		complete: function(data)
		{
			let apps = data.responseJSON
			
			if(apps.length == 0) return
			
			emptyTable('patientAppsTable')
			index = 0;
			for(let app of apps)
			{
				list_App(app,index,user)
				index++
			}
		}
	})
}

function list_App(data,i,user)
{
	let dateSplit = data.date.split(' ')
	let date = dateSplit[0]
	let time = dateSplit[1]
	
	let d = [date,time,data.hallNumber,data.doctors[0],data.typeOfExamination,data.price]
	insertTableData('patientAppsTable',d)
}

function setPreAppointmentsTable()
{
	$.ajax({		
		type: 'GET',
		url: "api/appointments/getAllPredefined",
		complete: function(data)
		{
			let apps = data.responseJSON
			
			if(apps.length == 0) return
			
			emptyTable('preAppTable')
			index = 0;
			for(let app of apps)
			{
				list_preApp(app,index,thisUser)
				index++
			}
		}
	})
		
}

function list_preApp(data,i,user)
{
	
	let dateSplit = data.date.split(' ')
	let date = dateSplit[0]
	let time = dateSplit[1]
	
	let d = [date,time,data.hallNumber,data.doctors[0],data.typeOfExamination,data.price,'<button class="btn btn-primary" id="submitPredefinedAppRequest'+i+'">Zakazi</button>']
	
	insertTableData('preAppTable',d)
	
	
	$('#submitPredefinedAppRequest'+i).click(function(e){
		e.preventDefault()
		
		let json = JSON.stringify({"date":data.date,"clinicName":data.clinicName,"hallNumber":data.hallNumber,"version":data.version})
		
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
					setPreAppointmentsTable()
				}
				else if(data.status == "423")
				{
					console.error("Vec zakazano!")
				}
									
			}
		})
	})
	
}


async function getClinics(date)
{	

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
			
			emptyTable('clinicTable')
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
	
	setHistoryTable()
}

function setHistoryTable()
{
	
}


function listAppRequests()
{
	showView('showAppointmentRequestsPatient')
	showBread('Pregledi na cekanju')
	
	
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

	let d = [data.name, data.address, data.city, data.state, data.description, data.rating,'<td><button type="button" class="btn btn-primary" id = "makeAppointment_btn'+i+'">Zakazi pregled</button></td>']
	insertTableData('clinicTable',d)
		
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
				emptyTable('chooseDoctorTable')
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
					$('#tableDoctorsDisabled tbody').empty()
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
					
					let timeInput = $('#inputStartTime')
					if(!validation(timeInput, time == "", "Morate izabrati vreme"))
					{
						return
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