package org.clintonhealthaccess.vca.web.controller;

import javax.annotation.Resource;

import org.clintonhealthaccess.vca.service.MessageResourceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view/llins/*")
public class DashboardLlinsController {
	
	@Resource(name="messageResourceService")
	private MessageResourceService messageResourceService;
    //private static final Logger logger = LoggerFactory.getLogger(DashboardLlinsController.class);
    
    
    
    
}
