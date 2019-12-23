/**
 *
 */
//ULAZNA FUNKCIJA
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

	$.ajax({
			type: 'GET',
			url:"api/doctors/getClinic/" + user.email,
			complete: function(data)
			{
				findPatients(data)

			}
	})


	$('#pacientList').click(function(e){
		e.preventDefault()

		$("#addHallContainer").hide()
		$("#showHallContainer").hide()
		$("#changeHallContainer").hide()
		$("#showUserContainer").show()

	})

    initCalendarDoc(user)

	$("#workCalendar").click(function(e){
        e.preventDefault()

           $('#breadcrumbCurrPage').removeAttr('hidden')
           $('#breadcrumbCurrPage').text("Radni kalendar")
           $('#breadcrumbCurrPage2').attr('hidden',true)
           $('#showPatientsContainer').hide()
           $("#showUserContainer").hide()
           $('#showCalendarContainer').show()

     });

     $("#startExamination").click(function(e){
         		e.preventDefault()

                 setUpCodebooks()

                 $('#breadcrumbCurrPage2').removeAttr('hidden')
                 $('#breadcrumbCurrPage2').text("Pregled u toku")
              //   $('#breadcrumbCurrPage2').attr('hidden',true)
                 $("#modalCalendar").modal('toggle')

                 $('#showPatientsContainer').hide()
                 $("#showUserContainer").hide()
         		 $('#showCalendarContainer').hide()
         		 $('#showExaminationContainer').show()

              //   $('select').selectpicker();
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
                              window.location.href = "index.html"
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
			$('#tableUsers tbody').empty()
			for(let u of patients)
            {
				listPatient(u,i);
				i++;
            }

		}
	})
}

function listPatient(data,i)
{
	let tr=$('<tr></tr>');
	let tdName=$('<td class="number" data-toggle="modal" data-target="#exampleModalLong">'+ data.name +'</td>');
	let tdSurname=$('<td class="clinic" data-toggle="modal" data-target="#exampleModalLong">'+ data.surname +'</td>');
	let tdEmail=$('<td class="clinic" data-toggle="modal" data-target="#exampleModalLong">'+ data.email +'</td>');
	let tdPhone=$('<td class="clinic" data-toggle="modal" data-target="#exampleModalLong">'+ data.phone +'</td>');
	let tdAddress=$('<td class="clinic" data-toggle="modal" data-target="#exampleModalLong">'+ data.address +'</td>');
	let tdCity=$('<td class="clinic" data-toggle="modal" data-target="#exampleModalLong">'+ data.city +'</td>');
	let tdState=$('<td class="clinic" data-toggle="modal" data-target="#exampleModalLong">'+ data.state +'</td>');


	tr.append(tdName).append(tdSurname).append(tdEmail).append(tdPhone).append(tdAddress).append(tdCity).append(tdState)
	$('#tableUsers tbody').append(tr);



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

function setUpCodebooks(){
    alert('test2');
    $.ajax({
        type: 'GET',
        url:"api/drug/getAllDrugs",
//        data: {
//                    'memberId': value
//                },
        complete: function(data)
        {
            $('#selectDrug').multiselect('destroy')
            $('#selectDrug').empty()
            $('#selectDrug')
                 .append($("<option></option>")
                            .attr("value","proba")
                            .text("proba"));

            $.each(data.responseJSON, function(key, value) {
                console.log("key",key)
                console.log("value", value)
                 $('#selectDrug')
                     .append($("<option></option>")
                                .attr("value",key)
                                .text(value.name));

            });

            $('#selectDrug').multiselect();


        }
    });


     $.ajax({
            type: 'GET',
            url:"api/diagnosis/getAllDiagnosis",
            complete: function(data)
            {
               let select = $('#selectDiagnosis').val()

               			$.each(data.responseJSON, function (i, item) {
               			    console.log(item)
               			    console.log(item.name)
               			    $('#selectDiagnosis').append($('<option>', {
               			        value: item.name,
               			        text : item.name
               			    }));
               			});
            }
        })
}