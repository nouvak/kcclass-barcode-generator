package si.kcclass.barcodegenerator.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/barcodegenerator/**")
@Controller
public class BarcodeGeneratorController {

    @RequestMapping(method = RequestMethod.POST, value = "{id}")
    public void post(@PathVariable Long id, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
    }

    @RequestMapping
    public String index() {
        return "barcodegenerator/index";
    }
    
    @RequestMapping(value="generate/strBarcode", method = RequestMethod.GET)
    public void generate(@PathVariable String strBarcode) {
    	
    }
}
