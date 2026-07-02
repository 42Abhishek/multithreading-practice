package com.abhishek.multithreading.ThreadPoolPractice;

import java.util.concurrent.*;

public class CompletableFutureThenApplyAsyncPractice {
    public static void main(String[] args) {
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 1, 1, TimeUnit.HOURS, new ArrayBlockingQueue<>(2), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.HOURS, new ArrayBlockingQueue<>(2), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        CompletableFuture<String> res1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Thread is : " + Thread.currentThread().getName());
            return "Abhishek";
        });

        CompletableFuture<String> res2 = res1.thenApply((String s) -> {
            System.out.println(Thread.currentThread().getName());
            return s + " Kumar.";
        }).thenApply((String s) -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(s);
            return s + "ikdkss";
        });

        System.out.println(res2.join());

        //To Avoid this we have thenCompose , here there is nested CompletableFuture
        CompletableFuture<CompletableFuture<String>> res4 = res1.thenApply((String s) -> {
            return CompletableFuture.supplyAsync(() -> {
                return s + "kya kahu ankhon ne meri sab keh diya...";
            });
        });

        System.out.println(res4.join().join());

        CompletableFuture<String> res5 = res1.thenApplyAsync((String s) -> {
            System.out.println(Thread.currentThread().getName());
            return s + " Kumar...";
        }, executor).thenApplyAsync((String s) -> {
            System.out.println(Thread.currentThread().getName());
            return s + "ksls";
        }, executor).thenApplyAsync((String s) -> {
            System.out.println(Thread.currentThread().getName());
            return s + "sieol";
        }, executor);

        System.out.println(res5.join());

        executor.shutdown();

    }
}
