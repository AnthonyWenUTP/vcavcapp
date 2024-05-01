var CheckChangeCredentials = function () {
	 return {
	        //main function to initiate the module
	        init: function (parametros) {
	        	$.getJSON(parametros.url, { userName: parametros.username }, function(valido) {
	    	        if (valido) {
	    	        	window.location.href = parametros.urlChgPass;
	    	        }
	    	    });
	        }
	    };
}();