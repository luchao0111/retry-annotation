package retry.listener;

import retry.retry.context.RetryContext;

public interface RetryListener {

    void before(RetryContext context);

    void after(RetryContext context, Throwable throwable);

    void onError(RetryContext context, Throwable throwable);
}
