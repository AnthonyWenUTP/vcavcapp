var IdleTimeout = function () {

    return {

        //main function to initiate the module
        init: function (parametros) {

            // cache a reference to the countdown element so we don't have to query the DOM for it on each ping.
            var $countdown=null;

            $('body').append('<div class="modal fade" id="idle-timeout-dialog" data-backdrop="static"><div class="modal-dialog modal-small"><div class="modal-content"><div class="modal-header"><h4 class="modal-title">'+ parametros.sesionExp +'</h4></div><div class="modal-body"><p><i class="fa fa-warning"></i> '+ parametros.sesionExpTime +' <span id="idle-timeout-counter"></span> '+ parametros.seconds +'.</p><p>'+ parametros.sesionExpConf +'</p></div><div class="modal-footer"><button id="idle-timeout-dialog-logout" type="button" class="btn btn-default">'+ parametros.logoutsession +'</button><button id="idle-timeout-dialog-keepalive" type="button" class="btn btn-primary" data-dismiss="modal">'+ parametros.keepsession +'</button></div></div></div></div>');
                    
            // start the idle timer plugin
            $.idleTimeout('#idle-timeout-dialog', '.modal-content button:last', {
                idleAfter: 240, // 240 seconds
                timeout: 30000, //30 seconds to timeout
                pollingInterval: 30, // 30 seconds
                keepAliveURL: parametros.keepSessionUrl,
                serverResponseEquals: 'OK',
                onTimeout: function(){
                    window.location = parametros.logoutUrl;
                },
                onIdle: function(){
                    $('#idle-timeout-dialog').modal('show');
                    $countdown = $('#idle-timeout-counter');

                    $('#idle-timeout-dialog-keepalive').on('click', function () { 
                        $('#idle-timeout-dialog').modal('hide');
                    });

                    $('#idle-timeout-dialog-logout').on('click', function () { 
                        $('#idle-timeout-dialog').modal('hide');
                        $.idleTimeout.options.onTimeout.call(this);
                    });
                },
                onCountdown: function(counter){
                    $countdown.html(counter); // update the counter
                }
            });
            
        }

    };

}();