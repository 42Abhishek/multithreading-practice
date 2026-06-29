package com.abhishek.multithreading.Problems;


// Two threads , a producer and a consumer , share a common, fixed-size buffer as a queue.
// The producer's job is to generate data and put it into the buffer, while consumer's job is to consume
// the data from the buffer.
// The problem is to make sure that the producer won't produce data if the buffer is full, and the consumer
// won't consume data if the buffer is empty.

import java.util.*;
class Buffer{
    private final int capacity;
    private final Queue<Integer> buffer = new ArrayDeque<>();
    public Buffer(int capacity){
        this.capacity = capacity;
    }

    public synchronized void produceData(int data) throws InterruptedException{
        while(buffer.size() >= this.capacity){
            wait();
        }
        buffer.add(data);
        notifyAll();
    }

    public synchronized int consumeData() throws InterruptedException{
        while(buffer.isEmpty()){
            wait();
        }
        int data = buffer.poll();
        notifyAll();
        return data;
    }
}

class Producer implements Runnable{
    private Buffer buffer;
    public Producer(Buffer buffer){
        this.buffer = buffer;
    }

    public void run(){
        for(int i = 0; i < 30; i++) {
            try {
                buffer.produceData(i);
                System.out.println("Produced : " + i);

                //  "Why can Consumed 0 appear before Produced 0?"
                //"Because the println() statements are outside the synchronized methods.
                // After the producer inserts the item and returns from produceData(),
                // the scheduler may switch to the consumer before the producer executes
                // its println(). Synchronization guarantees the correctness of the shared buffer,
                // not the order in which unrelated print statements execute."
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

}

class Consumer implements Runnable{
    private Buffer buffer;
    public Consumer(Buffer buffer){
        this.buffer = buffer;
    }

    public void run(){
        for(int i = 0; i < 30; i++) {
            try {
                int data = buffer.consumeData();
                System.out.println("Consumed : " + data);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

}
public class ProducerConsumerProblem {
    public static void main(String[] args) {
        Buffer buffer = new Buffer(5);

        Producer p = new Producer(buffer);
        Consumer c = new Consumer(buffer);

        Thread t1 = new Thread(p);
        Thread t2 = new Thread(c);

        t1.start();
        t2.start();

        try{
            t1.join();
            t2.join();
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
        }


        System.out.println("All thread have completed.");

    }
}
