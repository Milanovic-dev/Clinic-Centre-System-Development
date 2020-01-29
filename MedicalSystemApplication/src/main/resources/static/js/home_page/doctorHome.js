/**
 * 
 */
// ULAZNA FUNKCIJA
var doctorClinic = null

function initDoctor(user)
{
	  $.ajax({
			type: 'GET',
			url:"api/doctors/getClinic/" + user.email,
			complete: function(data)
			{
				doctorClinic = data.responseJSON
				setUpPageDoctor(user)
				//findPatients(data)
			}
	})
}
function setUpPageDoctor(user)
{
	let sideBar = $("#sideBar")
	sideBar.append("<li class='nav-item active'><a class='nav-link' href='userProfileNew.html'><span id='profileUser'>Profil</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' href='index.html'><span id='pacientList'>Lista pacijenata</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' href='index.html'><span id='workCalendar'>Radni kalendar</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' href='index.html'><span id='examinationRequest'>Zakazivanje pregleda</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' href='index.html'><span id='operationRequest'>Zakazivanje operacija</span></a></li>")
	sideBar.append("<li class='nav-item active'><a class='nav-link' href='index.html'><span id='vacationRequest'>Zahtev za godišnji odmor</span></a></li>")

	clearViews()
	addView("addHallContainer")
	addView("showHallContainer")
	addView("changeHallContainer")
	addView("showExaminationContainer")
	addView("showUserContainer")
	addView("showPatientContainer")
	addView("showAppointmentContainerWithCheckBox")
	addView("showStartExaminationContainer")
	addView("showCalendarContainer")
	addView("updateMedicalRecordContainer")
	addView("showVacationRequestContainer")

	var bc1 = new BreadLevel()
    bc1.append('Radni kalendar').append('Pregled u toku').append('Izmena zdravstvenog kartona')
    var bc2 = new BreadLevel()
    bc2.append('Zapocni pregled').append('Pregled u toku ').append('Izmena zdravstvenog kartona ')
    var bc3 = new BreadLevel()
    bc3.append('Lista pacijenata')
    var bc4 = new BreadLevel()

    initBreadcrumb([bc1,bc2,bc3])
    
    
    
    let patientSearch = new TableSearch()
	patientSearch.input("<input class='form-control' type='text' placeholder='Ime pacijenta' id='patientNameLabel'>")
	patientSearch.input("<input class='form-control' type='text' placeholder='Prezime pacijenta' id='patientSurnameLabel'>")
	patientSearch.input("<input class='form-control' type='text' placeholder='Broj osiguranika' id='patientIdLabel'>")
    
	let headersPatients = ["Ime","Prezime","Email","Telefon","Adresa","Grad","Drzava","Broj zdravstvenog osiguranja"]
	createDataTable("listPatientTable","showPatientContainer","Lista pacijenata",headersPatients,0)
	getTableDiv("listPatientTable").show()
	
	insertSearchIntoTable("listPatientTable",patientSearch,function(){
		pname = $('#patientNameLabel').val()
		psurname = $('#patientSurnameLabel').val()
		pid = $('#patientIdLabel').val()
		let json = JSON.stringify({"name": pname,"surname": psurname,"insuranceId":pid })
		$.ajax({
			type: 'POST',
			url: 'api/clinic/getPatientsByFilter/' + doctorClinic.name,
			data: json,
			dataType: "json",
			contentType : "application/json; charset=utf-8",
			complete: function(data)
			{
				let i = 0
				let patients = data.responseJSON
				emptyTable("listPatientTable")
				for(p of patients)
				{
					listPatient(p,i)
					i++
				}
			}
			
		})
		
	})

	let headersApps = ["Datum","Pacijent","Doktori","Klinika","Sala","Tip pregleda","Tip zakazivanja"]
	createDataTable("listAppointmentTable","showAppointmentContainerWithCheckBox","Zakazani pregledi",headersApps,0)
	getTableDiv("listAppointmentTable").show()

    let historyHeaders = ["Pacijent","Doktor","Datum pregleda","Dijagnoze",""]
    let handle = createTable("historyTable","Istorija bolesti",historyHeaders)
    insertTableInto("updateMedicalRecordContainer",handle)
    getTableDiv("historyTable").show()
    
    $('#vacationRequest').click(function(e){
    	e.preventDefault()
    	showView("showVacationRequestContainer")
    	addVacationRequest(user)
    })
    
    
	$('#pacientList').click(function(e){
		e.preventDefault()
		showView("showPatientContainer")
        showBread('Lista pacijenata')
        
    /*
        $.ajax({
			type: 'GET',
			url:"api/doctors/getClinic/" + user.email,
			complete: function(data)
			{
				doctorClinic = data.responseJSON
				findPatients(data)
			}
	})
	*/

	})
	
	
	$.ajax({
        	type:'GET',
        	url:'api/priceList/getAll',
        	complete: function(data)
        	{
			
        		let pricelists = data.responseJSON
				console.log(pricelists.length)		
        		for(p of pricelists)
        		{
        			$('#nextAppToE').append($('<option>',{
        				value: p.typeOfExamination,
        				text: p.typeOfExamination
        			}))
				
        		}
			
        	}
        })

    initCalendarDoc(user)

	$("#workCalendar").click(function(e){
        e.preventDefault()
        showView("showCalendarContainer")
        showBread('Radni kalendar')
     });
	
	$('#nextAppDate').datepicker({
		dateFormat: "dd-mm-yyyy",
		position: "top left"
	})

    $("#startExamination").click(function(e){
      e.preventDefault()
      setUpCodebooks()
      $("#modalCalendar").modal('toggle')
      showBread('Pregled u toku')
      showView("showExaminationContainer")
      $('#collapseThree').collapse('toggle')
      
    });
    
    
    $('#btnOK').click(function(e){
        e.preventDefault()
        $('#modalOK').modal('hide')
        location.reload()
    })

     $('#addRow').click(function(e){
        e.preventDefault()
        const Tr = `
            <tr class="hide">
              <td class="pt-3-half" contenteditable="true" placeholder="alergija"></td>
              <td>
                <span class="table-remove"><button type="button" class="btn btn-primary btn-rounded btn-sm my-0 waves-effect waves-light">Obrisi</button></span>
              </td>
            </tr>`;

            $('#tableAlergiesID tbody').append(Tr);
            table = $('#tableAlergiesID')

     })

     $('#tableAlergiesID').on('click', '.table-remove', function () {
        $(this).parents('tr').detach();
     });

    if(getParameterByName("startExam") != undefined)
    {
    	setUpCodebooks()
        showBread('Pregled u toku')
        showView("showExaminationContainer")
    	getAppointment(doctorClinic.name,getParameterByName("date"),getParameterByName("hall"),user)
    }
    

}

