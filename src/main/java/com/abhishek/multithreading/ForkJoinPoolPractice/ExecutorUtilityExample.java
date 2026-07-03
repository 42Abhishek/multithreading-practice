package com.abhishek.multithreading.ForkJoinPoolPractice;

import java.util.concurrent.*;

class ComputeSumTask extends RecursiveTask<Integer> {

    private int start;
    private int end;

    public ComputeSumTask(int start, int end){
        this.start = start;
        this.end = end;
    }

    @Override
    public Integer compute(){
        if(end - start <= 4){
            int sum = 0;
            for(int i = start; i <= end; i++){
                sum += i;
            }
            return sum;
        }

        int mid = (start + end)/2;

        ComputeSumTask left = new ComputeSumTask(start, mid);
        ComputeSumTask right = new ComputeSumTask(mid + 1, end);

        left.fork();

        int rightResult = right.compute();

        int leftResult = left.join();

        return leftResult + rightResult;



    }
}

public class ExecutorUtilityExample {
    public static void main(String[] args) {

        ForkJoinPool pool = ForkJoinPool.commonPool();
//        Executors.newWorkStealingPool();
        Future<Integer> futureObj = pool.submit(new ComputeSumTask(0, 100));
        try{
            System.out.println(futureObj.get());
        }catch(Exception e){

        }

        pool.shutdown();

    }
}
