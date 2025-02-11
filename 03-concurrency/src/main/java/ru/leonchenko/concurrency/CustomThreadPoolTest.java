package ru.leonchenko.concurrency;

public class CustomThreadPoolTest {
    public static void main(String[] args) throws InterruptedException {

        CustomThreadPool pool = new CustomThreadPool(3);

        System.out.println("Запускаются задачи в пуле...");
        runTasks(pool);

        pool.shutdown();

        var areThreadsTerminated =  pool.awaitTermination();
        System.out.println("Все потоки в пуле завершены: " + areThreadsTerminated);
    }

    private static void runTasks(CustomThreadPool pool) throws InterruptedException {
        for (int i = 1; i < 6; i++) {
            int taskId = i;
            pool.execute(() -> {
                System.out.printf("Таска %s запущена в потоке %s%n",  taskId, Thread.currentThread().getName());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
