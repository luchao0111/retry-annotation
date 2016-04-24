package retry.retry.context;

public class DefaultRetryContext implements RetryContext {

    private Throwable lastException;
    private int count;
    private boolean terminate;

    public void setExhaustedOnly(boolean terminate) {
        this.terminate = terminate;
    }

    public boolean isExhaustedOnly() {
        return terminate;
    }

    public int getRetryCount() {
        return count;
    }

    public Throwable getLastThrowable() {
        return lastException;
    }

    public void registerThrowable(Throwable throwable) {
        this.lastException = throwable;
        if (throwable != null)
            count++;
    }

}