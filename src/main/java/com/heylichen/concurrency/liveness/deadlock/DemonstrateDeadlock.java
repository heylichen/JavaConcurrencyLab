package com.heylichen.concurrency.liveness.deadlock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class DemonstrateDeadlock {
  private static final int NUM_THREADS = 20;
  private static final int NUM_ACCOUNTS = 5;
  private static final int NUM_ITERATIONS = 1000000;
  private static final Random rnd = new Random();
  private static Logger logger = LoggerFactory.getLogger(DemonstrateDeadlock.class);

  public static void main(String[] args) {

    final Account[] accounts = new Account[NUM_ACCOUNTS];
    for (int i = 0; i < accounts.length; i++) {
      accounts[i] = new Account();
      accounts[i].setBalance(10000000);
      accounts[i].setLock(new ReentrantLock());
    }

    class TransferThread extends Thread {
      public void run() {
        for (int i = 0; i < NUM_ITERATIONS; i++) {
          int fromAcct = rnd.nextInt(NUM_ACCOUNTS);
          int toAcct = rnd.nextInt(NUM_ACCOUNTS);
          Integer amount =
              rnd.nextInt(10);
          boolean success = withTryLock.transferMoney(accounts[fromAcct],
              accounts[toAcct], amount);
          if (!success) {
            logger.info(" transfer false");
          }
        }
      }
    }
    for (int i = 0; i < NUM_THREADS; i++)
      new TransferThread().start();
  }

  /**
   * 死锁版本 deadlock version
   */
  private static MoneyTransfer deadlock = new MoneyTransfer() {
    @Override
    public boolean transferMoney(Account fromAccount, Account toAccount, Integer amount) throws Account.InsufficientFundsException {
      synchronized (fromAccount) {
        synchronized (toAccount) {
          transfer(fromAccount, toAccount, amount);
        }
      }
      return true;
    }
  };
  /**
   * 线程安全版本, avoiding deadlock, keep lock acquiring order by hash code.
   */
  private static MoneyTransfer orderedByHash = new MoneyTransfer() {
    private final Object tieLock = new Object();

    @Override
    public boolean transferMoney(Account fromAcct, Account toAcct, Integer amount) throws Account.InsufficientFundsException {
      int fromHash = System.identityHashCode(fromAcct);
      int toHash = System.identityHashCode(toAcct);
      if (fromHash < toHash) {
        synchronized (fromAcct) {
          synchronized (toAcct) {
            transfer(fromAcct, toAcct, amount);
          }
        }
      } else if (fromHash > toHash) {
        synchronized (toAcct) {
          synchronized (fromAcct) {
            transfer(fromAcct, toAcct, amount);
          }
        }
      } else {
        synchronized (tieLock) {
          synchronized (fromAcct) {
            synchronized (toAcct) {
              transfer(fromAcct, toAcct, amount);
            }
          }
        }
      }
      return true;
    }
  };

  private static MoneyTransfer withTryLock = new MoneyTransfer() {
    /* an
    alternate way of addressing the dynamic ordering deadlock from Section 10.1.2:
    use tryLock to attempt to acquire both locks, but back off and retry if they cannot
    both be acquired. The sleep time has a fixed component and a random component
    to reduce the likelihood of livelock. If the locks cannot be acquired within the
    specified time, transferMoney returns a failure status so that the operation can
    fail gracefully.*/
    private long timeout = 100;
    private TimeUnit unit = TimeUnit.MILLISECONDS;
    private Random rand = new Random();

    @Override
    public boolean transferMoney(Account fromAcct, Account toAcct, Integer amount) throws Account.InsufficientFundsException {
      long fixedDelay = getFixedDelayComponentNanos(timeout, unit);
      long randMod = getRandomDelayModulusNanos(timeout, unit);
      long stopTime = System.nanoTime() + unit.toNanos(timeout);
      while (true) {
        if (fromAcct.lock.tryLock()) {
          try {
            if (toAcct.lock.tryLock()) {
              try {
                transfer(fromAcct, toAcct, amount);
                return true;
              } finally {
                toAcct.lock.unlock();
              }
            }
          } finally {
            fromAcct.lock.unlock();
          }
        }
        if (System.nanoTime() > stopTime) {
          logger.info("false");
          return false;
        }
        try {
          long duration = fixedDelay + rnd.nextLong() % (randMod + 1);
          NANOSECONDS.sleep(duration);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

      }
    }

    private long getFixedDelayComponentNanos(long timeout, TimeUnit unit) {
      return unit.toNanos(timeout / 20);
    }

    private long getRandomDelayModulusNanos(long timeout, TimeUnit unit) {
      int rd = rand.nextInt((int) timeout / 20);
      return unit.toNanos(rd);
    }
  };


  /**
   * this interface defines a method for testing deadlock
   */
  public interface MoneyTransfer {
    boolean transferMoney(Account fromAccount,
                          Account toAccount,
                          Integer amount)
        throws Account.InsufficientFundsException;
  }

  /**
   * tool method
   *
   * @param fromAcct
   * @param toAcct
   * @param amount
   * @throws Account.InsufficientFundsException
   */
  public static void transfer(Account fromAcct, Account toAcct, Integer amount) throws Account.InsufficientFundsException {
    if (fromAcct.getBalance().compareTo(amount) < 0)
      throw new Account.InsufficientFundsException();
    else {
      fromAcct.debit(amount);
      toAcct.credit(amount);
    }
  }

  public static class Account {
    private Integer balance = 0;
    private Lock lock;

    public void debit(Integer amount) {
      balance = balance - amount;
    }

    public void credit(Integer amount) {
      balance = balance + amount;
    }

    public static class InsufficientFundsException extends RuntimeException {
    }

    public Integer getBalance() {
      return balance;
    }

    public void setBalance(Integer balance) {
      this.balance = balance;
    }

    public Lock getLock() {
      return lock;
    }

    public void setLock(Lock lock) {
      this.lock = lock;
    }
  }
}
