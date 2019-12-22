/**
 * 
 */


function createTable(id,name,headers)
{
	if($('#'+id).length > 0)
	{
		console.log("Cannot create table: "+id+" already exists in current DOM")
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


function insertTableData(id,data)
{
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

function getTableDiv(id)
{
	return $('#'+id)
}

function emptyTable(id)
{
	$('#table_'+id+' tbody').empty()
}