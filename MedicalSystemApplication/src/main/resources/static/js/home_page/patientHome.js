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
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button' href='medicalRecord.html'><span id='medicalRecord'>Zdravstveni karton</span></a></li>")	
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
    setUpPatientPage(user)
}


function createClinicTable()
{
	let headers = ["Ime Klinike","Adresa","Grad","Drzava","Opis","Ocena",""]
	createDataTable("clinicTable","showClinicContainer","Lista klinika",headers,0)
		
	let search = new TableSearch()
	search.input('<input class="form-control datepicker-here" data-language="en" placeholder="Izaberite datum" id="clinicDatePick" readonly="true">')
	search.input('<select class="form-control" id="selectAppointmentType"><option selected disabled>Tip pregleda</option></select>')
	search.input('<input class="form-control" type="text" placeholder="Unesite adresu" id="clinicAdressPick">')
	search.input('<input class="form-control" type="number" placeholder="Unesite ocenu" id="clinicRatingPick">')
	 
	insertSearchIntoTable("clinicTable", search, function(){
		
		let date = $('#clinicDatePick').val()
		
		if(date == "") return
		
    	getClinics(date)
    	
	})
	
	 getTableDiv('clinicTable').show()
}


function createChooseDoctorTable()
{
	let headers = ['Tip', 'Email', 'Ime', 'Prezime', 'Prosecna ocena','Zauzece', 'Pocetak smene','Kraj smene','']
	createDataTable('chooseDoctorTable','showDoctorsContainer','Izaberite doktora',headers,0)
    insertElementIntoTable('chooseDoctorTable','<br><button type="button" class="btn btn-primary" id = "detailsAppointment_btn">Pregled</button>')  
    getTableDiv('chooseDoctorTable').show()
}

function createPreAppointmentsTable()
{
	let headers = ['Datum','Termin','Klinika','Sala','Doktor','Tip pregleda','Cena','']
	createDataTable('preAppTable',"preAppointmentContainer","Unapred definisani pregledi",headers,0)
	getTableDiv('preAppTable').show()
}

function createAppointmentsTable()
{
	let headers = ['Datum','Klinika','Termin','Sala','Doktor','Tip pregleda','Cena']
	let handle = createTable('patientAppsTable',"Zakazani pregledi",headers)
	insertTableInto('showAppointmentsPatient',handle)
	getTableDiv('patientAppsTable').show()
}


function createBreadcrumb()
{
	var bc1 = new BreadLevel()
	bc1.append('Lista klinika').append('Zakazivanje').append('Pregled zahteva')
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
    	dateFormat: "dd-mm-yyyy",
    	minDate: new Date()
	})
	
	$('#Apps').click(function(e){
		e.preventDefault()
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

	
	$('#appRequests').click(function(e){
		e.preventDefault()
		
		listAppRequests()

	})
	
	$('#listClinicsLink').click(function(e){
		e.preventDefault()
		showView('showClinicContainer')
		showBread('Lista klinika')
	})
	
	if(getParameterByName("makeApp") == "true")
	{	
		$.ajax({
			type:'GET',
			url:"api/users/getDoctor/"+ getParameterByName("doctorEmail"),
			complete: function(data)
			{
				let doctor = data.responseJSON
				
				if(data.status == "200")
				{
					showView('detailsAppointmentContainer')
					showBread('Pregled zahteva')
					
					$.ajax({
						type:'GET',
						url:"api/clinic/"+getParameterByName("clinicName"),
						complete:function(data)
						{
							if(data.status == "200")
							{
								let clinic = data.responseJSON
								
								$('#inputClinicName').val(clinic.name)
								$('#inputClinicAddress').val(clinic.address+", "+clinic.city+", "+clinic.state)
								$('#inputDate').val(getParameterByName("date"))
								$('#inputAppointmentType').val(getParameterByName("type"))
								$('#inputStartTime').prop('min',doctor.shiftStart)
								$('#inputStartTime').prop('max',doctor.shiftEnd)
								
								
								$('#tableDoctorsDisabled tbody').empty()
								p_listDoctorDisabled(doctor, 0, 1);
								
								$('#submitAppointmentRequest').off('click')
								$('#submitAppointmentRequest').click(function(e){
									e.preventDefault()
									
									submitAppointmentRequest(user, [doctor])
								})
							}
						}
					})								
				}			
			}
		})		
	}

}


function setAppointmentsTable()
{
	$.ajax({		
		type: 'GET',
		url: "api/appointments/patient/getAll/"+thisUser.email,
		complete: function(data)
		{
			let apps = data.responseJSON
					
			emptyTable('patientAppsTable')
		
			$.each(apps, function(i, item){
				list_App(item, i, user)
			});
			
		}
	})
}

function list_App(data,i,user)
{
	let dateSplit = data.date.split(' ')
	let date = dateSplit[0]
	let time = dateSplit[1]
	
	let d = [date,getClinicProfileLink(data.clinicName),time,data.hallNumber,getProfileLink(data.doctors[0]),data.typeOfExamination,data.price]
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
		
			emptyTable('preAppTable')		
			
			$.each(apps, function(i, item){
				list_preApp(item, i, thisUser)
			});
			
		}
	})
		
}

