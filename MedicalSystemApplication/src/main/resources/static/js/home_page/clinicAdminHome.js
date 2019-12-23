/**
 * 
 */

var clinic = null

//ULAZNA FUNKCIJA
function initClinicAdmin(user)
{

	let sideBar = $("#sideBar")
	sideBar.append("<li class='nav-item active'><a class='nav-link' href='userProfileNew.html'><i class='fas fa-fw fa-tachometer-alt'></i><span id='profileUser'>Profil</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><span id='changeProfileClinic'>Uredi profil klinike</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><span id='addHall'>Dodaj salu</span></a></li>")	
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><span id='showHalls'>Lista sala</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><span id='addDoctor'>Dodaj lekara</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><span id='showDoctor'>Lista lekara</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><span id='addTypeOfExamination'>Dodaj tip pregleda</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><span id='showTypeOfExamination'>Lista tipova pregleda</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><span id='addPredefinedAppointment'>Dodaj predefinisani pregled</span></a></li>")
	
	$.ajax({
		type:'GET',
		url:"api/admins/clinic/getClinicFromAdmin/" + user.email,
		complete: function(data)
		{
			clinic = data.responseJSON
		}
	})
	
	
	clearViews()
	addView("addHallContainer")
	addView("showHallContainer")
	addView("changeHallContainer")
	addView("showUserContainer")
	addView("addTypeOfExaminationContainer")
	addView("showTypeOfExaminationContainer")
	addView("registrationConteiner")
	addView("AppointmentContainer")
	addView("changeProfileClinicContainer")

	
	let headersDoctors = ["Ime","Prezime","Email","Telefon","Tip pregleda koji obavlja","Adresa","Grad","Drzava"]
	let handleDoctors = createTable("listDoctorsTable","Lista lekara",headersDoctors)
	insertTableInto("doctorContainer",handleDoctors)
	
	let headersHalls = ["Broj sale","Klinika kojoj pripada"]
	let handleHalls = createTable("listHallsTable","Lista Sala",headersHalls)
	insertTableInto("hallContainer",handleHalls)
	
	let headersPrices = ["Tip pregleda" ,"Cena pregleda"]
	let handlePrices = createTable("listPricesTable","Cenovnik",headersPrices)
	insertTableInto("pricesContainer",handlePrices)
	
	//IZMENA PROFILA KLINIKE
	$('#changeProfileClinic').click(function(e){
		e.preventDefault()
		showView("changeProfileClinicContainer")
		
		getTableDiv("listDoctorsTable").show()
		getTableDiv("listHallsTable").show()
		getTableDiv("listPricesTable").show()

		
		$('#inputNameClinicProfile').val(clinic.name)
		$('#inputAddressClinicProfile').val(clinic.address)
		$('#inputDescriptionClinicProfile').val(clinic.description)
				
		$.ajax({
			type: 'GET',
			url: 'api/clinic/getDoctors/' + clinic.name,
			complete: function(data)
			{
				doctors = data.responseJSON
				emptyTable("listDoctorsTable")
				for(d of doctors)
				{
					let values = [d.user.name,d.user.surname,d.user.email,d.user.phone,d.type,d.user.address,d.user.city,d.user.state]
					insertTableData("listDoctorsTable",values)
				}
			}
				
		})
		
		$.ajax({
			type: 'GET',
			url: 'api/hall/getAllByClinic/' + clinic.name,
			complete: function(data)
			{
				halls = data.responseJSON
						
				emptyTable("listHallsTable")
				for(h of halls )
				{
					let values = [h.number , h.clinicName]
					insertTableData("listHallsTable",values)
				}
			}
		})
				
		$.ajax({
			type: 'GET',
			url: 'api/priceList/getAllByClinic/' + clinic.name,
			complete: function(data)
			{
				prices = data.responseJSON
						
				emptyTable("listPricesTable")
				for(p of prices)
				{
					let values = [p.typeOfExamination , p.price]
					insertTableData("listPricesTable",values)
				}
			}
		})
				

	$('#submitChangesClinicProfile').click(function(e){
		let nameClinic = $('#inputNameClinicProfile').val()
		let addressClinic = $('#inputAddressClinicProfile').val()
		let descriptionClinic = $('#inputDescriptionClinicProfile').val()
		
					
		let c = {"name": nameClinic,"address": addressClinic,"city": clinic.city,"state": clinic.state,"description": descriptionClinic,"rating": clinic.rating}
		clinicJSON = JSON.stringify(c)
				
		$.ajax({
			type: 'PUT',
			url: 'api/clinic/update/' + clinic.name,
			data: clinicJSON,
			dataType: "json",
			contentType : "application/json; charset=utf-8",
			complete: function(data)
			{
				if(data.status == "200")
				{
					$('#successSubmit').text("Uspesno ste izmenili profil klinike")
					$('#successSubmit').show()
				}
				else
				{
					$('#successSubmit').text("Izmena klinike nije uspela")
					$('#successSubmit').show()

				}
			}
					
		})
		
		
	})
	
	})
	
	//KRAJ IZMENE PROFILA KLINIKE
	
	//DODAVANJE PREDEFINISANOG PREGLEDA
	
	$('#addPredefinedAppointment').click(function(e){
		e.preventDefault()
		
		showView("AppointmentContainer")
		makeAppointment(clinic)	
	})
	//KRAJ DODAVANJA PREDEFINISANOG PREGLEDA
	
	
	$('#submitPredefinedAppointmentRequest').off('click')
	$('#submitPredefinedAppointmentRequest').click(function(e){
		e.preventDefault()
		
		$.ajax({
			type: 'POST',
			url: 'api/appointments/makePredefined',
			data
			complete: function(data)
			{
				alert(data.status)
			}
		
		})
	})
	
	//LISTA TIPOVA PREGLEDA
	$('#showTypeOfExamination').click(function(e){
		e.preventDefault()
		showView("showTypeOfExaminationContainer")		
		makeTypeOfExaminationTable(clinic)	
		
	})
	//KRAJ LISTE TIPOVA PREGLEDA
	
	//DODAVANJE TIPA PREGLEDA
	$('#addTypeOfExamination').click(function(e){
		e.preventDefault()
		showView("addTypeOfExaminationContainer")
		$('#errorSpanTypeOfExamination').hide()
	})
		
		$('#submitTypeOfExamination').click(function(e){
			e.preventDefault()

			let typeOfExaminationName = $('#inputTypeOfExamination').val()
			let typeOfExaminationPrice = $('#inputTypeOfExaminationPrice').val()
			let typeOfExamination = JSON.stringify({"clinicName":clinic.name,"typeOfExamination" : typeOfExaminationName,"price" : typeOfExaminationPrice})
			
			$.ajax({
				type: 'POST',
				url: 'api/priceList/add',
				data: typeOfExamination,
				dataType : "json",
				contentType : "application/json; charset=utf-8",
				complete: function(data2)
				{
						
					if(data2.status == "208")
					{
						$('#errorSpanTypeOfExamination').show()
						$('#errorSpanTypeOfExamination').text("Tip pregleda koji zelite da unesete vec postoji")
					}
					if(data2.status == "200")
					{
						$('#errorSpanTypeOfExamination').hide()
						showView("showTypeOfExaminationContainer")
						makeTypeOfExaminationTable(clinic)
					}
					
				}
			})		
		}) //KRAJ SUBMIT TYPE OF EXAMINATION

	//KRAJ DODAVANJA TIPA PREGLEDA
	
	
	
	$('#showDoctor').click(function(e){
		e.preventDefault()
		showView("showUserContainer")
		
		makeDoctorTable(clinic)
	})
	
	
	$('#addHall').click(function(e){
		
		e.preventDefault()
		showView("addHallContainer")	
	})
	
	$('#addDoctor').click(function(e){
		
		e.preventDefault()
		showView("registrationConteiner")
						
		$.ajax({
			type:'GET',
			url:"api/priceList/getAllByClinic/"+clinic.name,
			complete: function(data)
			{
				let prices = data.responseJSON
						
				$('#selectTypeOfExam').empty()
				for(let p of prices)
				{
					$('#selectTypeOfExam').append($('<option>',{
						value: p.typeOfExamination,
						text: p.typeOfExamination
					}))
								
				}
						
			}
		})
				
				
		$('#submitDoctor').off('click')
		$('#submitDoctor').click(function(e){
			e.preventDefault()
			submitDoctorForm(clinic)									
		})								

	})
	
	$('#showHalls').click(function(e){
		
		e.preventDefault()
		showView("showHallContainer")
		makeHallTable()
	})
	
	let email = user.email
	$('#submitHall').click(function(e){
			e.preventDefault()
			let idHall = $('#inputHall').val()
			let nameHall = $('#inputHallName').val()
			
			let hall = JSON.stringify({"clinicName":clinic.name,"number" : idHall , "name": nameHall})
			$.ajax({
				type: 'POST',
				url: 'api/hall/addHall',
				data: hall,
				dataType : "json",
				contentType : "application/json; charset=utf-8",
				complete: function(data)
				{
							
					if(data.status == "200")
					{
						showView("showHallContainer")
						makeHallTable()
					}
				}						
			})	
	})
	
	//KRAJ SUBMIT HALLS


}


