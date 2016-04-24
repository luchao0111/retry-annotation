package retry.listener;

import retry.retry.context.RetryContext;

public class AdvancedLoggingListener extends LoggingListener {

    @Override
    public void onError(RetryContext context, Throwable t) {
        super.onError(context, t);
        System.err.println("Time now is " + System.currentTimeMillis());
    }

}