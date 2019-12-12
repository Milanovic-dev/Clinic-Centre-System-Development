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

	setUpPatientPage(user)
}

function sleep(ms) {
	  return new Promise(resolve => setTimeout(resolve, ms));
	}

function setUpPatientPage(user)
{
	$("#clinicList").click(function(e){
		e.preventDefault()
		
		$('#showClinicContainer').show()
		$('#MedicalRecordContainer').hide()
		$('#makeAppointmentContainer').hide()
		$('#detailsAppointmentContainer').hide()
		$('#breadcrumbCurrPage').removeAttr('hidden')
		$('#breadcrumbCurrPage').text("Lista klinika")
		$('#breadcrumbCurrPage2').attr('hidden',true)
		
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
	
    $('#searchClinics').click(function(e){
    	e.preventDefault()
    	
    	let picker = $('#clinicDatePick').val()
    	getClinics(picker)
    })
	
	
	$('#medicalRecord').click(function(e){
		e.preventDefault()
		
		$('#showClinicContainer').hide()
		$('#makeAppointmentContainer').hide()
		$('#MedicalRecordContainer').show()
		$('#detailsAppointmentContainer').hide()
		$('#breadcrumbCurrPage').removeAttr('hidden')
		$('#breadcrumbCurrPage').text("Zdravstveni karton")
		$('#breadcrumbCurrPage2').attr('hidden',true)
		
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

}


async function getClinics(date)
{
	$('#tableClinics tbody').empty()
	$('#searchClinics').attr('disabled',true)
	$('#searchBtnText').text("Trazim...")
	$('#clinicSpinner').show()
	await sleep(1000)
	
	let json = JSON.stringify({"name":"Klinika","address":"","city":"","state":"","rating":""})

	$.ajax({
		type: 'POST',
		url:"api/clinic/getAll/"+date,
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
	
	$('#makeAppointment_btn'+i).click(function(e){
		e.preventDefault()
		$('#makeAppointmentContainer').show()
		$('#showClinicContainer').hide()
		$('#MedicalRecordContainer').hide()
		$('#detailsAppointmentContainer').hide()
		
		$('#inputClinicName').val(data.name)
		$('#inputClinicAddress').val(data.address+", "+data.city+", "+data.state)
		$('#inputDate').val($('#clinicDatePick').val())
		$('#inputAppointmentType').val("Examination")
		$('#breadcrumbCurrPage2').removeAttr('hidden')
		$('#breadcrumbCurrPage2').text("Zakazivanje")
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
					p_listDoctorActive(d,index,doctors.length);
					index++;
				}
				
				$('#inputAppointmentType').change(function(e){
					
					for(let j = 0 ; j < doctors.length ; j++)
					{
						$("#checkDoctor"+j).prop('checked',false)
					}
					
				})
				
				$('#detailsAppointment_btn').click(function(e){
					e.preventDefault()
					
					$('#makeAppointmentContainer').hide()
					$('#showClinicContainer').hide()
					$('#MedicalRecordContainer').hide()
					$('#detailsAppointmentContainer').show()
					
					
					doctorsSelected = []
					
					for(let j = 0 ; j < doctors.length ; j++)
					{
						if($("#checkDoctor"+j).is(":checked"))
						{
							doctorsSelected.push(doctors[j])
						}
						
					}
					
					$('#tableDoctorsDisabled tbody').empty()
					for(let d of doctorsSelected)
					{
						p_listDoctorDisabled(d,index,doctors.length);
						index++;
					}
					
				})
				
			
				$('#submitAppointmentRequest').click(function(e){
					e.preventDefault()
					
					let clinicName = $('#inputClinicName').val()
					let patientEmail = user.email
					
					let json = JSON.stringify({"clinicName":clinicName,"patientEmail":patientEmail,"doctors":doctorsSelected})
					console.log(json)
					//SEND REQUEST
				})
			
			
			}		
				
		})
		
		
		
	})
	
}

function p_listDoctorActive(data,i,doctorCount)
{
	let tr=$('<tr></tr>');
	let tdName=$('<td>'+ data.user.name +'</td>');
	let tdSurname=$('<td>'+ data.user.name +'</td>');
	let tdRating=$('<td>'+ data.avarageRating +'</td>');
	let tdCalendar =$("<td><span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span></td>");
	let tdSelect = $("<td><input type='checkbox' id='checkDoctor"+i+"'><label for='checkDoctor"+i+"'></label></td>" )
	
	tr.append(tdName).append(tdSurname).append(tdRating).append(tdCalendar).append(tdSelect)
	$('#tableDoctorsActive tbody').append(tr)
	
	$('#checkDoctor'+i).click(function(e){
		
		
		if($('#inputAppointmentType').val() == "Surgery") return
		
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
	let tdName=$('<td>'+ data.user.name +'</td>');
	let tdSurname=$('<td>'+ data.user.name +'</td>');
	let tdRating=$('<td>'+ data.avarageRating +'</td>');
	
	tr.append(tdName).append(tdSurname).append(tdRating)
	$('#tableDoctorsDisabled tbody').append(tr)
}