function submitDoctorForm(clinic)
{
	let email = $('#inputEmailDoctor').val()
	let name = $('#inputNameDoctor').val()
	let surname = $('#inputSurnameDoctor').val()
	let state = $('#selectStateDoctor').val()
	let city = $('#inputCityDoctor').val()
	let address = $('#inputAddressDoctor').val()
	let phone = $('#inputPhoneDoctor').val()
	let insurance = $('#inputInsuranceDoctor').val()
	
	let flag = true
	
	if(email.indexOf("@gmail.com") == -1)
	{
		var emailInput = $('#inputEmailDoctor')
		
		emailInput.addClass('is-invalid')
		emailInput.removeClass('is-valid')
		flag = false
	}
	else
	{
		var emailInput = $('#inputEmailDoctor')
		
		emailInput.addClass('is-valid')
		emailInput.removeClass('is-invalid')
	}
	
	if(/^[a-zA-Z]+$/.test(name) == false || name == "")
	{
		var nameInput = $('#inputNameDoctor')
		
		nameInput.addClass('is-invalid')
		nameInput.removeClass('is-valid')
		flag = false
	}
	else
	{
		var nameInput = $('#inputNameDoctor')
		
		nameInput.addClass('is-valid')
		nameInput.removeClass('is-invalid')
	}
	
	
	if(/^[a-zA-Z]+$/.test(surname) == false || surname == "")
	{
		var nameInput = $('#inputSurnameDoctor')
		
		nameInput.addClass('is-invalid')
		nameInput.removeClass('is-valid')
		flag = false
	}
	else
	{
		var nameInput = $('#inputSurnameDoctor')
		
		nameInput.addClass('is-valid')
		nameInput.removeClass('is-invalid')
	}
			
		
	if(city == "")
	{
		var input = $('#inputCityDoctor')
		
		input.addClass('is-invalid')
		input.removeClass('is-valid')
		flag = false
	}
	else
	{
		var input = $('#inputCityDoctor')
		
		input.removeClass('is-invalid')
		input.addClass('is-valid')
	}
	
	
	if(address == "")
	{
		var input = $('#inputAddressDoctor')
		
		input.addClass('is-invalid')
		input.removeClass('is-valid')
		flag = false
	}
	else
	{
		var input = $('#inputAddressDoctor')
		
		input.removeClass('is-invalid')
		input.addClass('is-valid')
	}
	
	if(phone == "")
	{
		var input = $('#inputPhoneDoctor')
		
		input.addClass('is-invalid')
		input.removeClass('is-valid')
		flag = false
	}
	else
	{
		var input = $('#inputPhoneDoctor')
		
		input.removeClass('is-invalid')
		input.addClass('is-valid')
	}
	
	if(insurance == "")
	{
		var input = $('#inputInsuranceDoctor')
		
		input.addClass('is-invalid')
		input.removeClass('is-valid')
		flag = false
	}
	else
	{
		var input = $('#inputInsuranceDoctor')
		
		input.removeClass('is-invalid')
		input.addClass('is-valid')
	}

	if($('#selectStateDoctor').find(':selected').prop('disabled')){
            		    var input = $('#selectStateDoctor')
                        input.addClass('is-invalid')
                        input.removeClass('is-valid')
                        flag = false
            		} else {
            		    var input = $('#selectStateDoctor')
                        input.removeClass('is-invalid')
                        input.addClass('is-valid')
            		}
	
	if(flag == false) return
	

    let type = $('#selectTypeOfExam').val()
    let start = $('#inputStartShiftDoctor').val()
    let end = $('#inputEndShiftDoctor').val()
    
    
	let userJ = {"password":"","email":email,"name":name,"surname":surname,"city":city,"address":address,"state":state,"phone":phone,"insuranceId":insurance}
	let json = JSON.stringify({"user":userJ,"type":type,"shiftStart":start,"shiftEnd":end,"clinicName":clinic.name,"averageRating":"0"})
	$.ajax({
		type: 'POST',
		url:"api/doctors/makeNewDoctor",
		data : json,
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		complete: function(data)
		{
			if(data.status == "208")
			{
				$('#alreadyExistsD').text("Lekar sa tim emailom vec postoji.")
			}
			else
			{
				$('#alreadyExistsD').hide()
				$('#registrationConteiner').hide()
				$("#showUserContainer").show()
				makeDoctorTable(clinic)
				
			}
		}
	})
	
}

