package concurrency;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultithreadedExecutor {
    final ExecutorService executorService;
    final List<Callable<String>> executables;

    MultithreadedExecutor(final int numThreads, final List<Callable<String>> executables) {
        this.executorService = Executors.newFixedThreadPool(numThreads);
        this.executables = executables;
    }

    void process() throws InterruptedException {
        executorService.invokeAll(executables);
        executorService.shutdown();
    }
}
