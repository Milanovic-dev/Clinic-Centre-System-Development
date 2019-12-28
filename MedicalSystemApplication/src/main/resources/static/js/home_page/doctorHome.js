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

	})
	
	$('#examinationStart').click(function(e){
		e.preventDefault()
		showView("showPatientContainerWithCheckBox")
		
	})

    initCalendarDoc(user)

	$("#workCalendar").click(function(e){
        e.preventDefault()

           $('#breadcrumbCurrPage').removeAttr('hidden')
           $('#breadcrumbCurrPage').text("Radni kalendar")
           $('#breadcrumbCurrPage2').attr('hidden',true)
           $('#showPatientsContainer').hide()
           $("#showUserContainer").hide()
           $('#showExaminationContainer').hide()
           $('#showCalendarContainer').show()

     });

     $("#startExamination").click(function(e){
         		e.preventDefault()

                 setUpCodebooks()

                 $('#breadcrumbCurrPage2').removeAttr('hidden')
                 $('#breadcrumbCurrPage2').text("Pregled u toku")
                 $("#modalCalendar").modal('toggle')

                 $('#showPatientsContainer').hide()
                 $("#showUserContainer").hide()
         		 $('#showCalendarContainer').hide()
         		 $('#showExaminationContainer').show()

                 $('#collapseThree').collapse('toggle')

     });


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
           let patEmail = $('#patientExaminEmail').text()

           let clinicNameExamin = $('#clinicExamin').text()
           let res = clinicNameExamin.split(": ")
           let cname = res[1]
           let today = new Date();

           let prescriptionDTO = {"description":description,"drugs":drugs,"nurse":"","isValid":false, "validationDate":""}
           let prescription = JSON.stringify({"description":description,"drugs":drugs,"nurse":"","isValid":false, "validationDate":""})
           let reportJson = JSON.stringify({"description":report,"diagnosis":diagnosis,"doctorEmail":user.email,"clinicName":cname,"dateAndTime":today,"patient":patEmail,"prescription":prescriptionDTO})
           console.log(reportJson)
           $('#submitReportSpinner').show()

                $.ajax({
                    type:'POST',
                    url:'api/users/patient/addPatientMedicalReport/' + patEmail,
                    data: reportJson,
                    dataType : "json",
                    contentType : "application/json; charset=utf-8",
                    complete: function(data)
                    {
                        $('#submitReportSpinner').hide()
                         if(data.status == "201")
                          {
                              alert('kreiran izvestaj')
                          }
                    }
                })





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
                  defaultDate: '2019-12-07',
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


                      console.log(info.event.extendedProps)
                      $("#durationId").text('Trajanje: ' + info.event.extendedProps.duration + 'h');
                      $("#typeId").text('Tip pregleda: ' + type);
                      $("#clinicId").text('Klinika: ' + info.event.extendedProps.clinicName);
                      $("#hallId").text('Broj sale: ' + info.event.extendedProps.hallNumber);
                      $("#startId").text('Pocetak: ' + sd);

                      //podaci u izvestaju
                      $("#startExamin").text('Pocetak: ' + sd);
                      $("#typeExamin").text('Tip pregleda: ' + type);
                      $("#clinicExamin").text('Klinika: ' + info.event.extendedProps.clinicName);
                      $("#doctorExamin").text('Doktor: ' + user.name + ' ' + user.surname);

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
              calendar.render();
         });


}

function formatDateHours (dateObj) {
      var date = new Date(dateObj);
      var hours = date.getHours();
      var minutes = date.getMinutes();
      var ampm = hours >= 12 ? 'pm' : 'am';
      hours = hours % 12;
      hours = hours ? hours : 12; // the hour '0' should be '12'
      minutes = minutes < 10 ? '0'+minutes : minutes;
      var strTime = hours + ':' + minutes + ' ' + ampm;
      var month = date.getMonth()+1
      return date.getDate() + "." + month+ "." + date.getFullYear() + ".   " + strTime;
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