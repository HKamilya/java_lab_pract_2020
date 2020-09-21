package ru.kpfu.itis.pool;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ThreadPool {
    private Deque<Runnable> tasks;

    public ThreadPool newPool(int threadsCount) {
        ThreadPool threadPool = new ThreadPool();
        tasks = new ConcurrentLinkedDeque<>();
        PoolWorker[] pool = new PoolWorker[threadsCount];

        for (int i = 0; i < threadsCount; i++) {
            pool[i] = new PoolWorker();
            pool[i].start();
        }

        return threadPool;
    }

    public void submit(Runnable task) {
        synchronized (tasks) {
            tasks.add(task);
            tasks.notify();
        }

    }

    private class PoolWorker extends Thread {
        @Override
        public void run() {
            Runnable task;

            while (true) {
                synchronized (tasks) {
                    while (tasks.isEmpty()) {
                        try {
                            tasks.wait();
                        } catch (InterruptedException e) {
                            throw new IllegalStateException(e);
                        }
                    }
                    task = tasks.poll();
                }
                try {
                    task.run();
                } catch (RuntimeException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}