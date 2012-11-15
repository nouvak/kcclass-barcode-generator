package si.kcclass.barcodegenerator.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.transaction.NotSupportedException;

import org.apache.log4j.Logger;
import org.krysalis.barcode4j.impl.AbstractBarcodeBean;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.code128.EAN128Bean;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.impl.int2of5.Interleaved2Of5Bean;
import org.krysalis.barcode4j.impl.postnet.POSTNETBean;
import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.impl.upcean.EAN8Bean;
import org.krysalis.barcode4j.impl.upcean.UPCABean;
import org.krysalis.barcode4j.impl.upcean.UPCEBean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

public class BarcodeGenerator {
	
	private final static int DPI = 150;
	
	protected static Logger logger = Logger.getLogger(BarcodeGenerator.class);
	
	private String outputDirectory;
	
	public BarcodeGenerator(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}
	
	private AbstractBarcodeBean createBarcode(BarcodeTypes barcodeType) 
			throws NotSupportedException {
		AbstractBarcodeBean bean = null;
		if (barcodeType == BarcodeTypes.CODE39) {
			bean = new Code39Bean();
			((Code39Bean)bean).setWideFactor(3);
		}
		else if (barcodeType == BarcodeTypes.CODE128) {
			bean = new Code128Bean();
		}
		else if (barcodeType == BarcodeTypes.INTERLEAVED_2_OF_5) {
			bean = new Interleaved2Of5Bean();
		}
		else if (barcodeType == BarcodeTypes.POSTNET) {
			bean = new POSTNETBean();
		}
		else if (barcodeType == BarcodeTypes.EAN8) {
			bean = new EAN8Bean();
		}
		else if (barcodeType == BarcodeTypes.EAN13) {
			bean = new EAN13Bean();
		}
		else if (barcodeType == BarcodeTypes.EAN128) {
			bean = new EAN128Bean();
		}
		else if (barcodeType == BarcodeTypes.UPCA) {
			bean = new UPCABean();
		}
		else if (barcodeType == BarcodeTypes.UPCE) {
			bean = new UPCEBean();
		}
		else {
			throw new NotSupportedException(barcodeType.toString());
		}
		return bean;
	}
	
	public File generate(BarcodeTypes barcodeType, String value) {
		logger.debug("Generating barcode for string: " + value);
        try {
            //Create the barcode bean
        	AbstractBarcodeBean bean = createBarcode(barcodeType);
            
            //Configure the barcode generator
            bean.setModuleWidth(UnitConv.in2mm(1.0f / DPI));
            bean.doQuietZone(false);
            //Open output file
            String tmpFilename = outputDirectory + "/" + UUID.randomUUID().toString() + ".jpg";
            File outputFile = new File(tmpFilename);
            OutputStream out = new FileOutputStream(outputFile);
            try {
                //Set up the canvas provider for monochrome JPEG output 
                BitmapCanvasProvider canvas = new BitmapCanvasProvider(
                		out, "image/jpeg", DPI, BufferedImage.TYPE_BYTE_BINARY, false, 0);
                //Generate the barcode
                bean.generateBarcode(canvas, value);
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
