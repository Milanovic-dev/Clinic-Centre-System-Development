/**
 * 
 */
//TODO: Resiti id-ove

function createTable(id,name,headers,tableClass)
{
	
	if($('#'+id).length > 0)
	{
		console.error("Cannot create table: "+id+" already exists in current DOM")
		return
	}
	
	let tClass
	
	if(tableClass == undefined)
	{
		tClass = "table table-bordered"
	}
	else
	{
		tClass = tableClass
	}
	
	let tableHTML = '<div class="card mb-3" id = '+id+' style="display:none"><div class="card-header">'+name+'</div><div class="card-body"><div class="table-responsive"><table class="'+tClass+'" id="table_'+id+'" width="100%" cellspacing="0"><thead><tr>'
					
	for(let i = 0 ; i < headers.length ; i++)
	{
		tableHTML += '<th scope="col">'+ headers[i] + '</th>'
	}
	

	tableHTML +='</tr></thead><tbody></tbody></table></div></div></div>'


	return tableHTML	
}


function createDataTable(id,div,name,headers,orderBy)
{
	let handle = createTable(id,name,headers)
	
	insertTableInto(div,handle)
	
	
	$('#table_'+id).DataTable(
	{
		"order":[[orderBy,"desc"]],
		"language": {
			"info": "Prikazano od _START_ do _END_ od _TOTAL_ stavki",
			"infoEmpty": "Prikazano od 0 do 0 od 0 stavki",
			"search": "Filter:",
			"loadingRecords": "&nbsp;",
            "processing": "<div class='spinner'></div>",
			"zeroRecords": "Trenutno ne postoje stavke.",
			"emptyTable" : "Trenutno ne postoje stavke.",
			"lengthMenu": "Prikazi _MENU_ po stranici",
			"paginate":{
				"first":"Prvi",
				"last":"Poslednji",
				"next": "Sledeci",
				"previous": "Prethodni"
			}
		}
	})

}


function insertElementIntoTable(id,element, _class)
{

	let part
	
	if(_class == undefined)
	{
		part = document.getElementById(id).getElementsByClassName("card-body")[0]
	}
	else
	{
		part = document.getElementById(id).getElementsByClassName(_class)[0]
	}

	
	part.innerHTML += element
}

function insertSearchIntoTable(id, search, func)
{
	let inputs = search.getInputs()
	
	let header = document.getElementById(id).getElementsByClassName("card-header")[0]
	
	header.innerHTML += "<br><br><form class='needs-validation' id='form_"+id+"'></form>"
		
	let form = header.getElementsByTagName("form")[0]
	let rowCount = -1
	for(let i = 0 ; i < inputs.length ; i++)
	{
		if(i == 0 || i % 4 == 0)
		{
			form.innerHTML += '<div class="form-group row"></div>'			
			rowCount++
		}
		
		let row = form.getElementsByClassName("form-group row")[rowCount]
		row.innerHTML += "&nbsp&nbsp&nbsp" + inputs[i]
			
	}
	
	form.innerHTML += '<button class="btn btn-primary" id="tableSearch_btn_'+id+'" type="button">Trazi</button>'
	
	let loadedInputs = header.getElementsByTagName("input")
	let loadedSelects = header.getElementsByTagName("select")

	for(let i = 0 ; i < loadedInputs.length ; i++)
	{
		loadedInputs[i].style.width = "20%"
	}
	
	for(let i = 0 ; i < loadedSelects.length ; i++)
	{
		loadedSelects[i].style.width = "20%"
	}
		
	let button = form.getElementsByTagName("button")[0]
	
	button.addEventListener("click",function(e){
		e.preventDefault()
		
		if($.fn.DataTable.isDataTable('#table_'+id))
		{
			$('#table_'+id).DataTable().sort()			
		}


		if(func != undefined)
			func()
	})
}


function getTableSearchButton(id)
{
	return $('#tableSearch_btn_'+id)
}


function setTableClickEvent(id, row, col, func)
{
	let table = getTable(id)
	
	let rowLength = document.getElementById("table_"+id).getElementsByTagName("tr").length - 1
	let colLength = document.getElementById("table_"+id).getElementsByTagName("tr")[row + 1].getElementsByTagName("td").length
	let rowIndex = row + 1	
	let colIndex = col + 1
	
	if($.fn.DataTable.isDataTable('#table_'+id))
	{
		rowLength = $("#table_"+id).children('tbody').children('tr').length
		colLength = $("#table_"+id).children('tbody').children('tr').children('td').length
		colIndex = col + 1
	}
	
	
	if(rowIndex > rowLength)
	{
		console.error("rowIndex out of bounds")
		return
	}
	
	if(colIndex > colLength)
	{
		console.error("colIndex out of bounds: col:"+colIndex + " colLength: " + colLength)
		return
	}
	
	let cols = $('#table_'+id+' tr:nth-child('+ rowIndex +') td:nth-child('+colIndex+')')
	console.log(cols.prop('outerHTML') +" (row: " + rowIndex+ " , col: " + colIndex + " " + func) 
	if(cols == undefined)
	{
		console.error("Row or col index out of bounds")
		return
	}
	
	cols.click(function(e)
	{
		e.preventDefault()
		
		func()
	})
}

function insertTableData(id,data)
{
	if($.fn.DataTable.isDataTable('#table_'+id))
	{
		$('#table_'+id).DataTable().row.add(data).draw()
		return
	}
	
	let tr=$('<tr></tr>')
	
	for(let i = 0 ; i < data.length ; i++)
	{
		tr.append($('<td>'+data[i]+'</td>'))
	}
	
	$('#table_'+id+' tbody').append(tr)
}


function insertTableInto(id,handle)
{
	$('#'+id).append(handle)
}

function getTable(id)
{
	return $('#table_'+id)
}

function getTableDiv(id)
{
	return $('#'+id)
}

function emptyTable(id)
{
	if($.fn.DataTable.isDataTable('#table_'+id))
	{
		$('#table_'+id).DataTable().clear().draw()
	}
	
	$('#table_'+id+' tbody').empty()
}


class TableSearch
{
	constructor()
	{
		this.inputs = []
	}
	
	input(inpt)
	{
		this.inputs.push(inpt)
	}
	
	getInputs()
	{
		return this.inputs
	}
}
