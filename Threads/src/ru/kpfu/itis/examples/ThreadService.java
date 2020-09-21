package ru.kpfu.itis.examples;

public class ThreadService {
    public void submit(Runnable task) {
        Thread thread = new Thread(task);
        thread.start();
    }
}

