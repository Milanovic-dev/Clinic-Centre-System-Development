function addRequest(request)
{

	let tr=$('<tr></tr>');
	let tdEmail=$('<td class="email" data-toggle="modal" data-target="#exampleModalLong">'+ request.email +'</td>');
	let tdConfirm=$('<td> <button type="button" id="acceptRequest" class="btn btn-primary">Prihvati</button></td>');
	let tdDeny=$('<td> <button type="button" id="denyRequest" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal" data-whatever="@mdo">Odbij</button></td>');

	tdConfirm.click(confirmRegister(request));
    tdEmail.click(addPersonalInformations(request));
    tdDeny.click(deny(request.email));

	tr.append(tdEmail).append(tdConfirm).append(tdDeny);
	$('#tableRequests tbody').append(tr);

}

function addPersonalInformations(user)
{
    return function(){
    	$("#pName").text("Ime: "+user.name);
        $("#pSurname").text("Prezime: "+user.surname);
        $("#pEmail").text("Email: "+user.email);
        $("#pPhone").text("Telefon: "+user.phone);
        $("#pCity").text("Grad: "+user.city);
        $("#pState").text("Drzava: "+user.state);
        $("#pAddress").text("Adresa: "+user.address);
    }
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





function confirmRegister(request){
    return function(){
    showLoading('acceptRequest')
    let email = request.email

        $.ajax({
        			type: 'POST',
        			url:'/api/auth/confirmRegister/'+email,
        			contentType : "application/json; charset=utf-8",
        			success: function(data)
        			{
        				hideLoading('acceptRequest')
        				window.location.href = "registerRequests.html"
        			}
        		})

    }
}

function denyRegister(){

	 showLoading('denyRequest')
     var email = document.getElementById("recipient-name").value;
     var text = document.getElementById("message-text").value;

     let url = '/api/auth/denyRegister/'
     url+=email+','+text

        $.ajax({
        			type: 'DELETE',
        			url: url,
        			contentType : "application/json; charset=utf-8",
        			success: function(data)
        			{
        				hideLoading('denyRequest')
        			    window.location.href = "registerRequests.html"
        			}
        		})

}

function deny(email){

 var text = document.getElementById("recipient-name");
 text.value = email;
}