package com.gsw.easyrpc.common.utils;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.util.Set;

public class AnnotationUtil {

    public static AnnotationAttributes findAutowiredAnnotation(AccessibleObject ao, Set<Class<? extends Annotation>> annotationTypes) {
        if (ao.getAnnotations().length > 0) {
            for (Class<? extends Annotation> type : annotationTypes) {
                AnnotationAttributes attributes = AnnotatedElementUtils.getMergedAnnotationAttributes(ao, type);
                if (attributes != null) {
                    return attributes;
                }
            }
        }
        return null;
    }
}
