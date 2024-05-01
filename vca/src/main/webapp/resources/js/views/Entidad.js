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

  $.validator.setDefaults( {
    submitHandler: function () {
    	$('#datesForm').modal('hide');
    	processEntidad();
    }
  } );
  
  jQuery.validator.addMethod("noSpace", function(value, element) { 
		  return value.indexOf(" ") < 0 && value != ""; 
	}, "Inválido");
  
  jQuery.validator.addMethod("menorIgualQue", function(value, element, param) { 
	  return this.optional(element) || value <= $(param).val();
  }, "Valor no válido");
  
  jQuery.validator.addMethod("mayorIgualQue", function(value, element, param) { 
	  return this.optional(element) || value >= $(param).val();
  }, "Valor no válido");
  
  
  $( '#add-form' ).validate( {
    rules: {
      code: {
    	minlength: 2,
        maxlength: 15,
        noSpace:true,
        required: true
      },
      codigo: {
      	minlength: 2,
          maxlength: 15,
          noSpace:true,
          required: true
        },
      name: {
    	  minlength: 3,
          maxlength: 500,
          required: true
      },
      area: {
          required: true
      },
      district: {
          required: true
      },
      latitude: {
          required: true,
          min:parseFloat(parametros.latitudMinima),
          max:parseFloat(parametros.latitudMaxima)
      },
      zoom:{
    	  required: true,
    	  min:0,
    	  max:18
      },
      longitude: {
          required: true,
          min:parseFloat(parametros.longitudMinima),
          max:parseFloat(parametros.longitudMaxima)
      },
      population: {
          required: false,
          min:1,
          max:125000
      },
      pattern: {
          maxlength: 750,
          required: true,
      },
      obs: {
          maxlength: 750,
          required: false
      },
      value: {
          maxlength: 250,
          required: true
      },
      info: {
          maxlength: 750,
          required: false
      },
      obs: {
          maxlength: 750,
          required: false
      },
      clave: {
    	  minlength: 3,
          maxlength: 500,
          required: true
      },
      local: {
          required: true
      },
      tipo: {
          required: true
      },
      status: {
          required: true
      },
      visitType: {
          required: true
      },
      visitDate: {
          required: true
      },
      fisDate: {
          required : function(element) {
              var sint = $("#sint").val();
              if(sint == "1") { 
                  return true;
              } else {
                  return false;
              }
          },
          menorIgualQue:"#mxDate"
      },
      sint: {
          required: true
      },
      mxDate: {
          required: true
      },
      mxType: {
          required: true
      },
      resultado: {
          required: true
      },
      dateValue: {
          required: true
      },
      size:{
    	  required: true,
    	  min:0,
    	  max:2000
      },
      lostFollowUpReason: {
          required: true
      },
      diaTx: {
          required: true
      },
      edad:{
    	  required: true,
    	  min:0,
    	  max:99
      },
      sexo: {
          required: true
      },
      busqueda: {
          required: true
      },
      embarazada: {
          required: function(element) {
              var sex = $("#sexo").val();
              var edad = parseInt($("#edad").val());
              if(sex == 'F' && edad>12 && edad <50) { 
                  return true;
              } else {
                  return false;
              }
          }
      },
      menor6meses: {
          required: function(element) {
              var edad = $("#edad").val();
              if(edad < 1) { 
                  return true;
              } else {
                  return false;
              }
          }
      },
      color: {
          required: true
      },
      txResultType: {
          required: true
      },
      mxProactiva:{
    	  required: true,
    	  min:0,
    	  max:20
      },
      mxReactiva:{
    	  required: true,
    	  min:0,
    	  max:20
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
				                onUnblock: function(){ window.location.href = parametros.listUrl+entidad.ident+ "/"; } 
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
  
  $('#sint').change(
		    function(){
		    if ($(this).val()=="1") {
		        $('#fisDate').show();
		    }
		    else {
		    	$('#fisDate').val("");
		        $('#fisDate').hide();
		    }
		});
  
  $('#txSuspReason').change(
		    function(){
		    if ($(this).val()=="SUSPOTRO") {
		    	$('.suspendidootra').show();
		    }
		    else {
		    	$('#txSuspOtherReason').val("");
		    	$('.suspendidootra').hide();
		    }
		});
  
  $('#lostFollowUpReason').change(
		    function(){
		    if ($(this).val()=="OTHER") {
		    	$('.razonotra').show();
		    }
		    else {
		    	$('#lostFollowUpOtherReason').val("");
		    	$('.razonotra').hide();
		    }
		});  
  
  
  $('#sexo').change(
		    function(){
		    if ($(this).val()=="F" && parseInt($("#edad").val()) > 12 && parseInt($("#edad").val()) <50) {
		        $('#divemb').show();
		    }
		    else {
		    	$('#embarazada').val('').trigger('change');
		    	
		        $('#divemb').hide();
		    }
		});
  
  $('#edad').change(
		    function(){
		    if ($('#sexo').val()=="F" && parseInt($(this).val()) > 12 && parseInt($(this).val()) <50) {
		        $('#divemb').show();
		    }
		    else {
		    	$('#embarazada').val('').trigger('change');
		    	
		        $('#divemb').hide();
		    }
		    if (parseInt($(this).val()) <1) {
		    	$('#div6meses').show();
		    }
		    else{
		    	$('#menor6meses').val('').trigger('change');
		    	
		        $('#div6meses').hide();
		    }
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
