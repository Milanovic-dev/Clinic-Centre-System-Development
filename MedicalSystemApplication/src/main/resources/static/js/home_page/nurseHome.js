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

   // $('#tablePatients').DataTable();
	pageSetUp(user)





}

function pageSetUp(user)
{
	$("#patientList").click(function(e){
		e.preventDefault()

		$('#showPatientsContainer').show()

		$.ajax({
			type: 'GET',
			url:"api/users/getAll/Patient",
			complete: function(data)
			{
				let patients = data.responseJSON
				console.log(patients.length)

				$('#tablePatients tbody').empty()
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