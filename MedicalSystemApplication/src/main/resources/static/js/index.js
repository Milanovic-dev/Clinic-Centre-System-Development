/**
 * 
 */


$(document).ready(function(){
	
	
	$.ajax({
		type: 'GET',
		url: 'api/auth/sessionUser',
		complete: function(data)
		{
			user = data.responseJSON
			let sideBar = $("#sideBar")

			if(user != undefined)
			{
				$("#uu_index").attr('hidden')
				$("#wrapper").removeAttr('hidden')
				
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
			
			if(user.role == "Doctor"){
				sideBar.append("<li class='nav-item active'><a class='nav-link' href='userProfileNew.html'><i class='fas fa-fw fa-tachometer-alt'></i><span id='profileUser'>Profil</span></a></li>")	
				sideBar.append("<li class='nav-item active'><a class='nav-link' href='index.html'><i class='fas fa-fw fa-tachometer-alt'></i><span id='pacientList'>Lista pacijenata</span></a></li>")	
				sideBar.append("<li class='nav-item active'><a class='nav-link' href='index.html'><i class='fas fa-fw fa-tachometer-alt'></i><span id='examinationStart'>Zapocni pregled</span></a></li>")	
				sideBar.append("<li class='nav-item active'><a class='nav-link' href='index.html'><i class='fas fa-fw fa-tachometer-alt'></i><span id='workCalendar'>Radni kalendar</span></a></li>")	
				sideBar.append("<li class='nav-item active'><a class='nav-link' href='index.html'><i class='fas fa-fw fa-tachometer-alt'></i><span id='vacationRequest'>Zahtev za odustvo</span></a></li>")	
				sideBar.append("<li class='nav-item active'><a class='nav-link' href='index.html'><i class='fas fa-fw fa-tachometer-alt'></i><span id='examinationRequest'>Zakazivanje pregleda</span></a></li>")	
				sideBar.append("<li class='nav-item active'><a class='nav-link' href='index.html'><i class='fas fa-fw fa-tachometer-alt'></i><span id='operationRequest'>Zakazivanje operacija</span></a></li>")		
				
			}
			if(user.role == "Patient"){
				sideBar.append("<li class='nav-item active'><a class='nav-link' href='userProfileNew.html'><i class='fas fa-fw fa-tachometer-alt'></i><span id='profileUser'>Profil</span></a></li>")	
				sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><i class='fas fa-fw fa-tachometer-alt'></i><span id='clinicList'>Lista klinika</span></a></li>")	
				sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><i class='fas fa-fw fa-tachometer-alt'></i><span id='historyOfOperation'>Istorija pregleda i operacija</span></a></li>")	
				sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><i class='fas fa-fw fa-tachometer-alt'></i><span id='medicalRecord'>Zdravstveni karton</span></a></li>")	

				setUpPatientPage(user)
			}
			
			if(user.role == "ClinicAdmin"){
				sideBar.append("<li class='nav-item active'><a class='nav-link' href='userProfileNew.html'><i class='fas fa-fw fa-tachometer-alt'></i><span id='profileUser'>Profil</span></a></li>")	
				sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><i class='fas fa-fw fa-tachometer-alt'></i><span id='addHall'>Dodavanje sala</span></a></li>")	
				sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><i class='fas fa-fw fa-tachometer-alt'></i><span id='showHalls'>Lista sala</span></a></li>")
				sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><i class='fas fa-fw fa-tachometer-alt'></i><span id='addDoctor'>Dodaj lekara</span></a></li>")

				
				
				$('#addHall').click(function(e){
					
					e.preventDefault()
					$("#addHallContainer").show()
					$("#showHallContainer").hide()
					$("#changeHallContainer").hide()
					$("#showUserContainer").hide()
				})
				
				$('#addDoctor').click(function(e){
					
					e.preventDefault()
					$("#addHallContainer").hide()
					$("#showHallContainer").hide()
					$("#changeHallContainer").hide()
					$("#showUserContainer").show()
					makeUserTable()
					
				})
				
				$('#showHalls').click(function(e){
					
					e.preventDefault()
					$("#showHallContainer").show()
					$('#addHallContainer').hide()
					$("#changeHallContainer").hide()
					$("#showUserContainer").hide()
					makeHallTable()
			
					})
				let email = user.email
				$('#submitHall').click(function(e){
						e.preventDefault()
						let idHall = $('#inputHall').val()
						$.ajax({
							type: 'GET',
							url: 'api/admins/clinic/getClinicFromAdmin/'+email,
							complete: function(data){
								
								let clinic = data.responseJSON
								let hall = JSON.stringify({"number" : idHall,"clinic" : clinic })
								$.ajax({
									type: 'POST',
									url: 'api/hall/addHall',
									data: hall,
									dataType : "json",
									contentType : "application/json; charset=utf-8",
									complete: function(data)
									{
										console.log(data.status)
										
										if(data.status == "200")
										{
											$("#showHallContainer").show()
											$('#addHallContainer').hide()
											makeHallTable()
										}
									}
										
								})
							}
								
						})
						
						
				})
				
				//KRAJ SUBMIT HALLS
				
				
				
			} //KRAJ ULOGE ADMINA


			if(user.role == "Nurse"){
            	sideBar.append("<li class='nav-item active'><a class='nav-link' href='userProfileNew.html'><i class='fas fa-fw fa-tachometer-alt'></i><span id='profileUser'>Profil</span></a></li>")
            	sideBar.append("<li class='nav-item active'><a class='nav-link' href='#'><i class='fas fa-fw fa-tachometer-alt'></i><span id='pacientList'>Lista pacijenata</span></a></li>")
       			sideBar.append("<li class='nav-item active'><a class='nav-link' href='#'><i class='fas fa-fw fa-tachometer-alt'></i><span id='workCalendar'>Radni kalendar</span></a></li>")
            	sideBar.append("<li class='nav-item active'><a class='nav-link' href='#'><i class='fas fa-fw fa-tachometer-alt'></i><span id='vacationRequest'>Zahtev za odustvo</span></a></li>")
      			sideBar.append("<li class='nav-item active'><a class='nav-link' href='#'><i class='fas fa-fw fa-tachometer-alt'></i><span id='recipeAuth'>Overa recepata</span></a></li>")

      		    $("#addHallContainer").hide()
       			$("#showHallContainer").hide()
            	$("#changeHallContainer").hide()
            	$("#zakazno").hide()
            	$("#showUserContainer").hide()
      		}
			
		}//KRAJ COMPLETE FUNKCIJE
	
})//KRAJ AJAX POZIVA
	
	
	
	$('#register_btn').click(function(e){
		e.preventDefault()
		window.location.href = "register.html"
	})
	
	
})//KRAJ DOCUMENT READY




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
		$('#MedicalRecordContainer').show()
		$('#makeAppointmentContainer').hide()
		
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
		$('#inputClinicAddress').val(data.address+", "+data.city)
		
		let tr=$('<tr></tr>');
				
		$('#tableDoctors tbody').append(tr)
	})
	
}