function addVacationRequest(user)
{
	let selectChanged = function()
	{
		let startDate = $('#startDayInputVacationRequest').val()
		let endDate = $('#endDayInputVacationRequest').val()
		
		if(startDate == "" || endDate == "")
		{
			$('#vacationRequestSpinner').hide()
			return
		}
		
		let json = JSON.stringify({"startDate": startDate,"endDate": endDate,"userEmail": user.email })
		
		$('#vacationRequestSpinner').show()
		$.ajax({
			type:'POST',
			url: "api/vacation/checkAvailability/" + doctorClinic.name,
			data: json,
			dataType : "json",
			contentType : "application/json; charset=utf-8",		
			complete:function(data)
			{
				$('#vacationRequestSpinner').hide()

				$('#submitVacationRequest').prop('disabled', !data.responseJSON)
				if(data.responseJSON)
				{
					$('#checkedImageVacationRequest').show()
					$('#uncheckedImageVacationRequest').hide()

				}
				else
				{
					$('#uncheckedImageVacationRequest').show()
					$('#checkedImageVacationRequest').hide()
				}
			}
		})
	}
	
	
	
	$('#startDayInputVacationRequest').datepicker({
		dateFormat: "dd-mm-yyyy",
		onSelect: function(formattedDate, date, inst){			
			let endPicker = $('#endDayInputVacationRequest').datepicker().data('datepicker')
			endPicker.update('minDate', date)
			
			selectChanged()
		}
	})
	
	$('#endDayInputVacationRequest').datepicker({
		dateFormat: "dd-mm-yyyy",
		onSelect: function(formattedDate, date, inst){
			let startPicker = $('#startDayInputVacationRequest').datepicker().data('datepicker')
			startPicker.update('maxDate', date)
			selectChanged()
		}
	})
	
	
	
	
	$('#submitVacationRequest').click(function(e){
		
		e.preventDefault()
		
		let startDate = $('#startDayInputVacationRequest').val()
		let endDate = $('#endDayInputVacationRequest').val()
		let json = JSON.stringify({"startDate": startDate,"endDate": endDate,"userEmail": user.email })
		showLoading('submitVacationRequest')
		
		$.ajax({
			type: 'POST',
			url: 'api/vacation/makeVacationRequest/' + doctorClinic.name ,
			data: json,
            dataType: "json",
            contentType : "application/json; charset=utf-8",
			complete: function(data)
			{
				if(data.status!='201')
				{
					warningModal("Greska","Vaš zahtev za godišnjim odmorom ili odsustvom nije uspešno kreiran.Pokušajte ponovo.")
				}
				
				hideLoading('submitVacationRequest')
			}
		})
	})

}

