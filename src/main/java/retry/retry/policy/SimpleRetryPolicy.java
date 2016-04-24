package retry.retry.policy;

import retry.classifier.ExceptionClassifier;
import retry.retry.context.DefaultRetryContext;
import retry.retry.context.RetryContext;

public class SimpleRetryPolicy implements RetryPolicy {

    private int limit;
    private ExceptionClassifier classifier = new ExceptionClassifier();

    public boolean canRetry(RetryContext context) {
        Throwable t = context.getLastThrowable();
        return (t == null || retryForException(t.getCause()))
                && context.getRetryCount() < limit;
    }

    public void registerThrowable(Throwable throwable, RetryContext context) {
        DefaultRetryContext retryContext = ((DefaultRetryContext) context);
        retryContext.registerThrowable(throwable);
    }

    public boolean isExhausted(RetryContext context) {
        return context.isExhaustedOnly();
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    private boolean retryForException(Throwable ex) {
        return classifier.classify(ex);
    }

    public void setExceptionClassifier(ExceptionClassifier classifier) {
        this.classifier = classifier;
    }

    public void setRetryableExceptions(Class<? extends Exception>[] exceptions) {
        classifier.setRetryableExceptions(exceptions);
    }

    public void setAbortableExceptions(Class<? extends Exception>[] exceptions) {
        classifier.setAbortableExceptions(exceptions);
    }
}