var ProcessUser = function () {
	
return {
  //main function to initiate the module
  init: function (parametros) {	
	
  $('#roles,#localidades').select2({
    theme: "bootstrap"
  });	
  $.validator.setDefaults( {
    submitHandler: function () {
      processUser();
    }
  } );
  jQuery.validator.addMethod("noSpace", function(value, element) { 
		  return value.indexOf(" ") < 0 && value != ""; 
	}, "Invalid");
  $( '#add-user-form' ).validate( {
    rules: {
      username: {
        required: true,
        minlength: 5,
        maxlength: 50,
        noSpace:true,
      },
      password: {
    	  minlength: 5,
          maxlength: 150,
          noSpace:true,
          required: true,
      },
      confirm_password: {
    	  minlength: 5,
          maxlength: 150,
          required: true,
          noSpace:true,
          equalTo: "#password"
      },
      completeName: {
          minlength: 5,
          maxlength: 250,
          required: true
      },
      email: {
          required: true,
          minlength: 3,
          maxlength: 100,
          noSpace:true,
          email: true
      },
      roles: {
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
  
  function processUser(){
	  $.blockUI({ message: parametros.waitmessage });
	    $.post( parametros.saveUserUrl
	            , $( '#add-user-form' ).serialize()
	            , function( data )
	            {
	    			usuario = JSON.parse(data);
	    			if (usuario.username === undefined) {
	    				toastr.error(data, parametros.errormessage, {
	    					    closeButton: true,
	    					    progressBar: true,
	    					  });
	    				$.unblockUI();
					}
					else{
						$.blockUI({ message: parametros.successmessage });
						$('#username').val(usuario.username);
						setTimeout(function() { 
				            $.unblockUI({ 
				                onUnblock: function(){ window.location.href = parametros.usuarioUrl+usuario.username+"/"; } 
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
