/**
 *
 */
//ULAZNA FUNKCIJA
var doctorClinic = null

function initDoctor(user)
{

	let sideBar = $("#sideBar")
	sideBar.append("<li class='nav-item active'><a class='nav-link' href='userProfileNew.html'><i class='fas fa-fw fa-tachometer-alt'></i><span id='profileUser'>Profil</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' href='index.html'><i class='fas fa-fw fa-tachometer-alt'></i><span id='pacientList'>Lista pacijenata</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' href='index.html'><i class='fas fa-fw fa-tachometer-alt'></i><span id='examinationStart'>Zapocni pregled</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' href='index.html'><i class='fas fa-fw fa-tachometer-alt'></i><span id='workCalendar'>Radni kalendar</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' href='index.html'><i class='fas fa-fw fa-tachometer-alt'></i><span id='vacationRequest'>Zahtev za odustvo</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' href='index.html'><i class='fas fa-fw fa-tachometer-alt'></i><span id='examinationRequest'>Zakazivanje pregleda</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' href='index.html'><i class='fas fa-fw fa-tachometer-alt'></i><span id='operationRequest'>Zakazivanje operacija</span></a></li>")
	
	clearViews()
	addView("addHallContainer")
	addView("showHallContainer")
	addView("changeHallContainer")
	addView("showExaminationContainer")
	addView("showUserContainer")
	addView("showPatientContainer")
	addView("showPatientContainerWithCheckBox")
	addView("showStartExaminationContainer")
	addView("showCalendarContainer")

	var bc1 = new BreadLevel()
    bc1.append('Radni kalendar').append('Pregled u toku')
    var bc2 = new BreadLevel()
    bc2.append('Zapocni pregled').append('Pregled u toku ')
    var bc3 = new BreadLevel()
    bc3.append('Lista pacijenata')

    initBreadcrumb([bc1,bc2,bc3])

	
	let headersPatients = ["Ime","Prezime","Email","Telefon","Adresa","Grad","Drzava","Broj zdravstvenog osiguranja"]
	createDataTable("listPatientTable","showPatientContainer","Lista pacijenata",headersPatients,0)
	
	let headersPatientsWithCheckBox = ["Ime","Prezime","Email","Telefon","Adresa","Grad","Drzava","Broj zdravstvenog osiguranja",""]
	createDataTable("listPatientTableWithCheckBox","showPatientContainerWithCheckBox","Izaberite pacijenta",headersPatientsWithCheckBox,0)
	insertElementIntoTable("listPatientTableWithCheckBox",'<br><button type="button" class="btn btn-primary" id = "startExamination_btn" disabled>Zapocni pregled</button>')
	
	getTableDiv("listPatientTable").show()	
	getTableDiv("listPatientTableWithCheckBox").show()	
	

	$.ajax({
			type: 'GET',
			url:"api/doctors/getClinic/" + user.email,
			complete: function(data)
			{
				doctorClinic = data.responseJSON
				findPatients(data)
			}
	})


	$('#pacientList').click(function(e){
		e.preventDefault()
		showView("showPatientContainer")
        showBread('Lista pacijenata')

	})
	
	$('#examinationStart').click(function(e){
		e.preventDefault()
		showView("showPatientContainerWithCheckBox")
        showBread('Zapocni pregled')
	})

    initCalendarDoc(user)

	$("#workCalendar").click(function(e){
        e.preventDefault()
           showView("showCalendarContainer")
           showBread('Radni kalendar')
     });

    $("#startExamination").click(function(e){
          e.preventDefault()

          setUpCodebooks()
          $("#modalCalendar").modal('toggle')

          showBread('Pregled u toku')
          showView("showExaminationContainer")
          $('#collapseThree').collapse('toggle')
    });

    $('#btnOK').click(function(e){
     		e.preventDefault()

     		$('#modalOK').modal('hide')
     		showView("showCalendarContainer")
            showBread('Radni kalendar')
    })


}

