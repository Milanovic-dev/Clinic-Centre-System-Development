/**
 *
 */
//ULAZNA FUNKCIJA
function initNurse(user)
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

    var bc1 = new BreadLevel()
    bc1.append('Lista pacijenata')
    var bc2 = new BreadLevel()
    bc2.append('Radni kalendar')
    var bc3 = new BreadLevel()
    bc3.append('Overa recepata')

    initBreadcrumb([bc1,bc2,bc3])

    let preHeaders = ["Terapija","Lekovi",""]
    let handle = createTable("tablePrescriptions","Lista recepata",preHeaders)
    insertTableInto("showPrescriptionAuthContainer",handle)
    getTableDiv("tablePrescriptions").show()
    
	pageSetUp(user)
    initCalendar(user)
    initTable(user)
    getPrescriptions(user)

}



function pageSetUp(user)
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

