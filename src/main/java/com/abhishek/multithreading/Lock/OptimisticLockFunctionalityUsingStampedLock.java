package com.abhishek.multithreading.Lock;

import java.util.concurrent.locks.StampedLock;

class SharedResource1{
    int a = 10;
    StampedLock lock = new StampedLock();

    public void produce(){
        long stamp = lock.writeLock();
        System.out.println("Write lock acquired by : " + Thread.currentThread().getName());
        try{
            System.out.println("performing task");
            Thread.sleep(3000);
            a = 9;
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
        finally{
            lock.unlock(stamp);
            System.out.println("Write lock released by : " + Thread.currentThread().getName());
        }

    }

    public void consume(){
        long stamp = lock.tryOptimisticRead();
        try {
            System.out.println("Taken optimistic lock.");
            int value = a;
            Thread.sleep(0);
            if (!lock.validate(stamp)) {
                stamp = lock.readLock();
                try {
                    value = a;
                    System.out.println("Read lock acquired by : " + Thread.currentThread().getName());
                } finally {
                    lock.unlockRead(stamp);
                    System.out.println("Read lock released by : " + Thread.currentThread().getName());
                }
            } else {
                System.out.println("Didn't required read lock, " + Thread.currentThread().getName());
            }

            System.out.println(value);
        }
        catch(InterruptedException e){
               Thread.currentThread().interrupt();
        }

    }
}
public class OptimisticLockFunctionalityUsingStampedLock {
    public static void main(String[] args) {
        SharedResource1 r = new SharedResource1();

        Thread t1 = new Thread(() -> {
            r.consume();
        });

        Thread t2 = new Thread(r::consume);
        Thread t3 = new Thread(r::produce);

        t1.start();
        t2.start();
        t3.start();
    }
}
