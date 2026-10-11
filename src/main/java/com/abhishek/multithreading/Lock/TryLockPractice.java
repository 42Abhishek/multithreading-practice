package com.abhishek.multithreading.Lock;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;

//class Resource1{
//
////    ReentrantLock lock = new ReentrantLock();
//
//    public void m1(ReentrantLock lock) throws InterruptedException {
//        if(lock.tryLock(5, TimeUnit.SECONDS)) {
//            try {
//                lock.lock();
//                System.out.println("Lock acquired");
//                System.out.println("jai SiyaRam");
//                Thread.sleep(3000);
//                System.out.println("lock released");
//            } catch (Exception e) {
//
//            } finally {
//                lock.unlock();
//                lock.unlock();
//                System.out.println("Lock released by : " + Thread.currentThread().getName());
//            }
//        }
//    }
//}
//
//
//public class TryLockPractice {
//    public static void main(String[] args){
//        ReentrantLock lock = new ReentrantLock();
//        Resource1 r1 = new Resource1();
//        Thread t1 = new Thread(() -> {
//            try {
//                r1.m1(lock);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        });
//
//        Thread t2 = new Thread(() -> {
//            try {
//                r1.m1(lock);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        });
//        t1.start();
//        t2.start();
//    }
//}

class Resource1{

    public void m1(ReentrantLock lock) throws InterruptedException{
        if(lock.tryLock(3, TimeUnit.SECONDS)){
            try{
                System.out.println("Lock acquired by : " + Thread.currentThread().getName());
                System.out.println("Har Har Mahadev");
                Thread.sleep(4000);
            }finally {
                lock.unlock();
                System.out.println("Lock released by: " + Thread.currentThread().getName());
            }
        }else{
            System.out.println(Thread.currentThread().getName() + " could not acquire the lock within 5 seconds");
        }
    }
}

public class TryLockPractice{
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Resource1 r1 = new Resource1();

        Thread t1 = new Thread(() -> {
            try{
                r1.m1(lock);
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        });

        Thread t2 = new Thread(() -> {
            try{
                r1.m1(lock);
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        });

        t1.start();
        t2.start();
    }
}
