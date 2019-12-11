function addDiagnosis(diagnosis)
{

	let tr=$('<tr></tr>');
	let tdCode=$('<td style="width:100px;">'+ diagnosis.code +'</td>');
	let tdTag=$('<td style="width:120px;">'+ diagnosis.tag +'</td>');
	let tdName=$('<td>'+ diagnosis.name +'</td>');
	let tdEdit=$('<td style="text-align:right;"><button type="button" class="btn btn-link" data-toggle="modal" data-target="#exampleModal" data-whatever="@mdo"><i class="fas fa-edit fa-lg"></i></button></td>');
	let tdDelete=$('<td style="text-align:right;width:10px"><button class="btn btn-link "><i class="fas fa-trash fa-lg"></i></button></td>');

    tdEdit.click(editDiagnosis(diagnosis));
    tdDelete.click(deleteDiagnosis(diagnosis));

	tr.append(tdCode).append(tdTag).append(tdName).append(tdEdit).append(tdDelete);
	$('#tableDiagnosis tbody').append(tr);

}



function editDiagnosis(diagnosis){
    $('#updateName').val(diagnosis.name)
    $('#updateCode').val(diagnosis.code)
    $('#updateTag').val(diagnosis.tag)
}

function deleteDiagnosis(diagnosis){
    return function(){
               $.ajax({
        			type: 'DELETE',
        			url: '/api/diagnosis/deleteDiagnosis/'+diagnosis.code,
        			contentType : "application/json; charset=utf-8",
        			success: function(data)
        			{
        			    window.location.href = "diagnosisCodebook.html"
        			}
        	   })
        	   }
}

function getDiagnosis(){
         $.get({
                  url: '/api/diagnosis/getAllDiagnosis',
                  contentType: 'application/json',
                  success: function(diagnosis)
                  {
                       $('#tableDiagnosis tbody').html('');

                       for(let d of diagnosis)
                            {
                              addDiagnosis(d);
                            }
                   }
               });

}



$(document).ready(()=>{

    getDiagnosis();

    $('#add').click(function(e){
    		e.preventDefault()

    		let name = $('#name').val()
           	let code = $('#code').val()
           	let tag = $('#tag').val()

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

            		if(tag == ""){
                        var input = $('#tag')
                         input.addClass('is-invalid')
                         flag = false
                    } else {
                        var input = $('#tag')
                        input.removeClass('is-invalid')
                    }

                    if(flag == false) return

           let data = JSON.stringify({"name":name,"code":code,"tag":tag})
           console.log(data)

    		$.ajax({
            			type: 'POST',
            			url:'/api/diagnosis/addDiagnosis',
            			data: data,
            			dataType : "json",
            			contentType : "application/json; charset=utf-8",
            			complete: function(data)
            			{
            				console.log(data.status)

            				if(data.status == "200")
            				{
            					window.location.href = "diagnosisCodebook.html"
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
             let tag = $('#updateTag').val()
            console.log(name)
            console.log(code)

        let data = JSON.stringify({"name":name,"code":code, "tag":tag})
		$.ajax({
			type: 'PUT',
			url: 'api/diagnosis/updateDiagnosis/'+code,
			data: data,
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			complete: function(data){
				console.log(data.status)

				if(data.status == "200")
				{
					window.location.href = "diagnosisCodebook.html"
				}
			}

		})


})

})