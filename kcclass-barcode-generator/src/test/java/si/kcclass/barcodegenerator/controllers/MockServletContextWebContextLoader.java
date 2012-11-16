package si.kcclass.barcodegenerator.controllers;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.MergedContextConfiguration;
import org.springframework.test.context.support.AbstractContextLoader;
import org.springframework.web.context.support.StaticWebApplicationContext;

public class MockServletContextWebContextLoader extends AbstractContextLoader {

    private static final String SERVLET_CONTEXT_WEB_ROOT = "/resources";
    
	@Override
	public ApplicationContext loadContext(MergedContextConfiguration conf)
			throws Exception {
		return loadContext(conf.getLocations());
	}

    @Override
    public final ConfigurableApplicationContext loadContext(String... locations) throws Exception {

        /*
         * Use a type which implements ConfigurableWebApplicationContext!
         */
        StaticWebApplicationContext context = new StaticWebApplicationContext();
        prepareContext(context);
        customizeBeanFactory(context.getDefaultListableBeanFactory());
        createBeanDefinitionReader(context).loadBeanDefinitions(locations);
        AnnotationConfigUtils.registerAnnotationConfigProcessors(context);
        customizeContext(context);
        context.refresh();
        context.registerShutdownHook();
        return context;
    }

    protected void prepareContext(StaticWebApplicationContext context) {

        /*
         * Incorporate mock servlet context!
         */
        ServletContext servletContext = new MockServletContext(SERVLET_CONTEXT_WEB_ROOT,
                new FileSystemResourceLoader());
        servletContext.setAttribute(StaticWebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
                context);
        context.setServletContext(servletContext);

    }

    protected void customizeBeanFactory(DefaultListableBeanFactory beanFactory) {
    }

    protected BeanDefinitionReader createBeanDefinitionReader(StaticWebApplicationContext context) {
        return new XmlBeanDefinitionReader(context);
    }

    protected void customizeContext(StaticWebApplicationContext context) {
    }

    @Override
    protected String getResourceSuffix() {
        return "-context.xml";
    }
}