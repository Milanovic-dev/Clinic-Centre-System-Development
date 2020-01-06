

function getProfileURL(email)
{
	return "userProfileNew?u=" + email
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
				console.error("user is undefined")
			}
			else
			{
				done(user)
			}
		}
			
		
	})
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