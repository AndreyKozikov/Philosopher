import java.util.concurrent.locks.Lock;

public class Philosopher extends Thread {
    private int id;
    private Lock leftFork;
    private Lock rightFork;
    private int eatCounter; //Считаем сколько раз поел философ

    Philosopher(int id, Lock leftFork, Lock rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    private void eat() throws InterruptedException {
        System.out.println("Философ " + id + " начинает есть.");
        Thread.sleep((long) (Math.random() * 1000));  // Симуляция процесса еды
        System.out.println("Философ " + id + " закончил есть.");
    }

    private void think() throws InterruptedException {
        System.out.println("Философ " + id + " размышляет.");
        Thread.sleep((long) (Math.random() * 1000));  // Симуляция процесса размышлений
    }

    @Override
    public void run() {
        try {
            int i = 0;
            while (i < 3) {
                think();  // Философ размышляет

                // Попробуем захватить вилки
                boolean gotLeftFork = leftFork.tryLock();
                boolean gotRightFork = rightFork.tryLock();

                // Если обе вилки захвачены, философ ест
                if (gotLeftFork && gotRightFork) {
                    try {
                        eat();// Философ ест
                        i++;
                        this.eatCounter = i;
                    } finally {
                        // Освобождаем вилки
                        rightFork.unlock();
                        leftFork.unlock();
                    }
                } else {
                    // Если не удалось захватить обе вилки, освобождаем захваченную
                    if (gotLeftFork) {
                        leftFork.unlock();
                    }
                    if (gotRightFork) {
                        rightFork.unlock();
                    }
                    // Ожидаем некоторое время перед новой попыткой
                    Thread.sleep((long) (Math.random() * 100));
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public String getEatCounter() {
        return "Философ " + this.id + " поел " + this.eatCounter + " раз(а).";
    }
}
