var ProcessDashboardCenso = function () {
	
	
return {
  //main function to initiate the module
  init: function (parametros) {
	  
	  $('#area,#district,#localidad,#censustaker,#foci,#tipoou,#tiempo').select2({
			theme: "bootstrap"
		});
	  
	  $('input[name="fecCensoRange"]').daterangepicker({
		   ranges: {
		     'Hoy': [moment(), moment()],
		 'Ayer': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
		 'Ultimos 7 Dias': [moment().subtract(6, 'days'), moment()],
		 'Ultimos 30 Dias': [moment().subtract(29, 'days'), moment()],
		 'Este mes': [moment().startOf('month'), moment().endOf('month')],
		 'Mes pasado': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
		   },
		   singleDatePicker: false,
		   startDate: moment().subtract(29, 'days'),
		   maxDate:moment(),
		   locale: {
		       "format": "YYYY-MM-DD",
		   "separator": " - ",
		   "applyLabel": "Aplicar",
		   "cancelLabel": "Cancelar",
		   "fromLabel": "Desde",
		   "toLabel": "Hasta",
		   "customRangeLabel": "Personalizado",
		   "weekLabel": "S",
		   "daysOfWeek": [
		   "Do",
		   "Lu",
		   "Ma",
		   "Mi",
		   "Ju",
		   "Vi",
		   "Sa"
		   ],
		   "monthNames": [
		   "Enero",
		   "Febrero",
		   "Marzo",
		   "Abril",
		   "Mayo",
		   "Junio",
		   "Julio",
		   "Agosto",
		   "Septiembre",
		   "Octubre",
		   "Noviembre",
		   "Diciembre"
		   ],
		   "firstDay": 1
		   },
		});
	  
	  
	  $('#checkDates').change(function() {
	        if(this.checked) {
	        	$("#fecCensoRange").prop('disabled', false);
	        }else{
	        	$("#fecCensoRange").prop('disabled', true);
	        }
	              
	    });
	  
	  //Por dia
	  var ctx1 = $('#dates-chart');
	  var dayChart = new Chart(ctx1, {});
	  viviendasPorDia ();
	  
	  //Por OU
	  var ctx2 = $('#ou-chart');
	  var ouChart = new Chart(ctx2, {});
	  viviendasPorOU ();
	  
	  //Por Ubi
	  viviendasPorUbi();
	  
	  //Por Mat
	  var ctx3 = $('#mat-chart');
	  var matChart = new Chart(ctx3, {});
	  viviendasPorMat ();
	  
	  $( '#filters-form' ).validate( {
		    rules: {
		      localidad: {
		    	  required: true
		      }
		    },
		    errorElement: 'em',
		    errorPlacement: function ( error, element ) {
		      // Add the `help-block` class to the error element
		      error.addClass( 'form-control-feedback' );
		      if ( element.prop( 'type' ) === 'checkbox' ) {
		        error.insertAfter( element.parent( 'label' ) );
		      } else {
		        error.insertAfter( element );
		      }
		    },
		    highlight: function ( element, errorClass, validClass ) {
		      $( element ).addClass( 'form-control-danger alert-danger' ).removeClass( 'form-control-success' );
		      $( element ).parents( '.form-group' ).addClass( 'alert-danger' ).removeClass( 'has-success' );
		    },
		    unhighlight: function (element, errorClass, validClass) {
		      $( element ).addClass( 'form-control-success' ).removeClass( 'form-control-danger alert-danger' );
		      $( element ).parents( '.form-group' ).addClass( 'has-success' ).removeClass( 'alert-danger' );
		    },
	        submitHandler: function (form) {
	        	viviendasPorDia ();
	        	viviendasPorOU ();
	        	viviendasPorUbi();
	        	viviendasPorMat ();
	        }
		  });
	  
	  
	  Date.prototype.yyyymmdd = function() {         
		  var yyyy = this.getFullYear().toString();                                    
		  var mm = (this.getMonth()+1).toString(); // getMonth() is zero-based         
		  var dd  = this.getDate().toString();             
		  return yyyy + '-' + (mm[1]?mm:"0"+mm[0]) + '-' + (dd[1]?dd:"0"+dd[0]);
	  };

	  
	  //Funcion grafico de viviendas por dia de censo
	  function viviendasPorDia (){
		  $('div.daychart').block({ message: parametros.waitmessage });
		  var fechas = [];
		  var viviendas = [];
		  var activas = [];
		  var totalViviendas=0;
		  var vivActivas=0;
		  $.getJSON(parametros.vivPorDiaUrl, $('#filters-form').serialize(), function(data) {
			  var table1 = $('#daytable').DataTable({
				  dom: 'lBfrtip',
		          "oLanguage": {
		              "sUrl": parametros.dataTablesLang
		          },
		          "bFilter": true, 
		          "bInfo": true, 
		          "bPaginate": true, 
		          "bDestroy": true,
		          "responsive": true,
		          "pageLength": 4,
		          "bLengthChange": false,
		          "responsive": true,
		          "buttons": [
		              {
		                  extend: 'excel'
		              },
		              {
		                  extend: 'pdfHtml5',
		                  orientation: 'portrait',
		                  pageSize: 'LETTER'
		              }
		          ],
		          "rowCallback": function( row, data, index ) {
		              if (data[1]=0) {
		                $('td', row).css('background-color', 'LightRed');
		              }
		          }
		      });
			  table1.clear().draw();
			  for (var row in data) {
				  var d1 = $('#tiempo').val()==="Dia"?(new Date(data[row][0])).yyyymmdd():data[row][1]+"-"+data[row][0];
				  fechas.push([d1]);
				  viviendas.push([data[row][2]]);
				  totalViviendas = totalViviendas + data[row][2];
				  activas.push([data[row][4]]);
				  vivActivas = vivActivas + data[row][4];
				  table1.row.add([d1, data[row][2], data[row][4], data[row][3]]);
			  }
			  var porcActivas = (vivActivas/totalViviendas*100).toFixed(2);
			  $('#labelTotViv').html(totalViviendas);
			  $('#labelPorViv').html(vivActivas + " (" + porcActivas +"%)");
			  
			  var optionsLine = {
					  responsive: true,
					  maintainAspectRatio: false,
					  scales: {
						  xAxes: [{
							  gridLines: {
								  drawOnChartArea: false,
							  },
							  scaleLabel:{
								  display: true,
								  labelString:$('#tiempo').val()
							  }
						  }],
						  yAxes: [{
							  ticks: {
								  beginAtZero: true,
				  		          maxTicksLimit: 5,
							  }
						  }]
					  },
					  elements: {
						  point: {
							  radius: 4,
				  		      hitRadius: 10,
				  		      hoverRadius: 4,
				  		      hoverBorderWidth: 3,
						  },
						  line: {
							  tension: 0.00001,
						  }
					  },
					  legend: {
						  display: false
					  }
			  };
			  
			  var dataSet1 = {
					  labels: fechas,
					  datasets: [
						  {
							  type: 'line',
							  label: parametros.vivact,
							  backgroundColor: 'transparent',
				  		      borderColor: 'rgba(0,0,0,0.75)',
				  		      pointBackgroundColor: '#000',
				  		      pointBorderColor: 'rgba(0,0,0,0.75)',
				  		      pointHoverBackgroundColor: '#000',
				  		      borderWidth: 2,
				  		      data: activas
						  },
						  {
							  type: 'bar',
				  		      label: parametros.vivtot,
				  		      backgroundColor: '#36a9e0',
				  		      borderColor: '#36a9e0',
				  		      pointHoverBackgroundColor: '#36a9e0',
				  		      data: viviendas
						  }
						  ]
			  };
			  
			  dayChart.destroy();
			  dayChart = new Chart(ctx1, {
				  type: 'bar',
				  data: dataSet1,
				  options: optionsLine
			  });
			  $('div.daychart').unblock();
		  })
		  .fail(function() {
			  alert( "error" );
			  $('div.daychart').unblock();
		  });
	  }
	  //Finaliza viviendas por dia
	  
	  
	  
	  //Funcion grafico de viviendas por OU
	  function viviendasPorOU (){
		  $('div.ouchart').block({ message: parametros.waitmessage });
		  var ous = [];
		  var totales = [];
		  var habitadas = [];
		  var deshabitadas = [];
		  var cerradas = [];
		  var totalViviendas=0;
		  var totalCuartos=0;
		  var cuartosRociables=0;
		  var totalHabitadas=0;
		  var totalHabitantes=0;
		  var totalDesHabitadas=0;
		  var totalCerradas=0;
		  $.getJSON(parametros.vivPorOUUrl, $('#filters-form').serialize(), function(data) {
			  var table2 = $('#outable').DataTable({
				  dom: 'lBfrtip',
		          "oLanguage": {
		              "sUrl": parametros.dataTablesLang
		          },
		          "bFilter": true, 
		          "bInfo": true, 
		          "bPaginate": true, 
		          "bDestroy": true,
		          "responsive": true,
		          "pageLength": 4,
		          "bLengthChange": false,
		          "responsive": true,
		          "buttons": [
		              {
		                  extend: 'excel'
		              },
		              {
		                  extend: 'pdfHtml5',
		                  orientation: 'portrait',
		                  pageSize: 'LETTER'
		              }
		          ]
		      });
			  table2.clear().draw();
			  for (var row in data) {
				  ous.push([data[row][0]]);
				  totales.push([data[row][1]]);
				  habitadas.push([data[row][2]]);
				  deshabitadas.push([data[row][8]]);
				  cerradas.push([data[row][9]]);
				  table2.row.add([data[row][0], data[row][1], data[row][2], (data[row][2]/data[row][1]*100).toFixed(2), data[row][4], data[row][5], (data[row][5]/data[row][4]*100).toFixed(2)
					  , data[row][7], data[row][8],(data[row][8]/data[row][1]*100).toFixed(2), data[row][9],(data[row][9]/data[row][1]*100).toFixed(2), data[row][10]]);
				  totalViviendas = totalViviendas + data[row][1];
				  totalCuartos = totalCuartos + data[row][4];
				  cuartosRociables = cuartosRociables + data[row][5];
				  totalHabitadas = totalHabitadas + data[row][2];
				  totalHabitantes = totalHabitantes + data[row][7];
				  totalDesHabitadas = totalDesHabitadas + data[row][8];
				  totalCerradas = totalCerradas + data[row][9] + data[row][11];
			  }
			  table2.draw();
			  
			  $('#labelTotCuartos').html(totalCuartos);
			  var porcRociables = (cuartosRociables/totalCuartos*100).toFixed(2);
			  $('#labelCuartosRociables').html(cuartosRociables + " (" + porcRociables +"%)");
			  
			  $('#labelvivhab').html(totalHabitadas + " (" + (totalHabitadas/totalViviendas*100).toFixed(2) +"%)");
			  $('#labelvivtothab').html(totalHabitantes);
			  
			  $('#labelvivnohab').html(totalDesHabitadas + " (" + (totalDesHabitadas/totalViviendas*100).toFixed(2) +"%)");
			  $('#labelvivcerrada').html(totalCerradas + " (" + (totalCerradas/totalViviendas*100).toFixed(2) +"%)");
			  
			  var optionsBar = {
					
					tooltips: {
						mode: 'index',
						intersect: 'false'
					},
					  responsive: true,
					  maintainAspectRatio: false,
					  scales: {
						  xAxes: [{
							  stacked: true
						  }],
						  yAxes: [{
							  stacked: true
						  }]
					  },
					  legend: {
						  display: true
					  }
			  };

			  
			  var barChartData = {
						labels: ous,
						datasets: [{
							label: parametros.vivhab,
							backgroundColor: '#36a9e0',
							data: habitadas
						}, {
							label: parametros.vivdeshab,
							backgroundColor: '#66b37a',
							data:deshabitadas
						}, {
							label: parametros.vivcerrada,
							backgroundColor: '#c2c059',
							data: cerradas
						}]

					};
			  
			  ouChart.destroy();
			  ouChart = new Chart(ctx2, {
				  type: 'bar',
				  data: barChartData,
				  options: optionsBar
			  });
			  $('div.ouchart').unblock();
		  })
		  .fail(function() {
			  alert( "error" );
			  $('div.ouchart').unblock();
		  });
	  }
	  //Finaliza viviendas por OU
	  
	  
	  //Funcion viviendas por Ubi
	  function viviendasPorUbi (){
		  $('div.mapid').block({ message: parametros.waitmessage });
		  $.getJSON(parametros.vivPorUbiUrl, $('#filters-form').serialize(), function(data) {
			  document.getElementById('mapCard').innerHTML = "<div id='mapid' style='width: 100%; height: 600px;'></div>";
			  var mymap = L.map('mapid').setView([14.4474, -90.9388], 15);
			  var theMarker = {};
			  var locMarkers = new L.FeatureGroup();
			  L.tileLayer('http://a.tile.openstreetmap.fr/hot/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
					maxZoom: 18,
					attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
						'<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
						'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
					id: 'mapbox.streets'
				}).addTo(mymap);
			  
			  var PointIcon = L.Icon.extend({
				    options: {
				        iconSize:     [8, 8]
				    }
				});
				
			  var blueIcon = new PointIcon({iconUrl: parametros.iconBlue});
			  var redIcon = new PointIcon({iconUrl: parametros.iconRed});
			  for (var row in data) {
				  var miLat = data[row].latitude;
				  var miLong = data[row].longitude;
				  if(data[row].pasive=='1'.charAt(0)){
					  theMarker = L.marker([miLat, miLong],{url: parametros.censusUrl+"/"+data[row].ident+"/"}).addTo(mymap).setIcon(redIcon).on('click', onClick);
				  }
				  else{
					  theMarker = L.marker([miLat, miLong],{url: parametros.censusUrl+"/"+data[row].ident+"/"}).addTo(mymap).setIcon(blueIcon).on('click', onClick);
				  }
				  theMarker.addTo(locMarkers);
				  theMarker.bindTooltip(data[row].code+ " / " +data[row].ownerName);
			  }
			  mymap.fitBounds(locMarkers.getBounds());
			  var popup = L.popup();
			  function onClick(e) {
				window.location.href = this.options.url;
			  }
			  /*Legend specific*/
			  var legend = L.control({ position: "bottomleft" });

			  legend.onAdd = function(map) {
				  var div = L.DomUtil.create("div", "legend");
				  div.innerHTML += "<h4>Vivienda</h4>";
				  div.innerHTML += '<i class="icon" style="background-image: url('+parametros.iconBlue+');background-repeat: no-repeat;"></i><span>Activa</span><br>';
				  div.innerHTML += '<i class="icon" style="background-image: url('+parametros.iconRed+');background-repeat: no-repeat;"></i><span>Inactiva</span><br>';
				  return div;
			  };

			  legend.addTo(mymap);
			  $('div.mapid').unblock();
		  })
		  .fail(function() {
			  alert( "error" );
			  $('div.mapid').unblock();
		  });
	  }
	  //Finaliza viviendas por OU
	  
	  
	//Funcion grafico de viviendas por Mat
	  function viviendasPorMat (){
		  $('div.matchart').block({ message: parametros.waitmessage });
		  var mats = [];
		  var totales = [];
		  var porcs = [];
		  varTotalGeneral=0;
		  
		  $.getJSON(parametros.vivPorMatUrl, $('#filters-form').serialize(), function(data) {
			  var table3 = $('#mattable').DataTable({
				  dom: 'lBfrtip',
		          "oLanguage": {
		              "sUrl": parametros.dataTablesLang
		          },
		          "bFilter": true, 
		          "bInfo": true, 
		          "bPaginate": true, 
		          "bDestroy": true,
		          "responsive": true,
		          "pageLength": 10,
		          "bLengthChange": false,
		          "responsive": true,
		          "buttons": [
		              {
		                  extend: 'excel'
		              },
		              {
		                  extend: 'pdfHtml5',
		                  orientation: 'portrait',
		                  pageSize: 'LETTER'
		              }
		          ]
		      });
			  table3.clear().draw();
			  for (var row in data) {
				  varTotalGeneral=varTotalGeneral+data[row][1];
			  }
			  for (var row in data) {
				  mats.push([data[row][0]]);
				  totales.push([data[row][1]]);
				  porcs.push([(data[row][1]/varTotalGeneral*100).toFixed(2)]);
				  table3.row.add([data[row][0], data[row][1],(data[row][1]/varTotalGeneral*100).toFixed(2)]);
				  
			  }
			  
			  var pieData = {
					    labels: mats,
					    datasets: [{
					      data: porcs,
					      backgroundColor: [
					        '#877264',
					        '#36a9e0',
					        '#3575e0',
					        '#7559e0',
					        '#9879e0',
					        '#5669e0'
					      ],
					      hoverBackgroundColor: [
					    	'#bdbfab',
					    	'#bdbfab',
					    	'#bdbfab',
					    	'#bdbfab',
					        '#bdbfab',
					        '#bdbfab'
					      ]
					    }]
					  };
			  
			  matChart.destroy();
			  matChart = new Chart(ctx3, {
				  type: 'pie',
				  data: pieData,
				  options: {
				      responsive: true
				    }
			  });
			 
			  $('div.matchart').unblock();
		  })
		  .fail(function() {
			  alert( "error" );
			  $('div.matchart').unblock();
		  });
	  }
	  //Finaliza viviendas por Mat
	  
  }

};

}();