package retry.service;

import retry.annotation.Retry;
import retry.classifier.RuntimeExceptionClassifier;
import retry.listener.LoggingListener;
import retry.proxy.RetryProxy;

import java.io.IOException;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

public class ConsolePrintService implements PrintService {
    private static Map<Boolean, PrintService> serviceCache = new HashMap<Boolean, PrintService>();

    private int counter = 0;
    long startTime = -1;

    private ConsolePrintService() {
    }

    public static PrintService getInstance(Boolean supportRetry) {
        if (!serviceCache.containsKey(supportRetry)) {
            PrintService service;
            if (supportRetry)
                service = RetryProxy.getNewProxy(new ConsolePrintService(), PrintService.class);
            else
                service = new ConsolePrintService();
            serviceCache.put(supportRetry, service);
        }
        return serviceCache.get(supportRetry);
    }

    public void printA() throws Exception {
        if (++counter < 3)
            throw new SocketException("Exception occured in service, after " + (counter) + " attempts");
        System.err.println("print completed successfully");
    }

    @Retry
    public void print() throws Exception {
        if (++counter < 3)
            throw new SocketException("Exception occured in service, after " + (counter) + " attempts");
        System.err.println("print completed successfully in " + counter + " attempts");
    }

    @Retry(limit = 5)
    public void printC() throws Exception {
        if (++counter < 4)
            throw new SocketException("Exception occured in service, after " + (counter) + " attempts");
        System.err.println("print completed successfully in " + counter + " attempts");
    }

    @Retry(retryableExceptions = IOException.class)
    public void printD() throws Exception {
        if (++counter < 3)
            throw new IOException("Exception occured in service, after " + (counter) + " attempts");
        System.err.println("print completed successfully in " + counter + " attempts ");
    }

    @Retry(abortableExceptions = SocketException.class)
    public void printE() throws Exception {
        if (++counter < 3)
            throw new SocketException("Exception occured in service, after " + (counter) + " attempts");
        System.err.println("print completed successfully in " + counter + " attempts ");
    }

    @Retry(exceptionClassifier = RuntimeExceptionClassifier.class)
    public void printF() throws Exception {
        if (++counter < 3)
            throw new ArrayIndexOutOfBoundsException("Exception occured in service, after " + (counter) + " attempts");
        System.err.println("print completed successfully in " + counter + " attempts");
    }

    @Retry(sleep = 5000)
    public void printG() throws Exception {
        if (startTime == -1) {
            startTime = System.currentTimeMillis();
        }
        long now = System.currentTimeMillis();
        if ((now - startTime) <= 4000)
            throw new SocketException("Exception occured in service, after " + (counter) + " attempts");
        System.err.println("print completed successfully in " + counter + " attempts after " + (now - startTime) + " ms");
    }

    @Retry(listeners = LoggingListener.class)
    public void printH() throws Exception {
        if (++counter < 3)
            throw new SocketException("Exception occured in service, after " + (counter) + " attempts");
        System.err.println("print completed successfully in " + counter + " attempts");
    }
}