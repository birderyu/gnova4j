package gnova.core.concurrent;

import gnova.core.annotation.Unsupported;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 空锁
 *
 * 空锁是锁的一种实现，用于某些必须加锁的场景，加空锁等于没有加锁
 *
 * @see Lock
 * @author birderyu
 * @version 1.0.0
 */
public final class EmptyLock
        implements Lock {

    /**
     * 空锁的一个实例
     * 可以不需要构建空锁对象，直接使用INSTANCE
     */
    public static final Lock INSTANCE = new EmptyLock();

    /**
     * 加锁
     *
     * 空锁对象一定会加锁成功
     *
     * @see Lock#lock()
     */
    @Override
    public void lock() {
        // do nothing
    }

    /**
     * 加锁
     *
     * 空锁对象一定会加锁成功
     *
     * @throws InterruptedException 抛出异常
     * @see Lock#lockInterruptibly()
     */
    @Override
    public void lockInterruptibly() throws InterruptedException {
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException();
        }
        // do nothing
    }

    /**
     * 尝试加锁
     *
     * 空锁对象一定会加锁成功
     *
     * @return 若加锁成功，则返回true，否则返回false
     * @see Lock#tryLock()
     */
    @Override
    public boolean tryLock() {
        return true;
    }

    /**
     * 尝试加锁
     *
     * 空锁对象一定会加锁成功
     *
     * @param time 超时时间
     * @param unit 超时时间的单位
     * @return 若加锁成功，则返回true，否则返回false
     * @throws InterruptedException 抛出异常
     * @see Lock#tryLock(long, TimeUnit)
     */
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException();
        }
        return true;
    }

    /**
     * 解锁
     *
     * 空锁对象一定会解锁成功
     *
     * @see Lock#unlock()
     */
    @Override
    public void unlock() {
        // do nothing
    }

    /**
     * 空锁对象不支持此方法
     *
     * @return 该方法不会返回值
     * @throws UnsupportedOperationException 抛出异常
     * @see Lock#newCondition()
     */
    @Override
    @Unsupported
    public Condition newCondition() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("newCondition");
    }
}
