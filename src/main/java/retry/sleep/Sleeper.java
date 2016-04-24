package retry.sleep;

public class Sleeper {

    private long sleepPeriod;

    public void setSleepPeriod(long sleepPeriod) {
        this.sleepPeriod = sleepPeriod;
    }

    public void sleep() throws Exception {
        if (sleepPeriod > 0) {
            System.err.println("Waiting for " + sleepPeriod);
            Object mutex = new Object();
            synchronized (mutex) {
                mutex.wait(sleepPeriod);
            }
        }
    }

}