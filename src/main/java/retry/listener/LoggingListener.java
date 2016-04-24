package retry.listener;

import retry.retry.context.RetryContext;

public class LoggingListener implements RetryListener {

    public void before(RetryContext context) {
        System.err.println("Before :" + context);
    }

    public void after(RetryContext context, Throwable t) {
        String message = (t == null) ? null : " Message " + t.getMessage();
        System.err.println("After :" + context + " " + message);
    }

    public void onError(RetryContext context, Throwable t) {
        System.err.println("OnError :" + context + " Message " + t.getMessage());
    }

}
