package si.kcclass.barcodegenerator.controllers;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import si.kcclass.barcodegenerator.util.BarcodeTypes;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext-test.xml", "file:src/main/webapp/WEB-INF/spring/webmvc-config-test.xml"})
public class BarcodeGeneratorControllerTest {

	@Autowired
	private RequestMappingHandlerAdapter handlerAdapter;
	
	@Autowired
	private RequestMappingHandlerMapping handlerMapping;
	
	private BarcodeTypes barcodeType;
	String barcodeValue;
	
	@Before
	public void setUp() throws Exception {
		barcodeType = BarcodeTypes.CODE39;
		barcodeValue = "123456";
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testGenerateOnRequest() throws Exception {
		//String realPath = "file:/home/marko/eclipse-workspaces/sts-workspace/kcclass-barcode-generator/kcclass-barcode-generator/src/main/webapp";  
		String realPath = "file:" + System.getProperty("user.dir") + "/src/main/webapp";
        MockServletContext context = new MockServletContext(realPath);
		MockHttpServletRequest request = new MockHttpServletRequest(
				context,
				"GET", "/barcodegenerator/generate/" + barcodeType + 
				"/" + barcodeValue);
		MockHttpServletResponse response = new MockHttpServletResponse();
		Object handler = handlerMapping.getHandler(request).getHandler();
		ModelAndView mav = handlerAdapter.handle(request, response, handler);
        Assert.assertNull(mav);
        Assert.assertNotNull(response);
	}

}
