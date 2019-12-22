/**
 * 
 */

var containers = []
var linked = []

function clearViews()
{
	containers = []
	linked = []
}

function addView(id)
{
	if(containers.includes(id))
	{
		console.log("Container with " + id + " is already subscribied")
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
		console.log("Container with " + id + " does not exist!")
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


function arrayRemove(arr, value) {

	   return arr.filter(function(ele){
	       return ele != value;
	   });

	}
