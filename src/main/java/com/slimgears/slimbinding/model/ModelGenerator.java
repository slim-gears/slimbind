package com.slimgears.slimbinding.model;

import com.slimgears.slimbinding.properties.Properties;
import com.slimgears.slimbinding.properties.Property;
import com.slimgears.slimbinding.properties.ValidatedProperty;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by denis on 4/1/2017.
 */
public class ModelGenerator {
    static class Handler implements InvocationHandler {
        private final Map<Method, Object> values = new HashMap<>();

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object value = values.get(method);
            if (value == null) {
                Class type = method.getReturnType();
                if (type.isAssignableFrom(ValidatedProperty.class)) {
                    value = Properties
                            .builder(type)
                            .description(Properties.descriptionForMethod(method))
                            .title(Properties.titleForMethod(method))
                            .build();
                    values.put(method, value);
                }
            }

            return value;
        }
    }

    public <M> M generateModel(Class<M> modelInterface) {
        assert modelInterface.isInterface();
        //noinspection unchecked
        return (M)Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] {modelInterface}, new Handler());
    }
}
