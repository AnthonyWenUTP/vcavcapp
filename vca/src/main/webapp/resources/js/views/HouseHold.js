var ProcessEntity = function () {
	
	var handleDatePickers = function (idioma) {
	    if (jQuery().datepicker) {
	        $('.date-picker').datepicker({
	            language: idioma,
	            autoclose: true
	        });
	        $('body').removeClass("modal-open"); // fix bug when inline picker is used in modal
	    }
	};
	
return {
  //main function to initiate the module
  init: function (parametros) {	
	  
	  handleDatePickers("es");
	  
	  var patron = new RegExp($("#pattern").val());

	 
  $.validator.setDefaults( {
    submitHandler: function () {
    	processEntidad();
    }
  } );
  
  jQuery.validator.addMethod("noSpace", function(value, element) { 
		  return value.indexOf(" ") < 0 && value != ""; 
	}, "Formato inválido");
  
  jQuery.validator.addMethod("regex", function(value, element, regexpr) {          
	     return regexpr.test(value);
	   }, "Formato inválido"); 
  
  
  $( '#add-form' ).validate( {
    rules: {
      code: {
    	minlength: 1,
        maxlength: 100,
        noSpace:true,
        regex: patron,
        required: true
      },
      ownerName: {
    	  minlength: 3,
          maxlength: 500,
          required: true
      },
      local: {
          required: true
      },
      censusTaker: {
          required: true
      },
      censusDate: {
          required: true
      },
      inhabited: {
          required: true
      },
      habitants: {
          required: function(element){
        	  return $("#inhabited").val()=="1";
          }
      },
      material: {
          required: true
      },
      rooms: {
          required: true
      },
      sprRooms: {
          required: true
      },
      noSprooms: {
          required: true
      },
      noSproomsReasons: {
    	  required: function(element){
        	  return $("#noSprooms").val()>0;
          }
      },
      latitude: {
          required: false,
          min:parseFloat(parametros.latitudMinima),
          max:parseFloat(parametros.latitudMaxima)
      },
      longitude: {
          required: false,
          min:parseFloat(parametros.longitudMinima),
          max:parseFloat(parametros.longitudMaxima)
      },
      obs: {
          maxlength: 750,
          required: false
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
  
  function processEntidad(){
	  $.blockUI({ message: parametros.waitmessage });
	    $.post( parametros.saveUrl
	            , $( '#add-form' ).serialize()
	            , function( data )
	            {
	    			entidad = JSON.parse(data);
	    			if (entidad.ident === undefined) {
	    				data = data.replace(/u0027/g,"");
	    				toastr.error(data, parametros.errormessage, {
	    					    closeButton: true,
	    					    progressBar: true,
	    					  });
	    				$.unblockUI();
					}
					else{
						$.blockUI({ message: parametros.successmessage });
						$('#ident').val(entidad.ident);
						setTimeout(function() { 
				            $.unblockUI({ 
				                onUnblock: function(){ window.location.href = parametros.listUrl; } 
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
  
  $('#local').change(
  		function() {
  			$.blockUI({ message: parametros.waitmessage });
  			$.getJSON(parametros.opclocaUrl, {
  				ident : $('#local').val(),
  				ajax : 'true'
  			}, function(data) {
  				patron = new RegExp(data.pattern);
  				$( "#code" ).rules( "remove", "regex" );
  				$( "#code" ).rules( "add", {
  				  regex: patron
  				});
  				$.unblockUI();
  			}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
	    		alert( "error:" + errorThrown);
	    		$.unblockUI();
	  		});
          });
  
  
  
  $(document).on('keypress','form input',function(event)
  		{                
  		    event.stopImmediatePropagation();
  		    if( event.which == 13 )
  		    {
  		        event.preventDefault();
  		        var $input = $('form input');
  		        if( $(this).is( $input.last() ) )
  		        {
  		        	processEntidad();
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
