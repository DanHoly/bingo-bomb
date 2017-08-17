package org.bingo.bomb.commons.fix;

import java.lang.reflect.Method;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.PropertyResourceConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.support.PropertiesLoaderSupport;
import org.springframework.stereotype.Component;

/**
 * Spring容器持有
 * @author jiangchangcheng  
 * @date 2017年8月15日 下午4:48:31
 * @since JDK 1.7
 */
@Component("springContextHolder")
@Scope("singleton")
public class SpringContextHolder implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	private Properties properties = new Properties();

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		initProperties();
	}

	private void checkApplicationContext() {
		if (applicationContext == null) {
			throw new IllegalStateException("applicaitonContext未注入");
		}
	}

	private void initProperties() {
		checkApplicationContext();
		try {
			String[] postProcessorNames = applicationContext.getBeanNamesForType(BeanFactoryPostProcessor.class, true, true);
			for (String ppName : postProcessorNames) {
				BeanFactoryPostProcessor beanProcessor = applicationContext.getBean(ppName, BeanFactoryPostProcessor.class);
				if (beanProcessor instanceof PropertyResourceConfigurer) {
					PropertyResourceConfigurer propertyResourceConfigurer = (PropertyResourceConfigurer) beanProcessor;
					Method mergeProperties = PropertiesLoaderSupport.class.getDeclaredMethod("mergeProperties");
					mergeProperties.setAccessible(true);
					Properties props = (Properties) mergeProperties.invoke(propertyResourceConfigurer);
					Method convertProperties = PropertyResourceConfigurer.class.getDeclaredMethod("convertProperties", Properties.class);
					convertProperties.setAccessible(true);
					convertProperties.invoke(propertyResourceConfigurer, props);
					properties.putAll(props);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getProperty(String name) {
		return this.properties.getProperty(name);
	}

	public Boolean hasProperty(String name) {
		return properties.containsKey(name);
	}

	public Boolean hasBean(String name) {
		checkApplicationContext();
		return applicationContext.containsBean(name);
	}

	public <T> T getBean(String name, Class<T> clazz) {
		checkApplicationContext();
		return applicationContext.getBean(name, clazz);
	}
}
