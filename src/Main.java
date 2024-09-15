import java.util.concurrent.locks.Lock;

public class Main {

    public static void main(String[] args) {
        int numberOfPhilosophers = 15;

        // Создаем вилки
        Lock[] forks = DiningPhilosophers.createForks(numberOfPhilosophers);

        // Создаем философов и даем им вилки
        Philosopher[] philosophers = DiningPhilosophers.createPhilosophers(numberOfPhilosophers, forks);

        // Запускаем философов
        for (Philosopher philosopher : philosophers) {
            philosopher.start();
        }

        // Ждем, пока все философы завершат
        for (Philosopher philosopher : philosophers) {
            try {
                philosopher.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("************************************");
        //Проверяем сколько раз поел каждый философ
        for (Philosopher philosopher : philosophers) {
            System.out.println(philosopher.getEatCounter());
        }
        System.out.println("Все философы поели.");
    }
}
