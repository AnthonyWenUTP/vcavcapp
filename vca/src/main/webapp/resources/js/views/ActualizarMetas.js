var ProcessSearch = function () {
	
    return {
        //main function to initiate the module
        init: function (parametros) {	
        
            $(document).on('click', 'button[type="submit"]', function() {
                var buttonId = $(this).attr('id');
                if (buttonId === 'buscarvivienda') {
                    processReport();
                } else if (buttonId === 'actualizarvivienda') {
                    processReport2();
                }
            });
            
            function processReport2() {
                // Show loading message
                $.blockUI({ message: parametros.waitmessage });
                var data = { season: $('#irsSeason').val() };
                $.post(parametros.updateTargetUrl, data)
                    .done(function(response) {
                        console.log("POST request successful");
                    })
                    .fail(function(xhr, status, error) {
                        console.error("POST request failed:", error);
                    })
                    .always(function() {
                        $.unblockUI();
                        console.log("Nada")
                    });
            }
        
        }
    };
}();
    