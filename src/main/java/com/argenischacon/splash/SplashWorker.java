package com.argenischacon.splash;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public final class SplashWorker extends SwingWorker<Void, Void> {

    private static final int MIN_SPLASH_TIME_MS = 2000;
    private final SplashScreen splash;
    private final long startTime;
    private final List<SplashTask> tasks;
    private final Runnable onFinishedCallback;
    private final Consumer<Throwable> onErrorCallback;

    private volatile int targetProgress = 0;
    private final Timer animationTimer;
    private volatile boolean backgroundWorkDone = false;
    private volatile Throwable executionError = null;


    @FunctionalInterface
    public interface ThrowableRunnable {
        void run() throws Exception;
    }

    private record SplashTask(String message, ThrowableRunnable action) {}

    private SplashWorker(SplashScreen splash, List<SplashTask> tasks, Runnable onFinishedCallback, Consumer<Throwable> onErrorCallback) {
        this.splash = Objects.requireNonNull(splash, "SplashScreen cannot be null");
        this.tasks = Objects.requireNonNull(tasks, "Task list cannot be null");
        this.onFinishedCallback = Objects.requireNonNull(onFinishedCallback, "onFinished callback cannot be null");
        this.onErrorCallback = Objects.requireNonNull(onErrorCallback, "onError callback cannot be null");
        this.startTime = System.currentTimeMillis();

        this.animationTimer = new Timer(15, e -> {
            if (splash.getProgress() < targetProgress) {
                splash.setProgress(splash.getProgress() + 1);
            }

            if (backgroundWorkDone) {
                // If an error occurred, stop immediately and handle it
                if (executionError != null) {
                    ((Timer) e.getSource()).stop();
                    splash.dispose();
                    onErrorCallback.accept(executionError);
                    return;
                }

                // Normal flow: wait for progress and min time
                if (splash.getProgress() >= 100) {
                    long elapsedTime = System.currentTimeMillis() - startTime;
                    if (elapsedTime >= MIN_SPLASH_TIME_MS) {
                        ((Timer) e.getSource()).stop();
                        splash.dispose();
                        onFinished();
                    }
                }
            }
        });
    }

    public void start() {
        splash.setVisible(true);
        animationTimer.start();
        execute();
    }

    @Override
    protected Void doInBackground() throws Exception {
        try {
            int totalTasks = tasks.size();
            if (totalTasks == 0) {
                return null;
            }

            for (int i = 0; i < totalTasks; i++) {
                SplashTask task = tasks.get(i);
                // Progress is based on completed tasks
                int progress = (int) ((i / (double) totalTasks) * 100);
                updateProgress(progress, task.message());
                task.action().run();
            }
        } finally {
            updateProgress(100, "Finalizando...");
        }
        return null;
    }

    @Override
    protected void done() {
        try {
            // Check if an exception occurred during doInBackground
            get();
        } catch (InterruptedException | ExecutionException e) {
            // Unwrap the exception to get the real cause
            this.executionError = e.getCause() != null ? e.getCause() : e;
        }
        this.backgroundWorkDone = true;
    }

    private void onFinished() {
        onFinishedCallback.run();
    }

    private void updateProgress(int progress, String message) {
        SwingUtilities.invokeLater(() -> splash.setStatusText(message));
        this.targetProgress = Math.min(progress, 100);
    }

    public static Builder create(SplashScreen splash) {
        return new Builder(splash);
    }

    public static class Builder {
        private final SplashScreen splash;
        private final List<SplashTask> tasks = new ArrayList<>();
        private Runnable onFinishedCallback = () -> {}; // Default empty action
        private Consumer<Throwable> onErrorCallback = Throwable::printStackTrace; // Default print stack trace

        public Builder(SplashScreen splash) {
            this.splash = splash;
        }

        public Builder addTask(String message, ThrowableRunnable action) {
            tasks.add(new SplashTask(message, action));
            return this;
        }

        public Builder onFinished(Runnable callback) {
            this.onFinishedCallback = callback;
            return this;
        }

        public Builder onError(Consumer<Throwable> callback) {
            this.onErrorCallback = callback;
            return this;
        }

        public SplashWorker build() {
            return new SplashWorker(splash, tasks, onFinishedCallback, onErrorCallback);
        }
    }
}
