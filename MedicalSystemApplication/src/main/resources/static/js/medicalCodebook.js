function addDrug(drug)
{
	console.log(drug);

	let tr=$('<tr></tr>');
	let tdCode=$('<td>'+ drug.code +'</td>');
	let tdName=$('<td>'+ drug.name +'</td>');
	let tdEdit=$('<td style="text-align:right;"><button type="button" class="btn btn-link" data-toggle="modal" data-target="#exampleModal" data-whatever="@mdo"><i class="fas fa-edit fa-lg"></i></button></td>');
	let tdDelete=$('<td style="text-align:right;width:10px"><button class="btn btn-link "><i class="fas fa-trash fa-lg"></i></button></td>');

    tdEdit.click(editDrug(drug));
    tdDelete.click(deleteDrug(drug));

	tr.append(tdCode).append(tdName).append(tdEdit).append(tdDelete);
	$('#tableDrugs tbody').append(tr);

}

function getDrugs(){
         $.get({
                  url: '/api/drug/getAllDrugs',
                  contentType: 'application/json',
                  success: function(drugs)
                  {
                       $('#tableDrugs tbody').html('');
                       console.log(drugs);
                       for(let drug of drugs)
                            {
                              addDrug(drug);
                            }
                   }
               });

}

function editDrug(drug){
    $('#updateName').val(drug.name)
    $('#updateCode').val(drug.code)
}

function deleteDrug(drug){
    return function(){
               $.ajax({
        			type: 'DELETE',
        			url: '/api/drug/deleteDrug/'+drug.code,
        			contentType : "application/json; charset=utf-8",
        			success: function(data)
        			{
        			    window.location.href = "medicalCodebook.html"
        			}
        	   })
        	   }
}


$(document).ready(()=>{

    getDrugs();

    $('#add').click(function(e){
    		e.preventDefault()

    		let name = $('#name').val()
           	let code = $('#code').val()

            let flag = true

            if(name == "")
            		{
            			var input = $('#name')

            			input.addClass('is-invalid')
            			flag = false

            		}
            		else
            		{
            			var input = $('#name')

            			input.removeClass('is-invalid')

            		}

            		if(code == "")
            		{
            			var input = $('#code')

            			input.addClass('is-invalid')
                        flag = false
            		}
            		else
            		{
            			var input = $('#code')

            			input.removeClass('is-invalid')

            		}

            	if(flag == false) return

           let data = JSON.stringify({"name":name,"code":code})
           console.log(data)

    		$.ajax({
            			type: 'POST',
            			url:'/api/drug/addDrug',
            			data: data,
            			dataType : "json",
            			contentType : "application/json; charset=utf-8",
            			complete: function(data)
            			{
            				console.log(data.status)

            				if(data.status == "200")
            				{
            					window.location.href = "medicalCodebook.html"
            				}

            				if(data.status == "208"){
            				    $('#add').popover('show')
            				}
            			}
            		})

    	})


    	$('#update').click(function(e){
             e.preventDefault()

    	     let name = $('#updateName').val()
             let code = $('#updateCode').val()
            console.log(name)
            console.log(code)

        let data = JSON.stringify({"name":name,"code":code, "description":""})
		$.ajax({
			type: 'PUT',
			url: 'api/drug/updateDrug/'+code,
			data: data,
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			complete: function(data){
				console.log(data.status)

				if(data.status == "200")
				{
					window.location.href = "medicalCodebook.html"
				}
			}

		})


})

})