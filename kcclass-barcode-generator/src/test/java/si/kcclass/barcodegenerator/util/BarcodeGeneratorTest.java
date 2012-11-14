package si.kcclass.barcodegenerator.util;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BarcodeGeneratorTest {
	
	File imgBarcode;

	@Before
	public void setUp() throws Exception {
		imgBarcode = null;
	}

	@After
	public void tearDown() throws Exception {
		if (imgBarcode != null) {
			imgBarcode.delete();
		}
	}

	@Test
	public void testGenerate() {
		imgBarcode = BarcodeGenerator.generate("123456");
		assertNotNull(imgBarcode);
	}

}
