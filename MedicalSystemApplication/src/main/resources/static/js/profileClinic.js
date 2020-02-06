
$(document).ready(function(){

	checkSession(function(exists){
		if(!exists) window.location.href = "index.html"
	})
	
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
	$('#pAdressClinic').empty()
	$("#pAdressClinic").append("<a href='map.html?clinic="+clinic.name+"' target='_blank'>"+clinic.address+"</a>");
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
	
	
	setHallTableClinicProfile(clinic.name)
	setPricelistTableClinicProfile(clinic.name)
	
	let sessionUser
	
	$.ajax({
		type: 'GET',
		url: 'api/auth/sessionUser',
		complete: function(data)
		{
			sessionUser = data.responseJSON			

			setDoctorRatings(clinic,sessionUser)
			setPreAppsTable(clinic.name)
			
			if(sessionUser.role == "ClinicAdmin")
			{
				
				$('#revanueTab-tab').show()
				$('#changeClinicInfo').show()
				$.ajax({
					type: 'GET',
					url:"api/appointments/clinic/getAllAppointmentsToday/" + clinic.name,
					complete: function(data)
					{
						createChartsDaily(data.responseJSON)	
						$('#DailyRevanue').text("Dnevni prihod: " + getRevanue(data.responseJSON) + ",00rsd")
					}
				})
				
				$.ajax({
					type:'GET',
					url:"api/appointments/clinic/getAllAppointmentsWeek/" + clinic.name,
					complete: function(data)
					{
						createChartsWeekly(data.responseJSON)
						$('#WeeklyRevanue').text("Nedeljni prihod: " + getRevanue(data.responseJSON) + ",00rsd")
					}
				})
				
				
				$.ajax({
					type: 'GET',
					url: "api/appointments/clinic/getAllAppointmentsMonth/" + clinic.name,
					complete: function(data)
					{
						createChartsMonthly(data.responseJSON)
						$('#MonthlyRevanue').text("Mesecni prihod: " + getRevanue(data.responseJSON) + ",00rsd")
					}
				})
				
				
				
			}
			
			
		}
			
	})
	
	
	
	
}

function setPreAppsTable(clinicName)
{
	let headers = ['Datum','Termin','Klinika','Sala','Doktor','Tip pregleda','Cena','Cena Sa popustom(20%)']
	createDataTable('preAppTable',"preApps","Unapred definisani pregledi",headers,0)
	getTableDiv('preAppTable').show()
	
	$.ajax({		
		type: 'GET',
		url: "api/appointments/getAllPredefined",
		complete: function(data)
		{
			let apps = data.responseJSON
		
			emptyTable('preAppTable')		
			
			$.each(apps, function(i, data){
				
				if(data.clinicName == clinicName)
				{
					let dateSplit = data.date.split(' ')
					let date = dateSplit[0]
					let startTime = dateSplit[1]
					let endTime = data.endDate.split(' ')[1]
					
					let d = [date,startTime + " - " + endTime,getClinicProfileLink(data.clinicName),data.hallNumber,getProfileLink(data.doctors[0]),data.typeOfExamination,data.price, getDiscountPrice(data.price,20)]
					insertTableData('preAppTable',d)
				}
				
			});
			
		}
	})
}

function setPricelistTableClinicProfile(clinicName)
{
	let headersPricelist = ["Pregled/Operacija","Tip pregleda","Cena"]
	createDataTable("tablePricelistClinicProfile","pricelistContainerProfileClinic","Cenovnik",headersPricelist,0)
	$.ajax({
			type: 'GET',
			url: "api/priceList/getAllByClinic/" + clinicName,
			complete:function(data)
			{
				pricelist = data.responseJSON
				let i = 0
				emptyTable("tablePricelistClinicProfile")
				for(p of pricelist )
				{
					listPricelist(p, i)
					i++
				}
			}
		})
		
		getTableDiv("tablePricelistClinicProfile").show()
}
function listPricelist(data,i)
{	
	let d = ["Pregled",data.typeOfExamination, data.price]
	insertTableData("tablePricelistClinicProfile",d)
}


function setHallTableClinicProfile(clinicName)
{
	let headersHall = ["Broj sale","Ime sale","Ime klinike"]
	createDataTable("tableHallClinicProfile","hallContainerProfileClinic","Lista sala",headersHall,0)
			$.ajax({
			type: 'GET',
			url: "api/hall/getAllByClinic/" + clinicName,
			complete:function(data)
			{
				halls = data.responseJSON
				let i = 0
				emptyTable("tableHallClinicProfile")
				for(h of halls )
				{
					listHall(h, i)
					i++
				}
			}
		})
	getTableDiv("tableHallClinicProfile").show()
}

function listHall(data,i)
{	
	let d = [data.number,data.name,data.clinicName]
	insertTableData("tableHallClinicProfile",d)
}


function getRevanue(apps)
{
	let sum = 0
	
	for(let app of apps)
	{
		sum += app.price
	}
	
	return sum
}

function setDoctorRatings(clinic, user)
{
	let headersTypes = ["Tip", "Ime ","Prezime ", "Email", "Prosečna ocena","Sl. Termini", ""]
	createDataTable("tableDoctorRatings","doctorsTab","Lista lekara i njihovih prosečnih ocena",headersTypes,0)
	getTableDiv('tableDoctorRatings').show()
	
	let search = new TableSearch()
	search.input('<input class="form-control" type="text" placeholder="Unesite Ime" id="chooseDoctorName">')
	search.input('<input class="form-control" type="text" placeholder="Unesite prezime" id="chooseDoctorSurname">')
	search.input('<input class="form-control" type="number" placeholder="Unesite ocenu" id="chooseDoctorRating">')
	search.input('<select class="form-control" type="text" placeholder="Unesite tip" id="chooseDoctorType"><option selected value="">Izaberite Tip</option></select>')
	search.input('<input class="form-control datepicker-here" data-language="en" placeholder="Izaberite datum" id="chooseDoctorDate" readonly="true">')
	
	insertSearchIntoTable('tableDoctorRatings', search, function(){
		
		let srcButton = getTableSearchButton('chooseDoctorTable')
		showLoading(srcButton)
		
		let name = $('#chooseDoctorName').val()
		let surname = $('#chooseDoctorSurname').val()
		let rating = $('#chooseDoctorRating').val()
		let type = $('#chooseDoctorType').val()
		let date = $('#chooseDoctorDate').val()
		
		let dto = 
		{
			user:{
				"name" : name,
				"surname" : surname
			},
			"averageRating": rating,
			"type" : type,
			"shiftStart": date
		}
		
		
		let json = JSON.stringify(dto)

		$.ajax({
			type: "POST",
			url:"api/clinic/getDoctorsByFilter/" + clinic.name,
			data: json,
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			complete: function(response)
			{
				let doctors = response.responseJSON
				let index = 0
				emptyTable("tableDoctorRatings")
				for(d of doctors)
				{	
					listDoctor(d, index, clinic, user)
					index++
				}
			
				
				hideLoading(srcButton)
			}
		})
		
	})
	
	
	$.ajax({
		type:'GET',
		url:'api/priceList/getAll',
		complete: function(data)
		{		
			let pricelists = data.responseJSON
						
			for(let p of pricelists)
			{
				$('#chooseDoctorType').append($('<option>',{
					value: p.typeOfExamination,
					text: p.typeOfExamination
				}))			
			}			
		}
	})
	
	
	$('#chooseDoctorDate').datepicker({
		dateFormat: "dd-mm-yyyy",
		minDate: new Date()
	})
	
}


function listDoctor(doctor, i, clinic, user)
{
	let dates = "<p>Morate uneti datum</p>"
	let makeApp = "<p>Morate uneti datum i tip</p>"
		
	if($('#chooseDoctorDate').val() != "")
	{
		dates = '<button class="btn btn-info" id="occupation'+i+'">Proveri</button>'
	}
	
	if($('#chooseDoctorDate').val() != "" && $('#chooseDoctorType').val() != "")
	{
		makeApp = '<button class="btn btn-info" id="makeApp'+i+'">Zakazi</button>'		
	}
	
	let values = [d.type, d.user.name, d.user.surname, getProfileLink(d.user.email), d.avarageRating, dates, makeApp]
	insertTableData("tableDoctorRatings",values)
	
	let name = d.user.name
	let surname = d.user.surname
	
	$.ajax({
		type:'GET',
		url:"api/doctors/getFreeTime/"+d.user.email+"/"+ $('#chooseDoctorDate').val(),
		complete: function(data)
		{

			let listOfDates = data.responseJSON
			
					
			$('#occupation'+i).off('click')
			$('#occupation'+i).click(function(e){
				e.preventDefault()
				$('#warningModalLabel').text("Slobodni termini: " + name + " " + surname)
							
				$('#warningBodyModal').empty()
				
				if(listOfDates.length == 1)
				{
					$('#warningBodyModal').append("<p>Doktor je slobodan.</p>")
					$('#warningBodyModal').append("<p>Radno vreme: <b>"+listOfDates[0].start + " - " + listOfDates[0].end + "</b>")
					
				}
				else
				{
					$.each(listOfDates, function(i, item){
						$('#warningBodyModal').append("<p>"+item.start + " - " + item.end +"</p>")					
					});				
				}
				
				$('#warningModal').modal('show')
			})
			
			if(user.role != "Patient")
			{
				$('#makeApp'+i).prop('disabled',true)
				return
			}
			
			$('#makeApp'+i).off('click')
			$('#makeApp'+i).click(function(e){
				e.preventDefault()				
				window.location.href = 'index.html?makeApp=true&clinicName='+clinic.name+'&type='+d.type+'&doctorEmail='+doctor.user.email+'&date='+$('#chooseDoctorDate').val();		
			})
		}
	})
	
}


function createChartsDaily(apps)
{
	$('#chartTab-tab').show()
	
	let cd = new ChartData(apps)
		
	var chart = Highcharts.chart('dailyChart', {
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
                for (i = cd.getToday(); i <= cd.getTomorrow(); i += 3600 * 1000) {
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

function createChartsMonthly(apps)
{
	$('#chartTabMonth-tab').show()
	
	$.ajax({
		type: 'GET',
		url: 'api/utility/date/getMonthInfo',
		complete: function(data)
		{
			monthdto = data.responseJSON
			let cd = new ChartData(apps, undefined, monthdto)

			var chart = Highcharts.chart('monthlyChart', {
		        chart: {
		            type: 'spline',
		            zoomType: 'x',
		            panning: true,
		            panKey: 'shift',
		        },
		        title: {
		            text: "Pregledi tokom meseca"
		        },
		        xAxis: {
		        	visible: true,
		        	min: getUTCMilliIgnoreHours(getUTCDateObject(monthdto.monthStart)),
		        	max: getUTCMilliIgnoreHours(getUTCDateObject(monthdto.monthEnd)),
		            type: 'datetime',
		            tickInterval: 7 * 24 * 3600 * 1000,
		            tickPositioner: function() {
		                var info = this.tickPositions.info;
		                var positions = [];
		                for (i = getUTCMilliIgnoreHours(getUTCDateObject(monthdto.monthStart)); i <= getUTCMilliIgnoreHours(getUTCDateObject(monthdto.monthEnd)); i += 24 * 3600 * 1000) {
		                  positions.push(i);
		                }
		                positions.info = info;
		                return positions;
		              },
		            title: {
		                text: 'Nedelja'
		            },
		            dateTimeLabelFormats: {
		                day: '%b %d-%m-%Y'
		              },
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
		                return "Vreme: " + moment.utc(moment.unix((this.x + 24 * 3600 * 1000)/1000)).format("DD/MM-YYYY HH:mm") + "<br> Br. Pregleda: " + this.y;
		            }
		        },
		        series: [{
		            name: 'Broj pregleda',
		            data: cd.getMonthData()
		        }]
		    });
			
		}
	})
	

}


function createChartsWeekly(apps)
{
	
	$('#chartTabWeek-tab').show()
	
	$.ajax({
		type: 'GET',
		url: "api/utility/date/getWeekInfo",
		complete: function(data)
		{
			weekdto = data.responseJSON

			let cd = new ChartData(apps, weekdto)
			var chart = Highcharts.chart('weeklyChart', {
		        chart: {
		            type: 'spline',
		            zoomType: 'x',
		            panning: true,
		            panKey: 'shift',
		        },
		        title: {
		            text: "Pregledi tokom nedelje"
		        },
		        xAxis: {
		        	visible: true,
		        	min: getUTCMilliIgnoreHours(getUTCDateObject(weekdto.weekStart)),
		        	max: getUTCMilliIgnoreHours(getUTCDateObject(weekdto.weekEnd)),
		            type: 'datetime',
		            tickInterval: 24 * 3600 * 1000,
		            tickPositioner: function() {
		                var info = this.tickPositions.info;
		                var positions = [];
		                for (i = getUTCMilliIgnoreHours(getUTCDateObject(weekdto.weekStart)); i <= getUTCMilliIgnoreHours(getUTCDateObject(weekdto.weekEnd)); i += 24 * 3600 * 1000) {
		                  positions.push(i);
		                }
		                positions.info = info;
		                return positions;
		              },
		            title: {
		                text: 'Nedelja'
		            },
		            dateTimeLabelFormats: {
		                day: '%b %d-%m-%Y'
		              },
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
		                return "Vreme: " + moment.utc(moment.unix((this.x + 24 * 3600 * 1000)/1000)).format("DD/MM-YYYY") + "<br> Br. Pregleda: " + this.y;
		            }
		        },
		        series: [{
		            name: 'Broj pregleda',
		            data: cd.getWeekData()
		        }]
		    });
						
		}
	})
}

class ChartData
{
	constructor(apps, weekInfo, monthInfo)
	{
		this.data = []
		this.week = []
		this.month = []
		this.hourData = {}
		this.weekData = {}
		this.monthData = {}
		
		//TODO: Prebrojati koliko ima pregleda u svakom satu 
		
		for(let i = 0 ; i <= 24 ; i++)
		{
			this.hourData[i] = 0
			for(let j = 0 ; j < apps.length ; j++)
			{
				let date = apps[j].date
				
				let utcObj = getUTCDateObject(date)
				
				this.day = utcObj.day
				this.month =  utcObj.month
				this.year =  utcObj.year
				
				this.tomorrow = this.day + 1
				
				if(this.tomorrow > 24)
				{
					this.tomorrow = 0
				}
										
				let hourInt = utcObj.hour
				
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
	
			
		//WEEK
		if(weekInfo != undefined)
		{
			let startUTC = getUTCDateObject(weekInfo.weekStart)
			let endUTC = getUTCDateObject(weekInfo.weekEnd)
			
			for(let i = 0 ; i <= 7 ; i++)
			{
				this.weekData[getUTCMilliIgnoreHours(startUTC)] = 0
				startUTC.day += 1
			}
			
			for(let i = 0 ; i < apps.length ; i++)
			{
				let date = apps[i].date
				
				let dateUTC = getUTCDateObject(date)
				
				let key = getUTCMilliIgnoreHours(dateUTC)
				
				this.weekData[key] = this.weekData[key] + 1			
			}
			
			let keys = Object.keys(this.weekData)
			
			for(let i = 0 ; i < keys.length ; i++)
			{
				this.week.push([keys[i] - 3600000, this.weekData[keys[i]]])
			}
		}
				
		//MONTH
		if(monthInfo != undefined)
		{
			let startUTC = getUTCDateObject(monthInfo.monthStart)
			let endUTC = getUTCDateObject(monthInfo.monthEnd)
			
			for(let i = 0 ; i < endUTC.day  ; i++)
			{
				this.monthData[getUTCMilliIgnoreHours(startUTC)] = 0
				startUTC.day += 1
			}
			
			
			for(let i = 0 ; i < apps.length ; i++)
			{
				let date = apps[i].date
				
				let dateUTC = getUTCDateObject(date)
				
				let key = getUTCMilliIgnoreHours(dateUTC)
				
				this.monthData[key] = this.monthData[key] + 1	
			}
			
			this.month = []
			let keys = Object.keys(this.monthData)
			
			for(let i = 0 ; i < keys.length ; i++)
			{
				this.month.push([keys[i] - 3600000, this.monthData[keys[i]]])
			}
		
		}
			
	}
	
	getMonthData()
	{
		return this.month
	}
	
	getWeekData()
	{
		return this.week
	}
	
	getHourData()
	{
		return this.hourData
	}
	
	getData()
	{
		return this.data
	}
	
	getTodayInt()
	{
		return this.day
	}
	
	getTomorrowInt()
	{
		return this.tomorrow;
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


function getUTCDateObject(date)
{
	return {
		day: parseInt(date.split(' ')[0].split('-')[0]),
		month: parseInt(date.split(' ')[0].split('-')[1]) - 1,
		year: parseInt(date.split(' ')[0].split('-')[2]),
		hour: parseInt(date.split(' ')[1].split(':')[0])
	}
}

function getUTCMilli(dateObj)
{
	return Date.UTC(dateObj.year, dateObj.month, dateObj.day, dateObj.hour, 0, 0)
}

function getUTCMilliIgnoreHours(dateObj)
{
	return Date.UTC(dateObj.year, dateObj.month, dateObj.day, 0, 0, 0)
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