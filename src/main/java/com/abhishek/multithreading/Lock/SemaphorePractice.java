package com.abhishek.multithreading.Lock;

import java.util.concurrent.Semaphore;

public class SemaphorePractice {
    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(2);
//        new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        };
        Runnable task = () -> {

            try{
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + " entered");
                Thread.sleep(3000);

            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }finally{
                semaphore.release();
                System.out.println(Thread.currentThread().getName() + " exited");

            }
        };

        for(int i = 0; i < 5; i++){
            new Thread(task).start();
        }
    }
}
