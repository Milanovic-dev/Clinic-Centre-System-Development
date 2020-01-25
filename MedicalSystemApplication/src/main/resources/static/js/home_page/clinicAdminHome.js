/**
 * 
 */

var clinic = null

//ULAZNA FUNKCIJA
function initClinicAdmin(user)
{

	$.ajax({
		type:'GET',
		url:"api/admins/clinic/getClinicFromAdmin/" + user.email,
		complete: function(data)
		{
			clinic = data.responseJSON
			setUpClinicAdminPage(user)
		}
	
	})
}

function setUpClinicAdminPage(user)
{
	
	let sideBar = $("#sideBar")
	sideBar.append("<li class='nav-item active'><a class='nav-link' href='userProfileNew.html'><span id='profileUser'>Profil</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' href = 'clinicProfile.html?clinic=" + clinic.name +"'><span id='showReport'>Profil klinike</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><span id='changeProfileClinic'>Uredi profil klinike</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><span id='addHall'>Dodaj salu</span></a></li>")	
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><span id='showHalls'>Lista sala</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><span id='addDoctor'>Dodaj lekara</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><span id='showDoctor'>Lista lekara</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><span id='addTypeOfExamination'>Dodaj tip pregleda</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><span id='showTypeOfExamination'>Lista tipova pregleda</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><span id='addPredefinedAppointment'>Dodaj predefinisani pregled</span></a></li>")
	
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
	
	
	
	

	let headersTypes = ["Ime tipa","Klinika","Cena","",""]
	createDataTable("tableTypeOfExamination","showTypeOfExaminationContainer","Lista Tipova Pregleda",headersTypes,0)
		
	let headersUser = ["Ime","Prezime","Email","Telefon","Adresa","Grad","Drzava",""]
	createDataTable("tableDoctorUsers","showUserContainer","Lista lekara",headersUser,0)
	
	//Lista doktora kod pravljenja predefinsanog pregleda
	headersDoctorsPredefApp = ["Ime","Prezime","Email","Telefon","Adresa","Grad","Drzava",""]
	createDataTable("preAppTableDoctor","makePredefined_chooseDoctor", "Izaberite lekara",headersDoctorsPredefApp,0)
	
	//LISTA SALA
	let hallSearch = new TableSearch()
	hallSearch.input("<input class='form-control' type='text' placeholder='Ime sale' id='hallNameLabel'>")
	hallSearch.input("<input class='form-control' type='text' placeholder='Broj sale' id='hallNumberLabel'>")
	hallSearch.input('<input class="form-control datepicker-here" data-language="en" placeholder="Izaberite datum" id="hallDateLabel" readonly="true">')
	

	let headersHall = ["Broj sale","Ime sale","Ime klinike" ,"","",""]
	createDataTable("tableHall","showHallContainer","Lista sala",headersHall,0)
	
	

	insertSearchIntoTable("tableHall",hallSearch,function(){
		hname = $('#hallNameLabel').val()
		hnumber = $('#hallNumberLabel').val()
		hdate = $('#hallDateLabel').val()
		let json = JSON.stringify({"name": hname,"number": hnumber, "clinicName": clinic.name, "date": hdate})
		
		$.ajax({
			type: 'POST',
			url: "api/hall/getAllByFilter/",
			data: json,
			dataType: "json",
			contentType : "application/json; charset=utf-8",
			complete:function(data)
			{
				halls = data.responseJSON
				let i = 0
				emptyTable("tableHall")
				
				if(halls == undefined) return
				
				for(let h of halls )
				{
					listHall(h, i )
					i++
				}
			}
		})
		
	})

	$('#hallDateLabel').datepicker({
    	dateFormat: "dd-mm-yyyy"   	
	})
	
	//KRAJ LISTE SALA
	getTableDiv("tableTypeOfExamination").show()	
	getTableDiv("listDoctorsTable").show()
	getTableDiv("listHallsTable").show()
	getTableDiv("listPricesTable").show()
	getTableDiv("tableDoctorUsers").show()
	getTableDiv("tableHall").show()
	getTableDiv("preAppTableDoctor").show()
	
	if(getParameterByName("changeProfileClinic") == "true")
	{
		changeProfileClinicFunction(clinic)
	}
	
	//IZMENA PROFILA KLINIKE
	$('#changeProfileClinic').click(function(e){
		e.preventDefault()

		changeProfileClinicFunction(clinic)
	})
	
	//KRAJ IZMENE PROFILA KLINIKE
	
	//DODAVANJE PREDEFINISANOG PREGLEDA
	
	$('#addPredefinedAppointment').click(function(e){
		e.preventDefault()
		
		showView("AppointmentContainer")
		makeAppointment(clinic)	
	})
	//KRAJ DODAVANJA PREDEFINISANOG PREGLEDA
	
	
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
	
function changeProfileClinicFunction(clinic)
{
	showView("changeProfileClinicContainer")
		
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
	let d = [data.number,data.name,data.clinicName,'<button type="button" class="btn btn-warning" id = "calendarHall_btn'+i+'">Zauzeće</button>','<button type="button" class="btn btn-primary" id = "changeHall_btn'+i+'">Izmeni</button>','<button type="button" class="btn btn-danger" id = "deleteHall_btn'+i+'">Izbrisi</button>']
	
	insertTableData("tableHall",d)
	$('#calendarHall_btn' + i).click(function(e){
		setUpCalendar()
		$('#hallCalendar').modal('show');
		$('#hallCalendarModalLabel').text("Kalendar zauzeća sale " + data.number)

	})
	
	$('#deleteHall_btn'+i).click(function(e)
	{
		e.preventDefault()
		
		$.ajax({
			type: 'DELETE',
			url: 'api/hall/deleteHall/'+data.number,
			complete: function(data)
			{
				if(data.status == "200")
				{
					makeHallTable()
				}
				if(data.status == "409")
				{
					warningModal("Nije moguće obrisati", "Izabranu salu nije moguće obrisati,zbog postojanja zakazanih pregleda ili operacija u njoj.")
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
			emptyTable('tableTypeOfExamination')
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
			emptyTable('tableDoctorUsers')
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

	let d = [data.user.name,data.user.surname,getProfileLink(data.user.email),data.user.phone,data.user.address,data.user.city,data.user.state,'<button type="button" class="btn btn-danger" id = "deleteDoctor_btn'+i+'">Obrisi lekara</button>']
	insertTableData("tableDoctorUsers",d)
	
	$('#deleteDoctor_btn'+i).click(function(e){
		
		e.preventDefault()
		$.ajax({
			type:'DELETE',
			url: 'api/doctors/removeDoctor/' + data.user.email,
			complete: function(response)
			{
				if(response.status == "200")
				{
					makeDoctorTable(clinic)
				}
				if(response.status == "409")
				{
					warningModal("Nije moguće obrisati", "Izabranog lekara nije moguće obrisati,zbog postojanja zakazanih pregleda ili operacija u kojima učestvuje.")
				}
			
			}
		
		})
	})

}

function listTypesOfExamination(t,i,clinic)
{
	let d = [t.typeOfExamination,t.clinicName,t.price,"<button class='btn btn-primary' data-toggle='modal' data-target='#changeTypeOfExaminationModal' id='changeTypeOfExamination_btn"+i+"'>Izmeni</button>","<button class='btn btn-danger' id='deleteTypeOfExamination_btn"+i+"'>Obrisi</button>"]
	insertTableData("tableTypeOfExamination",d)
	
	$('#changeTypeOfExamination_btn'+i).click(function(e){
		e.preventDefault()

		$('#typeOfExamination_input').val(t.typeOfExamination)
		$('#typeOfExaminationPrice_input').val(t.price)
		
		$('#changeTypeOfExam_btn').off('click')
		$('#changeTypeOfExam_btn').click(function(e){
			let typeOfExam = $('#typeOfExamination_input').val()
			let typeOfExamPrice = $('#typeOfExaminationPrice_input').val()
			let data = JSON.stringify({"clinicName":clinic.name,"typeOfExamination" : typeOfExam,"price" : typeOfExamPrice})
			
		
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
				if(response.status == "400")
				{
					warningModal("Nije moguće obrisati", "Nije moguce obrisati tip " + t.typeOfExamination + " jer postoji zakazan pregled ovog tipa!")
					return
				}
				
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
			halls = data.responseJSON
			let i = 0
			emptyTable('tableHall')
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
	
	doctorsSelected = []
	let users = null
	$('#inputAppointmentTypeSelect').change(function(e){
		$.ajax({
			type: 'GET',
			url: 'api/clinic/getDoctorsByType/' + clinic.name + "/" + $('#inputAppointmentTypeSelect').val(),
			complete: function(data)
			{
				users = data.responseJSON
				let i = 0
				emptyTable('preAppTableDoctor')
				
				for(let u of users)
	            {
					let d = [u.user.name, u.user.surname, getProfileLink(u.user.email), u.user.phone, u.user.address, u.user.city, u.user.state,"<input type='checkbox' id='checkDoctorSelect"+i+"'><label for='checkDoctorSelect"+i+"'></label>"]
					insertTableData("preAppTableDoctor",d)
					i++
					
					$('#checkDoctorSelect'+i).off('click')
					$('#checkDoctorSelect'+i).click(function(e){
						
						for(let j = 0 ; j < users.length ; j++)
						{
							if(j == i)
							{
								$("#checkDoctorSelect"+j).prop('checked',true)
							}
							else
							{
								$("#checkDoctorSelect"+j).prop('checked',false)
							}
						}
					})
	            }
			}
		})
	})
	


			$('#submitPredefinedAppointmentRequest').click(function(e){
				for(let j = 0 ; j < users.length ; j++)
				{
					if($("#checkDoctorSelect"+j).is(":checked"))
					{
						doctorsSelected.push(users[j].user.email)
					}
					
				}
				submitPredefinedAppointment(doctorsSelected)
				
			})


}

function submitPredefinedAppointment(doctorsSelected)
{
	if(doctorsSelected.length == 0)
	{
		displayError('submitPredefinedAppointmentRequest',"Morate izabrati doktora")
		return
	}
	showLoading("submitPredefinedAppointmentRequest")
	
	let date = $('#inputDatePredef').val()
	let clinicName = clinic.name
	let hallNumber = $('#inputAppointmentHall').val()
	let doctors = doctorsSelected
	let timeStart = $('#inputTimeBegin').val()
	let timeEnd = $('#inputTimeEnd').val()
	let price = $('#inputPrice').val()
	let typeOfExamination = $('#inputAppointmentTypeSelect').val()
	let json = JSON.stringify({"date":date,"clinicName":clinicName,"hallNumber": hallNumber, "doctors":doctors,"duration": 0,"price": price,"typeOfExamination":typeOfExamination,"type":"Examination"})
	
	console.log(json)
	$.ajax({
		type: 'POST',
		url: 'api/appointments/makePredefineded',
		data: json,
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		complete: function(data)
		{
			console.log(data)
			if(data.status == '201')
			{
				alert('Uspesno kreiran predefinisani pregled')
			}
			
			hideLoading("submitPredefinedAppointmentRequest")
		}
		
		
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


