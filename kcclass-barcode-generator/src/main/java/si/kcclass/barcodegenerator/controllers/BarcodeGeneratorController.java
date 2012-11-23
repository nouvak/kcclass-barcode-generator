package si.kcclass.barcodegenerator.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
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
		    
	@RequestMapping(value="generate/{barcodeType}/{barcodeValue}", method = RequestMethod.GET)
    public @ResponseBody byte[] generate(@PathVariable String barcodeType,
    		@PathVariable String barcodeValue,
    		HttpServletRequest request,
    		HttpServletResponse response) throws IOException {
    	BarcodeTypes type = BarcodeTypes.valueOf(barcodeType);
    	ServletContext servletContext = request.getSession().getServletContext();
    	String tempDir = servletContext.getRealPath("/temp");
    	BarcodeGenerator generator = new BarcodeGenerator(tempDir);
    	File tmpImgBarcode = generator.generate(type, barcodeValue);
        InputStream in = servletContext.getResourceAsStream("/temp/" + tmpImgBarcode.getName());
        response.setContentType("image/jpeg");
        response.setHeader("Content-Disposition", "attachment; filename=\"barcode-" + 
        		barcodeValue + ".jpg\"");
        byte[] imgBytes = IOUtils.toByteArray(in);
        tmpImgBarcode.delete();
        return imgBytes;
    }

}