function getReports(report, i, user){

    var sd = formatDateHours(report.dateAndTime)

    var btn = '<button type="button" class="btn btn-primary" id="updateBtn'+i+'">Izmeni</button>'
    if(report.doctorEmail != user.email){
        btn = ""
    }

    let data = [ report.patientEmail, report.doctorEmail, sd, report.diagnosis, btn]
	insertTableData("historyTable",data)

    $('#updateBtn'+i).click(function(e){
		e.preventDefault()
		patientMedicalReportUpdate(report)
	})

}

function patientMedicalReportUpdate(report){


    showView("showExaminationContainer")
    $("#updateRecord").hide()
    $("#submitReport").hide()
    $("#updateReportBtn").show()
    $("#additional").hide()

    $('#selectDiagnosis').val(report.diagnosis)

    $('#report').val(report.description)

    $('select[name=selectDiagnosis]').val(report.diagnosis);
    $('.selectpicker').selectpicker('refresh');

    $.ajax({
        type: 'GET',
        url: 'api/reports/getReportPrescription/' + report.id,
        complete: function(data)
        {
            let prescription = data.responseJSON

            $('#selectDrug').val(prescription.drugs)
            $('#description').val(prescription.description)
            $('select[name=selectDrug]').val(prescription.drugs);
            $('.selectpicker').selectpicker('refresh');
        }
    })

    $('#updateReportBtn').click(function(e){
        e.preventDefault()

           let drugs = []
           $('#selectDrug option:selected').each(function() {
               drugs.push($(this).val())
           });

           let diagnosis = []
           $('#selectDiagnosis option:selected').each(function() {
               diagnosis.push($(this).val())
           });

          let description = $('#description').val()
          let info = $('#report').val()

          let today = new Date();

          if(!drugs == [] && !description == '')
          {
              $('#reportLabel').text("Izvestaj je uspesno azuriran. Recept je izmenjen i nalazi se na listi recepata za overu kod medicinske sestre.")
          }

          flag = true
          if(info == ""){
               var input = $('#report')
               input.addClass('is-invalid')
               flag = false
          } else {
               var input = $('#report')
               input.removeClass('is-invalid')
          }


          if(flag == false){
               return
          }



        let prescriptionDTO = {"description":description,"drugs":drugs}
        let prescription = JSON.stringify({"description":description,"drugs":drugs})
        let reportJson = JSON.stringify({"description":info,"diagnosis":diagnosis,"prescription":prescriptionDTO})


        $.ajax({
                type: 'PUT',
                url: 'api/reports/updateReport/' + report.id,
                data: reportJson,
                dataType: "json",
                contentType : "application/json; charset=utf-8",
                async: false,
                complete: function(data)
                {
                    if(data.status == "200")
                    {
                       // showView('showExaminationContainer')
                       $('#modalOK').modal('show')

                    }

                }

         })
        })
}


function findPatients(data)
{
	let clinic = data.responseJSON
	$.ajax({
		type: 'GET',
		url: 'api/clinic/getPatients/' + clinic.name,
		complete: function(data)
		{
			let patients = data.responseJSON
			let i = 0
			emptyTable("listPatientTable")
			for(let u of patients)
            {
				listPatient(u,i);
				i++;
            }

		}
	})
}


function listAppointmentWithCheckBox(data,i,appCount,user)
{
	let type
	if(data.type == 'Surgery'){
        type = 'Operacija'
     }else if (data.type == 'Examination'){
        type = 'Pregled'
     }

	let d = [data.date,data.patientEmail,data.doctors[0],data.clinicName,data.hallNumber,data.typeOfExamination, type]
	insertTableData('listAppointmentTable',d)
	/*
	 * $("#startExamin_btn"+i).off('click')
	 * $("#startExamin_btn"+i).click(function(e){
	 * 
	 * showView("showExaminationContainer") showBread('Pregled u toku ')
	 * setUpDiagnosis() setUpCodebooks() getAppointment(data.clinicName,
	 * data.date, data.hallNumber, user)
	 */
}

