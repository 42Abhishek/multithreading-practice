package com.abhishek.multithreading.Lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class SharedResource{


    ReadWriteLock lock;

    SharedResource(ReadWriteLock lock){
        this.lock = lock;
    }

    public void produce(){
        lock.writeLock().lock();
        try{
            System.out.println("Write lock acquired by : " + Thread.currentThread().getName());
            Thread.sleep(4000);
        }catch(Exception e){

        }finally {
            lock.writeLock().unlock();
            System.out.println("Write lock released by : " + Thread.currentThread().getName());
        }
    }

    public void consume(){
        lock.readLock().lock();
        try{
            System.out.println("Read lock acquired by : " + Thread.currentThread().getName());
            Thread.sleep(4000);
        }catch(Exception e){

        }finally {
            lock.readLock().unlock();
            System.out.println("Read lock released by : " + Thread.currentThread().getName());
        }
    }
}
public class ReadWriteLockPractice {
    public static void main(String[] args) {

        ReadWriteLock lock = new ReentrantReadWriteLock();
        SharedResource r = new SharedResource(lock);

        Thread t1 = new Thread(() -> {
            r.consume();
        });

        Thread t2 = new Thread(() -> {
            r.consume();
        });

        Thread t3 = new Thread(() -> {
            r.consume();
        });

        Thread t4 = new Thread(() -> {
            r.produce();
        });

        t1.start();
        t4.start();
        t2.start();
        t3.start();


    }
}
