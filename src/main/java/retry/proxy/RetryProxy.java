package retry.proxy;

import java.lang.reflect.Proxy;

public class RetryProxy {

    @SuppressWarnings("unchecked")
    public static <T> T getNewProxy(Object proxied, Class<T> interfaze) {
        long t1 = System.currentTimeMillis();
        T proxy = (T) Proxy.newProxyInstance(RetryProxy.class.getClassLoader(), new Class[]{interfaze}, new RetryProxyHandler(proxied));
        long t2 = System.currentTimeMillis();
        System.err.println("Proxy creation took " + (t2 - t1) + " ms");
        return proxy;
    }

}