function listPatient(data,i)
{
	let d = [data.name,data.surname,getProfileLink(data.email),data.phone,data.address,data.city,data.state,data.insuranceId]
	insertTableData("listPatientTable",d)
}


function initCalendarDoc(user)
{

        var calendarButton = document.getElementById('calendarButton');
                  var calendarEl = document.getElementById('calendar');

                  var calendar = $('#calendar').fullCalendar({
                  // plugins: [ 'interaction', 'dayGrid', 'timeGrid',
					// 'monthGrid', 'timeline' ],
                  // defaultView: 'dayGridMonth',
                  defaultDate: '2020-01-01',
                  buttonText: {
                         today:    'danas',
                         month:    'mesec',
                         week:     'nedelja',
                         day:      'dan',
                         year:     'godina',
                         list:     'list'
                       },
                  monthNames: ['Januar', 'Februar', 'Mart', 'April', 'Maj', 'Jun', 'Jul',
                                'Avgust', 'Septembar', 'Oktobar', 'Novembar', 'Decembar'],
                  monthNamesShort: ['Jan','Feb','Mar','Apr','Maj','Jun','Jul','Avg','Sep','Okt','Nov','Dec'],
                  dayNames: ['Nedelja','Ponedeljak','Utorak','Sreda','Cetvrtak','Petak','Subota'],
                  dayNamesShort: ['Ned','Pon','Uto','Sre','Cet','Pet','Sub'],

                  selectable:true,
                  eventClick: function(info)
                  {
                      var type
                      if(info.type == 'Surgery'){
                            type = 'Operacija'
                       }else if (info.type == 'Examination'){
                            type = 'Pregled'
                       }

                      var sd=info.start._i
                           sd = formatDateHours(sd)


                      $.ajax({
                                type: 'GET',
                                url: 'api/users/getUser/' + info.patientEmail,
                                complete: function(data){
                                   var patient = data.responseJSON
                                   var patientName = patient.name
                                   var patientSurname = patient.surname
                                    $("#patientId").text('Pacijent: ' + patientName + ' ' + patientSurname + ' ' + info.patientEmail);
                                    $("#patientExamin").text('Pacijent: ' + patientName + ' ' + patientSurname);
                                    $("#patientExaminEmail").text(info.patientEmail)
                                },

                      })

                      $("#durationId").text('Trajanje: ' + info.duration + 'h');
                      $("#typeId").text('Tip pregleda: ' + type);
                      $("#clinicId").text('Klinika: ' + info.clinicName);
                      $("#hallId").text('Broj sale: ' + info.hallNumber);
                      $("#startId").text('Pocetak: ' + sd);

                      getAppointment(info.clinicName ,sd, info.hallNumber, user)

                      $('#modalCalendar').modal('show');

                      },
                      header: {
                          right: 'agendaDay,agendaWeek,month,timelineCustom',
                          center: 'title',
                          left: 'prev,today,next'
                      },

                  fixedWeekCount: false,
                  timeFormat: 'HH:mm',
                  contentHeight: 550,
                  views: {
                      timelineCustom: {
                          type: 'timeline',
                          buttonText: 'godina',
                          dateIncrement: { years: 1 },
                          slotDuration: { months: 1 },
                          visibleRange: function (currentDate) {
                              return {
                                  start: currentDate.clone().startOf('year'),
                                  end: currentDate.clone().endOf("year")
                              };
                          }

                      }
                  },
                  eventColor: '#2f989d',
                  eventSources: [
                         {
                           url: 'api/appointments/doctor/getAllAppointmentsCalendar/'+user.email,
                           method: 'GET',
                           extraParams: {

                           },

                           failure: function() {
                             alert('there was an error while fetching events!');
                           },

                         },
                       ],
                    schedulerLicenseKey: 'GPL-My-Project-Is-Open-Source'
                });

         $('#workCalendar').click(function(){
              showView("showCalendarContainer")
         });


}

function formatDateHours (dateObj) {
      var date = new Date(dateObj);
      var hours = date.getHours();
      var minutes = date.getMinutes();
      minutes = minutes < 10 ? '0'+minutes : minutes;
      var strTime = hours + ':' + minutes;
      var month = date.getMonth()+1
      return date.getDate() + "-" + month+ "-" + date.getFullYear() + " " + strTime;
}

