/**
 *
 */
//ULAZNA FUNKCIJA
function initNurse(user)
{
	let sideBar = $("#sideBar")
	sideBar.append("<li class='nav-item active'><a class='nav-link' href='userProfileNew.html'><i class='fas fa-fw fa-tachometer-alt'></i><span id='profileUser'>Profil</span></a></li>")
	sideBar.append("<li class='nav-item active' style='cursor:pointer'><a class='nav-link'><i class='fas fa-fw fa-tachometer-alt'></i><span id='patientList'>Lista pacijenata</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' href='#'><i class='fas fa-fw fa-tachometer-alt'></i><span id='workCalendar'>Radni kalendar</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' href='#'><i class='fas fa-fw fa-tachometer-alt'></i><span id='vacationRequest'>Zahtev za odustvo</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' href='#'><i class='fas fa-fw fa-tachometer-alt'></i><span id='prescriptionAuth'>Overa recepata</span></a></li>")


	pageSetUp(user)
    initCalendar(user)
    initTable(user)
    getPrescriptions(user)

}



function pageSetUp(user)
{

    clearViews()
    addView('showCalendarContainer')
    addView('showPatientsContainer')
    addView('showPrescriptionAuthContainer')

	$("#patientList").click(function(e){
		e.preventDefault()

        showView('showPatientsContainer')
        $('#breadcrumbCurrPage').removeAttr('hidden')
        $('#breadcrumbCurrPage').text("Lista pacijenata")
        $('#breadcrumbCurrPage2').attr('hidden',true)

	})


	$("#workCalendar").click(function(e){
		e.preventDefault()

        showView('showCalendarContainer')

        $('#breadcrumbCurrPage').removeAttr('hidden')
        $('#breadcrumbCurrPage').text("Radni kalendar")
        $('#breadcrumbCurrPage2').attr('hidden',true)

    });

    $("#prescriptionAuth").click(function(e){
        e.preventDefault()

        showView('showPrescriptionAuthContainer')

        $('#breadcrumbCurrPage').removeAttr('hidden')
        $('#breadcrumbCurrPage').text("Overa recepata")
        $('#breadcrumbCurrPage2').attr('hidden',true)

    });

}

function initCalendar(user)
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
                                        $("#patientId").text('Pacijent: ' + patientName + ' ' + patientSurname);
                           		    },

                           	 })




                          console.log(info.event.extendedProps)
                          $("#durationId").text('Trajanje: ' + info.event.extendedProps.duration + 'h');
                          $("#typeId").text('Tip pregleda: ' + type);
                          $("#clinicId").text('Klinika: ' + info.event.extendedProps.clinicName);
                          $("#hallId").text('Broj sale: ' + info.event.extendedProps.hallNumber);
                          $("#startId").text('Pocetak: ' + sd);


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



//           eventRender: function(info) {
//
//            var sd=info.event.start
//                sd=myDateFormatter(sd)
//
//             var tooltip = new Tooltip(info.el, {
//                   title: info.event.title +
//                    '<br>Pocetak: '+ sd +
//                    '<br>Trajanje: ' + info.event.extendedProps.duration +
//                    '<br>Tip pregleda: '+info.event.extendedProps.type +
//                    '<br>Pacijent: '+info.event.extendedProps.patientName +
//                    ' '+ info.event.extendedProps.patientSurname +
//                    '<br>Klinika: '+info.event.extendedProps.clinicName +
//                    '<br>Sala: '+info.event.extendedProps.hallNumber,
//                   html: true,
//                   animated: 'fade',
//                   placement: 'top',
//                   trigger: 'hover',
//                   container: 'body'
//             });
//           },
           eventColor: '#2f989d'
//           eventSources: [
//                 {
//                   url: 'api/appointments/doctor/getAllAppointments/'+user.email,
//                   method: 'GET',
//                   extraParams: {
//
//                   },
//
//                   failure: function() {
//
//                   },
//
//                 },
//
//
//               ]

         });

         calendar.render();
         $('#workCalendar').click(function(){
              calendar.render();
            });


}

function initTable(user){

$.ajax({
			type: 'GET',
			url:"api/users/getAll/Patient",
			complete: function(data)
			{
				let patients = data.responseJSON
                for(p of patients){
                console.log(p)
                }
				$('#tablePatients').DataTable( {
                        data: patients,
                        columns: [
                            { data: "name" },
                            { data: "surname" },
                            { data: "address" },
                            { data: "city" },
                            { data: "state" },
                            { data: "phone" },
                            { data: "email" },
                            { data: "insuranceId" }
                        ]
                    } );

//                    $('#tablePatients_length').hide()
                      $('#tablePatients_info').hide()
//                    $('#tablePatients_length').hide()

			}

		})

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

function getPrescriptions(user){

//    let headersPrescription = ["Terapija"]
//    let handlePrescriptions = createTable("tablePrescriptions","Lista recepata",headersPrescription)
//    insertTableInto("prescriptionsDiv", handlePrescriptions)
//
//    getTableDiv("prescriptionsDiv").show()

    $.ajax({
			type: 'GET',
			url: 'api/prescription/getAllPrescriptions',
			complete: function(data)
			{
				prescriptions = data.responseJSON
				emptyTable("tablePrescriptions")
				for(p of prescriptions)
				{
					addPrescriptionTr(p, user)
				}
			}

		})

}

function addPrescriptionTr(prescription, user){

    let tr=$('<tr></tr>');
	let tdDescription=$('<td>'+ prescription.description +'</td>');
	let tdDrugs=$('<td>'+ prescription.drugs +'</td>');
	let tdConfirm=$('<td> <button type="button" class="btn btn-primary">Overi</button></td>');

	tdConfirm.click(prescriptionAuth(prescription, user));

	tr.append(tdDescription).append(tdDrugs).append(tdConfirm);
	$('#tablePrescriptions tbody').append(tr);
}

function prescriptionAuth(prescription, user){
     return function(){

        $.ajax({
            type: 'PUT',
            url:'/api/prescription/validate/'+user.email,
            data: JSON.stringify({"description":prescription.description, "id":prescription.id, "drugs":prescription.drugs}),
            dataType: "json",
            contentType : "application/json; charset=utf-8",
            complete: function(data)
            {
                $('#tablePrescriptions tbody').html('')
                getPrescriptions()
                clearViews()
                showView('showPrescriptionAuthContainer')
            }
        })

        }
}