function findPatients(data)
{
	let clinic = data.responseJSON
	$.ajax({
		type: 'GET',
		url: 'api/clinic/getPatients/' + clinic.name,
		complete: function(data)
		{

			let patients = data.responseJSON
			let i = 0
			emptyTable("listPatientTableWithCheckBox")
			emptyTable("listPatientTable")
			for(let u of patients)
            {
				listPatient(u,i);
				listPatientWithCheckBox(u,i,patients.length);
				i++;
            }

		}
	})
}


function listPatientWithCheckBox(data,i,patientsCount)
{
	let d = [data.name,data.surname,data.email,data.phone,data.address,data.city,data.state,data.insuranceId,"<input type='checkbox' id='checkPatient"+i+"'><label for='checkPatient"+i+"'></label>"]
	insertTableData("listPatientTableWithCheckBox",d)
	
	
	$('#checkPatient'+i).click(function(e){
		
		let flag = false
		
		for(let j = 0 ; j < patientsCount ; j++)
		{
			if($("#checkPatient"+j).is(":checked"))
			{
				flag = true;
				$('#patientStartExamin').text("Ime i prezime pacijenta: " + data.name + " " + data.surname)
				$('#patientEmailStartExamin').text("Email: " + data.email)
				$('#patientPhoneStartExamin').text("Telefon: " + data.phone)
				$('#patientAddressStartExamin').text("Prebivaliste: " + data.address + ", " + data.city + ", " + data.state)
				$('#doctorStartExamin').text("Ime i prezime lekara: " + user.name + " " + user.surname)
				$('#clinictartExamin').text("Klinika: " + doctorClinic.name)
				setUpDrugs()
				setUpDiagnosis()
				setUpHall()
				
				
			}
			
		}
		
		if(flag == false)
		{
			$('#startExamination_btn').prop('disabled',true)
		}
		else
		{
			$('#startExamination_btn').prop('disabled',false)
		}
		
		$('#startExamination_btn').click(function(e){
			e.preventDefault()
			showView("showStartExaminationContainer")
			showBread('Pregled u toku ')
		})
		
		if(patientsCount <= 1)
		{		
			return
		}
	
	
		for(let j = 0 ; j < patientsCount ; j++)
		{
			if(j == i)
			{
				$("#checkPatient"+j).prop('checked',true)
			}
			else
			{
				$("#checkPatient"+j).prop('checked',false)
			}
		}
		
		
	})
}

function listPatient(data,i)
{
	let d = [data.name,data.surname,data.email,data.phone,data.address,data.city,data.state,data.insuranceId]
	insertTableData("listPatientTable",d)
}


