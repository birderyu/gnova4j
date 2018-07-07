package gnova.graph.traverse;

import java.util.Map;
import java.util.Objects;

import gnova.graph.structure.Graphable;
import gnova.graph.util.GraphPath;

/**
 * 图追踪者
 * 图追踪者从一个或多个图单元出发，按照一定的方向进行追踪，并将当前追踪的轨迹保留下来
 * 
 * @author birderyu
 *
 */
public interface GraphTracker<T extends Graphable> {

    /**
     * 判断一个单元是否被访问过
     *
     * @param component
     * @return
     */
    boolean isWalked(T component);
	
	/**
	 * 追踪之前调用此方法
	 */
	void beforeTrack();

    /**
     * 单源追踪
     *
     * @param source
     * @param iterator
     * @param walker
     */
	default void track(T source,
                       GraphIterator<T> iterator,
                       GraphWalker<T> walker) {
		track(source, iterator, walker, null);
	}

	/**
	 * 单源追踪并收集轨迹
	 * 
	 * @param source
	 * @param traces
	 */
	void track(T source,
			   GraphIterator<T> iterator,
               GraphWalker<T> walker,
               Map<T, GraphPath<T>> traces);
	
	/**
	 * 多源追踪
	 * 
	 * @param sources
	 */
	default void track(T[] sources,
                       GraphIterator<T> iterator,
                       GraphWalker<T> walker) {
        track(sources, iterator, walker, null);
    }
	
	/**
	 * 多源追踪并收集轨迹
	 * 
	 * @param sources
	 * @param traces
	 */
	default void track(T[] sources,
                       GraphIterator<T> iterator,
                       GraphWalker<T> walker,
                       Map<T, GraphPath<T>> traces) {
        Objects.requireNonNull(sources);
        for (T source : sources) {
            track(source, iterator, walker, traces);
        }
    }

}
