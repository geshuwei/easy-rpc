package com.gsw.easyrpc.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class EasyRpcServerAutowireProcessor implements BeanPostProcessor, BeanFactoryPostProcessor {


    private final Log logger = LogFactory.getLog(getClass());

    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        configurableListableBeanFactory.addBeanPostProcessor(this);
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof EasyRpcService) {
            try {
                ServiceContainer.INSTANCE.addEasyRpcService((EasyRpcService) bean);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new BeanInitializationException("addEasyRpcService fail", e);
            }
        }
        return bean;
    }

}
