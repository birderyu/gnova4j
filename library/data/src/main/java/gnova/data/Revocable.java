package gnova.data;

/**
 * 可撤销的
 */
public interface Revocable {

    /**
     * 是否可以撤销
     *
     * @return 若可以撤销，则返回true，否则返回false
     */
    boolean canUndo();

    /**
     * 撤销
     *
     * 返回是否可以继续撤销
     *
     * @return 若还可以继续撤销，则返回true，否则返回false
     */
    boolean undo();

    /**
     * 是否可以回退
     *
     * @return 若可以回退，则返回true，否则返回false
     */
    boolean canRedo();

    /**
     * 回退
     *
     * 返回是否可以继续回退
     *
     * @return 若还可以继续回退，则返回true，否则返回false
     */
    boolean redo();

}
