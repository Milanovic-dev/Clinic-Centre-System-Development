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
				sideBar.append("<li class='nav-item active'><a class='nav-link' href=''><i class='fas fa-fw fa-tachometer-alt'></i><span id='addHall'>Dodavanje sala</span></a></li>")	
				sideBar.append("<li class='nav-item active'><a class='nav-link' href=''><i class='fas fa-fw fa-tachometer-alt'></i><span id='showHalls'>Lista sala</span></a></li>")
				
				$('#addHall').click(function(e){
					
					e.preventDefault()
					$("#addHallContainer").removeAttr('hidden')
					
				})
				
				$('#showHalls').click(function(e){
					
					e.preventDefault()
					$("#showHallContanier").removeAttr('hidden')
					
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
											window.location.href = "index.html"
										}
									}
										
								})
							}
								
						})
						
						
				})
				
			}
			//TODO: Napisati dinamicke <li> clanove u nav baru sa strane za ulogu medicinske sestre.
			
			
		}
})
	
	
	
	$('#register_btn').click(function(e){
		e.preventDefault()
		window.location.href = "register.html"
	})
	
	
})


