package concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

public class Print1To100Multithreading {
    int commonInt;
    List<Thread> turns = new ArrayList<>();

    private void process() {
        System.out.println("::::::START:::::");
        for (int i = 0; i < 10; ++i) {
            int mod = i;
            turns.add(new Thread(new TakingTurns(k -> k % 10 == mod)));
        }
        turns.parallelStream().forEach(Thread::start);
        turns.parallelStream().forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("::::::DONE:::::");
    }

    private class TakingTurns implements Runnable {
        Predicate<Integer> predicate;
        Lock lock;

        public TakingTurns(Predicate<Integer> predicate) {
            this.predicate = predicate;
            this.lock = new ReentrantLock();
        }

        @Override
        public void run() {
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
        }
    }

    public static void main(String[] args) {
        Print1To100Multithreading printUtility = new Print1To100Multithreading();
        printUtility.commonInt = 1;
        printUtility.process();
    }
}
