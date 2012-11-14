package si.kcclass.barcodegenerator.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

public class BarcodeGenerator {
	
	private final static int DPI = 150;
	
	protected static Logger logger = Logger.getLogger(BarcodeGenerator.class);
	
	public static File generate(String strBarcode) {
		logger.debug("Generating barcode for string: " + strBarcode);
        try {
            //Create the barcode bean
            Code39Bean bean = new Code39Bean();
            
            //Configure the barcode generator
            bean.setModuleWidth(UnitConv.in2mm(1.0f / DPI));
            bean.setWideFactor(3);
            bean.doQuietZone(false);
            //Open output file
            File outputFile = new File("out.jpg");
            OutputStream out = new FileOutputStream(outputFile);
            try {
                //Set up the canvas provider for monochrome JPEG output 
                BitmapCanvasProvider canvas = new BitmapCanvasProvider(
                		out, "image/jpeg", DPI, BufferedImage.TYPE_BYTE_BINARY, false, 0);
                //Generate the barcode
                bean.generateBarcode(canvas, strBarcode);
                //Signal end of generation
                canvas.finish();
            } finally {
                out.close();
            }
            return outputFile;
        } catch (Exception e) {
            logger.error("Barcode generation failed:", e);
            return null;
        }		
	}

}
