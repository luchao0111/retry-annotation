package retry.retry;

import retry.callback.Callback;
import retry.listener.RetryListener;
import retry.retry.context.DefaultRetryContext;
import retry.retry.context.RetryContext;
import retry.retry.policy.RetryPolicy;
import retry.sleep.Sleeper;

import java.util.ArrayList;
import java.util.List;

public class RetryHandler {

    private volatile List<RetryListener> listeners = new ArrayList<RetryListener>();
    private volatile Sleeper backOff = new Sleeper();
    private volatile RetryPolicy policy;

    public final Object execute(Callback callback) throws Exception {
        RetryContext context = initRetryContext();
        try {
            while (policy.canRetry(context)) {
                System.err.println("Attempt " + context.getRetryCount());
                notifyBefore(context);
                try {
                    return callback.execute();
                } catch (Throwable t) {
                    policy.registerThrowable(t, context);
                    notifyError(context, t);
                    backOff.sleep();
                }
            }
            handleRetryExhausted(context);
        } finally {
            notifyAfter(context);
        }
        return null;
    }

    public void addListeners(RetryListener... listener) {
        for (RetryListener thisListener : listener)
            listeners.add(thisListener);
    }

    public void setPolicy(RetryPolicy policy) {
        this.policy = policy;
    }

    public void setWaitPeriod(long waitPeriod) {
        this.backOff.setSleepPeriod(waitPeriod);
    }

    private void notifyBefore(RetryContext context) {
        for (RetryListener listener : listeners)
            listener.before(context);
    }

    private void notifyAfter(RetryContext context) {
        for (RetryListener listener : listeners) {
            Throwable t = context.getLastThrowable();
            listener.after(context, t != null ? t.getCause() : null);
        }
    }

    private void notifyError(RetryContext context, Throwable t) {
        for (RetryListener listener : listeners)
            listener.onError(context, t.getCause());
    }

    private RetryContext initRetryContext() {
        return new DefaultRetryContext();
    }

    private void handleRetryExhausted(RetryContext ctx) throws Exception {
        throw wrapException(ctx.getLastThrowable());
    }

    private static Exception wrapException(Throwable throwable) {
        if (throwable instanceof Error) {
            throw (Error) throwable;
        } else if (throwable instanceof Exception) {
            return (Exception) throwable.getCause();
        } else {
            return new RuntimeException("RuntimeException ", throwable);
        }
    }
}