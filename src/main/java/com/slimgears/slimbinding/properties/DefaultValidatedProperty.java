package com.slimgears.slimbinding.properties;

import com.slimgears.slimbinding.signals.Signal;
import com.slimgears.slimbinding.signals.Signals;
import java8.util.function.Consumer;
import java8.util.function.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by denis on 3/31/2017.
 */
public class DefaultValidatedProperty<T> implements ValidatedProperty<T> {
    private final Info<T> info;
    private final Signal<T> valueSignal;
    private final Validator<T> validator;

    public DefaultValidatedProperty(Executor executor, Info<T> info) {
        this.valueSignal = Signals.<T>builder()
                .executor(executor)
                .filter(val -> validator().validate(val).isValid())
                .build();
        this.validator = new InternalValidator<>();
        this.info = info;
    }

    @Override
    public Info<T> info() {
        return info;
    }

    @Override
    public Validator<T> validator() {
        return validator;
    }

    @Override
    public void subscribe(Consumer<T> subscriber) {
        valueSignal.subscribe(subscriber);
    }

    @Override
    public void unsubscribe(Consumer<T> subscriber) {
        valueSignal.unsubscribe(subscriber);
    }

    @Override
    public void publish(T value) {
        valueSignal.publish(value);
    }

    public static class Builder<T> {
        private Executor executor = Runnable::run;
        private final List<Function<T, ? extends Exception>> validators = new ArrayList<>();
        private final List<Consumer<T>> subscribers = new ArrayList<>();
        private final DefaultInfo<T> info;

        public Builder(Class<T> type) {
            this.info = new DefaultInfo<>(type);
        }

        public ValidatedProperty<T> build() {
            ValidatedProperty<T> property = new DefaultValidatedProperty<>(executor, info);
            validators.forEach(property.validator()::addValidator);
            subscribers.forEach(property::subscribe);
            return property;
        }

        public Builder<T> title(String title) {
            info.setTitle(title);
            return this;
        }

        public Builder<T> description(String description) {
            info.setDescription(description);
            return this;
        }

        public Builder<T> subscribe(Consumer<T> subscriber) {
            subscribers.add(subscriber);
            return this;
        }

        public Builder<T> validator(Function<T, ? extends Exception> validator) {
            validators.add(validator);
            return this;
        }

        public Builder<T> executor(Executor executor) {
            this.executor = executor;
            return this;
        }
    }

    static class DefaultInfo<T> implements Info<T> {
        private String title;
        private String description;
        private final Class<T> type;

        DefaultInfo(Class<T> type) {
            this.type = type;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public Class<T> getType() {
            return type;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
