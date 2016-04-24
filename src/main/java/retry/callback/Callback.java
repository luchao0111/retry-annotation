package retry.callback;

public interface Callback {

    Object execute() throws Throwable;

}