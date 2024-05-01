var ProcessMensaje = function () {
	
return {
  //main function to initiate the module
  init: function (parametros) {
	  
  $.validator.setDefaults( {
    submitHandler: function () {
    	processMensaje();
    }
  } );	
  jQuery.validator.addMethod("noSpace", function(value, element) { 
		  return value.indexOf(" ") < 0 && value != ""; 
	}, "Invalid");
  $( '#edit-mensaje-form' ).validate( {
    rules: {
      messageKey: {
    	  minlength: 1,
          maxlength: 100,
          required: true
      },
      spanish: {
    	  minlength: 1,
          maxlength: 255,
          required: true
      },
      english: {
    	  minlength: 1,
          maxlength: 255,
          required: true
      },
      catKey: {
    	  minlength: 1,
          maxlength: 50,
          required: true
      },
      order: {
    	  min: 1,
          max: 99,
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
    }
  });
  
  function processMensaje(){
	  $.blockUI({ message: parametros.waitmessage });
	    $.post( parametros.saveMensajeUrl
	            , $( '#edit-mensaje-form' ).serialize()
	            , function( data )
	            {
	    			mensaje = JSON.parse(data);
	    			if (mensaje.messageKey === undefined) {
	    				toastr.error(data, parametros.errormessage, {
	    					    closeButton: true,
	    					    progressBar: true,
	    					  });
	    				$.unblockUI();
					}
					else{
						$.blockUI({ message: parametros.successmessage });
						$('#messageKey').val(mensaje.messageKey);
						setTimeout(function() { 
				            $.unblockUI({ 
				                onUnblock: function(){ window.location.href = parametros.mensajesUrl; } 
				            }); 
				        }, 1000); 
					}
	            }
	            , 'text' )
		  		.fail(function(XMLHttpRequest, textStatus, errorThrown) {
		    		alert( "error:" + errorThrown);
		    		$.unblockUI();
		  		});
	}
  
  
  $(document).on('keypress','form input',function(event)
  		{                
  		    event.stopImmediatePropagation();
  		    if( event.which == 13 )
  		    {
  		        event.preventDefault();
  		        var $input = $('form input');
  		        if( $(this).is( $input.last() ) )
  		        {
  		        	processMensaje();
  		        }
  		        else
  		        {
  		            $input.eq( $input.index( this ) + 1 ).focus();
  		        }
  		    }
  		});
  }
 };
}();
