package retry.retry.context;

public interface RetryContext {

    void setExhaustedOnly(boolean terminate);

    boolean isExhaustedOnly();

    int getRetryCount();

    Throwable getLastThrowable();

}