function listHall(data,i)
{
	
	let tr=$('<tr></tr>');
	let tdNumber=$('<td class="number" data-toggle="modal" data-target="#exampleModalLong">'+ data.number +'</td>');
	let tdName=$('<td class="name" data-toggle="modal" data-target="#exampleModalLong">'+ data.name +'</td>');
	let tdClinic=$('<td class="clinic" data-toggle="modal" data-target="#exampleModalLong">'+ data.clinicName +'</td>');
	let tdChange=$('<td> <button type="button" class="btn btn-primary" id = "changeHall_btn'+i+'">Izmeni</button></td>');
	let tdDelete=$('<td> <button type="button" class="btn btn-danger" id = "deleteHall_btn'+i+'">Izbrisi</button></td>');
	
	tr.append(tdNumber).append(tdName).append(tdClinic).append(tdChange).append(tdDelete);
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
		showView("changeHallContainer")
		
		$('#inputChangeHall').val(data.number)
		$('#inputChangeHallName').val(data.name)
		
		$('#submitChangeHall').click(function(e)
		{
			let newNumber = $('#inputChangeHall').val()
			let newName = $('#inputChangeHallName').val()
			$.ajax({
				type: 'PUT',
				url: 'api/hall/changeHall/'+data.number+"/"+newNumber + "/" +newName,
				complete: function(data2)
				{
					if(data2.status == "200")
					{
						showView("showHallContainer")
						makeHallTable(data.clinicName)
					}
				}
			
			})
	
		})
		
	})
	
}

