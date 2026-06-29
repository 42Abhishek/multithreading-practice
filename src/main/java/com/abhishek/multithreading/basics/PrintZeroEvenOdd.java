package com.abhishek.multithreading.basics;

class PrintZero_Even_Odd{
    private boolean zeroTurn = true;
    private boolean oddTurn = true;

    public synchronized void printZero(int val) throws InterruptedException{
        while(!zeroTurn){
            wait();
        }
        System.out.print(val + " ");
        zeroTurn = false;
        notifyAll();
    }

    public synchronized void printOddEven(int val, boolean oddTurn) throws InterruptedException{
        while(zeroTurn || this.oddTurn != oddTurn){
            wait();
        }
        System.out.print(val + " ");
        zeroTurn = true;
        this.oddTurn = !oddTurn;
        notifyAll();
    }

}
public class PrintZeroEvenOdd {
    public static void main(String[] args) {
        PrintZero_Even_Odd p = new PrintZero_Even_Odd();

        Thread t1 = new Thread(() -> {
            for(int i = 0; i < 20; i++){
                try{
                    p.printZero(0);
                }catch(InterruptedException e){
                    System.out.println(e.getMessage());
                }
            }
        });

        Thread t2 = new Thread(() -> {
            for(int i = 1; i <= 19; i+=2){
                try{
                    p.printOddEven(i, true);
                }catch(InterruptedException e){
                    System.out.println(e.getMessage());
                }
            }
        });

        Thread t3 = new Thread(() -> {
            for(int i = 2; i <= 20; i+=2){
                try{
                    p.printOddEven(i, false);
                }catch(InterruptedException e){
                    System.out.println(e.getMessage());
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();

    }
}
