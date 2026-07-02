package com.abhishek.multithreading.ThreadPoolPractice;

import java.util.concurrent.*;
import java.util.function.Supplier;

class MySupplier implements Supplier<String> {

    @Override
    public String get(){
        System.out.println("Thread is : " + Thread.currentThread().getName());
        return "Hello";
    }
}

public class CompletableFutureSupplyAsync {
    public static void main(String[] args) {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.HOURS, new ArrayBlockingQueue<>(2), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        CompletableFuture<String> res1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Thread is : " + Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            }catch(Exception e){

            }
            System.out.println("Shri Ram Janki baithe hai mere seene me...");
            return "Har Har Mahadev";
        });

        CompletableFuture<String> res2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Thread is : " + Thread.currentThread().getName());
//            try {
//                Thread.sleep(2000);
//            }catch(Exception e){
//
//            }
            System.out.println("Shri Ram Janki baithe hai mere seene me... 2");
            return "Har Har Mahadev";
        }, executor);

        CompletableFuture<String> res3 = CompletableFuture.supplyAsync(new MySupplier());

        try {
            System.out.println(res1.get());
            System.out.println(res2.get());
            System.out.println(res3.get());
        } catch (Exception e) {
            System.out.println(e);
        }

        executor.shutdown();
    }
}
