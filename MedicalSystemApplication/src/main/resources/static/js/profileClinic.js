
$(document).ready(function(){

	let n = getParameterByName("clinic")

		$.ajax({
			type: 'GET',
			url: 'api/clinic/' + n,
			complete: function(data){
				addClinicInformations(data)
			}
				
		})	
})



function addClinicInformations(data)
{
	clinic = data.responseJSON
	if(clinic == undefined)
	{
		console.log("no clinic data.")
		return;
	}
	
	$("#pNameClinic").text(clinic.name);
	$("#pAdressClinic").text(clinic.address);
	$("#pDescriptionClinic").text(clinic.description);
	$("#pCityClinic").text(clinic.city);
	$("#pStateClinic").text(clinic.state);
	
	if(clinic.rating <= -1)
	{
		$("#sClinicRating").text("Klinika jos uvek nije ocenjena.")
	}
	else
	{
		$("#sClinicRating").text(parseFloat(clinic.rating).toPrecision(3))
	}
	
	setDoctorRatings(clinic)
	
	let sessionUser
	
	$.ajax({
		type: 'GET',
		url: 'api/auth/sessionUser',
		complete: function(data)
		{
			console.log(data)
			sessionUser = data.responseJSON			

			if(sessionUser.role == "ClinicAdmin")
			{
				
				$.ajax({
					type: 'GET',
					url:"api/appointments/clinic/getAllAppointmentsToday/" + clinic.name,
					complete: function(data)
					{
						createChartsDaily(data.responseJSON)
					}
				})
				
			}
		}
			
	})
	
	
}


function createChartsDaily(apps)
{
	$('#chartTab-tab').show()
	
	let cd = new ChartData(apps)
		
	var chart = Highcharts.chart('myChart', {
        chart: {
            type: 'spline',
            zoomType: 'x',
            panning: true,
            panKey: 'shift',
        },
        title: {
            text: "Pregledi tokom dana"
        },
        xAxis: {
        	min: cd.getToday(),
        	max: cd.getTomorrow(),
            type: 'datetime',
            tickAmount: 24,
            tickInterval: 3600 * 1000,
            minTickInterval: 3600 * 1000,
            dateTimeLabelFormats: {
            	day: '%b %d-%m-%Y %H:%M'
            },
            tickPositioner: function() {
                var info = this.tickPositions.info;
                var positions = [];
                for (i = Date.UTC(2020, 0, 1, 0, 0, 0); i <= Date.UTC(2020, 0, 2, 0, 0, 0); i += 3600 * 1000) {
                  positions.push(i);
                }
                positions.info = info;
                return positions;
              },
            title: {
                text: 'Satnica'
            }
        },
        yAxis: {
        	allowDecimals: false,
            title: {
                text: 'Broj pregleda'
            },
            min: 0,
            max : apps.length + 5
        },
        tooltip: {
            crosshairs: [true],
            formatter: function () {
                return "Vreme: " + moment.utc(moment.unix(this.x/1000)).format("DD/MM-YYYY HH:mm") + "<br> Br. Pregleda: " + this.y;
            }
        },
        series: [{
            name: 'Broj pregleda',
            data: cd.getData()
        }]
    });
}

function setDoctorRatings(clinic)
{
	let headersTypes = ["Ime ","Prezime ", "Email", "Prosečna ocena"]
	createDataTable("tableDoctorRatings","doctorsTab","Lista lekara i njihovih prosečnih ocena",headersTypes,0)
	getTableDiv('tableDoctorRatings').show()
	
	
	$.ajax({
			type: 'GET',
			url: 'api/clinic/getDoctors/' + clinic.name,
			complete: function(data)
			{
				doctors = data.responseJSON
				emptyTable("tableDoctorRatings")
				for(d of doctors)
				{	
					let values = [d.user.name, d.user.surname, getProfileLink(d.user.email), d.avarageRating]
					insertTableData("tableDoctorRatings",values)
				}
			}
				
		})

}

function newDate(days) {
	return moment().add(days, 'd').toDate();
}

function newDateString(days) {
	return moment().add(days, 'd').format(timeFormat);
}


class ChartData
{
	constructor(apps)
	{
		this.data = []
		this.hourData = {}
		this.weekData = {}
		
		//TODO: Prebrojati koliko ima pregleda u svakom satu 
		
		for(let i = 0 ; i <= 24 ; i++)
		{
			this.hourData[i] = 0
			for(let j = 0 ; j < apps.length ; j++)
			{
				let date = apps[j].date
				
				this.day = parseInt(date.split(' ')[0].split('-')[0])
				this.month =  parseInt(date.split(' ')[0].split('-')[1]) - 1
				this.year =  parseInt(date.split(' ')[0].split('-')[2])
				
				this.tomorrow = this.day + 1
				
				if(this.tomorrow > 24)
				{
					this.tomorrow = 0
				}
							
				let hours = date.split(' ')[1].split(':')[0]
				
				let hourInt = parseInt(hours)
				
				if(hourInt == i)
				{
					this.hourData[i] = this.hourData[i] + 1
				}
			}
		}
		
		for(let i = 0 ; i <=24 ; i++)
		{
			let mills = Date.UTC(this.year, this.month, this.day, i,0,0)
			let num = this.hourData[i]
			
			this.data.push([mills, num])
		}		
		
		
		
	}
	
	getHourData()
	{
		return this.hourData
	}
	
	getData()
	{
		return this.data
	}
	
	getToday()
	{
		return Date.UTC(this.year, this.month, this.day, 0, 0, 0)
	}
	
	getTomorrow()
	{
		return Date.UTC(this.year, this.month, this.tomorrow, 0, 0, 0)
	}
}


function getUrlVars()
{
    var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
        hash = hashes[i].split('=');
        vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }
    return vars;
}