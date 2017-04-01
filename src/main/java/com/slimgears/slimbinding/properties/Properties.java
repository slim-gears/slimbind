package com.slimgears.slimbinding.properties;

import com.slimgears.slimbinding.annotations.Description;
import com.slimgears.slimbinding.annotations.Title;
import java8.util.Optional;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by denis on 3/31/2017.
 */
public class Properties {
    public static <T> DefaultValidatedProperty.Builder<T> builder(Class<T> type) {
        return new DefaultValidatedProperty.Builder<>(type);
    }

    public static String descriptionForMethod(Method method) {
        return forMethod(method, Description.class).map(Description::value).orElse(null);
    }

    public static String titleForMethod(Method method) {
        return forMethod(method, Title.class).map(Title::value).orElse(null);
    }

    private static <A extends Annotation> Optional<A> forMethod(Method method, Class<A> annotationClass) {
        return Optional.ofNullable(method.getAnnotation(annotationClass));
    }
}
