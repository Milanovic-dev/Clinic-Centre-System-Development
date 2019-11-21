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


$(document).ready(()=>{

      let request1;
      let request2;
      let request3;
      request1 = "mia@gmail.com"
      request2 = "milana@gmail.com"
      request3 = "nikola@gmail.com"
      addRequest(request1);
      addRequest(request2);
      addRequest(request3);


    $.get({
          url: '/api/auth/getAllRegRequest',
          contentType: 'application/json',
          success: function(requests)
          {
               window.location='./registerRequests.html';
               $('#tableRequests tbody').html('');
               console.log(requests);
               for(let req of requests)
                    {
                      addRequest(req);
                    }
           }
           });


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
        			url:'/api/auth/confirmRegister/email',
        			contentType : "application/json; charset=utf-8",
        			complete: function(data)
        			{
        				console.log(data.status)

        				if(data.status == "200")
        				{
        					window.location.href = "registerRequests.html"
        				}
        			}
        		})

    }
}