function initCalendarDoc(user)
{

        var calendarButton = document.getElementById('calendarButton');
                  var calendarEl = document.getElementById('calendar');

                  var calendar = new FullCalendar.Calendar(calendarEl, {
                  plugins: [ 'interaction', 'dayGrid', 'timeGrid', 'monthGrid', 'timeline' ],
                  defaultView: 'dayGridMonth',
                  defaultDate: '2020-01-01',
                  buttonText: {
                         today:    'danas',
                         month:    'mesec',
                         week:     'nedelja',
                         day:      'dan',
                         list:     'list'
                       },
                  monthNames: ['Januar', 'Februar', 'Mart', 'April', 'Maj', 'Jun', 'Jul',
                                'Avgust', 'Septembar', 'Oktobar', 'Novembar', 'Decembar'],
                  monthNamesShort: ['Jan','Feb','Mar','Apr','Maj','Jun','Jul','Avg','Sep','Okt','Nov','Dec'],
                  dayNames: ['Nedelja','Ponedeljak','Utorak','Sreda','Cetvrtak','Petak','Subota'],
                  dayNamesShort: ['Ned','Pon','Uto','Sre','Cet','Pet','Sub'],

                  selectable:true,

                  eventClick: function(info)
                  {
                      var type
                      if(info.event.extendedProps.type == 'Surgery'){
                            type = 'Operacija'
                       }else if (info.event.extendedProps.type == 'Examination'){
                            type = 'Pregled'
                       }

                       var sd=info.event.start
                           sd=formatDateHours(sd)

                        $.ajax({
                                type: 'GET',
                                url: 'api/users/getUser/' + info.event.extendedProps.patientEmail,
                                complete: function(data){
                                   var patient = data.responseJSON
                                   var patientName = patient.name
                                   var patientSurname = patient.surname
                                    $("#patientId").text('Pacijent: ' + patientName + ' ' + patientSurname + ' ' + info.event.extendedProps.patientEmail);
                                    $("#patientExamin").text('Pacijent: ' + patientName + ' ' + patientSurname);
                                    $("#patientExaminEmail").text(info.event.extendedProps.patientEmail)
                                },

                         })

                      $("#durationId").text('Trajanje: ' + info.event.extendedProps.duration + 'h');
                      $("#typeId").text('Tip pregleda: ' + type);
                      $("#clinicId").text('Klinika: ' + info.event.extendedProps.clinicName);
                      $("#hallId").text('Broj sale: ' + info.event.extendedProps.hallNumber);
                      $("#startId").text('Pocetak: ' + sd);

                      getAppointment(info.event.extendedProps.clinicName ,sd, info.event.extendedProps.hallNumber, user)

                      $('#modalCalendar').modal('show');
                      },
                  header: {
                    left: 'prev,next today',
                    center: 'title',
                    right: 'dayGridMonth,timeGridWeek,timeGridDay'
                  },
                  fixedWeekCount: false,
                  contentHeight: 650,
                  views: {
                      timelineCustom: {
                          type: 'timeline',
                          buttonText: 'year',
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
                   eventSources: [
                         {
                           url: 'api/appointments/doctor/getAllAppointments/'+user.email,
                           method: 'GET',
                           extraParams: {

                           },

                           failure: function() {
                             alert('there was an error while fetching events!');
                           },

                         },
                       ]
                });

         calendar.render();
         $('#workCalendar').click(function(){
              showView("showCalendarContainer")
              calendar.render();
         });


}

function formatDateHours (dateObj) {
      var date = new Date(dateObj);
      var hours = date.getHours();
      var minutes = date.getMinutes();
      minutes = minutes < 10 ? '0'+minutes : minutes;
      var strTime = hours + ':' + minutes;
      var month = date.getMonth()+1
      return date.getDate() + "-" + month+ "-" + date.getFullYear() + " " + strTime;
}

function setUpDrugs(){
	$.ajax({
        type: 'GET',
        url:"api/drug/getAllDrugs",
        complete: function(data)
        {
        	drugs = data.responseJSON
            $('#selectDrugStartExamin').empty()
				for(let d of drugs)
				{
					$('#selectDrugStartExamin').append($('<option>',{
						value: d.name,
						text: d.name
					}))
								
				}
        }
    })
	
}

function setUpHall(){
	$.ajax({
		type: 'GET',
		url: 'api/hall/getAllByClinic/'+doctorClinic.name,
		complete: function(data)
		{
			halls = data.responseJSON
			let i = 0
			$('#selectHallStartExamin').empty()
			for(let h of halls)
            {
				$('#selectHallStartExamin').append($('<option>',{
					value: h.name,
					text: h.name
				}))	
            
            }
		}
	})
}

function setUpDiagnosis(){
	$.ajax({
        type: 'GET',
        url:"api/diagnosis/getAllDiagnosis",
        complete: function(data)
        {
           let select = $('#selectDiagnosisStartExamin').val()

           			$.each(data.responseJSON, function (i, item) {
           			    $('#selectDiagnosisStartExamin').append($('<option>', {
           			        value: item.name,
           			        text : item.name
           			    }));
           			});
        }
    })
	
}

function setUpCodebooks(){

    $.ajax({
        type: 'GET',
        url:"api/drug/getAllDrugs",
        complete: function(data)
        {
           let select = $('#selectDrug').val()
           $('#selectDrug').empty()
           			$.each(data.responseJSON, function (i, item) {
           			    $('#selectDrug').append($('<option>', {
           			        value: item.name,
           			        text : item.name
           			    }));
           			});
           			$('.selectpicker').selectpicker('refresh');
        }
    });


     $.ajax({
            type: 'GET',
            url:"api/diagnosis/getAllDiagnosis",
            complete: function(data)
            {
               let select = $('#selectDiagnosis').val()
               $('#selectDiagnosis').empty()
               			$.each(data.responseJSON, function (i, item) {
               			    $('#selectDiagnosis').append($('<option>', {
               			        value: item.name,
               			        text : item.name
               			    }));
               			});
               			$('.selectpicker').selectpicker('refresh');
            }
        })
     
}


function getAppointment(clinicName, date, hallNumber, user){

        console.log(date)
        hallNumber = parseInt(hallNumber)

        var appointment
        var patient

        $.ajax({
            type: 'GET',
            url: 'api/appointments/getAppointment/'+clinicName+'/'+date+'/'+hallNumber,
            complete: function(data)
            {
                 appointment = data.responseJSON
                 console.log(appointment)

                 $.ajax({
                        type: 'GET',
                        url: 'api/users/getUser/' + appointment.patientEmail,
                        complete: function(data){
                           patient = data.responseJSON
                           var patientName = patient.name
                           var patientSurname = patient.surname
                           var patientEmail = patient.email
                            $("#patientExamin").text('Pacijent: ' + patientName + ' ' + patientSurname + ' ' + patientEmail);

                            var type
                            if(appointment.type == 'Surgery'){
                                type = 'Operacija'
                             }else if (appointment.type == 'Examination'){
                                type = 'Pregled'
                             }

                            var sd = appointment.date
                                sd = formatDateHours(sd)

                            $("#startExamin").text('Pocetak: ' + sd);
                            $("#typeExamin").text('Tip pregleda: ' + type);
                            $("#clinicExamin").text('Klinika: ' + appointment.clinicName);
                            $("#doctorExamin").text('Doktor: ' + user.name + ' ' + user.surname);
                        },

                 })

                }
        })

        $('#submitReport').off('click')
        $('#submitReport').click(function(e){
            e.preventDefault()

            let drugs = []
            $('#selectDrug option:selected').each(function() {
                drugs.push($(this).val())
            });

            let diagnosis = []
            $('#selectDiagnosis option:selected').each(function() {
                diagnosis.push($(this).val())
            });

           let description = $('#description').val()
           let report = $('#report').val()

           let today = new Date();

           if(!drugs == [] && !description == '')
           {
           		$('#prescriptionLabel').show()
           		$('#reportLabel').hide()
           } else {
                $('#reportLabel').show()
                $('#prescriptionLabel').hide()
           }

           flag = true
           if(report == ""){
                var input = $('#report')
                input.addClass('is-invalid')
                flag = false
           } else {
                var input = $('#report')
                input.removeClass('is-invalid')
           }


           if(flag == false){
                return
           }

                let prescriptionDTO = {"description":description,"drugs":drugs,"nurse":"","isValid":false, "validationDate":""}
                let prescription = JSON.stringify({"description":description,"drugs":drugs,"nurse":"","isValid":false, "validationDate":""})
                let reportJson = JSON.stringify({"description":report,"diagnosis":diagnosis,"doctorEmail":user.email,"clinicName":appointment.clinicName,"dateAndTime":today,"patient":patient.email,"prescription":prescriptionDTO})

           console.log(reportJson)

                $.ajax({
                    type:'POST',
                    url:'api/users/patient/addPatientMedicalReport/' + patient.email,
                    data: reportJson,
                    dataType : "json",
                    contentType : "application/json; charset=utf-8",
                    complete: function(data)
                    {
                         if(data.status == "201")
                          {
                               $('#modalOK').modal('show')
                          }
                    }
                })
        })


}