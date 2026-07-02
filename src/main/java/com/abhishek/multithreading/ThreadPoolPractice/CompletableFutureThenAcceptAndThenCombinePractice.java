package com.abhishek.multithreading.ThreadPoolPractice;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureThenAcceptAndThenCombinePractice {
    public static void main(String[] args) {

        CompletableFuture.supplyAsync(()->{
            System.out.println("Thread is : " + Thread.currentThread().getName());
            return "Har Har Mahadev.";
        }).thenAccept((String s) -> {
            System.out.println("Thread is : " + Thread.currentThread().getName());
            System.out.println(s);
        });

        CompletableFuture.supplyAsync(()->{
            System.out.println("Thread is : " + Thread.currentThread().getName());
            return "Har Har Mahadev.";
        }).thenAcceptAsync((String s) -> {
            System.out.println("Thread is : " + Thread.currentThread().getName());
            System.out.println(s);
        });

        CompletableFuture<Integer> asyncTask1 = CompletableFuture.supplyAsync(() -> {
            return 1000;
        });

        CompletableFuture<String> asyncTask2 = CompletableFuture.supplyAsync(() -> {
            return "crore";
        });

        CompletableFuture<String> res = asyncTask1.thenCombine(asyncTask2, (Integer a, String b) -> a + b);
//        asyncTask2.thenCombine(asyncTask1, (String b, Integer a) -> a + b);

        System.out.println(res.join());
    }
}
