package com.abhishek.multithreading.basics;

class PrintABCSequentially{
    int turn = 0;

    public synchronized void print(char ch, int turn) throws InterruptedException{
        while(this.turn != turn){
            wait();
        }
        System.out.println(ch);
        this.turn = (this.turn + 1) % 3;
        notifyAll();
    }

//    public synchronized void printA(char ch) throws InterruptedException{
//        while(turn != 0){
//            wait();
//        }
//        System.out.println(ch);
//        turn = (turn + 1) % 3;
//        notifyAll();
//    }
//
//    public synchronized void printB(char ch) throws InterruptedException{
//        while(turn != 1){
//            wait();
//        }
//        System.out.println(ch);
//        turn = (turn + 1) % 3;
//        notifyAll();
//    }
//
//    public synchronized void printC(char ch) throws InterruptedException{
//        while(turn != 2){
//            wait();
//        }
//        System.out.println(ch);
//        turn = (turn + 1) % 3;
//        notifyAll();
//    }

}
public class PrintABC {
    public static void main(String[] args) {

        PrintABCSequentially p = new PrintABCSequentially();

        Thread t1 = new Thread(() -> {
            for(int i = 0; i < 5; i++) {
                try {
                    p.print('A', 0);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }

        });

        Thread t2 = new Thread(() -> {
            for(int i = 0; i < 5; i++) {
                try {
                    p.print('B', 1);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }

        });

        Thread t3 = new Thread(() -> {
            for(int i = 0; i < 5; i++) {
                try {
                    p.print('C', 2);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();

    }
}
