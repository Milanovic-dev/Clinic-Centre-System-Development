/**
 * 
 */

//ULAZNA FUNKCIJA
function initClinicAdmin(user)
{
	let sideBar = $("#sideBar")
	sideBar.append("<li class='nav-item active'><a class='nav-link' href='userProfileNew.html'><i class='fas fa-fw fa-tachometer-alt'></i><span id='profileUser'>Profil</span></a></li>")	
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><span id='addHall'>Dodavanje sala</span></a></li>")	
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><span id='showHalls'>Lista sala</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><span id='addDoctor'>Dodaj lekara</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><span id='showDoctor'>Lista lekara</span></a></li>")

	
	$('#showDoctor').click(function(e){
		e.preventDefault()
		
		$("#addHallContainer").hide()
		$("#showHallContainer").hide()
		$("#changeHallContainer").hide()
		$("#showUserContainer").show()
		
		$.ajax({
			type: 'GET',
			url: 'api/admins/clinic/getClinicFromAdmin/' + user.email,
			complete: function(data)
			{
				let clinic = data.responseJSON
				makeDoctorTable(clinic)
			}
		
		
	})
	
	
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
		
		$.ajax({
			type: 'GET',
			url: 'api/admins/clinic/getClinicFromAdmin/' + user.email,
			complete: function(data)
			{
				let clinic = data.responseJSON
				makeUserTable(clinic)
			}
			
		})

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

function makeDoctorTable(clinic)
{
	$.ajax({
		type: 'GET',
		url: 'api/clinic/getDoctors/' + clinic.name,
		complete: function(data)
		{
			console.log(data)
			users = data.responseJSON
			let i = 0
			$('#tableUsers tbody').empty()
			for(let u of users)
            {
				listDoctor(u,i,clinic);
				i++;
            }
		}
								
	})


}

function makeUserTable(clinic)
{
	$.ajax({
		type: 'GET',
		url: 'api/users/getAll/Patient',
		complete: function(data)
		{
			console.log(data)
			users = data.responseJSON
			let i = 0
			$('#tableUsers tbody').empty()
			for(let u of users)
            {
				listUser(u,i,clinic);
				i++;
            }
		}
								
	})

}

function listDoctor(data,i,clinic)
{
	let tr=$('<tr></tr>');
	let tdName=$('<td class="number" data-toggle="modal" data-target="#exampleModalLong">'+ data.user.name +'</td>');
	let tdSurname=$('<td class="clinic" data-toggle="modal" data-target="#exampleModalLong">'+ data.user.surname +'</td>');
	let tdEmail=$('<td class="clinic" data-toggle="modal" data-target="#exampleModalLong">'+ data.user.email +'</td>');
	let tdPhone=$('<td class="clinic" data-toggle="modal" data-target="#exampleModalLong">'+ data.user.phone +'</td>');
	let tdAddress=$('<td class="clinic" data-toggle="modal" data-target="#exampleModalLong">'+ data.user.address +'</td>');
	let tdCity=$('<td class="clinic" data-toggle="modal" data-target="#exampleModalLong">'+ data.user.city +'</td>');
	let tdState=$('<td class="clinic" data-toggle="modal" data-target="#exampleModalLong">'+ data.user.state +'</td>');
	
	let tdChange=$('<td> <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addShiftModal" id = "changeUserRole_btn'+i+'">Promeni u lekara</button></td>');

	tr.append(tdName).append(tdSurname).append(tdEmail).append(tdPhone).append(tdAddress).append(tdCity).append(tdState).append(tdChange)
	$('#tableUsers tbody').append(tr);

}

function listUser(data,i,clinic)
{
	
	let tr=$('<tr></tr>');
	let tdName=$('<td class="number" data-toggle="modal" data-target="#exampleModalLong">'+ data.name +'</td>');
	let tdSurname=$('<td class="clinic" data-toggle="modal" data-target="#exampleModalLong">'+ data.surname +'</td>');
	let tdEmail=$('<td class="clinic" data-toggle="modal" data-target="#exampleModalLong">'+ data.email +'</td>');
	let tdPhone=$('<td class="clinic" data-toggle="modal" data-target="#exampleModalLong">'+ data.phone +'</td>');
	let tdAddress=$('<td class="clinic" data-toggle="modal" data-target="#exampleModalLong">'+ data.address +'</td>');
	let tdCity=$('<td class="clinic" data-toggle="modal" data-target="#exampleModalLong">'+ data.city +'</td>');
	let tdState=$('<td class="clinic" data-toggle="modal" data-target="#exampleModalLong">'+ data.state +'</td>');
	
	//<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal" data-whatever="@mdo">Open modal for @mdo</button>
	let tdChange=$('<td> <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addShiftModal" id = "changeUserRole_btn'+i+'">Promeni u lekara</button></td>');
	
	tr.append(tdName).append(tdSurname).append(tdEmail).append(tdPhone).append(tdAddress).append(tdCity).append(tdState).append(tdChange)
	$('#tableUsers tbody').append(tr);

	$('#changeUserRole_btn'+i).click(function(e)
	{
		e.preventDefault()
		$('#exampleModalLabel').text("Radno vreme ( " + data.name + " "  + data.surname + " )" )
		
	})
	
	let startShift = $('#shiftStart_input').val()
	let endShift = $('#shiftEnd_input').val()
	
	
	$('#addShift_btn').click(function(e)
	{
		e.preventDefault()
		
		$.ajax({
			type:'POST',
			url: 'api/doctors/makeDoctor/' + data.email + '/' + '08:00' + '/' + '18:00',
			complete: function(response)
			{
				$.ajax({
					type: 'PUT',
					url: 'api/clinic/addDoctor/'+ clinic.name +'/' + data.email,
					complete: function(e)
					{
						
					}
					
				})//KRAJ AJAXA ZA DODAVANJE NOVOG DOKTORA U KLINIKU ADMINA
			}
			
		}) //KRAJ AJAXA ZA IZMENU ULOGE
		
		
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



