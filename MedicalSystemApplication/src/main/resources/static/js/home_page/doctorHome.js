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
}