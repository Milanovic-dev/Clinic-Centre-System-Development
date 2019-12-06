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
	sideBar.append("<li class='nav-item active'><a class='nav-link' href='#'><i class='fas fa-fw fa-tachometer-alt'></i><span id='recipeAuth'>Overa recepata</span></a></li>")


	pageSetUp(user)
    initCalendar(user)
    initTable(user)

}

function pageSetUp(user)
{
	$("#patientList").click(function(e){
		e.preventDefault()

        $('#breadcrump').hide()
        $('#showCalendarContainer').hide()
		$('#showPatientsContainer').show()

	})


	$("#workCalendar").click(function(e){
		e.preventDefault()


        $('#breadcrump').hide()
        $('#showPatientsContainer').hide()
		$('#showCalendarContainer').show()

    });

}

function initCalendar(user)
{

        var calendarButton = document.getElementById('calendarButton');
          var calendarEl = document.getElementById('calendar');

          var calendar = new FullCalendar.Calendar(calendarEl, {
            plugins: [ 'interaction', 'dayGrid', 'timeGrid', 'monthGrid', 'timeline' ],
            defaultView: 'dayGridMonth',
            defaultDate: '2019-08-07',
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
            events: [
              {
                title: 'All Day Event',
                start: '2019-08-01'
              },
              {
                title: 'Long Event',
                start: '2019-08-07',
                end: '2019-08-10'
              },
              {
                groupId: '999',
                title: 'Repeating Event',
                start: '2019-08-09T16:00:00'
              },
              {
                groupId: '999',
                title: 'Repeating Event',
                start: '2019-08-16T16:00:00'
              },
              {
                title: 'Conference',
                start: '2019-08-11',
                end: '2019-08-13'
              },
              {
                title: 'Meeting',
                start: '2019-08-12T10:30:00',
                end: '2019-08-12T12:30:00'
              },
              {
                title: 'Lunch',
                start: '2019-08-12T12:00:00'
              },
              {
                title: 'Meeting',
                start: '2019-08-12T14:30:00'
              },
              {
                title: 'Birthday Party',
                start: '2019-08-13T07:00:00'
              },
              {
                title: 'Click for Google',
                url: 'http://google.com/',
                start: '2019-08-28'
              }
            ]
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
				console.log(patients.length)

			//	$('#tablePatients tbody').empty()
//				for(let p of patients)
//				{
//					addPatient(p)
//				}

                console.log(patients)
				$('#tablePatients').DataTable( {
                        data: patients,
                        columns: [
                            { data: "name" },
                            { data: "surname" },
                            { data: "address" },
                            { data: "city" },
                            { data: "state" },
                            { data: "phone" },
                            { data: "email" }
                        ]
                    } );
			}

		})

}


//function addPatient(patient)
//{
//	let tr=$('<tr></tr>');
//	let tdName=$('<td>'+ patient.name +'</td>');
//	let tdSurname=$('<td>'+ patient.surname +'</td>');
//	let tdAddress=$('<td>'+ patient.address +'</td>');
//	let tdCity=$('<td>'+ patient.city +'</td>');
//	let tdState=$('<td>'+ patient.state +'</td>');
//	let tdPhone=$('<td>'+ patient.phone +'</td>');
//	let tdEmail=$('<td>'+ patient.email +'</td>');
////	let tdID=$('<td>'+ patient.insuranceId +'</td>');
//
//
//	tr.append(tdName).append(tdSurname).append(tdAddress).append(tdCity).append(tdState).append(tdPhone).append(tdEmail)
//	$('#tablePatients tbody').append(tr)
//
//}