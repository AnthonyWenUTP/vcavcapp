var ProcessDashboardIrs = function () {
	
	
return {
  //main function to initiate the module
  init: function (parametros) {
	  var rociables = 0;
	  var totalRociados=0;
	  $('#area,#district,#localidad,#rociador,#supervisor,#brigada,#foci,#tipoou,#tiempo').select2({
			theme: "bootstrap"
		});
	  
	  $('input[name="fecVisitaRange"]').daterangepicker({
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
	        	$("#fecVisitaRange").prop('disabled', false);
	        }else{
	        	$("#fecVisitaRange").prop('disabled', true);
	        }
	              
	    });
	  
	  $('#temporada').change(function() {
		rociables = 0;
		totalRociados=0;
		estadoTemporada ();
	  	visitasPorDia ();
	  	visitasPorOU ();
	  	visitasPorUbi();
	    });
	  
	  //Estado
	  estadoTemporada();
	  
	  //Por dia
	  var ctx1 = $('#dates-chart');
	  var dayChart = new Chart(ctx1, {});
	  visitasPorDia ();
	  
	  //Por OU
	  var ctx2 = $('#ou-chart');
	  var ouChart = new Chart(ctx2, {});
	  visitasPorOU ();
	  
	  //Por Ubi
	  visitasPorUbi();
	  
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
	        	rociables = 0;
	    		totalRociados=0;
	        	estadoTemporada ();
	        	visitasPorDia ();
	        	visitasPorOU ();
	        	visitasPorUbi();
	        }
		  });
	  
	  
	  Date.prototype.yyyymmdd = function() {         
		  var yyyy = this.getFullYear().toString();                                    
		  var mm = (this.getMonth()+1).toString(); // getMonth() is zero-based         
		  var dd  = this.getDate().toString();             
		  return yyyy + '-' + (mm[1]?mm:"0"+mm[0]) + '-' + (dd[1]?dd:"0"+dd[0]);
	  };
	  
	  //Funcion estado de las metas por temporadas
	  function estadoTemporada (){		  
		  $.getJSON(parametros.irsEstadoUrl, $('#filters-form,#temp-form').serialize(), function(data) {
			  $('#labelTotalMetas').html(data[0][0]-data[0][2]);
			  $('#labelTotalCasas').html(data[0][0]);
			  var porcNovisitadas = ((data[0][1]+data[0][3])/(data[0][0]-data[0][2])*100).toFixed(2);
			  $('#labelNoVisitadas').html(data[0][1]+data[0][3] + " (" + porcNovisitadas +"%)");
			  var porcDescartadas = ((data[0][2])/data[0][0]*100).toFixed(2);
			  $('#labelDescartadas').html(data[0][2] + " (" + porcDescartadas +"%)");
			  var porcCerradas = (data[0][4]/(data[0][0]-data[0][2])*100).toFixed(2);
			  $('#labelCerradas').html(data[0][4] + " (" + porcCerradas +"%)");
			  var porcRenuentes = (data[0][5]/(data[0][0]-data[0][2])*100).toFixed(2);
			  $('#labelRenuentes').html(data[0][5] + " (" + porcRenuentes +"%)");
			  var porcparciales = (data[0][6]/(data[0][0]-data[0][2])*100).toFixed(2);
			  $('#labelRociadasParcial').html(data[0][6] + " (" + porcparciales +"%)");
			  var porccompletas = (data[0][7]/(data[0][0]-data[0][2])*100).toFixed(2);
			  $('#labelRociadas').html(data[0][7] + " (" + porccompletas +"%)");
			  $('#labelRociables').html(data[0][8]);
			  rociables = data[0][8];
			  $('#labelHabitantes').html(data[0][9]);
			  	var porcRociados = (totalRociados/rociables*100).toFixed(2);
				$('#labelRociados').html(totalRociados + " (" + porcRociados +"%)");
		  })
		  .fail(function() {
			  alert( "error" );
		  });

			  
	  }
	  //Finaliza  estado de las metas por temporadas

	  
	  //Funcion grafico de visitas por dia
	  function visitasPorDia (){
		  $('div.daychart').block({ message: parametros.waitmessage });
		  var fechas = [];
		  var visitas = [];
		  var iniciales = [];
		  var seguimientos = [];
		  var totalVisitas=0;
		  var totalIniciales=0;
		  var totalSeguimientos=0;
		  var totalExitos=0;
		  var totalCargas=0;
		  var totalCharlas=0;
		  var totalVPreavisos=0;
		  var totalVRociados=0;
		  
		  $.getJSON(parametros.irsPorDiaUrl, $('#filters-form,#temp-form').serialize(), function(data) {
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
				  visitas.push([data[row][2]]);
				  totalVisitas = totalVisitas + data[row][2];
				  iniciales.push([data[row][3]]);
				  totalIniciales = totalIniciales + data[row][3];
				  seguimientos.push([data[row][4]]);
				  totalSeguimientos = totalSeguimientos + data[row][4];
				  totalExitos = totalExitos + data[row][5];
				  totalRociados = totalRociados + data[row][7];
				  totalCargas = totalCargas + data[row][8];
				  totalCharlas = totalCharlas + data[row][9];
				  totalVPreavisos = totalVPreavisos+ data[row][10];
				  totalVRociados = totalVRociados+ data[row][11];
				  table1.row.add([d1, data[row][2], data[row][3], data[row][4], data[row][5], data[row][10], data[row][11], data[row][7], data[row][8], data[row][9]]);
			  }
			  
			  $('#labelVisitas').html(totalVisitas);
			  var porcPreaviso = (totalVPreavisos/totalVisitas*100).toFixed(2);
			  $('#labelVisitasPre').html(totalVPreavisos + " (" + porcPreaviso +"%)");
			  var porcRociado = (totalVRociados/totalVisitas*100).toFixed(2);
			  $('#labelVisitasRoc').html(totalVRociados + " (" + porcRociado +"%)");
			  var porcIniciales = (totalIniciales/totalVisitas*100).toFixed(2);
			  $('#labelIniciales').html(totalIniciales + " (" + porcIniciales +"%)");
			  var porcSeguimientos = (totalSeguimientos/totalVisitas*100).toFixed(2);
			  $('#labelVisitasSeg').html(totalSeguimientos + " (" + porcSeguimientos +"%)");
			  var porcExitos = (totalExitos/totalVisitas*100).toFixed(2);
			  $('#labelExitosas').html(totalExitos + " (" + porcExitos +"%)");
			  $('#labelCargas').html(totalCargas);
			  $('#labelCharlas').html(totalCharlas);
			  
			  	var porcRociados = (totalRociados/rociables*100).toFixed(2);
				$('#labelRociados').html(totalRociados + " (" + porcRociados +"%)");
			  
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
							  label: parametros.totiniciales,
							  backgroundColor: 'transparent',
				  		      borderColor: 'rgba(0,0,0,0.75)',
				  		      pointBackgroundColor: '#000',
				  		      pointBorderColor: 'rgba(0,0,0,0.75)',
				  		      pointHoverBackgroundColor: '#000',
				  		      borderWidth: 2,
				  		      data: iniciales
						  },
						  {
							  type: 'line',
							  label: parametros.totseg,
							  backgroundColor: 'transparent',
				  		      borderColor: 'rgba(255,255,255,0.75)',
				  		      pointBackgroundColor: '#FFF',
				  		      pointBorderColor: 'rgba(255,255,155,0.75)',
				  		      pointHoverBackgroundColor: '#FFF',
				  		      borderWidth: 2,
				  		      data: seguimientos
						  },
						  {
							  type: 'bar',
				  		      label: parametros.totvisit,
				  		      backgroundColor: '#36a9e0',
				  		      borderColor: '#36a9e0',
				  		      pointHoverBackgroundColor: '#36a9e0',
				  		      data: visitas
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
	  //Finaliza visitas por dia
	  
	  
	  
	  //Funcion grafico de visitas por OU
	  function visitasPorOU (){
		  $('div.ouchart').block({ message: parametros.waitmessage });
		  var ous = [];
		  var totales = [];
		  $.getJSON(parametros.irsPorOUUrl, $('#filters-form,#temp-form').serialize(), function(data) {
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
				  table2.row.add([data[row][0], data[row][1], data[row][2], data[row][3], data[row][4], data[row][9], data[row][10], data[row][6], data[row][7], data[row][8]]);
			  }
			  
			  var optionsBar = {
					  responsive: true,
					  maintainAspectRatio: false,
					  scales: {
						  xAxes: [{
							  gridLines: {
								  drawOnChartArea: false,
							  },
							  scaleLabel:{
								  display: true,
								  labelString:$('#tipoou').val()
							  }
						  }],
						  yAxes: [{
							  ticks: {
								  beginAtZero: true,
				  		          maxTicksLimit: 5,
							  }
						  }]
					  },
					  legend: {
						  display: false
					  }
			  };

			  
			  var dataSet1 = {
					  labels: ous,
					  datasets: [
						  {
							  type: 'bar',
				  		      label: parametros.vivtot,
				  		      backgroundColor: '#36a9e0',
				  		      borderColor: '#36a9e0',
				  		      pointHoverBackgroundColor: '#36a9e0',
				  		      data: totales
						  }
						  ]
			  };
			  
			  ouChart.destroy();
			  ouChart = new Chart(ctx2, {
				  type: 'bar',
				  data: dataSet1,
				  options: optionsBar
			  });
			  $('div.ouchart').unblock();
		  })
		  .fail(function() {
			  alert( "error" );
			  $('div.ouchart').unblock();
		  });
	  }
	  //Finaliza visitas por OU
	  
	  
	  //Funcion visitas por Ubi
	  function visitasPorUbi (){
		  $('div.mapid').block({ message: parametros.waitmessage });
		  $.getJSON(parametros.irsPorUbiUrl, $('#filters-form,#temp-form').serialize(), function(data) {
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
			  var grayIcon = new PointIcon({iconUrl: parametros.iconGray});
			  var greenIcon = new PointIcon({iconUrl: parametros.iconGreen});
			  var blackIcon = new PointIcon({iconUrl: parametros.iconBlack});
			  var purpleIcon = new PointIcon({iconUrl: parametros.iconPurple});
			  var yellowIcon = new PointIcon({iconUrl: parametros.iconYellow});
			  var orangeIcon = new PointIcon({iconUrl: parametros.iconOrange});
			  
			  for (var row in data) {
				  var miLat = data[row].household.latitude;
				  var miLong = data[row].household.longitude;
				  if(data[row].sprayStatus=='NOTVIS'){
					  theMarker = L.marker([miLat, miLong]).addTo(mymap).setIcon(redIcon);
				  }
				  else if(data[row].sprayStatus=='DROPPED'){
					  theMarker = L.marker([miLat, miLong]).addTo(mymap).setIcon(blackIcon);
				  }
				  else if(data[row].sprayStatus=='PENDING'){
					  theMarker = L.marker([miLat, miLong]).addTo(mymap).setIcon(redIcon);
				  }
				  else if(data[row].sprayStatus=='CLOSED'){
					  theMarker = L.marker([miLat, miLong]).addTo(mymap).setIcon(orangeIcon);
				  }
				  else if(data[row].sprayStatus=='RELUCT'){
					  theMarker = L.marker([miLat, miLong]).addTo(mymap).setIcon(purpleIcon);
				  }
				  else if(data[row].sprayStatus=='SPRPAR'){
					  theMarker = L.marker([miLat, miLong]).addTo(mymap).setIcon(yellowIcon);
				  }
				  else if(data[row].sprayStatus=='SPRTOT'){
					  theMarker = L.marker([miLat, miLong]).addTo(mymap).setIcon(greenIcon);
				  }
				  else{
					  theMarker = L.marker([miLat, miLong]).addTo(mymap).setIcon(blackIcon);
				  }
				  theMarker.addTo(locMarkers);
				  theMarker.bindTooltip(data[row].household.code+ " / " +data[row].household.ownerName);
			  }
			  mymap.fitBounds(locMarkers.getBounds());
			  var popup = L.popup();
			  
			  /*Legend specific*/
			  var legend = L.control({ position: "bottomleft" });

			  legend.onAdd = function(map) {
				  var div = L.DomUtil.create("div", "legend");
				  div.innerHTML += "<h4>Meta</h4>";
				  div.innerHTML += '<i class="icon" style="background-image: url('+parametros.iconGreen+');background-repeat: no-repeat;"></i><span>Rociada completa</span><br>';
				  div.innerHTML += '<i class="icon" style="background-image: url('+parametros.iconYellow+');background-repeat: no-repeat;"></i><span>Rociada parcial</span><br>';
				  div.innerHTML += '<i class="icon" style="background-image: url('+parametros.iconPurple+');background-repeat: no-repeat;"></i><span>Renuente</span><br>';
				  div.innerHTML += '<i class="icon" style="background-image: url('+parametros.iconOrange+');background-repeat: no-repeat;"></i><span>Cerrada</span><br>';
				  div.innerHTML += '<i class="icon" style="background-image: url('+parametros.iconRed+');background-repeat: no-repeat;"></i><span>Pediente</span><br>';
				  div.innerHTML += '<i class="icon" style="background-image: url('+parametros.iconBlack+');background-repeat: no-repeat;"></i><span>Destruida/No rociable	</span><br>';
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
	  //Finaliza visitas por OU
	  
	  
	
  }

};

}();