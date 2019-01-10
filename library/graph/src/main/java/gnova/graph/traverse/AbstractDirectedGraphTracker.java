package gnova.graph.traverse;

import gnova.graph.structure.DirectedGraphable;
import gnova.graph.util.GraphPath;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractDirectedGraphTracker<T extends DirectedGraphable>
        extends AbstractGraphTracker<T> implements DirectedGraphTracker<T> {

    @Override
    public void positiveTrack(T source,
                              GraphIterator<T> iterator,
                              DirectedGraphWalker<T> walker,
                              Map<T, GraphPath<T>> traces) {

        Objects.requireNonNull(source);

        // 初始化迭代器
        iterator.initialize(source);

        // 初始化一些变量
        T current = null; // 当前访问的单元
        Collection<T> nexts = new ArrayList<T>();

        // 开始进行追踪
        while ((current = iterator.next(this)) != null) {
            nexts.clear();
            if (!doPositiveTrack(current, nexts, iterator, walker, traces)) {
                return;
            }
        }
    }

    @Override
    public void negativeTrack(T source,
                              GraphIterator<T> iterator,
                              DirectedGraphWalker<T> walker,
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
            if (!doNegativeTrack(current, nexts, iterator, walker, traces)) {
                return;
            }
        }
    }

    protected boolean doPositiveTrack(T current,
                                      Collection<T> nexts,
                                      GraphIterator<T> iterator,
                                      GraphWalker<T> walker,
                                      final Map<T, GraphPath<T>> traces) {

        // 将当前单元添加进轨迹的集合
        iterator.addToPath(current, current.getInRelated());

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
                    iterator.process(this, current, current.getOutRelated());
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
                throw new IllegalStateException("Unrecognized return build from GraphWalker");
        }
    }

    protected boolean doNegativeTrack(T current,
                                      Collection<T> nexts,
                                      GraphIterator<T> iterator,
                                      GraphWalker<T> walker,
                                      final Map<T, GraphPath<T>> traces) {

        // 将当前单元添加进轨迹的集合
        iterator.addToPath(current, current.getOutRelated());

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
                    iterator.process(this, current, current.getInRelated());
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
                throw new IllegalStateException("Unrecognized return build from GraphWalker");
        }
    }

}
