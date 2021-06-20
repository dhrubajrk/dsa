package concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

public class Print1To100UsingExecutorService {
    int commonInt;

    private void process() throws InterruptedException {
        System.out.println("::::::START:::::");
        final MultithreadedExecutor multithreadedExecutor = new MultithreadedExecutor(10, createTurns());
        multithreadedExecutor.process();
        System.out.println("::::::DONE:::::");
    }

    private List<Callable<String>> createTurns() {
        final List<Callable<String>> turns = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            int mod = i;
            turns.add(new TakingTurns(k -> k % 10 == mod));
        }
        return turns;
    }

    private class TakingTurns implements Callable<String> {
        private final Predicate<Integer> predicate;
        private final Lock lock;

        public TakingTurns(final Predicate<Integer> predicate) {
            this.predicate = predicate;
            this.lock = new ReentrantLock();
        }

        @Override
        public String call() {
            while (commonInt <= 100) {
                try {
                    if (predicate.test(commonInt) && lock.tryLock(40, TimeUnit.MILLISECONDS)) {
                        System.out.println(Thread.currentThread().getName() + " -> " + commonInt);
                        commonInt++;
                        lock.unlock();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return "Exiting Turn";
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Print1To100UsingExecutorService printUtility = new Print1To100UsingExecutorService();
        printUtility.commonInt = 1;
        printUtility.process();
    }
}
