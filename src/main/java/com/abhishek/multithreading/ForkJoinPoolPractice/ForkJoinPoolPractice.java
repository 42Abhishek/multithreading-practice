package com.abhishek.multithreading.ForkJoinPoolPractice;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ForkJoinPoolPractice {
    public static void main(String[] args) {
        ExecutorService poolExecutor1 = Executors.newFixedThreadPool(3);
        Future<String> s = poolExecutor1.submit(() -> {
            System.out.println("Thread is : " + Thread.currentThread().getName());
            return "this is the async task.";
        });

        try{
            System.out.println(s.get());
        }catch(Exception e){
            System.out.println(e);
        }

//        Executors.newFixedThreadPool(5, Executors.defaultThreadFactory());
        poolExecutor1.shutdown();

//        ExecutorService poolExecutor2 = Executors.newCachedThreadPool();
//        Executors.newSingleThreadExecutor();
    }
}
