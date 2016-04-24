package retry.classifier;

@SuppressWarnings("unchecked")
public class ExceptionClassifier implements Classifier<Throwable, Boolean> {

    Class<? extends Exception>[] retryableExceptions = new Class[]{Exception.class};

    Class<? extends Exception>[] abortableExceptions = new Class[]{};

    public void setRetryableExceptions(Class<? extends Exception>[] exceptions) {
        this.retryableExceptions = exceptions;
    }

    public void setAbortableExceptions(Class<? extends Exception>[] exceptions) {
        this.abortableExceptions = exceptions;
    }

    public Boolean classify(Throwable classifiable) {

        for (Class<? extends Exception> e : abortableExceptions) {
            if (e.isAssignableFrom(classifiable.getClass()))
                return Boolean.FALSE;
        }

        for (Class<? extends Exception> e : retryableExceptions) {
            if (e.isAssignableFrom(classifiable.getClass()))
                return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

}