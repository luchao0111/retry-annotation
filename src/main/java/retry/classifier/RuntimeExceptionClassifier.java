package retry.classifier;

public class RuntimeExceptionClassifier extends ExceptionClassifier {

    @Override
    public Boolean classify(Throwable classifiable) {

        System.err.println("RuntimeExceptionClassifier.classify()");

        if (classifiable instanceof RuntimeException) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }

    }

}