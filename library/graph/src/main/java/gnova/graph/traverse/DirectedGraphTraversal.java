package gnova.graph.traverse;

import gnova.graph.structure.DirectedGraph;
import gnova.graph.structure.DirectedGraphable;
import gnova.graph.util.GraphPath;

import java.util.Map;
import java.util.function.Predicate;

public interface DirectedGraphTraversal<T extends DirectedGraphable>
        extends GraphTraversal<T> {

    @Override
    DirectedGraph getGraph();

    /**
     * 正向遍历
     *
     * @param tracker
     * @param iterator
     * @param walker
     */
    default void positiveTraverse(DirectedGraphTracker<T> tracker,
                                  GraphIterator<T> iterator,
                                  DirectedGraphWalker<T> walker) {
        positiveTraverse(tracker, iterator, walker, null);
    }

    /**
     * 正向遍历并收集轨迹
     *
     * @param tracker
     * @param iterator
     * @param walker
     * @param traces
     */
    default void positiveTraverse(DirectedGraphTracker<T> tracker,
                          GraphIterator<T> iterator,
                          DirectedGraphWalker<T> walker,
                          Map<T, GraphPath<T>> traces) {
        tracker.beforeTrack();
        getGraph().visit((Predicate<T>) component -> {
            if (!tracker.isWalked(component)) {
                // 若当前单元还未被访问过，则以该单元作为起点，进行追踪
                tracker.positiveTrack(component, iterator, walker, traces);
            }
            return true;
        });
    }

    /**
     * 逆向遍历
     *
     * @param tracker
     * @param iterator
     * @param walker
     */
    default void negativeTraverse(DirectedGraphTracker<T> tracker,
                          GraphIterator<T> iterator,
                          DirectedGraphWalker<T> walker) {
        negativeTraverse(tracker, iterator, walker, null);
    }

    /**
     * 逆向遍历并收集轨迹
     */
    default void negativeTraverse(DirectedGraphTracker<T> tracker,
                          GraphIterator<T> iterator,
                          DirectedGraphWalker<T> walker,
                          Map<T, GraphPath<T>> traces) {
        tracker.beforeTrack();
        getGraph().visit((Predicate<T>) component -> {
            if (!tracker.isWalked(component)) {
                // 若当前单元还未被访问过，则以该单元作为起点，进行追踪
                tracker.negativeTrack(component, iterator, walker, traces);
            }
            return true;
        });
    }

}
