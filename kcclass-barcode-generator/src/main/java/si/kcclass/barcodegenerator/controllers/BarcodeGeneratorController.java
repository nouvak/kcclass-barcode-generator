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
	
	private byte[] generateBarcodeImage(String barcodeType, String barcodeValue,
			ServletContext servletContext) throws IOException {
    	BarcodeTypes type = BarcodeTypes.valueOf(barcodeType);
    	String tempDir = servletContext.getRealPath("/temp");
    	/* generate the barcode image. */
    	BarcodeGenerator generator = new BarcodeGenerator(tempDir);
    	File tmpImgBarcode = generator.generate(type, barcodeValue);
        InputStream imageStream = servletContext.getResourceAsStream("/temp/" + tmpImgBarcode.getName());
        /* send image to the client. */
        byte[] imageBytes = IOUtils.toByteArray(imageStream);
        tmpImgBarcode.delete();
        return imageBytes;		
	}
		    
	@RequestMapping(value="generate/{barcodeType}/{barcodeValue}", method = RequestMethod.GET)
    public @ResponseBody byte[] generate(@PathVariable String barcodeType,
    		@PathVariable String barcodeValue,
    		HttpServletRequest request,
    		HttpServletResponse response) throws IOException {
		ServletContext servletContext = request.getSession().getServletContext();
		byte[] imageBytes = generateBarcodeImage(barcodeType, barcodeValue, servletContext);
        return imageBytes;
    }
	
	@RequestMapping(value="generate-as-file/{barcodeType}/{barcodeValue}", method = RequestMethod.GET)
    public void generateAsFile(@PathVariable String barcodeType,
    		@PathVariable String barcodeValue,
    		HttpServletRequest request,
    		HttpServletResponse response) throws IOException {
		ServletContext servletContext = request.getSession().getServletContext();
		byte[] imageBytes = generateBarcodeImage(barcodeType, barcodeValue, servletContext);		
        response.setContentType("image/jpeg");
        response.setHeader("Content-Disposition", "attachment; filename=\"barcode-" + 
        		barcodeValue + ".jpg\"");
        response.getOutputStream().write(imageBytes);
        response.getOutputStream().close();
    }
}
