/**
 *
 */
//ULAZNA FUNKCIJA
var nurseClinic = null;

function initNurse(user)
{

	$.ajax({
		type: 'GET',
		url: 'api/clinic/getNurse/' + user.email,
		complete: function(data)
		{
			nurseClinic = data.responseJSON
			setUpPageNurse(nurseClinic,user)
		}
		
	})
}


function setUpPageNurse(nurseClinic,user)
{
	let sideBar = $("#sideBar")
	sideBar.append("<li class='nav-item active'><a class='nav-link' href='userProfileNew.html'><span id='profileUser'>Profil</span></a></li>")
	sideBar.append("<li class='nav-item active' style='cursor:pointer'><a class='nav-link'><span id='patientList'>Lista pacijenata</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' href='#'><span id='workCalendar'>Radni kalendar</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' href='#'><span id='vacationRequest'>Zahtev za odustvo</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' href='#'><span id='prescriptionAuth'>Overa recepata</span></a></li>")

    clearViews()
    addView('showCalendarContainer')
    addView('showPatientsContainer')
    addView('showPrescriptionAuthContainer')
    addView("showVacationRequestContainer")

    let patientSearch = new TableSearch()
	patientSearch.input("<input class='form-control' type='text' placeholder='Ime pacijenta' id='patientNameLabelNurse'>")
	patientSearch.input("<input class='form-control' type='text' placeholder='Prezime pacijenta' id='patientSurnameLabelNurse'>")
	patientSearch.input("<input class='form-control' type='text' placeholder='Broj osiguranika' id='patientIdLabelNurse'>")
    
	let headersPatients = ["Ime","Prezime","Email","Telefon","Adresa","Grad","Drzava","Broj zdravstvenog osiguranja"]
	createDataTable("listPatientsTable","showPatientsContainer","Lista pacijenata",headersPatients,0)
	getTableDiv("listPatientsTable").show()
	
		insertSearchIntoTable("listPatientsTable",patientSearch,function(){
		pname = $('#patientNameLabelNurse').val()
		psurname = $('#patientSurnameLabelNurse').val()
		pid = $('#patientIdLabelNurse').val()
		let json = JSON.stringify({"name": pname,"surname": psurname,"insuranceId":pid })
		$.ajax({
			type: 'POST',
			url: 'api/clinic/getPatientsByFilter/' + nurseClinic.name,
			data: json,
			dataType: "json",
			contentType : "application/json; charset=utf-8",
			complete: function(data)
			{
				let patients = data.responseJSON
				emptyTable("listPatientsTable")
				for(p of patients)
				{
					listPatientNurse(p)
					
				}
			}
			
		})
		
	})

    var bc1 = new BreadLevel()
    bc1.append('Lista pacijenata')
    var bc2 = new BreadLevel()
    bc2.append('Radni kalendar')
    var bc3 = new BreadLevel()
    bc3.append('Overa recepata')
    var bc4 = new BreadLevel()
    bc4.append('Zahtev za odsustvo')

    initBreadcrumb([bc1,bc2,bc3,bc4])

    let preHeaders = ["Terapija","Lekovi",""]
    let handle = createTable("tablePrescriptions","Lista recepata",preHeaders)
    insertTableInto("showPrescriptionAuthContainer",handle)
    getTableDiv("tablePrescriptions").show()
    
	pageSetUp(user, nurseClinic)
    initCalendarNurse(user)
    getPrescriptions(user)

}



function pageSetUp(user, nurseClinic)
{

	$("#patientList").click(function(e){
		e.preventDefault()

        showView('showPatientsContainer')
        showBread('Lista pacijenata')

	})


	$("#workCalendar").click(function(e){
		e.preventDefault()

        showView('showCalendarContainer')
        showBread('Radni kalendar')

    });

    $("#prescriptionAuth").click(function(e){
        e.preventDefault()

        showView('showPrescriptionAuthContainer')
        showBread('Overa recepata')

    });

    $("#vacationRequest").click(function(e){
        e.preventDefault()

        showView('showVacationRequestContainer')
        showBread('Zahtev za odsustvo')
        addVacationRequestForNurse(user)
    });

}

