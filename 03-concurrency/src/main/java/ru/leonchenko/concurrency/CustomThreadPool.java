package ru.leonchenko.concurrency;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomThreadPool {

    private final BlockingQueue<Runnable> taskQueue;
    private final List<WorkerThread> workers;
    private volatile boolean isShutdown = false;
    private final AtomicInteger taskCount = new AtomicInteger(0);
    private final CountDownLatch tasksCompleted = new CountDownLatch(1);

    public CustomThreadPool(int capacity) {
        this.taskQueue = new LinkedBlockingQueue<>();
        this.workers = new LinkedList<>();

        for (int i = 0; i < capacity; i++) {
            WorkerThread worker = new WorkerThread();
            workers.add(worker);
            worker.start();
        }
    }

    public void execute(Runnable task) throws InterruptedException {
        if (isShutdown) {
            throw new IllegalStateException("Текущий пул потоков завершен.");
        }
        taskCount.incrementAndGet();
        taskQueue.put(task);
    }

    public void shutdown() {

        isShutdown = true;

        try {
            tasksCompleted.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        for (WorkerThread worker : workers) {
            worker.interrupt();
        }

        System.out.println("Задачи в пуле завершены! Выставлен флаг " + isShutdown + ".");
    }

    public boolean awaitTermination() {

        for (WorkerThread worker : workers) {
            try {
                worker.join();
            } catch (InterruptedException e) {
                return false;
            }

            if (worker.isAlive()) {
                return false;
            }
        }

        return true;
    }

    private class WorkerThread extends Thread {
        @Override
        public void run() {
            while (!isShutdown || !taskQueue.isEmpty()) {
                try {
                    Runnable task = taskQueue.take();
                    task.run();
                } catch (InterruptedException e) {
                    break;
                } finally {
                    if (taskCount.decrementAndGet() == 0) {
                        tasksCompleted.countDown();
                    }
                }
            }
        }
    }
}