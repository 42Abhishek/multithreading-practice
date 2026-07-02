package com.abhishek.multithreading.ThreadPoolPractice;

import java.util.concurrent.*;



class CustomThreadFactory implements ThreadFactory{

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(false);
        t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }
}

class CustomThreadRejectedExecutionHandler implements RejectedExecutionHandler{

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println("Task rejected : " + r.toString());
    }
}

public class TheadPoolExecutorPractice {
    public static void main(String[] args) {
    ThreadPoolExecutor executor = new ThreadPoolExecutor(2,
            4,
            10,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(2),
            new CustomThreadFactory(),
            new CustomThreadRejectedExecutionHandler());

    for(int i = 0; i < 7; i++){
        executor.submit(() -> {
            try {
                Thread.sleep(1000);
            }catch(InterruptedException e){
//                e.getCause();
            }
            System.out.println("Executed by : " + Thread.currentThread().getName());
        });
    }
    executor.shutdown();

    }
}
