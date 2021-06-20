package concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public class ProducerConsumer {
    private void process() throws InterruptedException {
        final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        List<Callable<String>> executables = new ArrayList<>();
        executables.add(new Producer(queue));
        executables.add(new Consumer(queue));
        new MultithreadedExecutor(2, executables).process();
    }

    public static void main(String[] args) throws InterruptedException {
        new ProducerConsumer().process();
    }

    private class Producer implements Callable<String> {
        private final BlockingQueue<Integer> queue;

        Producer(final BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public String call() {
            for (int i = 0; i < 100; ++i) {
                System.out.println("Produced item - " + i);
                tryPut(i);
            }
            System.out.println("Produced last item - -1");
            tryPut(-1);
            return "Exiting Producer";
        }

        private void tryPut(int i) {
            try {
                queue.put(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class Consumer implements Callable<String> {
        private final BlockingQueue<Integer> queue;

        Consumer(final BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public String call() {
            int item;
            while ((item = tryGet()) != -1) {
                System.out.println("Consumed item - " + item);
            }
            return "Exiting Consumer";
        }

        private int tryGet() {
            try {
                return queue.take();
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }
    }

}
