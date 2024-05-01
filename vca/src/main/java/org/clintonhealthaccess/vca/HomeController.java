package org.clintonhealthaccess.vca;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.clintonhealthaccess.vca.domain.Area;
import org.clintonhealthaccess.vca.domain.Censador;
import org.clintonhealthaccess.vca.domain.Distrito;
import org.clintonhealthaccess.vca.domain.Foco;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.irs.Brigada;
import org.clintonhealthaccess.vca.domain.irs.IrsSeason;
import org.clintonhealthaccess.vca.domain.irs.Personal;
import org.clintonhealthaccess.vca.domain.mtilds.Ciclo;
import org.clintonhealthaccess.vca.domain.mtilds.PersonalLlins;
import org.clintonhealthaccess.vca.language.MessageResource;
import org.clintonhealthaccess.vca.service.AreaService;
import org.clintonhealthaccess.vca.service.BrigadaService;
import org.clintonhealthaccess.vca.service.CensadorService;
import org.clintonhealthaccess.vca.service.DashboardCensoService;
import org.clintonhealthaccess.vca.service.DistritoService;
import org.clintonhealthaccess.vca.service.EmailServiceImpl;
import org.clintonhealthaccess.vca.service.FocoService;
import org.clintonhealthaccess.vca.service.IrsSeasonService;
import org.clintonhealthaccess.vca.service.LocalidadService;
import org.clintonhealthaccess.vca.service.MessageResourceService;
import org.clintonhealthaccess.vca.service.PersonalService;
import org.clintonhealthaccess.vca.service.UsuarioService;
import org.clintonhealthaccess.vca.service.mtilds.CicloService;
import org.clintonhealthaccess.vca.service.mtilds.PersonalLlinsService;
import org.clintonhealthaccess.vca.users.model.GenericResponse;
import org.clintonhealthaccess.vca.users.model.UserSistema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;








/**
 * Controlador que provee los mapeos en la pagina Web para:
 * 
 * <ul>
 * <li>Pagina Principal
 * <li>Pagina de Ingreso
 * <li>Ingreso Fallido
 * <li>Pagina de Salida
 * <li>No autorizado
 * <li>No encontrado
 * <li>Ruuuueset contraseña
 * </ul>
 * 
 * @author William Aviles
 **/
@Controller
@RequestMapping("/*")
public class HomeController {
	
	@Resource(name="usuarioService")
	private UsuarioService usuarioService;
	
	@Resource(name="emailServiceImpl")
	private EmailServiceImpl emailServiceImpl;
	@Resource(name="messageResourceService")
	private MessageResourceService messageResourceService;
	@Resource(name="dashboardCensoService")
	private DashboardCensoService dashboardCensoService;
	
	@Resource(name="localidadService")
	private LocalidadService localidadService;
	@Resource(name="areaService")
	private AreaService areaService;
	@Resource(name="distritoService")
	private DistritoService distritoService;
	@Resource(name="censadorService")
	private CensadorService censadorService;
	@Resource(name="focoService")
	private FocoService focoService;
	
	@Resource(name="personalService")
	private PersonalService personalService;
	
	@Resource(name="personalLlinsService")
	private PersonalLlinsService personalLlinsService;
	
	@Resource(name="brigadaService")
	private BrigadaService brigadaService;
	
	@Resource(name="temporadaService")
	private IrsSeasonService temporadaService;
	
