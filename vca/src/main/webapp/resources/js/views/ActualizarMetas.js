var ProcessEntity = function () {
    // Function to populate options in "Local Identifiers" select box based on metas data
    function populateOptions() {
        var irsSeasonValue = document.getElementById("irsSeason").value;
        var localidad = document.getElementById("localidad");
        localidad.innerHTML = ""; // Clear existing options
        var meta0Select = document.getElementById("meta0");
        var meta1Select = document.getElementById("meta1");
        var meta2Select = document.getElementById("meta2");
        for (var i = 0; i < meta0Select.options.length; i++) {
            if (meta0Select.options[i].value == irsSeasonValue) {
                var option = document.createElement("option");
                option.value = meta1Select.options[i].value;
                option.text = meta2Select.options[i].value;
                localidad.appendChild(option);
            }
        }
    }

    // Attach event listener to "IRS Season" select box
    document.getElementById("irsSeason").addEventListener("change", function() {
        // Call the function to populate options whenever IRS Season is changed
        populateOptions();
    });

    // Populate options when the page loads
    populateOptions();

    return {
        //main function to initiate the module
        init: function (parametros) {	
        
            $( '#update-form' ).validate( {
                rules: {
                    localidad: {
                        required: true
                    },
                    irsSeason: {
                        required: true
                    }
                },
                submitHandler: function () {
                    processEntidad2();
                }
            });
            
            function processEntidad2() {

                $.post(parametros.saveUrl, $('#update-form').serialize())
                    .done(function(data, textStatus, jqXHR) {
                        $.blockUI({ message: parametros.successmessage });
                        setTimeout(function() { 
				            $.unblockUI({ 
				                onUnblock: function(){ window.location.href = parametros.targetUrl; } 
				            }); 
				        }, 1000); 
                    })
                    .fail(function(jqXHR, textStatus, errorThrown) {
                        console.error("AJAX request failed:", textStatus, errorThrown);
                        alert("AJAX request failed: " + textStatus + "\nError: " + errorThrown);
                        $.unblockUI();
                    });
            }
        
        }
    };
}();
    