function list_preApp(data,i,user)
{
	
	let dateSplit = data.date.split(' ')
	let date = dateSplit[0]
	let startTime = dateSplit[1]
	let endTime = data.endDate.split(' ')[1]
	
	let d = [date,startTime + " - " + endTime,getClinicProfileLink(data.clinicName),data.hallNumber,getProfileLink(data.doctors[0]),data.typeOfExamination,data.price,'<button class="btn btn-primary" data-toggle="tooltip" id="submitPredefinedAppRequest'+i+'">Zakazi</button>']
	
	insertTableData('preAppTable',d)
	
	$('#submitPredefinedAppRequest'+i).tooltip({title:"Ukoliko zakazete pregled NECETE moci da ga otkazete.", placement: "right"})
	
	$('#submitPredefinedAppRequest'+i).off('click')
	$('#submitPredefinedAppRequest'+i).click(function(e){
		e.preventDefault()
		
		showLoading('submitPredefinedAppRequest'+i)
		let json = JSON.stringify({"date":data.date,"clinicName":data.clinicName,"hallNumber":data.hallNumber,"version":data.version})
		
		$.ajax({
			type:'PUT',
			url:"api/appointments/reservePredefined/"+user.email,
			data: json,
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			complete:function(response)
			{
				if(response.status == "200")
				{
					warningModal("Uspesno!", "Rezervistali ste pregled za datum " + data.date)
					setPreAppointmentsTable()
				}
				else if(response.status == "423")
				{
					console.error("Vec zakazano!")
					displayError('submitPredefinedAppRequest'+i, "Neuspesno. Osvezite stranicu.")
				}
				else if(response.status == "409")
				{
					warningModal("Neuspesno!", "Niste u mogucnosti da zakazete ovaj pregled jer vec imate pregled zakazan za " + data.date + " do " + data.endDate);
				}
				$('#submitPredefinedAppRequest'+i).tooltip('hide')
				hideLoading('submitPredefinedAppRequest'+i)
			}
		})
	})
	
}


async function getClinics(date)
{	
	let tableSearchBtn = getTableSearchButton('clinicTable')
	showLoading(tableSearchBtn)
	
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
								
			emptyTable('clinicTable')
			
			$.each(clinics, function(i, item){
				p_listClinic(item, i, user)
			});
			
			
			hideLoading(tableSearchBtn)
		}
		
	})
}