function setUpDrugs(){
	$.ajax({
        type: 'GET',
        url:"api/drug/getAllDrugs",
        complete: function(data)
        {
        	drugs = data.responseJSON
            $('#selectDrugStartExamin').empty()
				for(let d of drugs)
				{
					$('#selectDrugStartExamin').append($('<option>',{
						value: d.name,
						text: d.name
					}))

				}
        }
    })

}

function setUpHall(){
	$.ajax({
		type: 'GET',
		url: 'api/hall/getAllByClinic/'+doctorClinic.name,
		complete: function(data)
		{
			halls = data.responseJSON
			let i = 0
			$('#selectHallStartExamin').empty()
			for(let h of halls)
            {
				$('#selectHallStartExamin').append($('<option>',{
					value: h.name,
					text: h.name
				}))

            }
		}
	})
}


function setUpCodebooks(){

    $.ajax({
        type: 'GET',
        url:"api/drug/getAllDrugs",
        complete: function(data)
        {
           let select = $('#selectDrug').val()
           $('#selectDrug').empty()
           			$.each(data.responseJSON, function (i, item) {
           			    $('#selectDrug').append($('<option>', {
           			        value: item.name,
           			        text : item.name
           			    }));
           			});
           			$('.selectpicker').selectpicker('refresh');
        }
    });


     $.ajax({
            type: 'GET',
            url:"api/diagnosis/getAllDiagnosis",
            complete: function(data)
            {
               let select = $('#selectDiagnosis').val()
               $('#selectDiagnosis').empty()
               			$.each(data.responseJSON, function (i, item) {
               			    $('#selectDiagnosis').append($('<option>', {
               			        value: item.name,
               			        text : item.name + "(" + item.code + ")"
               			    }));
               			});
               			$('.selectpicker').selectpicker('refresh');
            }
        })

}