function initCalendarNurse(user)
{

        var calendarButton = document.getElementById('calendarButton');
                  var calendarEl = document.getElementById('calendar');

                  var calendar = $('#calendar').fullCalendar({
                  defaultDate: '2020-02-01',
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


                  selectable: true,
                  eventRender: function(event, element) {
                      if(event.uid == 1){
                          element.find('.fc-title').append("Godisnji odmor");
                          element.find('.fc-time').hide();
                          $(element).tooltip({title: "od " + dateFormat(event.start) + " do " + dateFormat(event.end), container: "body"})
                      }

                  },

                  eventClick: function(info)
                  {
                        console.log(info)
                      if(info.uid == 1){
                        return
                      }

                      var type
                      if(info.type == 'Surgery'){
                            type = 'Operacija'
                       }else if (info.type == 'Examination'){
                            type = 'Pregled'
                       }

                      var sd = info.start._i
                           sd = formatDateHours(sd)


                      $.ajax({
                                type: 'GET',
                                url: 'api/users/getUser/' + info.patientEmail,
                                complete: function(data){
                                   var patient = data.responseJSON
                                   var patientName = patient.name
                                   var patientSurname = patient.surname
                                    $("#patientId").text('Pacijent: ' + patientName + ' ' + patientSurname + ' ' + info.patientEmail);
                                    $("#patientExamin").text('Pacijent: ' + patientName + ' ' + patientSurname);
                                    $("#patientExaminEmail").text(info.patientEmail)
                                },

                      })

                      $("#durationId").text('Trajanje: ' + info.duration + 'h');
                      $("#typeId").text('Tip pregleda: ' + type);
                      $("#clinicId").text('Klinika: ' + info.clinicName);
                      $("#hallId").text('Broj sale: ' + info.hallNumber);
                      $("#startId").text('Pocetak: ' + sd);

                      getAppointment(info.clinicName ,sd, info.hallNumber, user)

                      $('#modalCalendar').modal('show');


                          if(!info.done)
                          {
                             $('#modalCalendar').modal('show');
                             $('#startExamination').show();
                          }
                          else
                          {
                              $('#modalCalendar').modal('show');
                              $('#startExamination').hide();
                          }

                      },
                      header: {
                          right: 'agendaDay,agendaWeek,month,timelineCustom',
                          center: 'title',
                          left: 'prev,today,next'
                      },

                  fixedWeekCount: false,
                  timeFormat: 'HH:mm',
                  contentHeight: 550,
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

                  eventSources: [
//                         {
//                          url: 'api/vacation/getAllVacationsByUser/'+user.email,
//                           method: 'GET',
//                           color: '#2f989d',
//                           textColor: 'black',
//                           failure: function() {
//                             alert('there was an error while fetching events!');
//                           },
//                           success: function(data){
//                                console.log(data)
//                           },
//
//                         },

                         {
                            url: 'api/vacation/getAllVacationsByUser/'+user.email,
                            method: 'GET',
                            color: '#f4a896',
                            textColor: 'black',
                            failure: function() {
                              alert('there was an error while fetching vac events!');
                            },
                             complete: function(data)
                            {
                            $('#calendar').fullCalendar('removeEvents')
                                  $.each(data.responseJSON, function(i, item){

                                      let event = {
                                              start: item.startDate,
                                              end: item.endDate,
                                              stick: true,
                                              color: '#f4a896',
                                              textColor: 'black',
                                              uid: 1,
                                      }

                                     $('#calendar').fullCalendar('renderEvent', event, true)

                                  })

                            }
                          },
                       ],
                    schedulerLicenseKey: 'GPL-My-Project-Is-Open-Source'
                });

         $('#workCalendar').click(function(){
              showView("showCalendarContainer")
         });


}

function listPatientNurse(data)
{
	let d = [data.name,data.surname,getProfileLink(data.email),data.phone,data.address,data.city,data.state,data.insuranceId]
	insertTableData("listPatientsTable",d)
}



