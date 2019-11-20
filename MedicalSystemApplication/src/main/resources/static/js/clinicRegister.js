/**
 *
 */


$(document).ready(function(){

	$('#reg_submit').click(function(e){
		e.preventDefault()

		let name = $('#name').val()
       	let address = $('#address').val()
        let city = $('#city').val()
        let state = $('#state').val()
        let description = $('#description').val()

       let data = JSON.stringify({"name":name,"address:":address,"city:":city,"state:":state,"description:":description})
       console.log(data)

		$.ajax({
        			type: 'POST',
        			url:'api/clinic/registerClinic',
        			data: data,
        			dataType : "json",
        			contentType : "application/json; charset=utf-8",
        			complete: function(data)
        			{
        				console.log(data.status)

        				if(data.status == "200")
        				{
        					window.location.href = "clinicCentreAdmin.html"
        				}
        			}
        		})

	})



})