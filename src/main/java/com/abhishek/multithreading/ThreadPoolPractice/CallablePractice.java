package com.abhishek.multithreading.ThreadPoolPractice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

class MyRunnable implements Runnable{

    List<Integer> list;

    public MyRunnable(List<Integer> list){
        this.list = list;
    }

    @Override
    public void run(){
        list.add(35);
        list.add(42);
        list.add(9328);
        System.out.println("Takes runnable and result parameter.");
    }
}

public class CallablePractice

{
    public static void main(String[] args) {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.HOURS, new ArrayBlockingQueue<>(2), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        //takes runnable
        Future<?> futureObj = executor.submit(() -> {
            System.out.println("This one takes runnable");
        });

        try{
            Object object = futureObj.get();
            System.out.println(object == null);
        }catch(Exception e){
            System.out.println(e);
        }


        List<Integer> list = new ArrayList<>();

        //takes Runnable and value(return)
        Future<List<Integer>> futureObj2 = executor.submit(new MyRunnable(list), list);

        try{
            List<Integer> output1 = futureObj2.get();
            System.out.println(output1);
        }catch(Exception e){
            System.out.println(e);
        }

        //takes Callable

        Future<List<Integer>> futureObj3 = executor.submit(() -> {
            List<Integer> list1 = new ArrayList<>();
            list1.add(111);
            list1.add(222);
            System.out.println("This one takes callable.");
            return list1;
        });

        try{
            List<Integer> res= futureObj3.get();
            System.out.println(res);
        }catch(Exception e){
            System.out.println(e);
        }

        executor.shutdown();

    }
}
