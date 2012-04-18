package br.com.bufunfa.finance.core.ui.jsf.util;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

import junit.framework.Assert;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import br.com.bufunfa.finance.user.ui.jsf.config.UserViewNames;

public class Property2BeanUtil {
	
	public static Object fillBeanWithProperties(Object bean, String propertyFileName) throws IOException, IllegalAccessException, InvocationTargetException {
		ResourceBundle bundle = ResourceBundle.getBundle(propertyFileName);
		Properties prop = new Properties();
		fillProperties(prop, bundle);
		BeanUtils.populate(bean, prop);
		return bean;
	}
	
	private static void fillProperties(Properties prop, ResourceBundle bundle) {
		Enumeration<String> en = bundle.getKeys();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			prop.put(key, bundle.getObject(key));
		}
	}

	@Test
	public void testFillBean() throws IOException, IllegalAccessException, InvocationTargetException {
		UserViewNames bean = new UserViewNames();
		String resource = UserViewNames.class.getCanonicalName();
		fillBeanWithProperties(bean, resource);
		
		Assert.assertNotNull("cancel nulo", bean.getButtonCancelNewUser());
	}

}
