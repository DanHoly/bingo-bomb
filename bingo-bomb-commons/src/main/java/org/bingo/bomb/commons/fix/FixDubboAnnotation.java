package org.bingo.bomb.commons.fix;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.ConcurrentHashSet;
import com.alibaba.dubbo.config.AbstractConfig;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ModuleConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.alibaba.dubbo.config.spring.ServiceBean;

/**
 * 修复Dubbo注解Bug
 * @author jiangchangcheng  
 * @date 2017年8月15日 下午4:23:55
 * @since JDK 1.7
 */
@Component("fixDubboAnnotation")
@Scope("singleton")
public class FixDubboAnnotation extends AbstractConfig implements DisposableBean, BeanPostProcessor, ApplicationContextAware {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(Logger.class);

	private final Set<ServiceConfig<?>> serviceConfigs = new ConcurrentHashSet<ServiceConfig<?>>();

	private final ConcurrentMap<String, ReferenceBean<?>> referenceConfigs = new ConcurrentHashMap<String, ReferenceBean<?>>();

	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void destroy() throws Exception {
		for (ServiceConfig<?> serviceConfig : serviceConfigs) {
			try {
				serviceConfig.unexport();
			} catch (Throwable e) {
				logger.error(e.getMessage(), e);
			}
		}
		for (ReferenceConfig<?> referenceConfig : referenceConfigs.values()) {
			try {
				referenceConfig.destroy();
			} catch (Throwable e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		Service service = getBeanClass(bean).getAnnotation(Service.class);
		if (service != null) {
			ServiceBean<Object> serviceConfig = new ServiceBean<Object>(service);
			if (void.class.equals(service.interfaceClass()) && "".equals(service.interfaceName())) {
				if (bean.getClass().getInterfaces().length > 0) {
					serviceConfig.setInterface(getBeanClass(bean).getInterfaces()[0]);
				} else {
					throw new IllegalStateException("Failed to export remote service class " + bean.getClass().getName() + ", cause: The @Service undefined interfaceClass or interfaceName, and the service class unimplemented any interfaces.");
				}
			}
			if (applicationContext != null) {
				serviceConfig.setApplicationContext(applicationContext);
				if (service.registry() != null && service.registry().length > 0) {
					List<RegistryConfig> registryConfigs = new ArrayList<RegistryConfig>();
					for (String registryId : service.registry()) {
						if (registryId != null && registryId.length() > 0) {
							registryConfigs.add((RegistryConfig) applicationContext.getBean(registryId, RegistryConfig.class));
						}
					}
					serviceConfig.setRegistries(registryConfigs);
				}
				if (service.provider() != null && service.provider().length() > 0) {
					serviceConfig.setProvider((ProviderConfig) applicationContext.getBean(service.provider(), ProviderConfig.class));
				}
				if (service.monitor() != null && service.monitor().length() > 0) {
					serviceConfig.setMonitor((MonitorConfig) applicationContext.getBean(service.monitor(), MonitorConfig.class));
				}
				if (service.application() != null && service.application().length() > 0) {
					serviceConfig.setApplication((ApplicationConfig) applicationContext.getBean(service.application(), ApplicationConfig.class));
				}
				if (service.module() != null && service.module().length() > 0) {
					serviceConfig.setModule((ModuleConfig) applicationContext.getBean(service.module(), ModuleConfig.class));
				}
				if (service.provider() != null && service.provider().length() > 0) {
					serviceConfig.setProvider((ProviderConfig) applicationContext.getBean(service.provider(), ProviderConfig.class));
				} else {

				}
				if (service.protocol() != null && service.protocol().length > 0) {
					List<ProtocolConfig> protocolConfigs = new ArrayList<ProtocolConfig>();
					for (String protocolId : service.registry()) {
						if (protocolId != null && protocolId.length() > 0) {
							protocolConfigs.add((ProtocolConfig) applicationContext.getBean(protocolId, ProtocolConfig.class));
						}
					}
					serviceConfig.setProtocols(protocolConfigs);
				}
				try {
					serviceConfig.afterPropertiesSet();
				} catch (RuntimeException e) {
					throw (RuntimeException) e;
				} catch (Exception e) {
					throw new IllegalStateException(e.getMessage(), e);
				}
			}
			serviceConfig.setRef(bean);
			serviceConfigs.add(serviceConfig);
			serviceConfig.export();
		}
		return bean;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		Method[] methods = getBeanClass(bean).getMethods();
		for (Method method : methods) {
			String name = method.getName();
			if (name.length() > 3 && name.startsWith("set") && method.getParameterTypes().length == 1 && Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers())) {
				try {
					Reference reference = method.getAnnotation(Reference.class);
					if (reference != null) {
						Object value = refer(reference, method.getParameterTypes()[0]);
						if (value != null) {
							method.invoke(bean, new Object[] {});
						}
					}
				} catch (Throwable e) {
					logger.error("Failed to init remote service reference at method " + name + " in class " + bean.getClass().getName() + ", cause: " + e.getMessage(), e);
				}
			}
		}
		Field[] fields = getBeanClass(bean).getDeclaredFields();
		for (Field field : fields) {
			try {
				if (!field.isAccessible()) {
					field.setAccessible(true);
				}
				Reference reference = field.getAnnotation(Reference.class);
				if (reference != null) {
					Object value = refer(reference, field.getType());
					if (value != null) {
						field.set(bean, value);
					}
				}
			} catch (Throwable e) {
				logger.error("Failed to init remote service reference at filed " + field.getName() + " in class " + bean.getClass().getName() + ", cause: " + e.getMessage(), e);
			}
		}
		return bean;
	}

	private Object refer(Reference reference, Class<?> referenceClass) {
		String interfaceName;
		if (!"".equals(reference.interfaceName())) {
			interfaceName = reference.interfaceName();
		} else if (!void.class.equals(reference.interfaceClass())) {
			interfaceName = reference.interfaceClass().getName();
		} else if (referenceClass.isInterface()) {
			interfaceName = referenceClass.getName();
		} else {
			throw new IllegalStateException("The @Reference undefined interfaceClass or interfaceName, and the property type " + referenceClass.getName() + " is not a interface.");
		}
		String key = reference.group() + "/" + interfaceName + ":" + reference.version();
		ReferenceBean<?> referenceConfig = referenceConfigs.get(key);
		if (referenceConfig == null) {
			referenceConfig = new ReferenceBean<Object>(reference);
			if (void.class.equals(reference.interfaceClass()) && "".equals(reference.interfaceName()) && referenceClass.isInterface()) {
				referenceConfig.setInterface(referenceClass);
			}
			if (applicationContext != null) {
				referenceConfig.setApplicationContext(applicationContext);
				if (reference.registry() != null && reference.registry().length > 0) {
					List<RegistryConfig> registryConfigs = new ArrayList<RegistryConfig>();
					for (String registryId : reference.registry()) {
						if (registryId != null && registryId.length() > 0) {
							registryConfigs.add((RegistryConfig) applicationContext.getBean(registryId, RegistryConfig.class));
						}
					}
					referenceConfig.setRegistries(registryConfigs);
				}
				if (reference.consumer() != null && reference.consumer().length() > 0) {
					referenceConfig.setConsumer((ConsumerConfig) applicationContext.getBean(reference.consumer(), ConsumerConfig.class));
				}
				if (reference.monitor() != null && reference.monitor().length() > 0) {
					referenceConfig.setMonitor((MonitorConfig) applicationContext.getBean(reference.monitor(), MonitorConfig.class));
				}
				if (reference.application() != null && reference.application().length() > 0) {
					referenceConfig.setApplication((ApplicationConfig) applicationContext.getBean(reference.application(), ApplicationConfig.class));
				}
				if (reference.module() != null && reference.module().length() > 0) {
					referenceConfig.setModule((ModuleConfig) applicationContext.getBean(reference.module(), ModuleConfig.class));
				}
				if (reference.consumer() != null && reference.consumer().length() > 0) {
					referenceConfig.setConsumer((ConsumerConfig) applicationContext.getBean(reference.consumer(), ConsumerConfig.class));
				}
				try {
					referenceConfig.afterPropertiesSet();
				} catch (RuntimeException e) {
					throw (RuntimeException) e;
				} catch (Exception e) {
					throw new IllegalStateException(e.getMessage(), e);
				}
			}
			referenceConfigs.putIfAbsent(key, referenceConfig);
			referenceConfig = referenceConfigs.get(key);
		}
		return referenceConfig.get();
	}

	private Class<?> getBeanClass(Object bean) {
		Class<?> clazz = bean.getClass();
		if (AopUtils.isAopProxy(bean)) {
			clazz = AopUtils.getTargetClass(bean);
		}
		return clazz;
	}
}
