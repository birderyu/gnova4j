package gnova.core;

import gnova.core.annotation.NotNull;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * 可执行的
 *
 * @param <A> Action，可执行的动作类型
 * @author birderyu
 * @version 1.0.0
 */
@FunctionalInterface
public interface Executable<A> {

    /**
     * 执行一个动作
     *
     * @param action 动作
     * @param <R> 执行该动作之后的返回值类型
     * @return 执行该动作之后的返回值
     * @throws Exception 执行该动作抛出的异常
     */
    <R> R executes(@NotNull A action) throws Exception;

    /**
     * 后台执行一个动作
     *
     * @param action 动作
     * @param <R> 执行该动作之后的返回值类型
     * @return Future对象，包含了执行该动作之后的返回值
     */
    @NotNull
    default <R> Future<R> post(@NotNull A action) {

        // 构建一个task
        RunnableFuture<R> task = new FutureTask<>(() -> executes(action));

        // 开启一个线程，并执行
        new Thread(task).start();

        // 返回线程的执行结果
        return task;
    }

    /**
     * 延迟执行一个动作
     *
     * @param action 动作
     * @param timeout 延迟的时间
     * @param unit 延迟时间的单位
     * @param <R> 执行该动作之后的返回值类型
     * @return Future对象，包含了执行该动作之后的返回值
     */
    @NotNull
    default <R> Future<R> delay(@NotNull A action,
                                long timeout,
                                @NotNull TimeUnit unit) {

        // 构建一个task
        RunnableFuture<R> task = new FutureTask<>(() -> executes(action));

        // 使用计时器完成延时任务
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        }, unit.toMillis(timeout));

        // 返回线程的执行结果
        return task;
    }

    /**
     * 回调接口
     *
     * @param <T> 值的类型
     */
    interface Callback<T> {

        /**
         * 执行动作成功时，触发的回调方法
         *
         * @param v 执行动作成功时的返回值对象，可以为null，视情况而定
         */
        void onSucceed(T v);

        /**
         * 执行动作异常时，触发的回调方法
         *
         * @param e 异常对象
         */
        void onError(@NotNull Throwable e);

    }

    /**
     * 延迟执行一个动作，并在执行完成后立即触发回调
     *
     * @param action 动作
     * @param timeout 延迟的时间
     * @param unit 延迟时间的单位
     * @param callback 回调对象
     * @param <R> 执行该动作之后的返回值类型
     */
    default <R> void delay(@NotNull A action,
                           long timeout,
                           TimeUnit unit,
                           @NotNull Callback<R> callback) {

        // 使用计时器完成延时任务
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    // 执行行为，并触发回调
                    R result = executes(action);
                    if (callback != null) {
                        callback.onSucceed(result);
                    }
                } catch (Throwable e) {
                    if (callback != null) {
                        callback.onError(e);
                    }
                }
            }
        }, unit.toMillis(timeout));
    }

}
