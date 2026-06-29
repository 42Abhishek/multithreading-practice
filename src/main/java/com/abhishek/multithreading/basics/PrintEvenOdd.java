package com.abhishek.multithreading.basics;

import static java.lang.Thread.sleep;

//class Print{
//    public synchronized void print(int val){
//        System.out.println(val);
//    }
//}
//
//public class PrintEvenOdd {
//    public static void main(String[] args) {
//
//        Print p = new Print();
//
//        Thread t1 = new Thread(() -> {
//            for(int i = 0; i < 10; i+=2){
//                try {
//                    System.out.print("Even printing : ");
//                    p.print(i);
//                    sleep(1000);
//                }catch(InterruptedException e){
//                    System.out.println(e.getMessage());
//                }
//            }
//        });
//
//        Thread t2 = new Thread(() -> {
//            for(int i = 1; i < 10; i+=2){
//                try {
//                    System.out.print("Odd printing : ");
//                    p.print(i);
//                    sleep(1000);
//                }catch(InterruptedException e){
//                    System.out.println(e.getMessage());
//                }
//            }
//        });
//
//        t1.start();
//        t2.start();
//    }
//}
//
//class Print{
//    public synchronized void print(int val){
//        System.out.println(val);
//        try{
//            sleep(1000);
//        }catch(InterruptedException e){
//            System.out.println(e.getMessage());
//        }
//
//    }
//}
//
//public class PrintEvenOdd {
//    public static void main(String[] args) {
//
//        Print p = new Print();
//
//        Thread t1 = new Thread(() -> {
//            for(int i = 0; i < 10; i+=2){
////                try {
//                  //  System.out.print("Even printing : ");
//                    p.print(i);
////                    sleep(1000);
////                }catch(InterruptedException e){
////                    System.out.println(e.getMessage());
////                }
//            }
//        });
//
//        Thread t2 = new Thread(() -> {
//            for(int i = 1; i < 10; i+=2){
////                try {
//                //    System.out.print("Odd printing : ");
//                    p.print(i);
////                    sleep(1000);
////                }catch(InterruptedException e){
////                    System.out.println(e.getMessage());
////                }
//            }
//        });
//
//        t1.start();
//        t2.start();
//    }
//}


class Print {
    boolean oddTurn = true;
    public synchronized void printOdd(int val) throws InterruptedException{
        while(!oddTurn) {
            wait();
        }
        sleep(500);
        System.out.println("Odd No." + val);
        oddTurn = !oddTurn;
        notify();
    }

    public synchronized void printEven(int val) throws InterruptedException{
        while(oddTurn){
            wait();
        }
        sleep(500);
        System.out.println("Even No." + val);
        oddTurn = !oddTurn;
        notify();

    }
}

public class PrintEvenOdd {
    public static void main(String[] args) {

        Print p = new Print();

        Thread t1 = new Thread(() -> {
            for(int i = 2; i <= 10; i+=2){
                try {
                p.printEven(i);
                }catch(InterruptedException e){
                    System.out.println(e.getMessage());
                }
            }
        });

        Thread t2 = new Thread(() -> {
            for(int i = 1; i < 10; i+=2){
                try {
                p.printOdd(i);
                }catch(InterruptedException e){
                    System.out.println(e.getMessage());
                }
            }
        });

        t1.start();
        t2.start();
    }
}



