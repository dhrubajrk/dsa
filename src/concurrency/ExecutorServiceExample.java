package concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceExample {
    private void tryOutExecutorService() {
        System.out.println("::::::START:::::");
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        List<Future> futures = new ArrayList<>();
        for (int k = 0; k < 5; ++k) {
            final int caller = k;
            futures.add(executorService.submit(() -> process(caller)));
        }
        futures.parallelStream().forEach(future -> {
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
        System.out.println("::::::DONE:::::");
    }

    private void process(final int caller) {
        for (int i = 0; i < 5; ++i) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Caller -> " + caller + " " + Thread.currentThread().getName() + " -> " + i);
        }
    }

    public static void main(String[] args) {
        ExecutorServiceExample executorServiceExample = new ExecutorServiceExample();
        executorServiceExample.tryOutExecutorService();
    }
}
