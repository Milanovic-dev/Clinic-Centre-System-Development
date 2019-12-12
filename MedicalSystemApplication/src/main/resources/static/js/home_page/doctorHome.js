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

    initCalendar(user)

	$("#workCalendar").click(function(e){
    		e.preventDefault()


            $('#breadcrump').hide()
            $('#showPatientsContainer').hide()
    		$('#showCalendarContainer').show()

        });

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


function initCalendar(user)
{
console.log('INIT CALENDAR')
// $.ajax({
// 			type: 'GET',
// 			url:"api/appointments/doctor/getAllAppointments/"+user.email,
// 			complete: function(data)
// 			{
// 				let appointments = data.responseJSON

                 var calendarButton = document.getElementById('calendarButton');
                  var calendarEl = document.getElementById('calendar');

                  var calendar = new FullCalendar.Calendar(calendarEl, {
                plugins: [ 'interaction', 'dayGrid', 'timeGrid', 'monthGrid', 'timeline' ],
                defaultView: 'dayGridMonth',
                defaultDate: '2019-12-07',
                header: {
                  left: 'prev,next today',
                  center: 'title',
                  right: 'dayGridMonth,timeGridWeek,timeGridDay,timelineCustom'
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
//                events: [
//                  {
//                    title: appointments.appointmentType,
//                    start: appointments.currentDate,
//                    name: appointments.patient.name,
//                    surname: appointments.patient.surname,
//
//                  }
//                ]
                    events: function( start, end, timezone, callback ) { //include the parameters fullCalendar supplies to you!

                      var events = [];
                    $.ajax({
                         url:"api/appointments/doctor/getAllAppointments/"+user.email,
                         type: 'GET',
                         dataType: 'json',
                         data: '',
                         success: function (data) {

                          for(d of data){
                                console.log(d.type)
                                console.log(d.date)
                                console.log(d.clinic)

                                events.push({

                                title: d.type,
                                start: d.date,

                                // end: doc.to_date
                             });
                            }

                            console.log(events)
                         // callback(events); //you have to pass the list of events to fullCalendar!
                        }
                      });
                    },

              });


            calendar.render();
             $('#workCalendar').click(function(){
                  calendar.render();
                });
//        }


}