function listAppRequests()
{
	showView('showAppointmentRequestsPatient')
	showBread('Pregledi na cekanju')
	
	
	$.ajax({
		type:'GET',
		url:"api/appointments/patient/getAllRequests/"+thisUser.email,
		complete: function(data)
		{
			
			let reqs = data.responseJSON
			let index = 0
			$('#tablePendingApps tbody').empty()
			
			$.each(reqs, function(i, item){
				p_listRequest(item, i)
			});
			
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
			let tdDelete = $('<td><button class="btn btn-danger" data-toggle="tooltip"  id="cancelReq'+i+'">Otkazi</button></td>')
			
			
			tr.append(tdDateAndTime).append(tdDoctor).append(tdType).append(tdClinic).append(tdAddress).append(tdDelete)
			
			$('#tablePendingApps tbody').append(tr)
					
		
			let cancelDate = new Date(convertToMMDDYYYY(req.date))
			cancelDate.setDate(cancelDate.getDate() + 1)
			
			$('#cancelReq'+i).tooltip({title:"Mozete otkazati do: " + cancelDate, placement: 'right'})
			
			$('#cancelReq'+i).off('click')
			$('#cancelReq'+i).click(function(e){
				e.preventDefault()
				
				showLoading('cancelReq'+i)
				
				let json = JSON.stringify({"date":req.date, "patientEmail": req.patientEmail, "clinicName": req.clinicName})

				$.ajax({
					type: 'DELETE',
					url: "api/appointments/cancelRequest/Patient",
					data: json,
					dataType : "json",
					contentType : "application/json; charset=utf-8",
					complete: function(data)
					{
						$('#cancelReq'+i).tooltip('hide')
						
						if(data.status == "200")
						{
							listAppRequests()
						}
						else if(data.status == "400")
						{
							displayError('cancelReq'+i, "Proslo je 24 sata od pravljena zahteva. Ne moze biti otkazano.")						
						}
						
						hideLoading('cancelReq'+i)
					}
				})
			})
			
		}
	})
}


function p_listClinic(data,i,user)
{
	let rating;
	if(data.rating == -1)
	{
		rating = "N/A"
	}
	else
	{
		rating = data.rating
	}
	
	let clinic = data

	let d = [getClinicProfileLink(data.name), data.address, data.city, data.state, data.description, rating,'<td><button type="button" class="btn btn-primary" id = "makeAppointment_btn'+i+'">Zakazi pregled</button></td>']
	insertTableData('clinicTable',d)
		
	$('#makeAppointment_btn'+i).off('click')
	$('#makeAppointment_btn'+i).click(function(e){
		e.preventDefault()
		showView('makeAppointmentContainer')
		
		$('#inputClinicName').val(data.name)
		$('#inputClinicAddress').val(data.address+", "+data.city+", "+data.state)
		$('#inputDate').val($('#clinicDatePick').val())
		$('#inputAppointmentType').val($('#selectAppointmentType').val())	
		
		showBread('Zakazivanje')
		
		$.ajax({
			type:'GET',
			url:"api/clinic/getDoctorsByType/" + data.name + "/" + $('#selectAppointmentType').val(),
			complete: function(data)
			{
				let doctors = data.responseJSON
				
				let index = 0
				
				let search = new TableSearch()
				search.input('<input class="form-control" type="text" placeholder="Unesite Ime" id="chooseDoctorName">')
				search.input('<input class="form-control" type="text" placeholder="Unesite prezime" id="chooseDoctorSurname">')
				search.input('<input class="form-control" type="number" placeholder="Unesite ocenu" id="chooseDoctorRating">')
				search.input('<input class="form-control" type="text" placeholder="Unesite tip" id="chooseDoctorType">')
					
				
				insertSearchIntoTable("chooseDoctorTable", search, function(){
						
					
					let srcButton = getTableSearchButton('chooseDoctorTable')
					showLoading(srcButton)
					
					let name = $('#chooseDoctorName').val()
					let surname = $('#chooseDoctorSurname').val()
					let rating = $('#chooseDoctorRating').val()
					let type = $('#chooseDoctorType').val()
					
					let dto = 
					{
						user:{
							"name" : name,
							"surname" : surname
						},
						"averageRating": rating,
						"type" : type
					}
					
					
					let json = JSON.stringify(dto)

					$.ajax({
						type: "POST",
						url:"api/clinic/getDoctorsByFilter/" + clinic.name,
						data: json,
						dataType : "json",
						contentType : "application/json; charset=utf-8",
						complete: function(response)
						{
							let doctors = response.responseJSON
							
							emptyTable('chooseDoctorTable')
							$.each(doctors, function(i, item){
								p_listDoctorActive(item, i, doctors.length);
							})
							
							hideLoading(srcButton)
						}
					})
					
				})
				
				
				emptyTable('chooseDoctorTable')
				
				$.each(doctors, function(i, item){
					p_listDoctorActive(item, i, doctors.length);
				})
				
				
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
					showBread('Pregled zahteva')
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
					
					submitAppointmentRequest(user, doctorsSelected)
				})
			
			
			}		
				
		})
		
		
		
	})
	
}


