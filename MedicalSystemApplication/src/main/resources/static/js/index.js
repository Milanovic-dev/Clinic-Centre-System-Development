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
				$("#uu_index").hide()
				$("#wrapper").show()
				
			}
			else
			{
				
				$("#uu_index").show()
				$("#wrapper").hide()
		
				
			}
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
				sideBar.append("<li class='nav-item active'><a class='nav-link' href='userProfileNew.html'><i class='fas fa-fw fa-tachometer-alt'></i><span id='clinicList'>Lista klinika</span></a></li>")	
				sideBar.append("<li class='nav-item active'><a class='nav-link' href='userProfileNew.html'><i class='fas fa-fw fa-tachometer-alt'></i><span id='historyOfOperation'>Istorija pregleda i operacija</span></a></li>")	
				sideBar.append("<li class='nav-item active'><a class='nav-link' href='userProfileNew.html'><i class='fas fa-fw fa-tachometer-alt'></i><span id='medicalRecord'>Zdravstveni karton</span></a></li>")	

			
			}
			
			if(user.role == "ClinicAdmin"){
				sideBar.append("<li class='nav-item active'><a class='nav-link' href='userProfileNew.html'><i class='fas fa-fw fa-tachometer-alt'></i><span id='profileUser'>Profil</span></a></li>")	
				sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><i class='fas fa-fw fa-tachometer-alt'></i><span id='addHall'>Dodavanje sala</span></a></li>")	
				sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><i class='fas fa-fw fa-tachometer-alt'></i><span id='showHalls'>Lista sala</span></a></li>")
				$("#addHallContainer").hide()
				$("#showHallContainer").hide()
				$("#changeHallContainer").hide()
				
				
				$('#addHall').click(function(e){
					
					e.preventDefault()
					$("#addHallContainer").show()
					$("#showHallContainer").hide()
					$("#changeHallContainer").hide()
									
					
				})
				
				$('#showHalls').click(function(e){
					
					e.preventDefault()
					$("#showHallContainer").show()
					$('#addHallContainer').hide()
					$("#changeHallContainer").hide()
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
			//TODO: Napisati dinamicke <li> clanove u nav baru sa strane za ulogu medicinske sestre.
			
			
		}//KRAJ COMPLETE FUNKCIJE
	
})//KRAJ AJAX POZIVA
	
	
	
	$('#register_btn').click(function(e){
		e.preventDefault()
		window.location.href = "register.html"
	})
	
	
})//KRAJ DOCUMENT READY

function listHall(data,i)
{
	
	console.log(data)
	let tr=$('<tr></tr>');
	let tdNumber=$('<td class="number" data-toggle="modal" data-target="#exampleModalLong">'+ data.number +'</td>');
	let tdClinic=$('<td class="clinic" data-toggle="modal" data-target="#exampleModalLong">'+ data.clinic.name +'</td>');
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



