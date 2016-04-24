package retry.retry.policy;

import retry.retry.context.RetryContext;

public interface RetryPolicy {

    boolean canRetry(RetryContext context);

    void registerThrowable(Throwable throwable, RetryContext context);

    boolean isExhausted(RetryContext context);

    void setLimit(int attempts);
}