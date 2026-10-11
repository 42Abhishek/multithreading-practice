package com.abhishek.multithreading.basics;

import static java.lang.Thread.sleep;

class SharedResource{
    private boolean value = false;

    public synchronized void produce(){
        value = true;
        System.out.println("Item has been produced by " + Thread.currentThread().getName());
        notifyAll();

    }

    public synchronized void consume(){
        System.out.println("Thread name : " + Thread.currentThread().getName());
        while(!value){
            try{
                System.out.println("Thread name : " + Thread.currentThread().getName() + " is waiting now.");
                wait();
            }catch(InterruptedException e){
                System.out.println(e.getMessage());
            }
        }
        value = false;
        System.out.println("Item has been consumed.");
    }
}
public class main {
    public static void main(String[] args) {
        SharedResource r = new SharedResource();
        Thread p = new Thread(() -> {
            System.out.println("Producer Thread : " + Thread.currentThread().getName());
            try{
                sleep(5000);
            }catch(InterruptedException e){
                System.out.println(e.getMessage());
            }

            r.produce();
        });
        Thread c = new Thread(() -> {
            System.out.println("Consumer Thread : " + Thread.currentThread().getName());
            r.consume();
        });

        p.start();
        c.start();

        System.out.println("Main method end...");

    }
}
