/**
 * 
 */


$(document).ready(function(){
	
	checkSession(function(exists){
		if(!exists) window.location.href = "index.html"
	})
	
	getProfileFromURL(function(profile)
			{
		
					$.ajax({
						type: 'GET',
						url: 'api/auth/sessionUser',
						complete: function(data)
						{
							let sessionUser = data.responseJSON
							
							if(profile == undefined)
							{
								getMedicalRecord(sessionUser)								
							}
							else
							{
								getMedicalRecord(profile, sessionUser)								
							}
						}
							
					})
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
	let height = record.height == null ? "N/A" : record.height
	let weight = record.weight == null ? "N/A" : record.weight
	let bloodType = record.bloodType == null ? "N/A" : record.bloodType

	$('#userEmail').append(" <b>" + user.email + "</b>")
	$('#userName').append(" <b>" + user.name + "</b>")
	$('#userSurname').append(" <b>" + user.surname + "</b>")
	$('#insuranceMR').text("Broj osiguranika: " + user.insuranceId)
	$('#height').text("Visina: " + height)
	$('#weight').text("Tezina: " + weight)
	$('#bloodType').text("Krvna grupa: " + bloodType)
	
	let alergies = record.alergies
	$('#alergies').empty()
	for(let al of alergies)
	{	
		$('#alergies').append("<div class='col-4 themed-grid-col' >"+al+"</div>")	
	}
	
	
	setupStarRating()
	
	createHistoryTable(record.reports, foreign.email != user.email)
}


function createHistoryTable(reports, foreign)
{
	let headers = ['Tip','Datum','Klinika','Doktor/i','Pacijent','Razlog pregleda','Dijagnoza','Recepti','Dodaj ocenu']
	createDataTable("historyTable","history","Istorija pregleda/operacija",headers,0)
	getTableDiv("historyTable").show()
	
	
	$.each(reports, function(i, item){
		let data = ['Pregled', item.dateAndTime, getClinicProfileLink(item.clinicName), getProfileLink(item.doctorEmail), item.patientEmail, item.description, "", "", "<button class='btn btn-primary' id='review_btn"+i+"'>Oceni...</button>"]
		insertTableData("historyTable",data)
		
		$('#review_btn'+i).prop('disabled', foreign)
		
		$('#review_btn'+i).click(function(e){
			e.preventDefault()
			
			$('#reviewModal').modal('show')
			$('#reviewDoctorEmail').text("Ocenite doktora: " + item.doctorEmail)
			$('#reviewClinicName').text("Ocenite kliniku: " + item.clinicName)
		})
					
			
		$('#submitDoctorReview').off('click')
		$('#submitDoctorReview').click(function(e){
			e.preventDefault()
								
			
			let doctorRating = document.getElementById('doctorStars').getElementsByClassName('checked').length

			let json = JSON.stringify({"rating": doctorRating, "doctorEmail":item.doctorEmail, "patientEmail": item.patientEmail })
				
			showLoading('submitDoctorReview')
				
			$.ajax({
				type:'POST',
				url: "api/doctors/addReview",
				data: json,
				dataType : "json",
				contentType : "application/json; charset=utf-8",
				complete: function(data)
				{
					hideLoading('submitDoctorReview')
						
					if(data.status == "400")
					{
						displayError('submitDoctorReview', "Vec ste ocenili ovog doktora.")
						$('#submitDoctorReview').prop('disabled',true)
					}
					else
					{
						displaySuccess('submitDoctorReview', "Uspesno ste ocenili doktora!")
					}
				}
			})
		})
			
		$('#submitClinicReview').off('click')
		$('#submitClinicReview').click(function(e){
			e.preventDefault()
						
			let clinicRating = document.getElementById('clinicStars').getElementsByClassName('checked').length
			
			let json = JSON.stringify({"rating": clinicRating, "clinicName":item.clinicName, "patientEmail": item.patientEmail })

			showLoading('submitClinicReview')
				
			$.ajax({
				type:'POST',
				url: "api/clinic/addReview",
				data: json,
				dataType : "json",
				contentType : "application/json; charset=utf-8",
				complete: function(data)
				{
					hideLoading('submitClinicReview')
						
					if(data.status == "400")
					{
						displayError('submitClinicReview', "Vec ste ocenili ovu kliniku.")
						$('#submitClinicReview').prop('disabled',true)
					}
					else
					{
						displaySuccess('submitClinicReview', "Uspesno ste ocenili kliniku!")
					}
				}
			})
		})
			
	})
}

function setupStarRating()
{
	
	for(let i = 1 ; i <= 5 ; i++)
	{
		$('#doctorStar'+i).click(function(e){
			
			if($('#doctorStar'+i).hasClass('checked'))
			{
				if(i != 1)
				{
					$('#doctorStar'+i).removeClass('checked')						
				}
				
				for(let j = i ; j <= 5 ; j++)
				{
					if(j != 1)
						$('#doctorStar'+j).removeClass('checked')
				}
			}
			else
			{
				for(let j = i ; j > 1 ; j--)
				{
					$('#doctorStar'+j).addClass('checked')
				}
			}
			
			
		})			
	}
	
	for(let i = 1 ; i <= 5 ; i++)
	{
		$('#clinicStar'+i).click(function(e){
			
			if($('#clinicStar'+i).hasClass('checked'))
			{
				if(i != 1)
					$('#clinicStar'+i).removeClass('checked')
				
				for(let j = i ; j <= 5 ; j++)
				{
					$('#clinicStar'+j).removeClass('checked')
				}
			}
			else
			{
				for(let j = i ; j > 1 ; j--)
				{
					if(j != 1)
						$('#clinicStar'+j).addClass('checked')
				}
			}
			
			
		})			
	}

}

