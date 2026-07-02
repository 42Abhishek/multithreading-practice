package com.abhishek.multithreading.ThreadPoolPractice;

import java.util.concurrent.*;

//public class FuturePractice {
//    public static void main(String[] args) {
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.HOURS, new ArrayBlockingQueue<>(2), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
//
//        Future<?> futureObj = executor.submit(() -> {
//            System.out.println("Task executed by thread.");
//        });
//
////        boolean b = futureObj.cancel(true);
//
//        try{
//            Thread.sleep(1000);
//        }catch(InterruptedException e){
//
//        }
//        System.out.println(futureObj.isDone());
//        System.out.println(futureObj.isCancelled());
//        executor.shutdown();
//    }
//}

public class FuturePractice{
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.HOURS, new ArrayBlockingQueue<>(2), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        Future<?> futureObj = executor.submit(() -> {
            try{
                Thread.sleep(5000);
                System.out.println("Task executed by thread.");
            }catch(InterruptedException e){
                System.out.println(e.getMessage());
            }
        });

        System.out.println("Is Done : " + futureObj.isDone());

        try{
            futureObj.get(2, TimeUnit.SECONDS);
        }catch(TimeoutException e){
            System.out.println("Time out execption happenend");
        }catch(Exception e){
            System.out.println(e);
        }

        try{
            futureObj.get();
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
        }catch(ExecutionException e){
            System.out.println(e.getMessage());
        }

        System.out.println("is Done : " + futureObj.isDone());
        System.out.println("is cancelled : " + futureObj.isCancelled());

        executor.shutdown();
    }
}
