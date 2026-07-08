package com.abhishek.multithreading.Lock;

import java.util.concurrent.locks.ReentrantLock;

//class Resource{
//
////    ReentrantLock lock = new ReentrantLock();
//
//    public void m1(ReentrantLock lock){
//        try{
//            lock.lock();
//            System.out.println("Lock acquired");
//            System.out.println("jai SiyaRam");
//            Thread.sleep(3000);
//            System.out.println("lock released");
//        }catch(Exception e){
//
//        }finally {
//            lock.unlock();
//            System.out.println("Lock released by : " + Thread.currentThread().getName());
//        }
//    }
//}



//public class ReentrantExample {
//    public static void main(String[] args) {
//        ReentrantLock lock = new ReentrantLock();
//        Resource r1 = new Resource();
//        Thread t1 = new Thread(() -> {
//            r1.m1(lock);
//        });
//        Resource r2 = new Resource();
//        Thread t2 = new Thread(() -> {
//            r2.m1(lock);
//        });
//        t1.start();
//        t2.start();
//    }
//
//}

//
//
//class Resource{
//
//    public void m1(ReentrantLock lock){
//        try{
//            lock.lock();
//            System.out.println("Lock acquired in m1");
//            System.out.println("jai SiyaRam");
//            Thread.sleep(3000);
//            m2(lock);
//            System.out.println("lock released from m1");
//        }catch(Exception e){
//
//        }finally {
//            lock.unlock();
//            System.out.println("Lock released by : " + Thread.currentThread().getName());
//        }
//    }
//
//    public void m2(ReentrantLock lock){
//        try{
//            lock.lock();
//            System.out.println("Lock acquired in m2");
//            Thread.sleep(2000);
//            System.out.println("lock released from m2");
//        }catch(Exception e){
//
//        }finally {
//            lock.unlock();
//        }
//    }
//}

class Resource{

    public synchronized void m1(){
        try{
            System.out.println("Lock acquired in m1");
            System.out.println("jai SiyaRam");
            Thread.sleep(3000);
            m2();
            System.out.println("lock released from m1");
        }catch(Exception e){

        }
    }

    public synchronized void m2(){
        try{
            System.out.println("Lock acquired in m2");
            Thread.sleep(2000);
            System.out.println("lock released from m2");
        }catch(Exception e){

        }
    }
}


public class ReentrantExample {
    public static void main(String[] args) {
//        ReentrantLock lock = new ReentrantLock();
        Resource r1 = new Resource();
        Thread t1 = new Thread(() -> {
            r1.m1();
        });
//        Resource r2 = new Resource();
//        Thread t2 = new Thread(() -> {
//            r2.m1(lock);
//        });
        t1.start();
//        t2.start();
    }

}
