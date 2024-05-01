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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;


/**
 * Controlador web de peticiones relacionadas a los catalogos del sistema
 * 
 * @author William Aviles
 */
@Controller
@RequestMapping("/admin/catalogs/*")
public class AdminCatalogosController {
	private static final Logger logger = LoggerFactory.getLogger(AdminCatalogosController.class);
	@Resource(name="messageResourceService")
	private MessageResourceService messageResourceService;
	@Resource(name="messageSource")
	private DatabaseDrivenMessageSource messageSource;
	
	/**
     * Controlador para presentar catalogos.
     * @param model Modelo enlazado a la vista
     * @return String con la vista
     */
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String obtenerCatalogos(Model model){ 	
    	logger.debug("Catalogos");
    	return "admin/catalogos/list";
	}
    
    /**
     * Retorna una lista de catalogos. Acepta una solicitud GET para JSON
     * @param descCatalogo el identificador por el que se buscan catalogos
     * @return ResponseBody Un arreglo JSON de catalogos
     */
    @RequestMapping(value = "catalogos", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<MessageResource> fetchMessageResourcesJson(@RequestParam(value = "descCatalogo", required = true) String descCatalogo) {
        logger.info("Obteniendo los catalogos en JSON");
        List<MessageResource> catalogos = null; 
        catalogos = this.messageResourceService.loadCatalogos(descCatalogo);
        return catalogos;	
    }
    
    /**
     * Controlador para editar un catalogo.
     * @param model Modelo enlazado a la vista
     * @param messageKey el identificador del catalogo a editar
     * @return String  con la vista
     */
    @RequestMapping(value = "/editCatalogo/{messageKey}/", method = RequestMethod.GET)
	public String initUpdateCatalogoForm(@PathVariable("messageKey") String messageKey, Model model) {
    	MessageResource catalogo = messageResourceService.getMensaje(messageKey);
    	model.addAttribute("catalogo",catalogo);
    	List<MessageResource> opciones = messageResourceService.getCatalogoTodos(messageKey);
    	model.addAttribute("opciones",opciones);
    	if (catalogo!=null){
    		return "admin/catalogos/viewForm";
    	}
		else{
			return "403";
		}
	}
    
    /**
     * Controlador para agregar una respuesta.
     * @param model Modelo enlazado a la vista
     * @param messageKey el identificador del catalogo a agregarle respuesta
     * @return String  con la vista
     */
    @RequestMapping(value = "/addRespuesta/{messageKey}/", method = RequestMethod.GET)
	public String initUpdateRespuestaForm(@PathVariable("messageKey") String messageKey, Model model) {
    	MessageResource catalogo = messageResourceService.getMensaje(messageKey);
    	model.addAttribute("catalogo",catalogo);
    	if (catalogo!=null){
    		return "admin/catalogos/enterForm";
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
     * @param isCat si es catalogo o no
     * @param pasive si la respuesta esta de baja
     * @param catRoot catalogo raiz de la respuesta
     * @param catKey valor de la respuesta
     * @param order ordenamiento de la respuesta
     * @return ResponseEntity con el mensaje guardado
     */
    @RequestMapping( value="saveCatalogo", method=RequestMethod.POST)
	public ResponseEntity<String> processCatalogoForm( @RequestParam(value="messageKey", required=true) String messageKey
			, @RequestParam( value="spanish", required=true ) String spanish
			, @RequestParam( value="english", required=true ) String english
			, @RequestParam( value="isCat", required=true ) String isCat
			, @RequestParam( value="pasive", required=true ) String pasive
			, @RequestParam( value="catRoot", required=false ) String catRoot
			, @RequestParam( value="catKey", required=false ) String catKey
			, @RequestParam( value="order", required=true ) Integer order
	        )
	{
    	try{
			MessageResource message = messageResourceService.getMensaje(messageKey);
			if(message==null) {
				message = new MessageResource();
				message.setMessageKey(messageKey);
				
			}
			//Actualiza la traduccion
			message.setSpanish(spanish);
			message.setEnglish(english);
			message.setIsCat(isCat.charAt(0));
			message.setPasive(pasive.charAt(0));
			message.setCatRoot(catRoot);
			message.setCatKey(catKey);
			message.setOrder(order);
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
    
    
    
    /**
     * Controlador para deshabilitar una respuesta
     * @param messageKey La respuesta a deshabilitar
     * @param redirectAttributes Regresa nombre de respuesta
     * @return a String con la vista a mostrar
     */
    @RequestMapping("/disableRespuesta/{messageKey}/")
    public String disableRespuesta(@PathVariable("messageKey") String messageKey, RedirectAttributes redirectAttributes) {
    	String redirecTo="404";
    	MessageResource message = messageResourceService.getMensaje(messageKey);
    	if(message!=null){
    		message.setPasive('1');
    		this.messageResourceService.saveMensaje(message);
    		redirecTo = "redirect:/admin/catalogs/editCatalogo/"+message.getCatRoot()+"/";
    		redirectAttributes.addFlashAttribute("respuestaDeshabilitada", true);
    		redirectAttributes.addFlashAttribute("nombreRespuesta", message.getMessageKey());
    	}
    	else{
    		redirecTo = "403";
    	}
    	return redirecTo;	
    }

    /**
     * Controlador para habilitar una respuesta
     * @param messageKey La respuesta a habilitar
     * @param redirectAttributes Regresa nombre de respuesta
     * @return a String con la vista a mostrar
     */
    @RequestMapping("/enableRespuesta/{messageKey}/")
    public String enableRespuesta(@PathVariable("messageKey") String messageKey, RedirectAttributes redirectAttributes) {
    	String redirecTo="404";
    	MessageResource message = messageResourceService.getMensaje(messageKey);
    	if(message!=null){
    		message.setPasive('0');
    		this.messageResourceService.saveMensaje(message);
    		redirecTo = "redirect:/admin/catalogs/editCatalogo/"+message.getCatRoot()+"/";
    		redirectAttributes.addFlashAttribute("respuestaHabilitada", true);
    		redirectAttributes.addFlashAttribute("nombreRespuesta", message.getMessageKey());
    	}
    	else{
    		redirecTo = "403";
    	}
    	return redirecTo;	
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
