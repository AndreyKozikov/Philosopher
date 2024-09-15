import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers {

    public static Lock[] createForks(int numberOfForks) {
        Lock[] forks = new ReentrantLock[numberOfForks];//Используем ReentrantLock для более явного управления механизмами блокировки
        for (int i = 0; i < numberOfForks; i++) {
            forks[i] = new ReentrantLock();
        }
        return forks;
    }

    public static Philosopher[] createPhilosophers(int numberOfPhilosophers, Lock[] forks) {
        Philosopher[] philosophers = new Philosopher[numberOfPhilosophers];//Создаем массив с философами
        //Создаем вилки и философов и раздаем вилки философам
        for (int i = 0; i < numberOfPhilosophers; i++) {
            Lock leftFork = forks[i];
            Lock rightFork = forks[(i + 1) % numberOfPhilosophers];

            philosophers[i] = new Philosopher(i, leftFork, rightFork);
        }
        return philosophers;
    }
}
