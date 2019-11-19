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


		$.post({
			url:'/api/clinics/registerClinic',
			data: JSON.stringify({name, address, city, state, description}),
			dataType: "json",
			contentType : "application/json; charset=utf-8",
			success: function(data){
                  alert('Uspesna registracija klinike!');
                   window.location.href = "./centreAdminPage.html";
             },
             error: function(){
                alert('Greska!');
			}
		})

	})



})