function listHall(data,i)
{
	
	let tr=$('<tr></tr>');
	let tdNumber=$('<td class="number" data-toggle="modal" data-target="#exampleModalLong">'+ data.number +'</td>');
	let tdClinic=$('<td class="clinic" data-toggle="modal" data-target="#exampleModalLong">'+ data.clinicName +'</td>');
	let tdChange=$('<td> <button type="button" class="btn btn-primary" id = "changeHall_btn'+i+'">Izmeni</button></td>');
	let tdDelete=$('<td> <button type="button" class="btn btn-danger" id = "deleteHall_btn'+i+'">Izbrisi</button></td>');
	
	tr.append(tdNumber).append(tdClinic).append(tdChange).append(tdDelete);
	$('#tableRequests tbody').append(tr);
	
	$('#deleteHall_btn'+i).click(function(e)
	{
		e.preventDefault()
		console.log(data.number)
		
		$.ajax({
			type: 'DELETE',
			url: 'api/hall/deleteHall/'+data.number,
			complete: function(data)
			{
				if(data.status == "200")
				{
					makeHallTable()
				}
			}
			
		})
	})
	
	$('#changeHall_btn'+i).click(function(e)
	{
		e.preventDefault()
		console.log(data.number)
		$('#addHallContainer').hide()
		$('#showHallContainer').hide()
		$('#changeHallContainer').show()
		

		$('#inputChangeHall').val(data.number) 
		
		$('#submitChangeHall').click(function(e)
		{
			let newNumber = $('#inputChangeHall').val()
			$.ajax({
				type: 'PUT',
				url: 'api/hall/changeHall/'+data.number+"/"+newNumber,
				complete: function(data)
				{
					console.log(data.status)
					if(data.status == "200")
					{
						
						$('#changeHallContainer').hide()
						$('#showHallContainer').show()
						makeHallTable()
					}
				}
			
			})
	
		})
		
	})
	
}

