var ResetPassword = function () {
	
return {
  //main function to initiate the module
  init: function (parametros) {	
	
  $.validator.setDefaults( {
    submitHandler: function () {
    	resetPassword();
    }
  } );
  jQuery.validator.addMethod("noSpace", function(value, element) { 
		  return value.indexOf(" ") < 0 && value != ""; 
	}, "Invalid");
  $( '#send-pass-form' ).validate( {
    rules: {
      username: {
        required: true,
        minlength: 5,
        maxlength: 50,
        noSpace:true,
      },
      email: {
          required: true,
          minlength: 3,
          maxlength: 100,
          noSpace:true,
          email: true
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
  
  function resetPassword(){
	  $.blockUI({ message: parametros.waitmessage });
	    $.post( parametros.generateTokenUrl
	            , $( '#send-pass-form' ).serialize()
	            , function( data )
	            {
	    			resultado = JSON.parse(data);
	    			if (resultado.result == "error") {
	    				toastr.error(resultado.message, parametros.errormessage, {
    					    closeButton: true,
    					    progressBar: true,
    					  });
	    				$.unblockUI();
	    			}
	    			else{
	    				toastr.info(resultado.message, parametros.successmessage, {
    					    closeButton: true,
    					    progressBar: true,
    					  });
						setTimeout(function() { 
				            $.unblockUI({ 
				                onUnblock: function(){ window.location.href = parametros.loginUrl; } 
				            }); 
				        }, 1000); 
	    			}
	            }
	            , 'text' )
		  		.fail(function(XMLHttpRequest, textStatus, errorThrown) {
		  			alert(errorThrown);
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
  		            //Time to submit the form!!!!
  		            //alert( 'Hooray .....' );
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
