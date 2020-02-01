/**
 * 
 */

var clinic = null
var hallCalendar = null

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
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><span id='vacationRequestList'>Zahtevi za godišnji odmor</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' type='button'><span id='examinationRequestList'>Zahtevi za pregled</span></a></li>")
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
	addView("showVacationRequestListContainer")
	addView("showExaminationRequestListContainer")
	
	
	setUpHallCalendar()
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
	
	appointmentHeaders = ["Ime pacijenta","Datum pocetka","Datum kraja","Cena","Tip pregleda","Lekari"]
	let appTable = createTable("appReqTable","Lista zahteva za pregled",appointmentHeaders)
	insertTableInto("showExaminationRequestListContainer",appTable)
	getTableDiv('appReqTable').show()
	
	vacationHeaders = ["Tip medicinskog osoblja" , "Ime","Prezime","Email","Datum početka odsustva","Datum kraja odsustva","Šifra zahteva","",""]
	let table = createTable("vacationReqTable","Lista zahteva za godišnji odmor ili odsustvo",vacationHeaders)
	insertTableInto("showVacationRequestListContainer",table)
	getTableDiv('vacationReqTable').show()

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
	
	//LISTA ZAHTEVA ZA GODISNJI ODMOR
	$('#vacationRequestList').click(function(e){
		e.preventDefault()
		
		listVacationRequest(user,clinic)
		
	})
	//KRAJ LISTE ZAHTEVA ZA GODISNJI ODMOR
	//LISTA ZAHTEVA ZA PREGLED
	$('#examinationRequestList').click(function(e){
		e.preventDefault()
		
		listAppointmentRequest(clinic)
		
	})
	//KRAJ LISTE ZAHTEVA ZA PREGLED
	
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
	
	$('#inputDatePredef').datepicker({
		dateFormat: "dd-mm-yyyy"
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

function listVacationRequest(admin,clinic)
{
	showView("showVacationRequestListContainer")
	getAllVacationRequestsByClinic(clinic)
	
}


function getAllVacationRequestsByClinic(clinic)
{
	$.ajax({
		type: 'GET',
		url: 'api/vacation/getAllVacationRequestsByClinic/' + clinic.name,
		complete: function(data)
		{
			let vacations = data.responseJSON
			emptyTable("vacationReqTable")
			let i=0
			for(v of vacations)
			{
				listVacationRequests(clinic,v,i)
				i++
			}
		}
	
	})
	
}

function listVacationRequests(clinic,data,i)
{
	let tdConfirm = '<button type="button" id="acceptRequestVacation'+i+'" class="btn btn-primary">Prihvati</button>'
	let tdDeny = '<button type="button" id="denyRequestVacation'+i+'" class="btn btn-danger" data-toggle="modal" data-target="#exampleModal" data-whatever="@mdo">Odbij</button>'
	
	
	let values = [data.user.role, data.user.name, data.user.surname, data.user.email, data.startDate,data.endDate,data.id,tdConfirm,tdDeny]
	insertTableData("vacationReqTable",values)
	
	//PRIHVATANJE ZAHTEVA ZA GODISNJI ODMOR ILI ODSUSTVO
	$('#acceptRequestVacation' + i).click(function(e){
		e.preventDefault()
		
		showLoading('acceptRequestVacation'+ i)
		request = JSON.stringify({"startDate":data.startDate,"endDate" : data.endDate , "user": data.user,"id": data.id})
		$.ajax({
			type: 'POST',
			url: 'api/vacation/confirmVacationRequest',
			data: request,
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			complete: function(response)
			{
				hideLoading('acceptRequestVacation'+ i)
				getAllVacationRequestsByClinic(clinic)
				
			}
			
		})
	})
	
	//ODBIJANJE ZAHTEVA ZA GODISNJI ODMOR ILI ODSUSTVO
	$('#denyRequestVacation' + i).click(function(e){
		e.preventDefault()
		$('#vacationRequestModal').modal("show")
		$('#vacation-user').val(data.user.email)
		
		$('#modalDeny').off('click')
		$('#modalDeny').click(function(e){
			
			let request = JSON.stringify({"startDate":data.startDate,"endDate" : data.endDate , "user": data.user,"id": data.id})
			let text = $('#messageVacationDeny-text').val()
			
			showLoading('modalDeny')
			
			$.ajax({
			type: 'DELETE',
			url: 'api/vacation/denyVacationRequest/' + text,
			data: request,
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			complete: function(response)
			{
				hideLoading('modalDeny')
				$('#vacationRequestModal').modal("hide")
				getAllVacationRequestsByClinic(clinic)
				
			}
			
		})
			
		})
		
		
	})
	
}

function listAppointmentRequest(clinic)
{
	showView("showExaminationRequestListContainer")
	getAllAppointmentRequestsByClinic(clinic)

}

function getAllAppointmentRequestsByClinic(clinic)
{
	$.ajax({
		type: 'GET',
		url: 'api/appointments/clinic/getAllAppointments/' + clinic.name,
		complete: function(data)
		{
			let apps = data.responseJSON
			console.log(apps)
			emptyTable("appReqTable")
			let i=0
			for(a of apps)
			{
				
				listAppointmentRequests(clinic,a,i)
				i++
			}
		}
	
	})
}
function listAppointmentRequests(clinic,appointment,i)
{	
	let values = [appointment.patientEmail, appointment.date,appointment.endDate,appointment.price,appointment.typeOfExamination,appointment.doctors]
	insertTableData("appReqTable",values)
	
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
	let d = [data.number,data.name,data.clinicName,'<button type="button" class="btn btn-primary" id = "calendarHall_btn'+i+'">Zauzeće</button>','<button type="button" class="btn btn-primary" id = "changeHall_btn'+i+'">Izmeni</button>','<button type="button" class="btn btn-danger" id = "deleteHall_btn'+i+'">Izbrisi</button>']
	
	insertTableData("tableHall",d)
	
	$('#calendarHall_btn' + i).click(function(e){
		fillHallCalendar(data)
		$('#hallCalendarModal').modal('show')
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

function setUpHallCalendar()
{
	  hallCalendar = $('#calendarHall').fullCalendar({
		    plugins: [ 'dayGridPlugin' ],
		    defaultDate: '2020-01-01',
	        buttonText: {
	               today:    'danas',
	               month:    'mesec',
	               week:     'nedelja',
	               day:      'dan',
	               year:     'godina',
	               list:     'list'
	             },
	        monthNames: ['Januar', 'Februar', 'Mart', 'April', 'Maj', 'Jun', 'Jul',
	                      'Avgust', 'Septembar', 'Oktobar', 'Novembar', 'Decembar'],
	        monthNamesShort: ['Jan','Feb','Mar','Apr','Maj','Jun','Jul','Avg','Sep','Okt','Nov','Dec'],
	        dayNames: ['Nedelja','Ponedeljak','Utorak','Sreda','Cetvrtak','Petak','Subota'],
	        dayNamesShort: ['Ned','Pon','Uto','Sre','Cet','Pet','Sub'],
	        fixedWeekCount: false,
	        timeFormat: 'HH:mm',
	        views: {
	            timelineCustom: {
	                type: 'timeline',
	                buttonText: 'godina',
	                dateIncrement: { years: 1 },
	                slotDuration: { months: 1 },
	                visibleRange: function (currentDate) {
	                    return {
	                        start: currentDate.clone().startOf('year'),
	                        end: currentDate.clone().endOf("year")
	                    };
	                }

	            }
	        },
	        eventColor: '#2f989d',
	        eventRender: function(event, element){
	        	$(element).tooltip({title: event.extendedProps.tooltip, container: "body"})
	        },
	        schedulerLicenseKey: 'GPL-My-Project-Is-Open-Source'
		  });	
}


//KALENDAR ZAUZECA SALE
function fillHallCalendar(hall)
{
	
	hallCalendar.fullCalendar('removeEvents')
  
	  $.ajax({
		  type:'GET',
		  url:"api/appointments/hall/getAll/"+clinic.name+"/"+hall.number,
		  complete: function(data)
		  {
			  $.each(data.responseJSON, function(i, item){
				
				  let event = {
						  title: "",
					      start: item.date,
					      end: item.endDate,
					      stick: true,
					      extendedProps: {
					          tooltip: getDateHours(item.date) + "-" + getDateHours(item.endDate)
					      }
				  }
				  
				  hallCalendar.fullCalendar('renderEvent', event, true)
				  
			  })
			  
		  }
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
				emptyTable('preAppTableDoctor')
				
				$.each(users, function(i, u)
				{
					let d = [u.user.name, u.user.surname, getProfileLink(u.user.email), u.user.phone, u.user.address, u.user.city, u.user.state,"<input type='checkbox' id='checkDoctorSelect"+i+"'><label for='checkDoctorSelect"+i+"'></label>"]
					insertTableData("preAppTableDoctor",d)
					
					$('#checkDoctorSelect'+i).off('click')
					$('#checkDoctorSelect'+i).click(function(e){
						for(let j = 0 ; j < getTableRowCount('preAppTableDoctor')  ; j++)
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
				})
										
			}
		})
	})
	
	$('#submitPredefinedAppointmentRequest').click(function(e){
		e.preventDefault()
		console.log(getTableRowCount('preAppTableDoctor'))
		for(let j = 0 ; j < getTableRowCount('preAppTableDoctor') ; j++)
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
	
	let date = $('#inputDatePredef').val()
	let clinicName = clinic.name
	let hallNumber = $('#inputAppointmentHall').val()
	let doctors = doctorsSelected
	let timeStart = $('#inputTimeBegin').val()
	let timeEnd = $('#inputTimeEnd').val()
	let price = $('#inputPrice').val()
	let typeOfExamination = $('#inputAppointmentTypeSelect').val()
	let json = JSON.stringify({"date":date + " " + timeStart, "endDate":date + " " + timeEnd, "clinicName":clinicName,"hallNumber": hallNumber, "doctors":doctors,"duration": 0,"price": price,"typeOfExamination":typeOfExamination,"type":"Examination"})
	
	let flag = true
	
	if(!validation($('#inputDatePredef'), date == "", "Morate izabrati datum"))
	{
		flag = false
	}
	
	if(!validation($('#inputTimeBegin'), timeStart == "", "Morate izabrati pocetak"))
	{
		flag = false
	}
	
	if(!validation($('#inputTimeEnd'), timeEnd == "", "Morate izabrati kraj."))
	{
		flag = false
	}

	if(!validation($('#inputAppointmentTypeSelect'),typeOfExamination == null,"Morate izabrati tip"))
	{
		flag = false
	}
	
	console.log(doctorsSelected)
	
	if(doctorsSelected == undefined)
	{
		displayError('submitPredefinedAppointmentRequest',"Morate izabrati doktora")
		flag = false
	}
	
	if(doctorsSelected.length == 0)
	{
		
		displayError('submitPredefinedAppointmentRequest',"Morate izabrati doktora")
		flag = false
	}
	
	if(!flag) return
	
	showLoading("submitPredefinedAppointmentRequest")
	
	$.ajax({
		type: 'POST',
		url: 'api/appointments/makePredefined',
		data: json,
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		complete: function(data)
		{
			console.log(data)
			if(data.status == '201')
			{
				warningModal('Uspesno','Uspesno kreiran predefinisani pregled')
				//window.location.href= "index.html"
			}
			else if(data.status == "409")
			{
				warningModal('Neuspesno', 'Vec postoji zakazan pregled u sali br.'+hallNumber+" izmedju "+timeStart+" i "+timeEnd)
			}
			else if(data.status == "208")
			{
				warningModal('Neuspesno', "Izabrani doktor vec ima zakazan pregled izmedju "+timeStart+" i "+timeEnd)
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


