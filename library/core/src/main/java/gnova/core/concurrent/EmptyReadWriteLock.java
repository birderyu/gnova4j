package gnova.core.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * 空读写锁
 *
 * 空读写锁是读写锁的一种实现，对空读写锁获取读锁和写锁，将获取一个空锁对象
 *
 * @see ReadWriteLock
 * @see EmptyLock
 * @author birderyu
 * @version 1.0.0
 */
public final class EmptyReadWriteLock
        implements ReadWriteLock {

    /**
     * 空读写锁的一个实例
     * 可以不需要构建空读写锁对象，直接使用INSTANCE
     */
    public static final ReadWriteLock INSTANCE = new EmptyReadWriteLock();

    /**
     * 获取读锁
     *
     * 这个读锁将会是一个空锁对象
     *
     * @return 锁对象
     * @see ReadWriteLock#readLock()
     */
    @Override
    public Lock readLock() {
        return EmptyLock.INSTANCE;
    }

    /**
     * 获取写锁
     *
     * 这个写锁将会是一个空锁对象
     *
     * @return 锁对象
     * @see ReadWriteLock#writeLock()
     */
    @Override
    public Lock writeLock() {
        return EmptyLock.INSTANCE;
    }

}
