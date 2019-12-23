/**
 * 
 */
//TODO: Resiti id-ove

function createTable(id,name,headers)
{
	if($('#'+id).length > 0)
	{
		console.error("Cannot create table: "+id+" already exists in current DOM")
		return
	}
	
	let tableHTML = '<div class="card mb-3" id = '+id+' style="display:none"><div class="card-header">'+name+'</div><div class="card-body"><div class="table-responsive"><table class="table table-bordered" id="table_'+id+'" width="100%" cellspacing="0"><thead><tr>'
					
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
		"order":[[orderBy,"desc"]]
	})
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