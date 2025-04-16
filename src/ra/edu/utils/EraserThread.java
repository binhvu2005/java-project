package ra.edu.utils;

public class EraserThread implements Runnable {
    private volatile boolean stop;

    @Override
    public void run() {
        while (!stop) {
            System.out.print("\b*");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stopMasking() {
        this.stop = true;
    }

}