function submitAppointmentRequest(user, doctorsSelected)
{
	showLoading("submitAppointmentRequest")
	
	let clinicName = $('#inputClinicName').val()
	let date =  $('#inputDate').val()
	let patientEmail = user.email
	let typeOfExamination = $('#inputAppointmentType').val()
	let time = $('#inputStartTime').val()

	let doctorArray = []
	
	for(let d of doctorsSelected)
	{
		doctorArray.push(d.user.email)
	}
	
	let timeInput = $('#inputStartTime')
	
	if(!validation(timeInput, time == "", "Morate izabrati vreme"))
	{
		hideLoading("submitAppointmentRequest")
		return
	}
		
	let json = JSON.stringify({"date":date+" "+time,"clinicName":clinicName,"patientEmail":patientEmail,"doctors":doctorArray,"typeOfExamination":typeOfExamination,"type":"Examination"})
	console.log(json)
	$.ajax({
		type:'POST',
		url:'api/appointments/sendRequest',
		data: json,
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		complete: function(data)
		{
			console.log(data)
			if(data.status == "201")
			{
				listAppRequests(user)
			}
			else
			{
				warningModal("Neuspesno " + data.status, "Server nije uspeo da odgovori. Molimo pokusajte kasnije")
			}
										
			hideLoading('submitAppointmentRequest')
		}
	})

}

function p_listDoctorActive(data, i, doctorCount)
{
	
	let d = [data.type, getProfileLink(data.user.email), data.user.name, data.user.surname, data.avarageRating,"<button class='btn btn-primary' id='doctorOcc"+i+"'>Sl.Termini</button>", data.shiftStart, data.shiftEnd, "<input type='checkbox' id='checkDoctor"+i+"'><label for='checkDoctor"+i+"'></label>"]
	insertTableData('chooseDoctorTable', d)
	
	
	$('#doctorOcc'+i).click(function(e){
		e.preventDefault()

		$.ajax({
			type:'GET',
			url:"api/doctors/getFreeTime/"+data.user.email+"/"+ $('#inputDate').val(),
			complete: function(response)
			{

				let listOfDates = response.responseJSON

				$('#warningModalLabel').text("Zauzece " + data.user.name + " " + data.user.surname)
							
				$('#warningBodyModal').empty()
				
				if(listOfDates.length == 1)
				{
					$('#warningBodyModal').append("<p>Doktor je slobodan.</p>")
					$('#warningBodyModal').append("<p>Radno vreme: <b>"+listOfDates[0].start + " - " + listOfDates[0].end + "</b>")
					
				}
				else
				{
					$.each(listOfDates, function(i, item){
						$('#warningBodyModal').append("<p>"+item.start + " - " + item.end +"</p>")					
					});				
				}
				
				$('#warningModal').modal('show')		
			}
		})
	})
	
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

function p_listDoctorDisabled(data, i, doctorCount)
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