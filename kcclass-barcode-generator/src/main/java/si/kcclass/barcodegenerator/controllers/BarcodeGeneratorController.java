package si.kcclass.barcodegenerator.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import si.kcclass.barcodegenerator.util.BarcodeGenerator;
import si.kcclass.barcodegenerator.util.BarcodeTypes;

@RequestMapping("/barcodegenerator/**")
@Controller
public class BarcodeGeneratorController {
	
	@Autowired
	private ServletContext servletContext;
	
    /*@RequestMapping(value="generate/{barcodeType}/{barcodeValue}", method = RequestMethod.GET)
    public void generate(@PathVariable String barcodeType,
    		@PathVariable String barcodeValue) {
    	BarcodeTypes type = BarcodeTypes.valueOf(barcodeType);
    	File tmpImgBarcode = BarcodeGenerator.generate(type, barcodeValue);
    }*/
    
	@RequestMapping(value="generate/{barcodeType}/{barcodeValue}", method = RequestMethod.GET)
    public @ResponseBody byte[] generate(@PathVariable String barcodeType,
    		@PathVariable String barcodeValue) throws IOException {
    	BarcodeTypes type = BarcodeTypes.valueOf(barcodeType);
    	String tempDir = servletContext.getRealPath("/temp");
    	BarcodeGenerator generator = new BarcodeGenerator(tempDir);
    	File tmpImgBarcode = generator.generate(type, barcodeValue);
        InputStream in = servletContext.getResourceAsStream("/temp/" + tmpImgBarcode.getName());
        return IOUtils.toByteArray(in);
    }

}