function getAppointment(clinicName, date, hallNumber, user){

		$('#collapseThree').collapse('toggle')
        hallNumber = parseInt(hallNumber)
        
        $('#nextAppType').change(function(e)
        {
        	$('#nextAppToE').prop('disabled', $('#nextAppType').val() != "Pregled")
        })

        var appointment
        var patient

        $.ajax({
            type: 'GET',
            url: 'api/appointments/getAppointment/'+clinicName+'/'+date+'/'+hallNumber,
            complete: function(data)
            {
                 appointment = data.responseJSON


                 $.ajax({
                        type: 'GET',
                        url: 'api/users/getUser/' + appointment.patientEmail,
                        complete: function(data){
                           patient = data.responseJSON
                           var patientName = patient.name
                           var patientSurname = patient.surname
                           var patientEmail = patient.email
                            $("#patientExamin").text('Pacijent: ' + patientName + ' ' + patientSurname + ' ' + patientEmail);

                            var type
                            if(appointment.type == 'Surgery'){
                                type = 'Operacija'
                             }else if (appointment.type == 'Examination'){
                                type = 'Pregled'
                             }

                            var sd = appointment.date


                            $("#startExamin").text('Pocetak: ' + sd);
                            $("#typeExamin").text('Tip pregleda: ' + type);
                            $("#clinicExamin").text('Klinika: ' + appointment.clinicName);
                            $("#doctorExamin").text('Doktor: ' + user.name + ' ' + user.surname);
                            
                            
                            

                        },

                 })

                }
        })

        $('#updateRecord').click(function(e){
           e.preventDefault()
           getMedicalRecord(patient, user)
           showView("updateMedicalRecordContainer")
           showBread('Izmena zdravstvenog kartona')
        });

        $('#submitUpdateRecord').off("click").click(function(e){
             e.preventDefault()
             var alergies = []
             alergies =   $('#tableAlergiesID td:nth-child(1)').map(function() {
                                 return $(this).text();
                          }).get();


             var weight = $('#updateWeight').val()
             var height = $('#updateHeight').val()
             var bloodType = $('#updateBloodType option:selected').val()

             let record = {"weight": weight,"height": height,"bloodType": bloodType,"alergies": alergies}
             recordJSON = JSON.stringify(record)

                    $.ajax({
                        type: 'PUT',
                        url: 'api/users/patient/updateMedicalRecord/' + patient.email,
                        data: recordJSON,
                        dataType: "json",
                        contentType : "application/json; charset=utf-8",
                        async: false,
                        complete: function(data)
                        {
                            if(data.status == "200")
                            {
                                showView('showExaminationContainer')
                            }

                        }

                    })
        });
        
        
 

        $('#submitReport').off("click").click(function(e){
            e.preventDefault()

            let drugs = []
            $('#selectDrug option:selected').each(function() {
                drugs.push($(this).val())
            });

            let diagnosis = []
            $('#selectDiagnosis option:selected').each(function() {
                diagnosis.push($(this).val())
            });

           let description = $('#description').val()
           let report = $('#report').val()

           let today = new Date();
           
           let nextDate = $('#nextAppDate')
           let nextType = $('#nextAppType')
           let ToE = $('#nextAppToE')
           

// if(!drugs == [] && !description == '')
// {
// $('#prescriptionLabel').show()
// $('#reportLabel').hide()
// } else {
// $('#reportLabel').show()
// $('#prescriptionLabel').hide()
// }

          if(!drugs == [] && !description == '')
          {
              $('#reportLabel').text("Izvestaj i recept su uspesno kreirani. Recept se nalazi na listi recepata za overu kod medicinske sestre.")
          }
           

           flag = true
           if(report == ""){
                var input = $('#report')
                input.addClass('is-invalid')
                flag = false
           } else {
                var input = $('#report')
                input.removeClass('is-invalid')
           }
           
           /*
           if(!validation(nextDate, nextDate.val() == "", "Morate uneti datum."))
           {
        	   flag = false        	   
           }
           
           if(!validation(nextType, nextType.val() == "", "Morate izabrati tip."))
           {
        	   flag = false
           }
                 
           if(!validation(ToE, ToE.val() == "", "Morate izabrati tip pregleda"))
           {
        	   flag = false
           }
           */

           if(flag == false){
                return
           }
           
           if(nextDate.val() != "" && ToE.val() != "" && nextType.val() != "")
           {
        	   let typeOfExam
          		
          		if(nextType.val() == "Pregled")
          		{
          			typeOfExam = "Examination"      			
          		}
          		else
          		{
          			typeOfExam = "Surgery"           			
          		}
        	   	
          		let nextAppRequestJSON = JSON.stringify({"date":nextDate.val(), "patientEmail":patient.email, "clinicName":clinicName, "doctors":[user.email], "typeOfExamination":ToE.val(), "type":typeOfExam})
          		
          		$.ajax({
						type:'POST',
						url:'api/appointments/sendRequest',
						data: nextAppRequestJSON,
						dataType : "json",
						contentType : "application/json; charset=utf-8",
						complete: function(data)
						{
							if(data.status != "201")
							{
								alert("Error pri cuvanju sledeceg pregleda")
							}													
						}
					})
           }
           
           		
           

                let prescriptionDTO = {"description":description,"drugs":drugs,"nurse":"","isValid":false, "validationDate":""}
                let prescription = JSON.stringify({"description":description,"drugs":drugs,"nurse":"","isValid":false, "validationDate":""})
                let reportJson = JSON.stringify({"description":report,"diagnosis":diagnosis,"doctorEmail":user.email,"clinicName":appointment.clinicName,"dateAndTime":today,"patient":patient.email,"prescription":prescriptionDTO})



                $.ajax({
                    type:'POST',
                    url:'api/reports/addPatientMedicalReport/' + patient.email,
                    data: reportJson,
                    dataType : "json",
                    contentType : "application/json; charset=utf-8",
                    complete: function(data)
                    {
                         if(data.status == "201")
                          {
                               $('#modalOK').modal('show')

                          }
                    }
                })
        })

function getMedicalRecord(patient, user){
    $.ajax({
			type: 'GET',
			url: 'api/users/patient/getMedicalRecord/'+patient.email,
			complete: function(data)
			{
				record = data.responseJSON

				let index = 0
                $('#tableAlergiesID tbody').html('')

				for(a of record.alergies)
				{
					addAlergiesTr(a, index, patient)
					index++
				}
				let i=0
				emptyTable("historyTable")

				for(report of record.reports)
                {
                    getReports(report, i, user)
                    i++
                }

				$('#updateHeight').val(record.height)
				$('#updateWeight').val(record.weight)
				$('#updateBloodType').val(record.bloodType)
			}

		})

}

function addAlergiesTr(alergie, i, patient){

 const newTr = `
    <tr class="hide">
      <td class="pt-3-half" contenteditable="true">` + alergie + `</td>
      <td>
        <span class="table-remove"><button type="button" class="btn btn-primary btn-rounded btn-sm my-0 waves-effect waves-light">Obrisi</button></span>
      </td>
    </tr>`;

    $('#tableAlergiesID tbody').append(newTr);

}



}