function getPrescriptions(user){

    $.ajax({
			type: 'GET',
			url: 'api/prescription/getAllPrescriptions',
			complete: function(data)
			{
				prescriptions = data.responseJSON

				if(prescriptions==''){
				    $('#tablePrescriptions').hide()
				    $('#emptyPrescriptions').show()
				}else{
				    $('#tablePrescriptions').show()
                	$('#emptyPrescriptions').hide()
				}
				let index = 0
				emptyTable("tablePrescriptions")
				for(p of prescriptions)
				{
					addPrescriptionTr(p,index, user)
					index++
				}
			}

		})

}

function addPrescriptionTr(prescription,i, user){

	let data = [ prescription.description,prescription.drugs,'<button type="button" class="btn btn-primary" id="confirmPre'+i+'">Overi</button>']
	insertTableData("tablePrescriptions",data)
	
	$('#confirmPre'+i).click(function(e){
		e.preventDefault()
		console.log(i)
		prescriptionAuth(prescription, user)
	})
}

function prescriptionAuth(prescription, user){
     
        $.ajax({
            type: 'PUT',
            url:'/api/prescription/validate/'+user.email,
            data: JSON.stringify({"description":prescription.description, "id":prescription.id, "drugs":prescription.drugs}),
            dataType: "json",
            contentType : "application/json; charset=utf-8",
            complete: function(data)
            {
                emptyTable("tablePrescriptions")
                getPrescriptions(user)
                showView('showPrescriptionAuthContainer')
            }
        })
     
}


function addVacationRequestForNurse(user)
{
	let selectChanged = function()
	{
		let startDate = $('#startDayInputVacationRequest').val()
		let endDate = $('#endDayInputVacationRequest').val()

		if(startDate == "" || endDate == "")
		{
			$('#vacationRequestSpinner').hide()
			return
		}

		let json = JSON.stringify({"startDate": startDate,"endDate": endDate,"user": user })

		$('#vacationRequestSpinner').show()
		$.ajax({
			type:'POST',
			url: "api/vacation/checkAvailability/" + nurseClinic.name,
			data: json,
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			complete:function(data)
			{
				$('#vacationRequestSpinner').hide()

				$('#submitVacationRequest').prop('disabled', !data.responseJSON)
				if(data.responseJSON == true)
				{
					$('#checkedImageVacationRequest').show()
					$('#uncheckedImageVacationRequest').hide()

				}
				else
				{
					$('#uncheckedImageVacationRequest').show()
					$('#checkedImageVacationRequest').hide()
				}
			}
		})
	}



	$('#startDayInputVacationRequest').datepicker({
		dateFormat: "dd-mm-yyyy",
		onSelect: function(formattedDate, date, inst){
			let endPicker = $('#endDayInputVacationRequest').datepicker().data('datepicker')
			endPicker.update('minDate', date)

			selectChanged()
		}
	})

	$('#endDayInputVacationRequest').datepicker({
		dateFormat: "dd-mm-yyyy",
		onSelect: function(formattedDate, date, inst){
			let startPicker = $('#startDayInputVacationRequest').datepicker().data('datepicker')
			startPicker.update('maxDate', date)
			selectChanged()
		}
	})




	$('#submitVacationRequest').click(function(e){

		e.preventDefault()

		$('#uncheckedImageVacationRequest').hide()
		$('#checkedImageVacationRequest').hide()
		$('#submitVacationRequest').prop('disabled',true)
		let startDate = $('#startDayInputVacationRequest').val()
		let endDate = $('#endDayInputVacationRequest').val()
		let json = JSON.stringify({"startDate": startDate,"endDate": endDate,"user": user })
		showLoading('submitVacationRequest')

		$.ajax({
			type: 'POST',
			url: 'api/vacation/makeVacationRequest/' + nurseClinic.name ,
			data: json,
            dataType: "json",
            contentType : "application/json; charset=utf-8",
			complete: function(data)
			{
				if(data.status!='201')
				{
					warningModal("Greska","Vaš zahtev za godišnjim odmorom ili odsustvom nije uspešno kreiran.Pokušajte ponovo kasnije.")
				}
				else
				{
					warningModal("Uspesno","Vaš zahtev je poslat.")
				}

				hideLoading('submitVacationRequest')
				$('#submitVacationRequest').prop('disabled',true)
			}
		})
	})

}

