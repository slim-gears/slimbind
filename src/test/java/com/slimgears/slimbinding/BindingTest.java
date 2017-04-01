package com.slimgears.slimbinding;

import com.slimgears.slimbinding.model.ModelGenerator;
import com.slimgears.slimbinding.properties.*;
import java8.util.function.Consumer;
import java8.util.function.Predicate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.lang.ref.WeakReference;
import java.util.function.Supplier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by denis on 3/31/2017.
 */
@RunWith(JUnit4.class)
public class BindingTest {
    @Test
    public void statefulProperty_publishThenSubscribe_shouldReceiveValue() {
        Property<String> stringProperty = Properties.builder(String.class).build();

        stringProperty.publish("Hello");

        Consumer<String> subscriber = consumerMock();
        stringProperty.subscribe(subscriber);

        verify(subscriber).accept("Hello");
    }

    @Test
    public void validatedProperty_publishWhenValidationFailed_doesNotTriggerSubscriber() {
        ValidatedProperty<String> stringProperty = Properties
                .builder(String.class)
                .validator(Validators.forPredicate((String str) -> str.contains("World"), str -> new Exception("Wrong input: " + str)))
                .build();

        Consumer<ValidationResult> resultConsumer = consumerMock();
        stringProperty.validator().subscribe(resultConsumer);
        Consumer<String> valueConsumer = consumerMock();
        stringProperty.subscribe(valueConsumer);

        stringProperty.publish("Hello");
        verify(valueConsumer, never()).accept(any());

        verify(resultConsumer, times(1)).accept(any());
        verify(resultConsumer).accept(ArgumentMatchers
                .argThat(res -> !res.isValid() && res.getErrors().size() == 1));

    }

    @Test
    public void generatedModel_hasAllProperties() {
        TestModel model = new ModelGenerator().generateModel(TestModel.class);

        Assert.assertNotNull(model.age());
        Assert.assertNotNull(model.firstName());

        Consumer<String> firstNameConsumer = consumerMock();
        model.firstName().subscribe(firstNameConsumer);
        model.firstName().publish("John");

        verify(firstNameConsumer, times(1)).accept(any());
        verify(firstNameConsumer).accept("John");

        model.firstName().publish("John");
        verify(firstNameConsumer, times(1)).accept(any());
    }

    private static <T> Consumer<T> consumerMock() {
        //noinspection unchecked
        return (Consumer<T>)Mockito.mock(Consumer.class);
    }

}
