/**
 * 
 */

let searchForms = []

function createSearch(form)
{
	
	let inLg = form.inputs.length
	let laLg = form.labels.length
	
	if(inLg != laLg || inLg == 0 || laLg == 0)
	{
		console.error('Inputs and labels must be the same size and not zero. Found '+inLg+' inputs and '+laLg+' labels')
		return
	}
	
	for(f of searchForms)
	{
		if(f.id == form.id)
		{
			console.error("Form with id: " + id + " already exists.")
			return
		}
	}
	
	let newForm = {
			id: form.id,
			header: form.header,
			inputs: form.inputs,
			labels: form.labels,
			onSubmit: form.onSubmit
	}
	
	searchForms.push(newForm)
}

function appendToSearch(id,_input,_label)
{
	let form = findForm(id)
	
	if(form == undefined)
	{
		console.error("Could not find Search Form with id: "+ id) 
		return
	}
	
	form.inputs.push(_input)
	form.labels.push(_label)
}

function showSearch(id,func)
{
	let form = findForm(id)
		
	if(form == undefined)
	{
		console.error("Could not find Search Form with id: "+ id) 
		return
	}
	console.log(form)
		
	$('#_searchModalLabel').text(form.header)
	$('#_searchModal').modal()
	
	let body = $('#_searchBodyModal')
	body.append('<form id="form_'+form.id+'"></form>')
	let formHTML = $('#form_'+form.id)

	for(let i = 0 ; i < form.inputs.length ; i++)
	{
		let input = form.inputs[i]
		let label = form.labels[i]
		
		appendInput(input,label,formHTML)
		
	}
	
	$('#_searchModalSubmit').click(function(e){
		e.preventDefault()
		
		let prepJSON = {}
		
		for(let i = 0 ; i < form.inputs.length ; i++)
		{
			let input = form.inputs[i]
			
			if(isArray(input))
			{
				for(let j = 0 ; j < input.length ; j++)
				{
					if(isObject(input[j]))
					{
						let type = input[j].type
						let name = input[j].name
						let options = input[j].options
						
						let key = name
						let value = $('#'+name).val()
						prepJSON[key] = value
					}
					else
					{
						let key = input[j]
						let value = $('#'+key).val()
						prepJSON[key] = value						
					}
				}
			}
			else if(isObject(input))
			{
				let type = input.type
				let value = input.value
				let options = input.options
				
				if(type == "select")
				{
					let key = input.name
					let value = $('#'+key).val()
					prepJSON[key] = value
				}
				else if(type == "time")
				{
					let key = input.name
					let value = $('#'+key).val()
					prepJSON[key] = value
				}
			}
			else if(isString(input))
			{
				let key = input
				let value = $('#'+key).val()
				prepJSON[key] = value
			}
			
		}
		
		if(func == undefined)
		{
			if(form.onSubmit != undefined)
			{
				form.onSubmit(prepJSON)					
			}
			else
			{
				console.log("submit has no function")
			}
		}
		else
		{
			func(prepJSON)
		}
	})
	
	
}


function appendInput(input,label,form)
{
	if(isString(input))
	{
		if(isString(label))
		{
			form.append(makeTextInput(input,label))
		}
		else
		{
			console.error('Input and label are not matching types(String)')
		}
	}
	else if(isObject(input))
	{
		let name = input.name
		let type = input.type
		let options = input.options
		
		switch(type)
		{
			case 'text': 
				form.append(makeTextInput(name,label))
				break
				
			case 'select':
				form.append(makeSelectInput(name,label))
				$.each(options, function (i, item) {
               			    $('#'+name).append($('<option>', {
               			        value: item,
               			        text : item
               			    }));
               			});
				break
			
			case 'time':
				form.append(makeTimeInput(name,label))
				if(options != undefined)
				{				
					if(options.length >= 2)
					{
						$('#'+name).prop('min',options[0])
						$('#'+name).prop('max',options[1])
					}
				}				
				break
				
		}
			
	}
	else if(isArray(input))
	{
		if(isArray(label))
		{
			form.append('<div class="row" id="row_id"></div>')
			
			let row = $('#row_id')
			
			for(let i = 0 ; i < input.length ; i++)
			{
				if(isString(input[i]))
				{
					row.append(makeTextInput(input[i],label[i],true))
				}
				else if(isObject(input[i]))
				{
					let name = input[i].name
					let type = input[i].type
					let options = input[i].options
					
					switch(type)
					{
						case 'text': 
							row.append(makeTextInput(name,label[i],true))
							break
							
						case 'select':
							row.append(makeSelectInput(name,label[i],true))
							$.each(options, function (i, item) {
			               			    $('#'+name).append($('<option>', {
			               			        value: item,
			               			        text : item
			               			    }));
			               			});
							break
						
						case 'time':
							row.append(makeTimeInput(name,label[i],true))
							if(options != undefined)
							{				
								if(options.length >= 2)
								{
									$('#'+name).prop('min',options[0])
									$('#'+name).prop('max',options[1])
								}
							}		
							break
							
					}
				}
			}
			
			row.prop('id',"")
		}
		else
		{
			console.error('Input and label are not matching types(Array)')
		}
		
	}

}

function makeTextInput(input,label, short)
{
	if(short == true)
	{
		return '<div class="col"><label for="'+input+'">'+label+'</label><input type="text" class ="form-control" id="'+input+'"></div>'
	}
	
	
	return '<div class="form-group"><label for="'+input+'">'+label+'</label><input type="text" class ="form-control" id="'+input+'"></div>'
}

function makeSelectInput(input,label, short)
{
	if(short == true)
	{
		return '<div class="col"><label for="'+input+'">'+label+'</label><select class="form-control" id="'+input+'"></select></div>'
	}
	
	return '<div class="form-group"><label for="'+input+'">'+label+'</label><select class="form-control" id="'+input+'"></select></div>'
}

function makeTimeInput(input,label, short)
{
	if(short == true)
	{
		return '<div class="col"><label for="'+input+'">'+label+'</label><input type="time" class ="form-control" id="'+input+'"></div>'
	}
	
	return '<div class="form-group"><label for="'+input+'">'+label+'</label><input type="time" class ="form-control" id="'+input+'"></div>'
}

function findForm(id)
{
	for(f of searchForms)
	{
		if(f.id == id)
		{
			return f
		}
	}
	
	return undefined
}

function isArray(obj)
{
    return obj !== undefined && obj !== null && obj.constructor == Array;
}

function isObject(obj)
{
    return obj !== undefined && obj !== null && obj.constructor == Object;
}

function isString(obj)
{
    return obj !== undefined && obj !== null && obj.constructor == String;
}