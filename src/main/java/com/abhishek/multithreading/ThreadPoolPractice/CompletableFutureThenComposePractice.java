package com.abhishek.multithreading.ThreadPoolPractice;

import java.util.concurrent.*;

public class CompletableFutureThenComposePractice {
    public static void main(String[] args) {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 3, 1, TimeUnit.HOURS, new ArrayBlockingQueue<>(2), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        CompletableFuture<String> res1 = CompletableFuture.supplyAsync(()->{
            System.out.println("Thread is : " + Thread.currentThread().getName());
            return "Sita Ram Sita Ram";
        });

        CompletableFuture<String> res2 = res1.thenComposeAsync((String s) -> {
            System.out.println("Thread is : " + Thread.currentThread().getName());
            return CompletableFuture.supplyAsync(() -> {
                return s + "Sita Ram Kahiye .... Jahi vidhi Rakhe Ram , wahi Vidhi Rahiye...";
            });
        });

        System.out.println(res2.join());

        CompletableFuture<String> res3 = res1.thenCompose((String s) -> {
            System.out.println("Thread is : " + Thread.currentThread().getName());
            return CompletableFuture.supplyAsync(() -> {
                return s + "Sita Ram Kahiye ...";
            });
        });

        System.out.println(res3.join());

    }
}
