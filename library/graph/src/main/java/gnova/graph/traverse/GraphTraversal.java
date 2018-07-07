package gnova.graph.traverse;

import java.util.Map;
import java.util.function.Predicate;

import gnova.graph.structure.Graph;
import gnova.graph.structure.Graphable;
import gnova.graph.util.GraphPath;

/**
 * 图遍历器
 * 
 * @author birderyu
 *
 */
public interface GraphTraversal<T extends Graphable> {

    Graph getGraph();

	/**
	 * 遍历
	 */
	default void traverse(GraphTracker<T> tracker,
				  GraphIterator<T> iterator,
				  GraphWalker<T> walker) {
		traverse(tracker, iterator, walker, null);
	}
	
	/**
	 * 遍历并收集轨迹
	 */
	default void traverse(GraphTracker<T> tracker,
                  GraphIterator<T> iterator,
                  GraphWalker<T> walker,
                  Map<T, GraphPath<T>> traces) {
        tracker.beforeTrack();
        getGraph().visit((Predicate<T>) component -> {
			if (!tracker.isWalked(component)) {
				// 若当前单元还未被访问过，则以该单元作为起点，进行追踪
				tracker.track(component, iterator, walker, traces);
			}
			return true;
		});
    }

}
