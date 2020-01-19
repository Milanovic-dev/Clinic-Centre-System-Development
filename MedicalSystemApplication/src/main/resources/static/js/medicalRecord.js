/**
 * 
 */


$(document).ready(function(){

	
	getProfileFromURL(function(profile)
			{
		
				if(profile == undefined)
				{
					$.ajax({
						type: 'GET',
						url: 'api/auth/sessionUser',
						complete: function(data)
						{
							getMedicalRecord(data.responseJSON,false)
						}
							
					})
				}
				else
				{
					getMedicalRecord(profile,true)
				}
			})
})


function getMedicalRecord(user, foreign)
{
	$.ajax({
		type:'GET',
		url:"api/users/patient/getMedicalRecord/"+user.email,
		complete: function(data)
		{
			let mr = data.responseJSON
			
			initMedicalRecord(mr, user, foreign)
		}
	})

}


function initMedicalRecord(record, user, foreign)
{
	console.log(record)
	$('#userGmail').append(" <b>" + user.email + "</b>")
	$('#userName').append(" <b>" + user.name + "</b>")
	$('#userSurname').append(" <b>" + user.surname + "</b>")
	$('#insuranceMR').text("Broj osiguranika: " + user.insuranceId)
	$('#height').text("Visina: " + record.height)
	$('#weight').text("Tezina: " + record.weight)
	$('#bloodType').text("Krvna grupa: " + record.bloodType)
	
	let alergies = record.alergies
	$('#alergies').empty()
	for(let al of alergies)
	{	
		$('#alergies').append("<div class='col-4 themed-grid-col' >"+al+"</div>")	
	}
	
	createHistoryTable(record.reports)
}


function createHistoryTable(reports)
{
	let headers = ['Tip','Datum','Klinika','Doktor/i','Pacijent','Razlog pregleda','Dijagnoza','Recepti']
	createDataTable("historyTable","history","Istorija pregleda/operacija",headers,0)
	getTableDiv("historyTable").show()
	
	for(report of reports)
	{
		let diagnosis
		
		for(d of diagnosis)
		{
			diagnosis += d+" "
		}
		
		let data = ['Pregled',report.dateAndTime, report.clinicName, report.doctorEmail, report.patientEmail, report.description, diagnosis, report.prescription.description]
		insertTableData("historyTable",data)
	}
	
}