function makeUserTable()
{
	$.ajax({
		type: 'GET',
		url: 'api/users/getAll',
		complete: function(data)
		{
			console.log(data)
			users = data.responseJSON
			let i = 0
			for(let u of users)
            {
				listUser(d,i);
				i++;
            }
		}
								
	})

}

function listUser(data,i)
{
	
	let tr=$('<tr></tr>');
	let tdName=$('<td class="number" data-toggle="modal" data-target="#exampleModalLong">'+ data.name +'</td>');
	let tdSurname=$('<td class="clinic" data-toggle="modal" data-target="#exampleModalLong">'+ data.surname +'</td>');
	let tdEmail=$('<td class="clinic" data-toggle="modal" data-target="#exampleModalLong">'+ data.email +'</td>');
	let tdPhone=$('<td class="clinic" data-toggle="modal" data-target="#exampleModalLong">'+ data.phone +'</td>');
	let tdAddress=$('<td class="clinic" data-toggle="modal" data-target="#exampleModalLong">'+ data.address +'</td>');
	let tdCity=$('<td class="clinic" data-toggle="modal" data-target="#exampleModalLong">'+ data.city +'</td>');
	let tdState=$('<td class="clinic" data-toggle="modal" data-target="#exampleModalLong">'+ data.state +'</td>');
	
	let tdChange=$('<td> <button type="button" class="btn btn-primary" id = "changeHall_btn'+i+'">Promeni u lekara</button></td>');
	let tdDelete=$('<td> <button type="button" class="btn btn-danger" id = "deleteHall_btn'+i+'">Izbrisi</button></td>');
	
	tr.append(tdName).append(tdSurname).append(tdEmail).append(tdPhone).append(tdAdress).append(tdCity).append(tdState).append(tdDelete).append(tdChange)
	$('#tableRequests tbody').append(tr);
	
	$('#deleteHall_btn'+i).click(function(e)
	{
		e.preventDefault()
		console.log(data.number)
		
		$.ajax({
			type: 'DELETE',
			url: 'api/hall/deleteHall/'+data.number,
			complete: function(data)
			{
				if(data.status == "200")
				{
					makeHallTable()
				}
			}
			
		})
	})
	
	$('#changeHall_btn'+i).click(function(e)
	{
		e.preventDefault()
		console.log(data.number)
		$('#addHallContainer').hide()
		$('#showHallContainer').hide()
		$('#changeHallContainer').show()

		
		$('#inputChangeHall').val() 
		
		$('#submitChangeHall').click(function(e)
		{
			let newNumber = $('#inputChangeHall').val()
			$.ajax({
				type: 'PUT',
				url: 'api/hall/changeHall/'+data.number+"/"+newNumber,
				complete: function(data)
				{
					console.log(data.status)
					if(data.status == "200")
					{
						
						$('#changeHallContainer').hide()
						$('#showHallContainer').show()
						makeHallTable()
					}
				}
			
			})
	
		})
		
	})
	
}


function makeHallTable()
{
	$.ajax({
		type: 'GET',
		url: 'api/hall/getAll',
		complete: function(data)
		{
			console.log(data)
			halls = data.responseJSON
			let i = 0
			$('#tableRequests tbody').empty()
			for(let d of halls)
            {
				listHall(d,i);
				i++;
            }
		}
								
	})
}



