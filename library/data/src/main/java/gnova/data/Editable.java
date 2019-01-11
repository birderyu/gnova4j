package gnova.data;

import gnova.core.annotation.NotNull;

/**
 * 可编辑的
 *
 * @param <E> 元素的类型
 */
public interface Editable<E> {

    /**
     * 编辑回调接口
     *
     * 当调用编辑方法（insert、replace、delete）等时，会根据实际的数据编辑情况，回调相应的方法
     *
     * @param <E> 元素的类型
     */
    interface Callback<E> {

        /**
         * 当新增一个元素时，会调用此方法
         *
         * 仅当实际发生了新增行为时，才会回调此方法，因此参数不可能为null
         *
         * @param e 新增的元素
         */
        void onInsert(@NotNull E e);

        /**
         * 当替换一个元素时，会调用此方法
         *
         * 仅当实际发生了替换行为时，才会回调此方法，因此参数不可能为null
         *
         * @param before 替换之前的元素
         * @param after 替换之后的元素
         */
        void onReplace(@NotNull E before, @NotNull E after);

        /**
         * 当删除一个元素时，会调用此方法
         *
         * 仅当实际发生了删除行为时，才会回调此方法，因此参数不可能为null
         *
         * @param e 被删除的元素
         */
        void onDelete(@NotNull E e);

    }

    /**
     * 新增一个元素
     *
     * 若元素已经存在，则新增操作会执行失败
     * 如果希望当元素已经存在时更新此元素，可以调用merge方法
     *
     * @param e 需要新增的元素，不允许为null
     * @return 若新增成功，则返回true，否则返回false
     */
    boolean insert(@NotNull E e);

    default int insert(@NotNull Iterable<? extends E> es,
                       Callback<E> callback) {
        int size = 0;
        for (E e : es) {
            if (e == null) {
                continue;
            }
            if (insert(e)) {
                size++;
                if (callback != null) {
                    callback.onInsert(e);
                }
            }
        }
        return size;
    }

    /**
     * 替换一个元素
     * 若元素不存在，则替换操作会执行失败
     *
     * @param e 需要替换的元素，不允许为null
     * @return 返回替换前的元素，若替换失败，则返回null
     */
    E replace(@NotNull E e);

    default int replace(@NotNull Iterable<? extends E> es,
                        Callback<E> callback) {
        int size = 0;
        for (E after : es) {
            if (after == null) {
                continue;
            }
            E before = replace(after);
            if (before != null) {
                size++;
                if (callback != null) {
                    callback.onReplace(before, after);
                }
            }
        }
        return size;
    }

    /**
     * 合并一个元素
     * 若元素不存在，则会执行新增操作
     * 若元素存在，则会执行替换操作
     *
     * @param e 需要合并的元素，不允许为null
     * @return 若元素不存在，则返回null，否则返回替换之前的元素
     */
    E merge(@NotNull E e);

    default void merge(@NotNull Iterable<? extends E> es,
                       Callback<E> callback) {
        for (E e : es) {
            if (e == null) {
                continue;
            }
            E before = merge(e);
            if (before == null) {
                if (callback != null) {
                    callback.onInsert(e);
                }
            } else {
                if (callback != null) {
                    callback.onReplace(before, e);
                }
            }
        }
    }

    /**
     * 删除一个元素
     *
     * @param e 需要删除的元素，不允许为null
     * @return 若删除成功，则返回true，否则返回false
     */
    boolean delete(@NotNull E e);

    default int delete(@NotNull Iterable<? extends E> es,
                       Callback<E> callback) {
        int size = 0;
        for (E e : es) {
            if (e == null) {
                continue;
            }
            if (delete(e)) {
                size++;
                if (callback != null) {
                    callback.onDelete(e);
                }
            }
        }
        return size;
    }

    default void modify(Iterable<? extends E> inserts,
                        Iterable<? extends E> replaces,
                        Iterable<? extends E> deletes,
                        Callback<E> callback) {
        if (inserts != null) insert(inserts, callback);
        if (replaces != null) replace(replaces, callback);
        if (deletes != null) delete(deletes, callback);
    }

    default void modify(Iterable<? extends E> merges,
                        Iterable<? extends E> deletes,
                        Callback<E> callback) {
        if (merges != null) merge(merges, callback);
        if (deletes != null) delete(deletes, callback);
    }

}
