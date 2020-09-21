package ru.kpfu.itis.examples;

public class HumanRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("Human");
        }
    }
}
