package retry.annotation.parser;

import retry.annotation.Retry;
import retry.listener.RetryListener;
import retry.retry.RetryHandler;
import retry.retry.policy.SimpleRetryPolicy;

public class RetryHandlerBuilder {

    public RetryHandler build(Retry retry) throws Exception {
        RetryHandler handler = new RetryHandler();

        setRetryPolicy(handler, retry);
        setWaitPeriod(handler, retry.sleep());
        setListeners(handler, retry.listeners());

        return handler;
    }

    private void setRetryPolicy(RetryHandler handler, Retry retry)
            throws Exception {
        SimpleRetryPolicy policy = new SimpleRetryPolicy();

        policy.setLimit(retry.limit());
        policy.setExceptionClassifier(retry.exceptionClassifier().newInstance());
        policy.setRetryableExceptions(retry.retryableExceptions());
        policy.setAbortableExceptions(retry.abortableExceptions());

        handler.setPolicy(policy);
    }

    private void setWaitPeriod(RetryHandler handler, long waitBetweenRetry) {
        handler.setWaitPeriod(waitBetweenRetry);
    }

    private void setListeners(RetryHandler handler,
                              Class<? extends RetryListener>[] listeners) throws Exception {

        for (Class<? extends RetryListener> listener : listeners) {
            handler.addListeners(listener.newInstance());
        }

    }

}