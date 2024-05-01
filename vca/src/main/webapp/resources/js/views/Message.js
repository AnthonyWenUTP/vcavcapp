var ProcessSearch = function () {
	
return {
  //main function to initiate the module
  init: function (parametros) {	

  $('#search-message-form').validate( {
	    rules: {
	      messageParameter: {
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
	  
  function processReport(){
	  $.blockUI({ message: parametros.waitmessage });
	  $.getJSON(parametros.messagesUrl, $('#search-message-form').serialize(), function(data) {
		  var table1 = $('#lista_mensajes').DataTable({
	          "oLanguage": {
	              "sUrl": parametros.dataTablesLang
	          },
	          "scrollX": true,
	          "bFilter": true, 
	          "bInfo": true, 
	          "bPaginate": true, 
	          "bDestroy": true,
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
				var editUrl = parametros.editMessagesUrl + data[row].messageKey+'/';
				btnEdit = '<a title="edit" href=' + editUrl + ' class="btn btn-xs btn-primary" ><i class="fa fa-edit"></i></a>';
				table1.row.add([data[row].messageKey, data[row].spanish, data[row].english, btnEdit]);
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
