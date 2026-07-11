package com.abhishek.multithreading.LockFreeConcurrency;

import java.util.concurrent.atomic.AtomicInteger;

class SharedResource{
    AtomicInteger counter = new AtomicInteger(0);
    public void increment(){
        counter.incrementAndGet();
    }

    public int get(){
        return counter.get();
    }
}
public class LockFreeCounter {
    public static void main(String[] args) {

        SharedResource resource = new SharedResource();
        Thread t1 = new Thread(() -> {
            for(int i = 1; i <= 500; i++){
                resource.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for(int i = 1; i <= 500; i++){
                resource.increment();
            }
        });

        t1.start();
        t2.start();

        try{
            t1.join();
            t2.join();
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }

        System.out.println(resource.get());
    }
}
