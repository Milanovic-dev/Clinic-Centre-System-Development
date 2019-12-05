/**
 * 
 */

//ULAZNA FUNKCIJA
function initPatient(user)
{
	let sideBar = $("#sideBar")
	sideBar.append("<li class='nav-item active'><a class='nav-link' href='userProfileNew.html'><i class='fas fa-fw fa-tachometer-alt'></i><span id='profileUser'>Profil</span></a></li>")	
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><i class='fas fa-fw fa-tachometer-alt'></i><span id='clinicList'>Lista klinika</span></a></li>")	
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><i class='fas fa-fw fa-tachometer-alt'></i><span id='historyOfOperation'>Istorija pregleda i operacija</span></a></li>")	
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><i class='fas fa-fw fa-tachometer-alt'></i><span id='medicalRecord'>Zdravstveni karton</span></a></li>")	

	setUpPatientPage(user)
}

function setUpPatientPage(user)
{
	$("#clinicList").click(function(e){
		e.preventDefault()
		
		$('#showClinicContainer').show()
		$('#MedicalRecordContainer').hide()
		$('#makeAppointmentContainer').hide()
		
		$.ajax({
			type: 'GET',
			url:"api/clinic/getAll",
			complete: function(data)
			{
				let clinics = data.responseJSON
				let i = 0
				console.log(clinics.length)
				
				$('#tableClinics tbody').empty()
				for(let c of clinics)
				{
					listClinic(c,i)
					i++
				}
			}
			
		})
		
	})
	
	$('#medicalRecord').click(function(e){
		e.preventDefault()
		
		$('#showClinicContainer').hide()
		$('#makeAppointmentContainer').hide()
		$('#MedicalRecordContainer').show()
		
		$.ajax({
			type:'GET',
			url:"api/users/patient/getMedicalRecord/"+user.email,
			complete: function(data)
			{
				let mr = data.responseJSON
				makeMedicalRecord(mr)
				
			}
		})
	})

}

function makeMedicalRecord(data)
{
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

function listClinic(data,i)
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
		
		$('#inputClinicName').val(data.name)
		$('#inputClinicAddress').val(data.address+", "+data.city+", "+data.state)
		
		
		$.ajax({
			type:'GET',
			url:"api/clinic/getDoctors/"+data.name,
			complete: function(data)
			{
				let doctors = data.responseJSON
				
				let index = 0
				for(let d of doctors)
				{
					listDoctor(d,index,doctors.length)
					index++
				}
				
			}
		})
		
		
	})
	
}

function listDoctor(data,i,doctorCount)
{
	let tr=$('<tr></tr>');
	let tdName=$('<td>'+ data.user.name +'</td>');
	let tdSurname=$('<td>'+ data.user.name +'</td>');
	let tdRating=$('<td>'+ data.avarageRating +'</td>');
	let tdSelect = $("<td> <label class='label'><input class='label__checkbox' type='checkbox' id='checkDoctor"+i+"'><span class='label__text'><span class='label__check'><i class='fa fa-check icon'></i></span></span></label>" )
	tr.append(tdName).append(tdSurname).append(tdRating).append(tdSelect)
	$('#tableDoctors tbody').append(tr)
	
	$('#checkDoctor'+i).click(function(e){
		console.log(i)
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