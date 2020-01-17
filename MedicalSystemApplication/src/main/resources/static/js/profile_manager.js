

function getPageURLWithUser(page, email)
{
	console.log(page + ".html?u=" + email)
	return page + ".html?u=" + email
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
		
			done(user)

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