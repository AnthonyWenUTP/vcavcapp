package org.clintonhealthaccess.vca.web.controller;

import java.util.List;
import javax.annotation.Resource;

import org.clintonhealthaccess.vca.language.DatabaseDrivenMessageSource;
import org.clintonhealthaccess.vca.language.MessageResource;
import org.clintonhealthaccess.vca.service.MessageResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;


/**
 * Controlador web de peticiones relacionadas a la traduccion de los mensajes del sistema
 * 
 * @author William Aviles
 */
@Controller
@RequestMapping("/admin/translations/*")
public class AdminTraduccionController {
	private static final Logger logger = LoggerFactory.getLogger(AdminTraduccionController.class);
	@Resource(name="messageResourceService")
	private MessageResourceService messageResourceService;
	@Resource(name="messageSource")
	private DatabaseDrivenMessageSource messageSource;
	
	/**
     * Controlador para presentar lista de mensajes.
     * @param model Modelo enlazado a la vista
     * @return String con la vista
     */
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String obtenerMensajes(Model model){ 	
    	logger.debug("Catalogo de mensajes");
    	return "admin/translations/list";
	}
    
    /**
     * Retorna una lista de mensajes. Acepta una solicitud GET para JSON
     * @param messageParameter el identificador por el que se buscan mensajes
     * @return ResponseBody Un arreglo JSON de mensajes
     */
    @RequestMapping(value = "messages", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<MessageResource> fetchMessageResourcesJson(@RequestParam(value = "messageParameter", required = true) String messageParameter) {
        logger.info("Obteniendo los MessageResource en JSON");
        List<MessageResource> messages = null; 
        messages = this.messageResourceService.loadMessages(messageParameter);
        return messages;	
    }
    
    /**
     * Controlador para editar un mensaje.
     * @param model Modelo enlazado a la vista
     * @param messageKey el identificador del mensaje a editar
     * @return String  con la vista
     */
    @RequestMapping(value = "/editMensaje/{messageKey}/", method = RequestMethod.GET)
	public String initUpdateMensajeForm(@PathVariable("messageKey") String messageKey, Model model) {
    	MessageResource message = messageResourceService.getMensaje(messageKey);
    	if (message!=null){
    		model.addAttribute("message", message);
    		return "admin/translations/enterForm";
    	}
		else{
			return "403";
		}
	}
    
    
    /**
     * Controlador para guardar un mensaje.
     * 
     * @param messageKey el identificador del mensaje a guardar
     * @param spanish mensaje en español
     * @param english mensaje en ingles
     * @return ResponseEntity con el mensaje guardado
     */
    @RequestMapping( value="saveTrans", method=RequestMethod.POST)
	public ResponseEntity<String> processTransForm( @RequestParam(value="messageKey", required=true) String messageKey
			, @RequestParam( value="spanish", required=true ) String spanish
			, @RequestParam( value="english", required=true ) String english
	        )
	{
    	try{
			MessageResource message = messageResourceService.getMensaje(messageKey);
			//Actualiza la traduccion
			message.setSpanish(spanish);
			message.setEnglish(english);
			this.messageResourceService.saveMensaje(message);
			messageSource.reload();
			return createJsonResponse(message);
    	}
    	catch (DataIntegrityViolationException e){
    		String message = e.getMostSpecificCause().getMessage();
    		Gson gson = new Gson();
    	    String json = gson.toJson(message);
    		return new ResponseEntity<String>( json, HttpStatus.CREATED);
    	}
    	catch(Exception e){
    		Gson gson = new Gson();
    	    String json = gson.toJson(e.toString());
    		return new ResponseEntity<String>( json, HttpStatus.CREATED);
    	}
    	
	}
   
    
    
    private ResponseEntity<String> createJsonResponse( Object o )
	{
	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Content-Type", "application/json");
	    Gson gson = new Gson();
	    String json = gson.toJson(o);
	    return new ResponseEntity<String>( json, headers, HttpStatus.CREATED );
	}

}
