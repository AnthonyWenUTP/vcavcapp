var ProcessMapDashboard = function () {
	
	
return {
  //main function to initiate the module
  init: function (parametros) {
	  
	  $('#area,#district,#localidad,#foci,#tipoou,#tiempo,#estado').select2({
			theme: "bootstrap"
		});
	  
	  $('input[name="fecMuestraRange"]').daterangepicker({
		   ranges: {
		     'Hoy': [moment(), moment()],
		 'Ayer': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
		 'Ultimos 7 Dias': [moment().subtract(6, 'days'), moment()],
		 'Ultimos 60 Dias': [moment().subtract(59, 'days'), moment()],
		 'Este mes': [moment().startOf('month'), moment().endOf('month')],
		 'Mes pasado': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
		   },
		   singleDatePicker: false,
		   startDate: moment().subtract(59, 'days'),
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
	  
	  
	  //Por dia
	  var ctx1 = $('#dates-chart');
	  var dayChart = new Chart(ctx1, {});
	  casosPorDia ();
	  
	  //Por OU
	  var ctx2 = $('#ou-chart');
	  var ouChart = new Chart(ctx2, {});
	  casosPorOU ();
	  
	  //Por Estado
	  var ctx3 = $('#est-chart');
	  var estChart = new Chart(ctx3, {});
	  casosPorEstado ();
	  
	  //Por Ubi
	  casosPorUbi();
	  
	  $( '#filters-form' ).validate( {
		    rules: {
		      localidad: {
		    	  required: true
		      },
		      fecMuestraRange:{
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
	        	casosPorDia ();
	        	casosPorOU ();
	        	casosPorEstado ();
	        	casosPorUbi();
	        }
		  });
	  
	  
	  Date.prototype.yyyymmdd = function() {         
		  var yyyy = this.getFullYear().toString();                                    
		  var mm = (this.getMonth()+1).toString(); // getMonth() is zero-based         
		  var dd  = this.getDate().toString();             
		  return yyyy + '-' + (mm[1]?mm:"0"+mm[0]) + '-' + (dd[1]?dd:"0"+dd[0]);
	  };

	  
	  //Funcion grafico de casos por dia de muestra
	  function casosPorDia (){
		  $('div.daychart').block({ message: parametros.waitmessage });
		  var fechas = [];
		  var casos = [];
		  var investigados = [];
		  var txiniciado = [];
		  var txcompleto = [];
		  var sx2sem = [];
		  var sx4sem = [];
		  var supervisados = [];
		  var lost = [];
		  var totalCasos=0;
		  var totalInvestigados=0;
		  var totalTxIniciado=0;
		  var totalTxCompleto=0;
		  var totalSx2Sem=0;
		  var totalSx4Sem=0;
		  var totalSupervisados=0;
		  var totalLost=0;
		  $.getJSON(parametros.casosPorDiaUrl, $('#filters-form').serialize(), function(data) {
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
				  casos.push([data[row][2]]);
				  totalCasos = totalCasos + data[row][2];
				  investigados.push([data[row][3]]);
				  totalInvestigados = totalInvestigados + data[row][3];
				  txiniciado.push([data[row][4]]);
				  totalTxIniciado = totalTxIniciado + data[row][4];
				  txcompleto.push([data[row][5]]);
				  totalTxCompleto = totalTxCompleto + data[row][5];
				  sx2sem.push([data[row][6]]);
				  totalSx2Sem = totalSx2Sem + data[row][6];
				  sx4sem.push([data[row][7]]);
				  totalSx4Sem = totalSx4Sem + data[row][7];
				  supervisados.push([data[row][8]]);
				  totalSupervisados = totalSupervisados + data[row][8];
				  lost.push([data[row][9]]);
				  totalLost = totalLost + data[row][9];
				  table1.row.add([d1, data[row][2], data[row][3],(data[row][3]/data[row][2]*100).toFixed(2)
					  , data[row][4],(data[row][4]/data[row][2]*100).toFixed(2), data[row][5],(data[row][5]/data[row][4]*100).toFixed(2), data[row][4]-data[row][8],(data[row][8]/data[row][4]*100).toFixed(2)
					  , data[row][6],(data[row][6]/data[row][4]*100).toFixed(2), data[row][7],(data[row][7]/data[row][4]*100).toFixed(2)
					  , data[row][9],(data[row][9]/data[row][2]*100).toFixed(2)]);
			  }
			  var porcInv = (totalInvestigados/totalCasos*100).toFixed(2);
			  $('#labelTotCas').html(totalCasos);
			  $('#labelPorInv').html(totalInvestigados + " (" + porcInv +"%)");
			  var porcTx = (totalTxIniciado/totalCasos*100).toFixed(2);
			  $('#labelPorTx').html(totalTxIniciado + " (" + porcTx +"%)");
			  var porcTxComp = (totalTxCompleto/totalTxIniciado*100).toFixed(2);
			  $('#labelPorTxCom').html(totalTxCompleto + " (" + porcTxComp +"%)");
			  var porcSx = (totalSx2Sem/totalTxIniciado*100).toFixed(2);
			  $('#labelPorSx').html(totalSx2Sem + " (" + porcSx +"%)");
			  var porcSxComp = (totalSx4Sem/totalTxIniciado*100).toFixed(2);
			  $('#labelPorSxCom').html(totalSx4Sem + " (" + porcSxComp +"%)");
			  
			  var porcTxSup = (totalSupervisados/totalTxIniciado*100).toFixed(2);
			  $('#labelPorTxSup').html(totalSupervisados + " (" + porcTxSup +"%)");
			  
			  var porcLost = (totalLost/totalCasos*100).toFixed(2);
			  $('#labelPorLost').html(totalLost + " (" + porcLost +"%)");
			  
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
						  display: true
					  }
			  };
			  
			  var dataSet1 = {
					  labels: fechas,
					  datasets: [
						  {
							  type: 'bar',
				  		      label: parametros.casostot,
				  		      backgroundColor: '#F07B7B',
				  		      borderColor: '#F07B7B',
				  		      pointHoverBackgroundColor: '#F07B7B',
				  		      data: casos
						  },
						  {
							  type: 'bar',
				  		      label: parametros.inv,
				  		      backgroundColor: '#d7bfbf',
				  		      borderColor: '#d7bfbf',
				  		      pointHoverBackgroundColor: '#d7bfbf',
				  		      data: investigados
						  },
						  {
							  type: 'bar',
				  		      label: parametros.tx,
				  		      backgroundColor: '#f5c01c',
				  		      borderColor: '#f5c01c',
				  		      pointHoverBackgroundColor: '#f5c01c',
				  		      data: txiniciado
						  },
						  {
							  type: 'bar',
				  		      label: parametros.txComp,
				  		      backgroundColor: '#71b9fc',
				  		      borderColor: '#71b9fc',
				  		      pointHoverBackgroundColor: '#71b9fc',
				  		      data: txcompleto
						  },
						  {
							  type: 'bar',
				  		      label: parametros.sx,
				  		      backgroundColor: '#08DD8F',
				  		      borderColor: '#08DD8F',
				  		      pointHoverBackgroundColor: '#08DD8F',
				  		      data: sx2sem
						  },
						  {
							  type: 'bar',
				  		      label: parametros.sxComp,
				  		      backgroundColor: '#2E6D56',
				  		      borderColor: '#2E6D56',
				  		      pointHoverBackgroundColor: '#2E6D56',
				  		      data: sx4sem
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
	  //Finaliza casos por dia
	  
	  
	  
	  //Funcion grafico de casos por OU
	  function casosPorOU (){
		  $('div.ouchart').block({ message: parametros.waitmessage });
		  var ous = [];
		  var totales = [];
		  var investigados = [];
		  var txiniciado = [];
		  var txcompleto = [];
		  var seg2sem = [];
		  var seg4sem = [];
		  var supervisados = [];
		  var lost = [];
		  $.getJSON(parametros.casosPorOUUrl, $('#filters-form').serialize(), function(data) {
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
				  investigados.push([data[row][2]]);
				  txiniciado.push([data[row][3]]);
				  txcompleto.push([data[row][4]]);
				  seg2sem.push([data[row][5]]);
				  seg4sem.push([data[row][6]]);
				  supervisados.push([data[row][7]]);
				  lost.push([data[row][8]]);
				  
				  table2.row.add([data[row][0], data[row][1], data[row][2], (data[row][2]/data[row][1]*100).toFixed(2),
					   data[row][3], (data[row][3]/data[row][1]*100).toFixed(2),data[row][4], (data[row][4]/data[row][3]*100).toFixed(2),data[row][7], (data[row][7]/data[row][3]*100).toFixed(2)
					   ,data[row][5], (data[row][5]/data[row][3]*100).toFixed(2),data[row][6], (data[row][6]/data[row][3]*100).toFixed(2),data[row][8], (data[row][8]/data[row][1]*100).toFixed(2)]);
			  }
			  table2.draw();
			  
			  var optionsBar = {
					
					tooltips: {
						mode: 'index',
						intersect: 'false'
					},
					  responsive: true,
					  maintainAspectRatio: false,
					  scales: {
						  xAxes: [{
							  stacked: false
						  }],
						  yAxes: [{
							  stacked: false
						  }]
					  },
					  legend: {
						  display: true
					  }
			  };

			  
			  var barChartData = {
						labels: ous,
						datasets: [{
							label: parametros.casostot,
							backgroundColor: '#F07B7B',
							data: totales
						}, {
							label: parametros.inv,
							backgroundColor: '#d7bfbf',
							data:investigados
						}, {
							label: parametros.tx,
							backgroundColor: '#f5c01c',
							data:txiniciado
						}, {
							label: parametros.txComp,
							backgroundColor: '#71b9fc',
							data:txcompleto
						}, {
							label: parametros.sx,
							backgroundColor: '#08DD8F',
							data:seg2sem
						}, {
							label: parametros.sxComp,
							backgroundColor: '#2E6D56',
							data:seg4sem
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
	  //Finaliza casos por OU
	  
	  
	  //Funcion grafico de casos por Estado
	  function casosPorEstado (){
		  $('div.estchart').block({ message: parametros.waitmessage });
		  var ous = [];
		  var totales = [];
		  var positivos = [];
		  var tratados = [];
		  var completos = [];
		  var seg2sem = [];
		  var seg4sem = [];
		  var recaidas = [];
		  var perdidos = [];
		  $.getJSON(parametros.casosPorEstadoUrl, $('#filters-form').serialize(), function(data) {
			  var table3 = $('#esttable').DataTable({
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
			  table3.clear().draw();
			  for (var row in data) {
				  ous.push([data[row][0]]);
				  totales.push([(data[row][1]/data[row][1]*100).toFixed(2)]);
				  positivos.push([(data[row][2]/data[row][1]*100).toFixed(2)]);
				  tratados.push([(data[row][3]/data[row][1]*100).toFixed(2)]);
				  completos.push([(data[row][4]/data[row][1]*100).toFixed(2)]);
				  seg2sem.push([(data[row][5]/data[row][1]*100).toFixed(2)]);
				  seg4sem.push([(data[row][6]/data[row][1]*100).toFixed(2)]);
				  recaidas.push([(data[row][7]/data[row][1]*100).toFixed(2)]);
				  perdidos.push([(data[row][8]/data[row][1]*100).toFixed(2)]);
				  table3.row.add([data[row][0], data[row][1], data[row][2], (data[row][2]/data[row][1]*100).toFixed(2)
					  , data[row][3], (data[row][3]/data[row][1]*100).toFixed(2), data[row][4], (data[row][4]/data[row][1]*100).toFixed(2)
					  , data[row][5], (data[row][5]/data[row][1]*100).toFixed(2), data[row][6], (data[row][6]/data[row][1]*100).toFixed(2)
					  , data[row][7], (data[row][7]/data[row][1]*100).toFixed(2), data[row][8], (data[row][8]/data[row][1]*100).toFixed(2)]);
			  }
			  table3.draw();
			  
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
							label: parametros.EST1,
							backgroundColor: '#F07B7B',
							data: positivos
						}, {
							label: parametros.EST2,
							backgroundColor: '#f5c01c',
							data:tratados
						}, {
							label: parametros.EST3,
							backgroundColor: '#71b9fc',
							data:completos
						}, {
							label: parametros.EST4,
							backgroundColor: '#08DD8F',
							data:seg2sem
						}, {
							label: parametros.EST5,
							backgroundColor: '#2E6D56',
							data:seg4sem
						}, {
							label: parametros.EST6,
							backgroundColor: '#d847f5',
							data:recaidas
						}, {
							label: parametros.EST7,
							backgroundColor: '#000000',
							data:perdidos
						}]

					};
			  
			  estChart.destroy();
			  estChart = new Chart(ctx3, {
				  type: 'bar',
				  data: barChartData,
				  options: optionsBar
			  });
			  $('div.estchart').unblock();
		  })
		  .fail(function() {
			  alert( "error" );
			  $('div.estchart').unblock();
		  });
	  }
	  //Finaliza casos por estado
	  
	  
	  //Funcion casos por Ubi
	  function casosPorUbi (){
		  $('div.mapid').block({ message: parametros.waitmessage });
		  $.getJSON(parametros.casosPorUbiUrl1, $('#filters-form').serialize(), function(data) {
			  document.getElementById('mapCard').innerHTML = "<div id='mapid' style='width: 100%; height: 480px;'></div>";
			  
			  var theMarker = {};
			  var locMarkers = new L.FeatureGroup();
			  var pdxMarkers = new L.FeatureGroup();
			  
			  var baseLayer = L.tileLayer('http://tile.openstreetmap.org/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
					maxZoom: 18,
					attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
						'<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
						'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
					id: 'mapbox.streets'
				})
			  
			  var mymap = L.map('mapid', {
				    center: [14.4474, -90.9388],
				    zoom: 10,
				    layers: [baseLayer, locMarkers, pdxMarkers],
				    fullscreenControl: true,
				    fullscreenControlOptions: {
				      position: 'topleft'
				    }
				});
			  
			  var PointIconPdx = L.Icon.extend({
				    options: {
				        iconSize:     [16, 16]
				    }
				});

				
			  var pdxsIcon = new PointIconPdx({iconUrl: parametros.iconPdxs});
			  
			  var redIcon = new L.Icon({
				  iconUrl: parametros.iconRed,
				  shadowUrl: parametros.iconShadow,
				  iconSize: [25, 41],
				  iconAnchor: [12, 41],
				  popupAnchor: [1, -34],
				  shadowSize: [41, 41]
				});
			  
			  var orangeIcon = new L.Icon({
				  iconUrl: parametros.iconOrange,
				  shadowUrl: parametros.iconShadow,
				  iconSize: [25, 41],
				  iconAnchor: [12, 41],
				  popupAnchor: [1, -34],
				  shadowSize: [41, 41]
				});
			  
			  var blueIcon = new L.Icon({
				  iconUrl: parametros.iconBlue,
				  shadowUrl: parametros.iconShadow,
				  iconSize: [25, 41],
				  iconAnchor: [12, 41],
				  popupAnchor: [1, -34],
				  shadowSize: [41, 41]
				});
			  
			  var yellowIcon = new L.Icon({
				  iconUrl: parametros.iconYellow,
				  shadowUrl: parametros.iconShadow,
				  iconSize: [25, 41],
				  iconAnchor: [12, 41],
				  popupAnchor: [1, -34],
				  shadowSize: [41, 41]
				});
			  
			  var greenIcon = new L.Icon({
				  iconUrl: parametros.iconGreen,
				  shadowUrl: parametros.iconShadow,
				  iconSize: [25, 41],
				  iconAnchor: [12, 41],
				  popupAnchor: [1, -34],
				  shadowSize: [41, 41]
				});
			  
			  var purpleIcon = new L.Icon({
				  iconUrl: parametros.iconPurple,
				  shadowUrl: parametros.iconShadow,
				  iconSize: [25, 41],
				  iconAnchor: [12, 41],
				  popupAnchor: [1, -34],
				  shadowSize: [41, 41]
				});
			  
			  var grayIcon = new L.Icon({
				  iconUrl: parametros.iconGray,
				  shadowUrl: parametros.iconShadow,
				  iconSize: [25, 41],
				  iconAnchor: [12, 41],
				  popupAnchor: [1, -34],
				  shadowSize: [41, 41]
				});
			  
			  var blackIcon = new L.Icon({
				  iconUrl: parametros.iconBlack,
				  shadowUrl: parametros.iconShadow,
				  iconSize: [25, 41],
				  iconAnchor: [12, 41],
				  popupAnchor: [1, -34],
				  shadowSize: [41, 41]
				});
			  
			  for (var row in data.puntoDiagnosticos) {
				  var miLat = data.puntoDiagnosticos[row].latitude;
				  var miLong = data.puntoDiagnosticos[row].longitude;
				  var subtitulo = "Localidad: "+ data.puntoDiagnosticos[row].local.name+ " <br> Tipo:" +data.puntoDiagnosticos[row].tipo+ " <br> Clave:" +data.puntoDiagnosticos[row].clave+ " <br> Info:" +data.puntoDiagnosticos[row].info;
				  theMarker = L.marker([miLat, miLong],{url: parametros.censusUrl+"/"+data.puntoDiagnosticos[row].ident+"/"}).addTo(mymap).setIcon(pdxsIcon).on('click', onClick);
				  theMarker.addTo(pdxMarkers);
				  theMarker.bindTooltip(subtitulo);
			  }
			  
			  for (var row in data.casos) {
				  var miLat = data.casos[row].latitude;
				  var miLong = data.casos[row].longitude;
				  var d1 = (new Date(data.casos[row].fisDate)).yyyymmdd();
				  var d2 = (new Date(data.casos[row].mxDate)).yyyymmdd();
				  var d3 = "";
				  if(data.casos[row].invDate!=null){
					  d3 = " <br> Fec Inv:" + (new Date(data.casos[row].invDate)).yyyymmdd();
				  }
				  var d4 = "";
				  if(data.casos[row].txDate!=null){
					  d4 = " <br> Fec Inicio:" + (new Date(data.casos[row].txDate)).yyyymmdd();
				  }
				  var d5 = "";
				  if(data.casos[row].txCompDate!=null){
					  d5 = " <br> Fec Completo:" + (new Date(data.casos[row].txCompDate)).yyyymmdd();
				  }
				  var d6 = "";
				  if(data.casos[row].sxDate!=null){
					  d6 = " <br> Fec primer seg:" + (new Date(data.casos[row].sxDate)).yyyymmdd();
				  }
				  var d7 = "";
				  if(data.casos[row].sxCompDate!=null){
					  d7 = " <br> Fec segundo seg:" + (new Date(data.casos[row].sxCompDate)).yyyymmdd();
				  }
				  var subtitulo ="Codigo:"+ data.casos[row].codigo+ " <br> FIS:" +d1+ " <br> Fec Mx:" +d2 
				  					+ " <br> " + parametros.inv +": " +data.casos[row].inv + d3 
				  					+ " <br> " + parametros.tx +": " +data.casos[row].tx + d4 
				  					+ " <br> " + parametros.txSup +": " +data.casos[row].txSup  
				  					+ " <br> " + parametros.txComp +": " +data.casos[row].txComp + d5 
				  					+ " <br> " + parametros.sx +": " +data.casos[row].sx + d6
				  					+ " <br> " + parametros.sxComp +": " +data.casos[row].sxComp + d7
				  					+ " <br> " + parametros.lostFollowUp +": " +data.casos[row].lostFollowUp;
				  if(data.casos[row].estadocaso=='CONF'){
					  theMarker = L.marker([miLat, miLong],{url: parametros.verCasoUrl+"/"+data.casos[row].ident+"/"}).addTo(mymap).setIcon(redIcon).on('click', onClick);
				  }
				  else if(data.casos[row].estadocaso=='TRAT') {
					  theMarker = L.marker([miLat, miLong],{url: parametros.verCasoUrl+"/"+data.casos[row].ident+"/"}).addTo(mymap).setIcon(orangeIcon).on('click', onClick);
				  }
				  else if(data.casos[row].estadocaso=='TRATC') {
					  theMarker = L.marker([miLat, miLong],{url: parametros.verCasoUrl+"/"+data.casos[row].ident+"/"}).addTo(mymap).setIcon(blueIcon).on('click', onClick);
				  }
				  else if(data.casos[row].estadocaso=='SEG2') {
					  theMarker = L.marker([miLat, miLong],{url: parametros.verCasoUrl+"/"+data.casos[row].ident+"/"}).addTo(mymap).setIcon(yellowIcon).on('click', onClick);
				  }
				  else if(data.casos[row].estadocaso=='SEG4') {
					  theMarker = L.marker([miLat, miLong],{url: parametros.verCasoUrl+"/"+data.casos[row].ident+"/"}).addTo(mymap).setIcon(greenIcon).on('click', onClick);
				  }
				  else if(data.casos[row].estadocaso=='SEGPOS') {
					  theMarker = L.marker([miLat, miLong],{url: parametros.verCasoUrl+"/"+data.casos[row].ident+"/"}).addTo(mymap).setIcon(purpleIcon).on('click', onClick);
				  }
				  else if(data.casos[row].estadocaso=='SEGINC') {
					  theMarker = L.marker([miLat, miLong],{url: parametros.verCasoUrl+"/"+data.casos[row].ident+"/"}).addTo(mymap).setIcon(grayIcon).on('click', onClick);
				  }
				  theMarker.addTo(locMarkers);
				  theMarker.bindTooltip(subtitulo);
			  }
			  
			  if(data.casos.length > 0){
				  mymap.fitBounds(locMarkers.getBounds());  
			  }
			  else if(data.puntoDiagnosticos.length > 0){
				  mymap.fitBounds(pdxMarkers.getBounds());  
			  }
			  
			  
			  var popup = L.popup();
			  function onClick(e) {
				window.location.href = this.options.url;
			  }
			  
			  var legendCriadero = L.control({ position: "bottomleft" });

			  legendCriadero.onAdd = function(map) {
				  var div = L.DomUtil.create("div", "legend");
				  div.innerHTML += "<h4>Criaderos</h4>";
				  div.innerHTML += '<i style="width:10px;height:10px;border:2px solid #e02222;"></i><span> Productivos </span><br>';
				  div.innerHTML += '<i style="width:10px;height:10px;border:2px solid #ebbc21;"></i><span> Potenciales </span><br>';
				  return div;
			  };
			  legendCriadero.addTo(mymap);
			  
			  /*Legend specific*/
			  var legend = L.control({ position: "bottomleft" });

			  legend.onAdd = function(map) {
				  var div = L.DomUtil.create("div", "legend");
				  div.innerHTML += "<h4>Casos en seguimiento</h4>";
				  div.innerHTML += '<i class="icon" style="background-image: url('+parametros.iconRed+');background-repeat: no-repeat;"></i><span>'
				  div.innerHTML +=  parametros.EST1 +'</span><br>';
				  div.innerHTML += '<i class="icon" style="background-image: url('+parametros.iconOrange+');background-repeat: no-repeat;"></i><span>';
				  div.innerHTML +=  parametros.EST2 +'</span><br>';
				  div.innerHTML += '<i class="icon" style="background-image: url('+parametros.iconBlue+');background-repeat: no-repeat;"></i><span>';
				  div.innerHTML +=  parametros.EST3 +'</span><br>';
				  div.innerHTML += '<i class="icon" style="background-image: url('+parametros.iconYellow+');background-repeat: no-repeat;"></i><span>';
				  div.innerHTML +=  parametros.EST4 +'</span><br>';
				  div.innerHTML += '<i class="icon" style="background-image: url('+parametros.iconGreen+');background-repeat: no-repeat;"></i><span>';
				  div.innerHTML +=  parametros.EST5 +'</span><br>';
				  div.innerHTML += '<i class="icon" style="background-image: url('+parametros.iconPurple+');background-repeat: no-repeat;"></i><span>';
				  div.innerHTML +=  parametros.EST6 +'</span><br>';
				  div.innerHTML += '<i class="icon" style="background-image: url('+parametros.iconGray+');background-repeat: no-repeat;"></i><span>';
				  div.innerHTML +=  parametros.EST7 +'</span><br>';
				  return div;
			  };
			  
			  /*Legend specific dx points*/
			  var legendDx = L.control({ position: "bottomright" });

			  legendDx.onAdd = function(map) {
				  var div = L.DomUtil.create("div", "legend");
				  div.innerHTML += '<i class="icon" style="background-image: url('+parametros.iconPdxs+');background-repeat: no-repeat;"></i><span>Puntos de diagnóstico</span><br>';
				  return div;
			  };
			  
			  legendDx.addTo(mymap);
			  legend.addTo(mymap);
			  
			  
			  //Layer control
			  var baseMaps = {
			    "Mapa base": baseLayer
			  };

			  var overlayMaps = {
			    "Casos": locMarkers,
			    "Puntos Dx": pdxMarkers,
			  };
			  
			  L.control.layers(baseMaps, overlayMaps).addTo(mymap);
			  
			  
			  for (var row in data.focos) {
				  var coordinates = [];
				  var puntos = [];
				  for(var punto in data.focos[row].coordinates){
					var miLat = data.focos[row].coordinates[punto].lat;
					var miLong = data.focos[row].coordinates[punto].lng;
					if(!(miLat == "" || miLong == "")){
						var punto = [];
						punto.push(miLong);
						punto.push(miLat);
						puntos.push(punto);
					}
				  }
				  coordinates.push(puntos);
				  var poligonFoco = [{
					    "type": "Feature",
					    "properties": {"name": data.focos[row].name,
					    	"popupContent": data.focos[row].name},
					    "geometry": {
					        "type": "Polygon",
					        "coordinates": coordinates
					    }
					}];
				
			  
				  L.geoJSON(poligonFoco, {
					    style: function(feature) {
					        switch (feature.properties.name) {
					            case data.focos[row].name: return {color: data.focos[row].color};
					        }
					    }
					}).addTo(mymap).bindTooltip(data.focos[row].name);
				  
				  var legendFoco = L.control({ position: "bottomright" });

				  legendFoco.onAdd = function(map) {
					  var div = L.DomUtil.create("div", "legend");
					  div.innerHTML += '<i style="width:10px;height:10px;border:2px solid '+data.focos[row].color+';"></i><span>'+data.focos[row].name+'</span><br>';
					  return div;
				  };
				  legendFoco.addTo(mymap);
			  }
			  
			  
			  for (var row in data.criaderos) {
				  var coordinates = [];
				  var puntos = [];
				  for(var punto in data.criaderos[row].coordinates){
					var miLat = data.criaderos[row].coordinates[punto].lat;
					var miLong = data.criaderos[row].coordinates[punto].lng;
					if(!(miLat == "" || miLong == "")){
						var punto = [];
						punto.push(miLong);
						punto.push(miLat);
						puntos.push(punto);
					}
				  }
				  coordinates.push(puntos);
				  var poligonCriadero = [{
					    "type": "Feature",
					    "properties": {"name": data.criaderos[row].name,
					    	"popupContent": data.criaderos[row].name},
					    "geometry": {
					        "type": "Polygon",
					        "coordinates": coordinates
					    }
					}];
				
			  
				  L.geoJSON(poligonCriadero, {
					    style: function(feature) {
					        switch (feature.properties.name) {
					            case data.criaderos[row].name: return {color: data.criaderos[row].color};
					        }
					    }
					}).addTo(mymap).bindTooltip(data.criaderos[row].name);
				  
			  }
			  
			  
			  mymap.on('overlayadd', function (eventLayer) {
				    if (eventLayer.name === 'Casos') {
				    	legend.addTo(this);
				    }
				    else if (eventLayer.name === 'Puntos Dx') {
				    	legendDx.addTo(this);
				    }
				});
			  
			  mymap.on('overlayremove', function (eventLayer) {
				    if (eventLayer.name === 'Casos') {
				        this.removeControl(legend);
				    }
				    else if (eventLayer.name === 'Puntos Dx') {
				    	this.removeControl(legendDx);
				    }
				});

			  $('div.mapid').unblock();
		  })
		  .fail(function() {
			  alert( "error" );
			  $('div.mapid').unblock();
		  });
	  }
	  //Finaliza casos por OU
	  
	
  }

};

}();