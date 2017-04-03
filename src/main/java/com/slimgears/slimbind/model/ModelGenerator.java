package com.slimgears.slimbind.model;

import com.slimgears.slimbind.properties.Action;
import com.slimgears.slimbind.properties.CollectionProperty;
import com.slimgears.slimbind.properties.MultiSelectionProperty;
import com.slimgears.slimbind.properties.PropertyInfo;
import com.slimgears.slimbind.properties.PropertyInfoProvider;
import com.slimgears.slimbind.properties.SingleSelectionProperty;
import com.slimgears.slimbind.properties.ValueProperty;
import com.slimgears.slimbind.properties.internal.DefaultAction;
import com.slimgears.slimbind.properties.internal.DefaultCollectionProperty;
import com.slimgears.slimbind.properties.internal.DefaultMultiSelectionProperty;
import com.slimgears.slimbind.properties.internal.DefaultSingleSelectionProperty;
import com.slimgears.slimbind.properties.internal.DefaultValueProperty;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by denis on 4/1/2017.
 */
public class ModelGenerator {
    private final PropertyInfoProvider infoProvider;

    public ModelGenerator(PropertyInfoProvider infoProvider) {
        this.infoProvider = infoProvider;
    }

    public <M> M generateModel(Class<M> modelInterface) {
        assert modelInterface.isInterface();
        //noinspection unchecked
        return (M)Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] {modelInterface}, new Handler());
    }

    class Handler implements InvocationHandler {
        private final Map<Method, Object> values = new HashMap<>();

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object value = values.get(method);
            if (value == null) {
                PropertyInfo info = infoProvider.getInfo(method);
                Class type = method.getReturnType();
                if (type.isAssignableFrom(ValueProperty.class)) {
                    value = new DefaultValueProperty<>(info);
                } else if (type.isAssignableFrom(CollectionProperty.class)) {
                    value = new DefaultCollectionProperty<>(info);
                } else if (type.isAssignableFrom(SingleSelectionProperty.class)) {
                    value = new DefaultSingleSelectionProperty<>(info);
                } else if (type.isAssignableFrom(MultiSelectionProperty.class)) {
                    value = new DefaultMultiSelectionProperty<>(info);
                } else if (type.isAssignableFrom(Action.class)) {
                    value = new DefaultAction(info);
                }
                values.put(method, value);
            }

            return value;
        }
    }
}
