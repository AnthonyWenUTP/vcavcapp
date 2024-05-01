package org.clintonhealthaccess.vca.web.controller;

import java.text.ParseException;
import java.util.List;
import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.Household;
import org.clintonhealthaccess.vca.service.MessageResourceService;
import org.clintonhealthaccess.vca.service.QCService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/qc/*")
public class QCController {
	
	@Resource(name="qcService")
	private QCService qcService;
	@Resource(name="messageResourceService")
	private MessageResourceService messageResourceService;
    private static final Logger logger = LoggerFactory.getLogger(QCController.class);
    
	@RequestMapping(value = "/duplicates/", method = RequestMethod.GET)
    public String getEntities(Model model) throws ParseException { 	
    	logger.debug("Mostrando viviendas duplicadas en JSP");
    	List<Household> casasDuplicadas = qcService.getViviendasDuplicadas();
    	model.addAttribute("casasDuplicadas", casasDuplicadas);
    	return "qc/duplicates/list";
	}
}
