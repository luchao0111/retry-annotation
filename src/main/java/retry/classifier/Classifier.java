package retry.classifier;

public interface Classifier<C, T> {

    T classify(C classifiable);

}
