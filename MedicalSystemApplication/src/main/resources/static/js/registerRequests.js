function addRequest(request)
{
	console.log(request);

	let tr=$('<tr></tr>');
	let tdEmail=$('<td class="email">'+ request.email +'</td>');
	let tdConfirm=$('<td> <button type="button" class="btn btn-info">Prihvati</button></td>');
	let tdDecline=$('<td> <button type="button" class="btn btn-light">Odbij</button></td>');


	tdConfirm.click(confirmRegister(request));
    tdEmail.click(addPersonalInformations(request));

	tr.append(tdEmail).append(tdConfirm).append(tdDecline);
	$('#tableRequests tbody').append(tr);

}


function getRequests(){
         $.get({
                  url: '/api/auth/getAllRegRequest',
                  contentType: 'application/json',
                  success: function(requests)
                  {
                       $('#tableRequests tbody').html('');
                       console.log(requests);
                       for(let req of requests)
                            {
                              addRequest(req);
                            }
                   }
               });

}

$(document).ready(()=>{

    getRequests();


})



function addPersonalInformations(request)
{
    return function(){
    		$("#pName").text(user.name);
        	$("#pSurname").text(user.surname);
        	$("#pEmail").text(user.email);
        	$("#pPhone").text(user.phone);
        	$("#pCity").text(user.city);
        	$("#pState").text(user.state);
        	$("#pAddress").text(user.address);
    }


}

function confirmRegister(request){
    return function(){

    let email = request.email

        $.ajax({
        			type: 'POST',
        			url:'/api/auth/confirmRegister/'+email,
        			contentType : "application/json; charset=utf-8",
        			complete: function(data)
        			{
        				console.log(data.status)

        				if(data.status == "201")
        				{
        					window.location.href = "registerRequests.html"
        				}
        			}
        		})

    }
}
