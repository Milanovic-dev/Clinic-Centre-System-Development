/**
 * 
 */

var containers = []
var linked = []

var breadcrumbs = []

var errorId

function getViews()
{
	return containers
}

function clearViews()
{
	containers = []
	linked = []
}

function addView(id)
{
	if(containers.includes(id))
	{
		console.error("Container with " + id + " is already subscribied")
		return
	}
	
	containers.push(id)	
}

function removeView(id)
{
	arrayRemove(containers,id)
}

function showView(id)
{
	if(!containers.includes(id))
	{
		console.error("Container with " + id + " does not exist!")
		return
	}
	
	
	for(let i = 0 ; i < containers.length ; i++)
	{
		if(containers[i] == id)
		{
			$('#'+id).show()
		}
		else
		{
			$('#'+containers[i]).hide()
		}
	}
	
	
	for(let i = 0 ; i < linked.length ; i++)
	{
		if(linked[i][0] == id)
		{
			$('#'+linked[i][1]).show()
		}
	}

}

function displayError(id,text)
{
	let button = $("#"+id)
	
	let spanId = id + "_mesSpan"
	if($('#'+spanId).length > 0)
	{
		$('#'+spanId).text(text)
		$('#'+spanId).css("color","#FF1103")
		return
	}
	
	button.before('<span style="color:#FF1103" id="'+spanId+'">'+text+'</span><br>')
}

function displaySuccess(id, text)
{
let button = $("#"+id)
	
	let spanId = id + "_mesSpan"
	if($('#'+spanId).length > 0)
	{
		$('#'+spanID).text(text)
		$('#'+spanID).css("color","#4BB543")
		return
	}
	
	button.before('<span style="color:#4BB543" id="'+spanId+'">'+text+'</span><br>')
}

function warningModal(header, content)
{
	$('#warningModal').modal('show')
	$('#warningModalLabel').text(header)
	$('#warningBodyModal').text(content)
}


function showLoading(button)
{
	let buttonElem;
	
	if(isString(button))
	{
		buttonElem = $('#'+button)
	}
	else
	{
		buttonElem = button
	}
	
	buttonElem.append('<span class="spin">&nbsp<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span></span>')
	buttonElem.prop('disabled', true)
}


function hideLoading(button)
{
	let buttonElem;
	
	if(isString(button))
	{
		buttonElem = $('#'+button)
	}
	else
	{
		buttonElem = button
	}
	
	buttonElem.children(".spin").remove()
	buttonElem.prop('disabled', false)
}


function validation(element, condition, errorMessage)
{
	let elem
	
	if(isString(element))
	{
		elem = $('#'+element)	
	}
	else
	{
		elem = element
	}
	
	
	let parent = elem.parent()
	
	
	if(parent.children('.invalid-feedback').length == 0)
	{
		if(errorMessage == ""){
			elem.append('<div class="invalid-feedback">'+errorMessage+'</div>')
		}
		else
		{
			elem.after('<div class="invalid-feedback">'+errorMessage+'</div>')			
		}
	}
	
	
	if(parent.children('.valid-feedback').length == 0)
	{
		if(errorMessage == "")
		{
			elem.append('<div class="valid-feedback">Izgleda dobro!</div>')
		}
		else
		{
			elem.after('<div class="valid-feedback">Izgleda dobro!</div>')			
		}
	}
	
	if(!condition)
	{
		elem.removeClass('is-invalid')
		elem.addClass('is-valid')	
		return true
	}
	else
	{
		elem.addClass('is-invalid')	
		elem.removeClass('is-valid')
		return false
	}
}


function hideValidation(element){
	
}

function hideView(id)
{
	$('#'+id).hide()
	
	for(let i = 0 ; i < linked.length ; i++)
	{
		if(linked[i][0] == id)
		{
			$('#'+linked[i][1]).hide()
		}
	}
}


function createViewLink(id1,id2)
{
	let link = [id1,id2]
	linked.push(link)
}


function arrayRemove(arr, value) 
{

	   return arr.filter(function(ele){
	       return ele != value;
	   });

}

class BreadLevel
{
	constructor()
	{
		this.levels = []
	}
	
	append(name)
	{
		this.levels.push(name)
		
		return this
	}
	
	getLevels()
	{
		return this.levels
	}
	
	print()
	{
		console.log(this.levels)
	}
}


function initBreadcrumb(levels)
{
	breadcrumbs = levels
}

function showBread(name)
{
	let bc
	
	for(let b of breadcrumbs)
	{
		let levels = b.getLevels()
		
		for(let level of levels)
		{
			if(name == level)
			{
				bc = b
				break
			}
		}
	}
	
	let breadcrumb= $('#breadcrumb')
	breadcrumb.empty()
	breadcrumb.append('<li class="breadcrumb-item"><a href="#">Pocetna</a></li>')
	for(let level of bc.getLevels())
	{
		breadcrumb.append('<li class="breadcrumb-item active">'+level+'</li>')
		
		if(level == name)
		{
			break
		}
	}
	
}

function isString(obj)
{
    return obj !== undefined && obj !== null && obj.constructor == String;
}

function convertToMMDDYYYY(date)
{
	let dd = date.split(' ')[0].split('-')[0]
	let mm = date.split(' ')[0].split('-')[1]
	let yyyy = date.split(' ')[0].split('-')[2]
	let hh = date.split(' ')[1].split(':')[0]
	let ss = date.split(' ')[1].split(':')[1]
	
	return mm+"-"+dd+"-"+yyyy+" "+hh+":"+mm
}

