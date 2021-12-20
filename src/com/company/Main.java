package com.company;

import java.util.Random;

public class Main {

    public static volatile int account = 1000;

    public static void main(String[] args) {
        Main.startThreads();
    }

    public static void startThreads() {
        new InputThread().start();
        new OutThread().start();
    }

    public static Object key = new Object();

    public static void putMoney(int amount) throws InterruptedException {
        synchronized (key){
            account += amount;
            System.out.println("Вы пополнили счет (" + amount + ")" + ", у вас на счету: " + account);
            Thread.sleep(3000);
        }
    }
    public static void getMoney(int amount) throws InterruptedException {
        synchronized (key){
            if (account - amount < 0) {
                System.out.println("Недостаточно средств для снятия суммы (" + amount + ")");
                Thread.sleep(3000);
            } else {
                account -= amount;
                System.out.println("Вы успешно сняли деньги (" + amount + ")" + ", на вашем счету: " + account);
                Thread.sleep(3000);
            }
        }
    }
}

class InputThread extends Thread {

    private int money;

    @Override
    public void run() {
        while (true) {
            Random random = new Random();
            money = random.nextInt(1000);
            try {
                Main.putMoney(money);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class OutThread extends Thread {

    private int money;

    @Override
    public void run() {
        while (true) {
            Random random = new Random();
            money = random.nextInt(1000);
            try {
                Main.getMoney(money);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
