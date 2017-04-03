package com.slimgears.slimbind;

import com.slimgears.slimbind.properties.AnnotationInfoProvider;
import com.slimgears.slimbind.model.ModelGenerator;
import com.slimgears.slimbind.properties.*;
import com.slimgears.slimbind.signals.Signal;
import com.slimgears.slimbind.signals.Signals;
import java8.util.function.Consumer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by denis on 3/31/2017.
 */
@RunWith(JUnit4.class)
public class BindingTest {
    @Test
    public void statefulProperty_publishThenSubscribe_shouldReceiveValue() {
        ValueProperty<String> stringProperty = Properties.value(String.class).build();

        stringProperty.publish("Hello");

        Consumer<String> subscriber = consumerMock();
        stringProperty.subscribe(subscriber);

        verify(subscriber).accept("Hello");
    }

    @Test
    public void validatedProperty_publishWhenValidationFailed_doesNotTriggerSubscriber() {
        ValueProperty<String> stringProperty = Properties
                .value(String.class)
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
    public void recursiveBinding_shouldNotProduceEndlessInvocations() {
        Signal<Integer> first = Signals.newSignal();
        Signal<Integer> second = Signals.newSignal();
        first.subscribe(val -> second.publish(val + 1));
        second.subscribe(val -> first.publish(val - 1));

        Consumer<Integer> consumer = consumerMock();
        second.subscribe(consumer);

        first.publish(1);
        verify(consumer, times(1)).accept(any());
    }

    @Test
    public void generatedModel_hasAllProperties() {
        PersonModel model = new ModelGenerator(new AnnotationInfoProvider()).generateModel(PersonModel.class);

        Assert.assertNotNull(model.age());
        Assert.assertNotNull(model.firstName());

        Consumer<String> firstNameConsumer = consumerMock();
        model.firstName().subscribe(firstNameConsumer);
        model.firstName().publish("John");

        verify(firstNameConsumer, times(1)).accept(any());
        verify(firstNameConsumer).accept("John");

        model.firstName().publish("John");
        verify(firstNameConsumer, times(2)).accept(any());
    }

    private static <T> Consumer<T> consumerMock() {
        //noinspection unchecked
        return (Consumer<T>)Mockito.mock(Consumer.class);
    }

}
