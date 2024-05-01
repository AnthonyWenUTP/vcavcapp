var ProcessSearch = function () {
	
return {
  //main function to initiate the module
  init: function (parametros) {	
	  
	$('#localidad, #temporada').select2({
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

	    });
	
	  processReport();
	  processReport2();
	
	
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
	        	processReport();
	        	processReport2();
	        }
		  });
  
  Date.prototype.yyyymmdd = function() {         
      
      var yyyy = this.getFullYear().toString();                                    
      var mm = (this.getMonth()+1).toString(); // getMonth() is zero-based         
      var dd  = this.getDate().toString();             
                          
      return yyyy + '-' + (mm[1]?mm:"0"+mm[0]) + '-' + (dd[1]?dd:"0"+dd[0]);
 };
	  
  function processReport(){
	  $.blockUI({ message: parametros.waitmessage });
	  $.getJSON(parametros.updateUrl, $('#filters-form,#consumo-form').serialize(), function(data) {
		  var table1 = $('#resultados').DataTable({
			  dom: 'lBfrtip',
	          "oLanguage": {
	              "sUrl": parametros.dataTablesLang
	          },
	          "bFilter": true, 
	          "bInfo": true, 
	          "bPaginate": true, 
	          "bDestroy": true,
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
		  table1.clear().draw();
		if (data == ''){
			toastr.info(data, parametros.noResults, {
				closeButton: true,
				progressBar: true,
			 });
		}
		else{
			for (var row in data) {
				var d1 = (new Date(data[row][0])).yyyymmdd();
				var razon = (data[row][4]/data[row][2]).toFixed(2);
				table1.row.add([d1,data[row][1],data[row][2],data[row][3],data[row][4],razon]);
			}
			
		}
		$.unblockUI();
	})
	.fail(function() {
	    alert( "error" );
	    $.unblockUI();
	});
  }
  
  
  function processReport2(){
	  $.blockUI({ message: parametros.waitmessage });
	  $.getJSON(parametros.updateUrl2, $('#filters-form,#consumo-form').serialize(), function(data) {
		  var table1 = $('#resultadossemana').DataTable({
			  dom: 'lBfrtip',
	          "oLanguage": {
	              "sUrl": parametros.dataTablesLang
	          },
	          "bFilter": true, 
	          "bInfo": true, 
	          "bPaginate": true, 
	          "bDestroy": true,
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
		  table1.clear().draw();
		if (data == ''){
			toastr.info(data, parametros.noResults, {
				closeButton: true,
				progressBar: true,
			 });
		}
		else{
			for (var row in data) {
				var d1 = (new Date(data[row][0])).yyyymmdd();
				var razon = (data[row][4]/data[row][2]).toFixed(2);
				table1.row.add([data[row][0],data[row][1],data[row][2],data[row][3],data[row][4],razon]);
			}
			
		}
		$.unblockUI();
	})
	.fail(function() {
	    alert( "error" );
	    $.unblockUI();
	});
  }
  
  
  }
 };
}();