	@Resource(name="cicloService")
	private CicloService cicloService;
	
	
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
    	try {
	    	logger.info("vca Iniciado...");
	    	List<Area> areas = areaService.getActiveAreas();
	    	model.addAttribute("areas", areas);
	    	List<Distrito> distritos = distritoService.getActiveDistricts();
	    	model.addAttribute("distritos", distritos);
	    	List<Localidad> localidades = localidadService.getActiveLocalitiesUsuario(SecurityContextHolder.getContext().getAuthentication().getName());
	    	model.addAttribute("localidades", localidades);
	    	List<Censador> censadores = censadorService.getActiveCensadores();
	    	model.addAttribute("censadores", censadores);
	    	List<Foco> focos = focoService.getActiveFocos();
	    	model.addAttribute("focos", focos);
    	}
    	catch(Exception e) {
    		logger.error(e.getLocalizedMessage());
    	}
    	return "home";
    }
    
    @RequestMapping(value = "/irs/dashboard/", method = RequestMethod.GET)
    public String homeIrs(Model model) {
    	try {
	    	logger.info("vca Iniciado...");
	    	List<Area> areas = areaService.getActiveAreas();
	    	model.addAttribute("areas", areas);
	    	List<Distrito> distritos = distritoService.getActiveDistricts();
	    	model.addAttribute("distritos", distritos);
	    	List<Localidad> localidades = localidadService.getActiveLocalitiesUsuario(SecurityContextHolder.getContext().getAuthentication().getName());
	    	model.addAttribute("localidades", localidades);
	    	List<Personal> rociadores = personalService.getActiveRociadores();
	    	model.addAttribute("rociadores", rociadores);
	    	List<Foco> focos = focoService.getActiveFocos();
	    	model.addAttribute("focos", focos);
	    	List<IrsSeason> temporadas = this.temporadaService.getIrsSeasons();
	    	model.addAttribute("temporadas", temporadas);
	    	if (temporadas.size()==0) {
	    		temporadas.add(new IrsSeason("noseasons","noseasons","No hay temporadas"));
	    	}
	    	List<Personal> supervisores = this.personalService.getActiveSupervisores();
	    	model.addAttribute("supervisores", supervisores);
	    	List<Brigada> brigadas = this.brigadaService.getActiveBrigadas();
	    	model.addAttribute("brigadas", brigadas);
    	}
    	catch(Exception e) {
    		logger.error(e.getLocalizedMessage());
    	}
    	return "irs/home";
    }
    
    @RequestMapping(value = "/maps/dashboard/", method = RequestMethod.GET)
    public String homeMaps(Model model) {
    	try {
	    	logger.info("vca Iniciado...");
	    	List<Area> areas = areaService.getActiveAreas();
	    	model.addAttribute("areas", areas);
	    	List<Distrito> distritos = distritoService.getActiveDistricts();
	    	model.addAttribute("distritos", distritos);
	    	List<Localidad> localidades = localidadService.getActiveLocalitiesUsuario(SecurityContextHolder.getContext().getAuthentication().getName());
	    	model.addAttribute("localidades", localidades);
	    	List<Foco> focos = focoService.getActiveFocos();
	    	model.addAttribute("focos", focos);
	    	List<MessageResource> estados = this.messageResourceService.getCatalogo("CAT_ESTADOCASO"); 
	    	model.addAttribute("estados", estados);
    	}
    	catch(Exception e) {
    		logger.error(e.getLocalizedMessage());
    	}
    	return "maps/map1";
    }
    
    @RequestMapping(value = "/llins/dashboard/", method = RequestMethod.GET)
    public String homeLlins(Model model) {
    	try {
	    	logger.info("vca LLINS Iniciado...");
	    	List<Area> areas = areaService.getActiveAreas();
	    	model.addAttribute("areas", areas);
	    	List<Distrito> distritos = distritoService.getActiveDistricts();
	    	model.addAttribute("distritos", distritos);
	    	List<Localidad> localidades = localidadService.getActiveLocalitiesUsuario(SecurityContextHolder.getContext().getAuthentication().getName());
	    	model.addAttribute("localidades", localidades);
	    	List<Foco> focos = focoService.getActiveFocos();
	    	model.addAttribute("focos", focos);
	    	List<Ciclo> ciclos = this.cicloService.getCiclos();
	    	model.addAttribute("ciclos", ciclos);
	    	if (ciclos.size()==0) {
	    		ciclos.add(new Ciclo("noseasons","noseasons","No hay ciclos"));
	    	}
	    	List<PersonalLlins> rrhhs = this.personalLlinsService.getActivePersonalLlinses();
	    	model.addAttribute("rrhhs", rrhhs);
    	}
    	catch(Exception e) {
    		logger.error(e.getLocalizedMessage());
    	}
    	return "llins/home";
    }
    
    @RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(ModelMap model) {
		return "login";
	}
    
    @RequestMapping(value="/resetPassword", method = RequestMethod.GET)
	public String resetPassword(ModelMap model) {
		return "resetPassword";
	}
    
	@RequestMapping( value="resetPassword", method=RequestMethod.POST)
	public ResponseEntity<String> processResetPassForm(HttpServletRequest request, @RequestParam(value="username", required=true ) String userName
	        , @RequestParam( value="email", required=true) String email
	        )
	{
		try{
			UserSistema user = usuarioService.getUser(userName,email);
			GenericResponse genericResponse;
			if (user == null) {
			    genericResponse = new GenericResponse("error", "Usuario no encontrado/User not found");
			}
			else {
				String token = UUID.randomUUID().toString();
				usuarioService.createPasswordResetTokenForUser(user, token);
				String scheme = request.getScheme();
				String host = request.getHeader("Host");        // includes server name and server port
				String contextPath = request.getContextPath();  // includes leading forward slash
	
				String url = scheme + "://" + host + contextPath + "/processToken?id=" + 
					      user.getUsername() + "&token=" + token;
				String mensaje = " " + user.getCompleteName() + "\n\n"
			                + "El enlace para recuperar su contraseña es: \n\n"
			                + url + "\n\n"
			                + "Este enlace será válido por 24 horas. Favor no contestar este mensaje";
				emailServiceImpl.sendEmail(user.getEmail(), "no-reply", "Reset password Sistema VCA",mensaje);
				genericResponse = new GenericResponse("success", user.getCompleteName() + ", por favor revise su correo / please check your email, " + user.getEmail());
				
			}
			return createJsonResponse(genericResponse);
		}
		catch(Exception e){
			GenericResponse genericResponse = new GenericResponse("error", "Hubo un error al enviar el correo!");
			return createJsonResponse(genericResponse);
    	}
	}
	
	@RequestMapping(value = "/processToken", method = RequestMethod.GET)
	public String showChangePasswordPage(Model model, @RequestParam("id") String id, @RequestParam("token") String token, RedirectAttributes redirectAttributes) {
	    String result = usuarioService.validatePasswordResetToken(id, token);
	    if (result != null) {
	    	redirectAttributes.addFlashAttribute("errorResetPassword", true);
    		redirectAttributes.addFlashAttribute("errorMensaje", result);
	        return "redirect:/login";
	    }
	    return "redirect:/users/resetpass";
	}
    
    @RequestMapping(value="/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {
    	model.addAttribute("error", "true");
		return "login";
	}
    
    @RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {
		return "login";
	}
	
	@RequestMapping(value="/403", method = RequestMethod.GET)
	public String noAcceso() {
		return "403"; 
	}
	
	@RequestMapping(value="/404", method = RequestMethod.GET)
	public String noEncontrado() { 
		return "404";
	}
	
	@RequestMapping(value = "/help/", method = RequestMethod.GET)
    public String help(Model model) {
    	try {
	    	logger.info("vca ayuda Iniciado...");
    	}
    	catch(Exception e) {
    		logger.error(e.getLocalizedMessage());
    	}
    	return "help";
    }
    
	@RequestMapping( value="keepsession")
	public @ResponseBody String keepSession()
	{	
		return "OK";
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
