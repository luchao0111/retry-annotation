package retry.annotation;

import retry.classifier.ExceptionClassifier;
import retry.listener.RetryListener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Retry {

    long sleep() default 0;

    Class<? extends RetryListener>[] listeners() default {};

    int limit() default 3;

    Class<? extends Exception>[] retryableExceptions() default {Exception.class};

    Class<? extends Exception>[] abortableExceptions() default {};

    Class<? extends ExceptionClassifier> exceptionClassifier() default ExceptionClassifier.class;
}