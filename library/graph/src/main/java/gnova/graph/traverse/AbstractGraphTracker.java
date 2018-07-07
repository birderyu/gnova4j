package gnova.graph.traverse;

import gnova.graph.structure.Graphable;
import gnova.graph.util.GraphPath;
import gnova.graph.util.SimpleGraphPath;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractGraphTracker<T extends Graphable>
        implements GraphTracker<T> {

    /**
     * 判断当前元素是否已经被访问过
     */
    private Map<T, Boolean> walked;

    public AbstractGraphTracker() {
        walked = buildMap();
    }

    protected void setWalked(T component, boolean visited) {
        if (visited) {
            this.walked.put(component, Boolean.TRUE);
        } else {
            this.walked.put(component, Boolean.FALSE);
        }
    }

    @Override
    public boolean isWalked(T component) {
        return walked.get(component) == Boolean.TRUE;
    }

    @Override
    public void beforeTrack() {
        walked.clear();
    }

    @Override
    public void track(T source,
                      GraphIterator<T> iterator,
                      GraphWalker<T> walker,
                      final Map<T, GraphPath<T>> traces) {

        Objects.requireNonNull(source);

        // 初始化迭代器
        iterator.initialize(source);

        // 初始化一些变量
        T current = null; // 当前访问的单元
        Collection<T> nexts = new ArrayList<T>();
        // 开始进行追踪
        while ((current = iterator.next(this)) != null) {
            nexts.clear();
            if (!doTrack(current, nexts, iterator, walker, traces)) {
                return;
            }
        }
    }

    /**
     * 收集轨迹进入轨迹的集合
     *
     * @param component
     * @param traces
     */
    protected void collectTrace(T component,
                                GraphIterator<T> iterator,
                                final Map<T, GraphPath<T>> traces) {
        if (null == component || null == traces) {
            return;
        }
        traces.put(component, new SimpleGraphPath(iterator.getPath(component)));
    }

    /**
     * 追踪
     *
     * @param current
     * @return 是否需要继续追踪
     */
    protected boolean doTrack(T current,
                              Collection<T> nexts,
                              GraphIterator<T> iterator,
                              GraphWalker<T> walker,
                              final Map<T, GraphPath<T>> traces) {

        // 将当前单元添加进轨迹的集合
        iterator.addToPath(current);

        // 获取当前单元在当前轨迹上的上一个单元
        T last = iterator.getLast(current);

        // 访问当前单元
        int status = walker.walk(last, current, nexts);

        // 将当前单元设置为已访问过
        setWalked(current, true);

        // 判断是否需要收集当前单元的轨迹
        if (traces != null && isCollectTrace(status, traces)) {
            // 将当前单元的轨迹收集上来
            collectTrace(current, iterator, traces);
        }

        // 向下追踪
        switch (status) {
            case GraphWalker.PROCESS:
            case GraphWalker.PROCESS_AND_COLLECT_TRACE:
                // 继续追踪
                if (nexts.isEmpty()) {
                    iterator.process(this, current);
                } else {
                    iterator.process(this, current, nexts);
                }
                return true;
            case GraphWalker.KILL_BRANCH:
            case GraphWalker.KILL_BRANCH_AND_COLLECT_TRACE:
                // 杀掉当前分支
                iterator.killBranch(this, current);
                return true;
            case GraphWalker.STOP:
            case GraphWalker.STOP_AND_COLLECT_TRACE:
                // 停止追踪
                return false;
            default:
                throw new IllegalStateException("Unrecognized return value from GraphWalker");
        }
    }

    protected boolean isCollectTrace(int status, Map<T, GraphPath<T>> traces) {
        if (traces == null) {
            return false;
        }
        return (status & GraphWalker.COLLECT_TRACE) == GraphWalker.COLLECT_TRACE;
    }

    protected abstract Map<T, Boolean> buildMap();

}