function makeTypeOfExaminationTable(clinic)
{
	$.ajax({
		type: 'GET',
		url: 'api/priceList/getAll',
		complete: function(data)
		{
			console.log(data)
			types = data.responseJSON
			let i = 0
			$('#tableTypeOfExamination tbody').empty()
			for(let t of types)
            {
				listTypesOfExamination(t,i,clinic);
				i++;
            }
		}

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
	
	let tdChange=$('<td> <button type="button" class="btn btn-danger" id = "deleteDoctor_btn'+i+'">Obrisi lekara</button></td>');

	tr.append(tdName).append(tdSurname).append(tdEmail).append(tdPhone).append(tdAddress).append(tdCity).append(tdState).append(tdChange)
	$('#tableUsers tbody').append(tr);
	
	$('#deleteDoctor_btn'+i).click(function(e){
		
		e.preventDefault()
		$.ajax({
			type:'DELETE',
			url: 'api/doctors/removeDoctor/' + data.user.email,
			complete: function(response)
			{
				makeDoctorTable(clinic)
			
			}
		
		})
	})

}

function listTypesOfExamination(t,i,clinic)
{
	let tr=$('<tr></tr>');
	let tdName=$('<td class="number" data-toggle="modal" data-target="#exampleModalLong">'+ t.typeOfExamination +'</td>');
	let tdClinic=$('<td class="number" data-toggle="modal" data-target="#exampleModalLong">'+ t.clinicName +'</td>');
	let tdPrice=$('<td class="number" data-toggle="modal" data-target="#exampleModalLong">'+ t.price +'</td>');
	let tdChange=$('<td> <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#changeTypeOfExaminationModal" id = "changeTypeOfExamination_btn'+i+'">Izmeni tip</button></td>');
	let tdDelete=$('<td> <button type="button" class="btn btn-danger" id = "deleteTypeOfExamination_btn'+i+'">Obrisi</button></td>');

	
	tr.append(tdName).append(tdClinic).append(tdPrice).append(tdChange).append(tdDelete)
	$('#tableTypeOfExamination tbody').append(tr);
	
	$('#changeTypeOfExamination_btn'+i).click(function(e){
		e.preventDefault()

		$('#typeOfExamination_input').val(t.typeOfExamination)
		$('#typeOfExaminationPrice_input').val(t.price)
		
		$('#changeTypeOfExam_btn').off('click')
		$('#changeTypeOfExam_btn').click(function(e){
			let typeOfExam = $('#typeOfExamination_input').val()
			let typeOfExamPrice = $('#typeOfExaminationPrice_input').val()
			let data = JSON.stringify({"clinicName":clinic.name,"typeOfExamination" : typeOfExam,"price" : typeOfExamPrice})
			
			console.log(typeOfExam)
			console.log(typeOfExamPrice)
			$.ajax({
				type:'PUT',
				url: 'api/priceList/update/' + t.typeOfExamination,
				data: data,
				dataType : "json",
				contentType : "application/json; charset=utf-8",
				complete: function(response)
				{
					console.log(response.status)
					if(response.status == "200")
					{
						makeTypeOfExaminationTable(clinic)
					}
				}
			})	
		})
			
			
		}) 
		
		
	
	$('#deleteTypeOfExamination_btn'+i).click(function(e){
		e.preventDefault()
		$.ajax({
			type:'DELETE',
			url: 'api/priceList/deletePriceList/' + t.typeOfExamination + "/" + clinic.name,
			complete: function(response)
			{
				makeTypeOfExaminationTable(clinic)
			}
		
		})
	})
	

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
	
	
	$('#addShift_btn').click(function(e)
	{
		e.preventDefault()
		let startShift = $('#shiftStart_input').val()
		let endShift = $('#shiftEnd_input').val()

		$.ajax({
			type:'POST',
			url: 'api/doctors/makeDoctor/' + data.email + '/' + startShift + '/' + endShift,
			complete: function(response)
			{
				$.ajax({
					type: 'PUT',
					url: 'api/clinic/addDoctor/'+ clinic.name +'/' + data.email,
					complete: function(e)
					{
						makeUserTable(clinic)
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
		url: 'api/hall/getAllByClinic/'+clinic.name,
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

function makeAppointment(clinic)
{
	$('#inputClinicNameAppointment').val(clinic.name)
	$('#inputClinicAddressAppointment').val(clinic.address)
	
	$.ajax({
		type: 'GET',
		url: 'api/hall/getAllByClinic/' + clinic.name,
		complete: function(data)
		{
			let halls = data.responseJSON
			for(let h of halls)
			{
				$('#inputAppointmentHall').append($('<option>',{
					value: h.number,
					text: h.number
				}))
			}
		}
		
	})
	
	$.ajax({
		type: 'GET',
		url: 'api/priceList/getAllByClinic/' + clinic.name,
		complete: function(data)
		{
			let types = data.responseJSON
			for(let t of types)
			{
				$('#inputAppointmentTypeSelect').append($('<option>',{
					value: t.typeOfExamination,
					text: t.typeOfExamination
				}))
			}
			
		}

	})
	
	$('#inputAppointmentTypeSelect').change(function(e){
		$.ajax({
			type: 'GET',
			url: 'api/doctors/getAll/' + $('#inputAppointmentTypeSelect').val() + "/" + clinic.name,
			complete: function(data)
			{
				let doctors = data.responseJSON
				$('#tableDoctorsList tbody').empty()
				let i=0
				for(let d of doctors)
				{
					console.log(d)
					makeDoctor(d,i)
					i++;
				}
				
			}
		})
	})

}
function makeDoctor(d,i)
{
	let tr=$('<tr></tr>');
	let tdName=$('<td class="number" data-toggle="modal" data-target="#exampleModalLong">'+ d.user.name +'</td>');
	let tdSurname=$('<td class="clinic" data-toggle="modal" data-target="#exampleModalLong">'+ d.user.surname +'</td>');
	let tdEmail=$('<td class="clinic" data-toggle="modal" data-target="#exampleModalLong">'+ d.user.email +'</td>');
	let tdPhone=$('<td class="clinic" data-toggle="modal" data-target="#exampleModalLong">'+ d.user.phone +'</td>');
	let tdSelect = $("<td><input type='checkbox' id='checkDoctor"+i+"'><label for='checkDoctor"+i+"'></label></td>" )

	
	tr.append(tdName).append(tdSurname).append(tdEmail).append(tdPhone).append(tdSelect)
	$('#tableDoctorsList tbody').append(tr);

}

