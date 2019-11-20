/**
 *
 */


$(document).ready(function(){

	$('#submitRegister').click(function(e){
		e.preventDefault()

		let name = $('#name').val()
       	let address = $('#address').val()
        let city = $('#city').val()
        let state = $('#state').val()
        let description = $('#description').val()

        if(/^[a-zA-Z]+$/.test(name) == false || name == "")
        		{
        			var nameInput = $('#name')

        			nameInput.addClass('is-invalid')
        			nameInput.removeClass('is-valid')
        			flag = false
        		}
        		else
        		{
        			var nameInput = $('#name')

        			nameInput.addClass('is-valid')
        			nameInput.removeClass('is-invalid')
        		}


        if(city == "")
        		{
        			var input = $('#city')

        			input.addClass('is-invalid')
        			input.removeClass('is-valid')
        		}
        		else
        		{
        			var input = $('#city')

        			input.removeClass('is-invalid')
        			input.addClass('is-valid')
        		}

        		if(address == "")
        		{
        			var input = $('#address')

        			input.addClass('is-invalid')
        			input.removeClass('is-valid')
        		}
        		else
        		{
        			var input = $('#address')

        			input.removeClass('is-invalid')
        			input.addClass('is-valid')
        		}

//        		if(state.selectedIndex==0){
//        		    var input = $('#selectState')
//                    input.addClass('is-invalid')
//                    input.removeClass('is-valid')
//        		} else {
//        		    var input = $('#selectState')
//                    input.removeClass('is-invalid')
//                    input.addClass('is-valid')
//        		}



       let data = JSON.stringify({"name":name,"address":address,"city":city,"state":state,"description":description})
       console.log(data)

		$.ajax({
        			type: 'POST',
        			url:'/api/clinic/registerClinic',
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