package com.gsw.easyrpc.client;

import com.gsw.easyrpc.common.annotations.EasyRpcClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.HashSet;

import static com.gsw.easyrpc.common.utils.AnnotationUtil.findAutowiredAnnotation;

public class EasyRpcClientAutowireProcessor implements BeanPostProcessor, BeanFactoryPostProcessor {

    private final Log logger = LogFactory.getLog(getClass());
    private ConfigurableListableBeanFactory configurableListableBeanFactory;

    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        configurableListableBeanFactory.addBeanPostProcessor(this);
        this.configurableListableBeanFactory = configurableListableBeanFactory;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        ReflectionUtils.doWithLocalFields(bean.getClass(), (field) -> {
            AnnotationAttributes ann = findAutowiredAnnotation(field, new HashSet<Class<? extends Annotation>>(){{add(EasyRpcClient.class);}});
            if (ann == null) {
                return;
            }
            if (Modifier.isStatic(field.getModifiers()) && logger.isWarnEnabled()) {
                logger.warn("EasyRpcClient annotation is not supported on static fields: " + field);
                return;
            }
            String remoteServerName = ann.getString("remoteServerName");
            ReflectionUtils.makeAccessible(field);
            RemoteServiceProxy remoteServiceProxy = configurableListableBeanFactory.getBean(RemoteServiceProxy.class);
            ReflectionUtils.setField(field, bean, remoteServiceProxy.getProxy(field.getType(), remoteServerName));
        });
        return bean;
    }
}
