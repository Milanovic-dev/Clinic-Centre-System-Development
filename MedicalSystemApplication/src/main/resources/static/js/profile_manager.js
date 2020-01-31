

function getPageURLWithUser(page, email)
{
	return page + ".html?u=" + email
}

function getPageURLWithClinic(page, clinicName)
{
	return page + ".html?clinic=" + clinicName
}

function getProfileLink(email)
{
	return "<a href = '" + getPageURLWithUser("userProfileNew",email)+ "'>" + email + "</a>";
}

function getClinicProfileLink(clinic)
{
	return "<a href='"+ getPageURLWithClinic("clinicProfile", clinic) +"'>" + clinic + "</a>"
}

function getDoctorOccupancy(apps)
{
	let listOfDates = []
	$.each(apps, function(i, item){
		let dateTime = "Zauzece("+(i+1)+"): Datum: <b>" +item.date.split(" ")[0] + "</b>,  Vreme: <b>" + item.date.split(" ")[1] + "</b> - <b>" + item.endDate.split(" ")[1] + "</b>\n"
		listOfDates.push(dateTime)
	});
	
	return listOfDates
}

function getProfileFromURL(done)
{
	let url = window.location.href
	let email = getParameterByName("u",url)
	
	$.ajax({
		type: 'GET',
		url: 'api/users/getUser/' + email,
		complete: function(data)
		{
			let user = data.responseJSON
			
			if(user == undefined)
			{
				done(undefined, undefined)
				return
			}
			
			getRoleUser(user.email, user.role, function(myUser){
				
				done(myUser, user.role)
				
			})
			
		}
			
		
	})
}

function checkSession(done)
{
	$.ajax({
		type: 'GET',
		url:"api/auth/sessionUser",
		complete:function(data)
		{
			done(data.resposneJSON != undefined)
		}
	})
}


function getRoleUser(email, role, complete)
{
		$.ajax({
			type:'GET',
			url: "api/users/get"+role+"/" + email,
			complete:function(data)
			{
				complete(data.responseJSON)
			}
		})
}

function validationError(id, errorMessage)
{
	if($('#' + id).next('.invalid-feedback').length <= 0)
	{
		$('#' + id).after('<div class="invalid-feedback">' +errorMessage+ '</div>')
	}
	else
	{
		($('#' + id).next('.invalid-feedback').text(errorMessage))
	}
	$(id).addClass('invalid-feedback')
}

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, '\\$&');
    var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
}