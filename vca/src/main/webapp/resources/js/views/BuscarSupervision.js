var ProcessSearch = function () {
	
return {
  //main function to initiate the module
  init: function (parametros) {	
	  
	$('#local, #irsSeason,#supervisor,#rociador').select2({
		theme: "bootstrap"
	});
	
	$('#supdiv').hide();
	
	$('input[name="fecSupervisionRange"]').daterangepicker({
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
	
	$('#checkId').change(function() {
        if(this.checked) {
        	$("#codeHouse").prop('disabled', false);
        }else{
        	$("#codeHouse").prop('disabled', true);
        }
              
    });
	
	$('#checkName').change(function() {
        if(this.checked) {
        	$("#ownerName").prop('disabled', false);
        }else{
        	$("#ownerName").prop('disabled', true);
        }
              
    });
	
	$('#checkDates').change(function() {
        if(this.checked) {
        	$("#fecSupervisionRange").prop('disabled', false);
        }else{
        	$("#fecSupervisionRange").prop('disabled', true);
        }
              
    });

  $( '#supervisiones-form' ).validate( {
	    rules: {
	      codeHouse: {
	    	  required: true
	      },
	      ownerName: {
	    	  required: true
	      },
	      local: {
	          required: true
	      },
	      fecSupervisionRange: {
	          required: true
	      },
	      irsSeason: {
	    	  required: true
	      },
	      local: {
	          required: true
	      },
	      supervisor: {
	          required: true
	      },
	      rociador: {
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
	  $.getJSON(parametros.searchUrl, $('#supervisiones-form').serialize(), function(data) {
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
				var d1 = (new Date(data[row].supervisionDate)).yyyymmdd();
				var d2 = (new Date(data[row].recordDate)).yyyymmdd();
				var viewUrl = parametros.supervisionUrl  + data[row].ident+'/';
				btnview = '<a title="" href=' + viewUrl + ' class="btn btn-xs btn-primary" ><i class="icon-magnifier"></i></a>';
				codeview = '<a title="" href=' + viewUrl + '>'+ data[row].target.household.code+ '</a>';
				
				table1.row.add([data[row].target.household.local.name, codeview,data[row].target.household.ownerName,data[row].target.irsSeason.name,d1,
					data[row].supervisor.name,data[row].rociador.name,data[row].pasive,data[row].recordUser,d2,btnview]);
			}
			$('#supdiv').show();
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
