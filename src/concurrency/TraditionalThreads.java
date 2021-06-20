package concurrency;

public class TraditionalThreads {

    // Implementing Runnable interface
    private static class RunnableThread implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 5; ++i) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "->" + i);
            }
        }
    }

    private void tryOutRunnableThreads() throws InterruptedException {
        Thread thread1 = new Thread(new RunnableThread());
        Thread thread2 = new Thread(new RunnableThread());
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

    public static void main(String[] args) throws InterruptedException {
        TraditionalThreads traditionalThreads = new TraditionalThreads();
        traditionalThreads.tryOutRunnableThreads();
    }
}
