package si.kcclass.barcodegenerator.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BarcodeGeneratorTest {
	
	List<File> imgBarcodes;
	
	String strBarcode = "123456";
	String strBarcodeEAN8UPCE = "1234567";
	String strBarcodeEAN13UPCA = "123456789012";

	@Before
	public void setUp() throws Exception {
		imgBarcodes = new ArrayList<File>();
	}

	@After
	public void tearDown() throws Exception {
		for (File imgBarcode: imgBarcodes) {
			imgBarcode.delete();
		}
	}

	@Test
	public void testGenerate() {
		File imgBarcode;
		
		BarcodeGenerator generator = new BarcodeGenerator(System.getProperty("user.dir"));
		imgBarcode = generator.generate(BarcodeTypes.CODE39, strBarcode);
		assertNotNull(imgBarcodes);
		imgBarcodes.add(imgBarcode);
		
		imgBarcode = generator.generate(BarcodeTypes.CODE128, strBarcode);
		assertNotNull(imgBarcodes);
		imgBarcodes.add(imgBarcode);

		imgBarcode = generator.generate(BarcodeTypes.INTERLEAVED_2_OF_5, strBarcode);
		assertNotNull(imgBarcodes);
		imgBarcodes.add(imgBarcode);

		imgBarcode = generator.generate(BarcodeTypes.POSTNET, strBarcode);
		assertNotNull(imgBarcodes);
		imgBarcodes.add(imgBarcode);

		imgBarcode = generator.generate(BarcodeTypes.EAN8, strBarcodeEAN8UPCE);
		assertNotNull(imgBarcodes);
		imgBarcodes.add(imgBarcode);

		imgBarcode = generator.generate(BarcodeTypes.EAN13, strBarcodeEAN13UPCA);
		assertNotNull(imgBarcodes);
		imgBarcodes.add(imgBarcode);

		imgBarcode = generator.generate(BarcodeTypes.EAN128, strBarcodeEAN13UPCA);
		assertNotNull(imgBarcodes);
		imgBarcodes.add(imgBarcode);

		imgBarcode = generator.generate(BarcodeTypes.UPCA, strBarcodeEAN13UPCA);
		assertNotNull(imgBarcodes);
		imgBarcodes.add(imgBarcode);

		imgBarcode = generator.generate(BarcodeTypes.UPCE, strBarcodeEAN8UPCE);
		assertNotNull(imgBarcodes);
		imgBarcodes.add(imgBarcode);
	}
	
	@Test
	public void testUndefinedBarcodeType() {
		BarcodeGenerator generator = new BarcodeGenerator(System.getProperty("user.dir"));
		File imgBarcode = generator.generate(BarcodeTypes.UNKNOWN, null);
		assertNull(imgBarcode);
	}
	
	@Test
	public void testBarcodeGenerationFailure() {
		BarcodeGenerator generator = new BarcodeGenerator(System.getProperty("user.dir"));
		File imgBarcode = generator.generate(BarcodeTypes.CODE39, null);
		assertNull(imgBarcode);
	}
	

}
